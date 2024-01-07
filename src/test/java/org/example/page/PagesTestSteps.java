package org.example.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.PageFactory;
import org.example.pages.PageType;
import org.example.pages.pageobject.CreatePostPO;
import org.example.pages.pageobject.tablepage.PostsPO;
import org.example.pages.pageobject.tablerow.PostRow;
import org.example.users.User;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;

public class PagesTestSteps {
    private final PostsPO postsPage = PageFactory.getPage(PageType.PAGES, PostsPO.class);
    private final CreatePostPO createPostPage = PageFactory.getPage(PageType.NEW_PAGE, CreatePostPO.class);
    private final Logger logger = Logger.getLogger(PagesTestSteps.class);

    public void openPagesPage(){
        postsPage.openPage();
    }
    public void closePage(){
        postsPage.close();
    }
    public void goToNewPostPage(){
        postsPage.clickAndRedirectTo(postsPage.getAddNewBttn(), createPostPage.getPageType().getUrl());
    }
    public SelenideElement getPostDraftRowByTitleAndUser(String postHeader, String userLogin) {
        logger.log(Level.INFO, "trying to fetch draft posts by title"+postHeader+" and user "+ userLogin);
        return postsPage.getDraftRows()
                .filter(Condition.matchText(postHeader + " — Draft\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getPostPublishedRowByTitleAndUser(String postHeader, String userLogin) {
        logger.log(Level.INFO, "trying to fetch published posts by title"+postHeader+" and user "+ userLogin);
        return postsPage.getPublishedRows()
                .filter(Condition.matchText(postHeader + "\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getPostTrashRowByTitleAndUser(String postHeader, String userLogin) {
        logger.log(Level.INFO, "trying to fetch trash posts by title"+postHeader+" and user "+ userLogin);
        return postsPage.getTrashRows()
                .filter(Condition.matchText(postHeader + "\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getTableRowById(int id) {
        logger.log(Level.INFO, "trying to get post by id "+id);
        return postsPage.getTableRows()
                .filter(Condition.id(String.format("post-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public PostRow getRowAsObject(SelenideElement trRoot) {
        logger.log(Level.INFO, "trying to get post "+trRoot.getAttribute("id") + " as row object");
        var rowObject = new PostRow(trRoot);
        rowObject.init();
        return rowObject;
    }

    public void openEditExistingDraftPost(int draftId) {
        logger.log(Level.INFO, "trying to publish draft post by id "+draftId);
        postsPage.clickAndRedirectTo(postsPage.getDraftRows()
                .filter(Condition.id(String.format("post-%d", draftId)))
                .first().$x(".//a[@class='row-title']"), String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post.php?post=%d&action=edit", draftId));
    }

    public void openEditExistingPublishedPost(int publishedId) {
        logger.log(Level.INFO, "trying to revert published post to draft by id "+publishedId);
        postsPage.clickAndRedirectTo(postsPage.getPublishedRows()
                .filter(Condition.id(String.format("post-%d", publishedId)))
                .first().$x(".//a[@class='row-title']"), String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post.php?post=%d&action=edit", publishedId));
    }


    public void goToMine(User user) {
        logger.log(Level.INFO, "opening 'Mine' section");
        postsPage.getFilterRowOptionsRoot()
                .$x("./li[@class='mine']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                 postsPage.getPageType() == PageType.PAGES ?
                        String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_type=page&author=%d", user.getUserId())
                        : String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_type=post&author=%d", user.getUserId())
        ));
    }

    public void goToTrash() {
        logger.log(Level.INFO, "opening 'Trash' section");
        postsPage.getFilterRowOptionsRoot()
                .$x("./li[@class='trash']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                postsPage.getPageType() == PageType.PAGES ?
                        "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_status=trash&post_type=page"
                        : "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_status=trash&post_type=post"
        ));
    }

    public void moveCheckedToTrash() {
        logger.log(Level.INFO, "performing move checked rows to trash");
        postsPage.getBulkActionsDropdown().selectOptionByValue("trash");
        postsPage.click(postsPage.getBulkActionsApplyBttn());
        postsPage.click(postsPage.getConfirmationPopupWindow().$x("./button"));
    }

    public void deleteChecked() {
        logger.log(Level.INFO, "performing delete checked rows");
        postsPage.getBulkActionsDropdown().selectOptionByValue("delete");
        postsPage.click(postsPage.getBulkActionsApplyBttn());
    }


    public void fillPostContent(String textHeader, String textBody) {
        logger.log(Level.INFO, "send keys to post header "+textHeader + " and body "+textBody);
        switchTo().frame("editor-canvas");
        createPostPage.getPostHeaderInputField().sendKeys(textHeader);
        createPostPage.getPostBodyInputField().sendKeys(textBody);
        switchTo().defaultContent();
    }

    public void waitForSuccessPopup() {
        logger.log(Level.INFO, "waiting for success popup");
        Selenide.Wait().withTimeout(Duration.ofSeconds(100)).until(webDriver -> $x("//div[@class='components-snackbar__content']").is(Condition.visible));
    }

    public int saveDraftPost(String postHeader, String postBody) {
        logger.log(Level.INFO, "performing save post as draft");
        fillPostContent(postHeader, postBody);
        createPostPage.click(createPostPage.getSaveDraftBttn());
        waitForSuccessPopup();
        return createPostPage.getNewPostIdAfterDraft();
    }

    public int saveDraftPostAndBack(String postHeader, String postBody) {
        logger.log(Level.INFO, "performing save post as draft and back");
        fillPostContent(postHeader, postBody);
        createPostPage.click(createPostPage.getSaveDraftBttn());
        waitForSuccessPopup();
        int id = createPostPage.getNewPostIdAfterDraft();
        createPostPage.openPageWithWaiter(createPostPage.getPageType() == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
        return id;
    }

    public void publishDraftFromEditScreen() {
        logger.log(Level.INFO, "performing publish draft post");
        createPostPage.click(createPostPage.getPublishBttn());
        createPostPage.click(createPostPage.getEditorPublishBttn());
        createPostPage.getPostPublishHeader();
        createPostPage.openPageWithWaiter(createPostPage.getPageType() == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
    }

    public int createNewPublishedPost(String postHeader, String postBody) {
        logger.log(Level.INFO, "performing crate new published post");
        int newPostId = saveDraftPost(postHeader, postBody);
        publishDraftFromEditScreen();
        createPostPage.openPageWithWaiter(createPostPage.getPageType() == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
        return newPostId;
    }

    public void switchPublishedPostToDraft() {
        logger.log(Level.INFO, "performing switch published post to draft");
        if(!Boolean.getBoolean(createPostPage.getSettingsBttn().getAttribute("aria-expanded"))){
            createPostPage.click(createPostPage.getSettingsBttn());
        }
        createPostPage.click(createPostPage.getToDraftBttn());
        createPostPage.click(createPostPage.getAreUSurePopupOkButton());
        waitForSuccessPopup();
        createPostPage.openPageWithWaiter(createPostPage.getPageType() == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());

    }
    public boolean isVisibleOnPostsPage(SelenideElement selenideElement){
        return postsPage.isVisible(selenideElement);
    }

}
