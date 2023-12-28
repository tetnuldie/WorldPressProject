package org.example.pages.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.pages.Page;
import org.example.pages.PageMenuFunc;
import org.example.pages.PageType;
import org.example.users.User;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;

public class PostsPO implements PageMenuFunc, Page {
    private final PageType pageType;

    public PostsPO(PageType pageType) {
        this.pageType = pageType;
    }

    public SelenideElement getTablePageRoot() {
        return $x("//div[@class='wrap']").shouldBe(Condition.visible);
    }

    public SelenideElement getAddNewBttn() {
        return getTablePageRoot().$x("./a[@class='page-title-action']").shouldBe(Condition.visible);
    }

    public SelenideElement getFilterRowOptionsRoot() {
        return getTablePageRoot().$x("./ul[@class='subsubsub']").shouldBe(Condition.visible);
    }

    public SelenideElement getContentTableRoot() {
        return getTablePageRoot().$x("./form[@id='posts-filter']/table").shouldBe(Condition.visible);
    }

    public ElementsCollection getTableRows() {
        return getContentTableRoot().$$x("./tbody/tr");
    }

    public ElementsCollection getDraftRows() {
        return getTableRows().filter(Condition.cssClass("status-draft"));
    }

    public ElementsCollection getPublishedRows() {
        return getTableRows().filter(Condition.cssClass("status-publish"));
    }

    public ElementsCollection getTrashRows() {
        return getTableRows().filter(Condition.cssClass("status-publish"));
    }

    public SelenideElement getPostDraftRowByTitleAndUser(String postHeader, String userLogin) {
        return getDraftRows()
                .filter(Condition.matchText(postHeader + " — Draft\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getPostPublishedRowByTitleAndUser(String postHeader, String userLogin) {
        return getPublishedRows()
                .filter(Condition.matchText(postHeader + "\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getPostTrashRowByTitleAndUser(String postHeader, String userLogin) {
        return getTrashRows()
                .filter(Condition.matchText(postHeader + "\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getPostRowById(int id) {
        return getTableRows()
                .filter(Condition.id(String.format("post-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public TdObject getPostAsObject(SelenideElement trRoot) {
        return new TdObject(trRoot);
    }

    public SelenideElement getBulkActionsDropdown() {
        return $x("//div[@class='tablenav top']//select[@id='bulk-action-selector-top']").shouldBe(Condition.visible);
    }

    public SelenideElement getBulkActionsApplyBttn() {
        return $x("//div[@class='tablenav top']//input[@id='doaction']").shouldBe(Condition.visible);
    }

    public SelenideElement getConfirmationPopupWindow() {
        return getTablePageRoot().$x(".//div[@id='message']").shouldBe(Condition.visible);
    }

    public void openEditExistingDraftPost(int draftId) {
        clickAndRedirectTo(getDraftRows()
                .filter(Condition.id(String.format("post-%d", draftId)))
                .first().$x(".//a[@class='row-title']"), String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post.php?post=%d&action=edit", draftId));
    }

    public void openEditExistingPublishedPost(int publishedId) {
        clickAndRedirectTo(getPublishedRows()
                .filter(Condition.id(String.format("post-%d", publishedId)))
                .first().$x(".//a[@class='row-title']"), String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post.php?post=%d&action=edit", publishedId));
    }


    public void goToMinePosts(User user) {
        getFilterRowOptionsRoot()
                .$x("./li[@class='mine']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                this.pageType == PageType.PAGES ?
                        String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_type=page&author=%d", user.getUserId())
                        : String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_type=post&author=%d", user.getUserId())
        ));
    }

    public void moveCheckedPostToTrash() {
        getBulkActionsDropdown().selectOptionByValue("trash");
        click(getBulkActionsApplyBttn());
        click(getConfirmationPopupWindow().$x("./button"));
    }

    public void deleteCheckedPost() {
        getBulkActionsDropdown().selectOptionByValue("delete");
        click(getBulkActionsApplyBttn());
    }

    public void goToTrash() {
        getFilterRowOptionsRoot()
                .$x("./li[@class='trash']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                this.pageType == PageType.PAGES ?
                        "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_status=trash&post_type=page"
                        : "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_status=trash&post_type=post"
        ));
    }

    @Override
    public void openPage() {
        open(pageType.getUrl());
    }

    @Override
    public void hover(SelenideElement element) {
        element.shouldBe(Condition.visible).hover();
    }

    @Override
    public void click(SelenideElement element) {
        element.shouldBe(Condition.visible).click();
    }

    @Override
    public boolean isVisible(SelenideElement element) {
        return element.isDisplayed();
    }

    @Override
    public void openPageWithWaiter(String url) {
        open(url);
        Selenide.Wait().until(ExpectedConditions.urlToBe(url));
    }

    @Override
    public void clickAndRedirectTo(SelenideElement element, String expectedUrl) {
        element.click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(expectedUrl));
    }
}
