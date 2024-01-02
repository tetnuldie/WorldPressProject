package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.Page;
import org.example.pages.PageType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class CreatePostPO implements Page {

    private final PageType pageType;
    private final Logger logger = Logger.getLogger(CreatePostPO.class);

    public CreatePostPO(PageType pageType) {
        this.pageType = pageType;
    }

    public SelenideElement getPostHeaderInputField() {
        logger.log(Level.INFO, "trying to get header input field");
        return $x("/html/body//h1[@role='textbox']").shouldBe(Condition.visible);
    }

    public SelenideElement getPostBodyInputField() {
        logger.log(Level.INFO, "trying to get body input field");
        return $x("/html/body//p").shouldBe(Condition.visible);
    }

    public SelenideElement getSaveDraftBttn() {
        logger.log(Level.INFO, "trying to get 'Save Draft' bttn");
        return $x("//div[@class='edit-post-header__settings']/button[contains(@class, 'is-tertiary')]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("aria-disabled", "false"));
    }

    public SelenideElement getPublishBttn() {
        logger.log(Level.INFO, "trying to get 'Publish' bttn");
        return $x("//div[@class='edit-post-header__settings']/button[contains(@class, 'editor-post-publish-button__button')]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("aria-disabled", "false"));
    }

    public SelenideElement getEditorPublishForm() {
        logger.log(Level.INFO, "trying to get post-publish form");
        return $x("//div[@class='editor-post-publish-panel']")
                .shouldBe(Condition.visible);
    }

    public SelenideElement getEditorPublishBttn() {
        logger.log(Level.INFO, "trying to get 'Publish' bttn on post-publish form");
        return getEditorPublishForm().$x(".//button[contains(@class, 'is-primary')]")
                .shouldBe(Condition.visible);
    }

    public SelenideElement getPostPublishHeader() {
        logger.log(Level.INFO, "trying to get post-publish panel header");
        return $x("//div[@class='post-publish-panel__postpublish']/div[1]")
                .shouldBe(Condition.visible);
    }

    public int getNewPostIdAfterDraft() {
        logger.log(Level.INFO, "trying to get new created post id from page url");
        return Integer.parseInt(WebDriverRunner.url().split("=")[1].split("&")[0]);
    }

    public void fillPostContent(String textHeader, String textBody) {
        logger.log(Level.INFO, "send keys to post header "+textHeader + " and body "+textBody);
        switchTo().frame("editor-canvas");
        getPostHeaderInputField().sendKeys(textHeader);
        getPostBodyInputField().sendKeys(textBody);
        switchTo().defaultContent();
    }

    public void waitForSuccessPopup() {
        logger.log(Level.INFO, "waiting for success popup");
        Selenide.Wait().withTimeout(Duration.ofSeconds(100)).until(webDriver -> $x("//div[@class='components-snackbar__content']").is(Condition.visible));
    }

    public SelenideElement getToDraftBttn() {
        logger.log(Level.INFO, "trying to get 'Switch to draft' bttn");
        return $x("//div[@class='components-panel']//button[contains(@class, 'switch-to-draft')]").shouldBe(Condition.visible);
    }

    public SelenideElement getAreUSurePopupOkButton() {
        logger.log(Level.INFO, "trying to get 'Are you shure' popup' 'OK' bttn");
        return $x("//div[@class='components-modal__content hide-header']")
                .should(Condition.appear)
                .$x(".//button[contains(@class, 'is-primary')]")
                .shouldBe(Condition.visible);
    }

    public int saveDraftPost(String postHeader, String postBody) {
        logger.log(Level.INFO, "performing save post as draft");
        fillPostContent(postHeader, postBody);
        click(getSaveDraftBttn());
        waitForSuccessPopup();
        return getNewPostIdAfterDraft();
    }

    public int saveDraftPostAndBack(String postHeader, String postBody) {
        logger.log(Level.INFO, "performing save post as draft and back");
        fillPostContent(postHeader, postBody);
        click(getSaveDraftBttn());
        waitForSuccessPopup();
        int id = getNewPostIdAfterDraft();
        openPageWithWaiter(pageType == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
        return id;
    }

    public void publishDraftFromEditScreen() {
        logger.log(Level.INFO, "performing publish draft post");
        click(getPublishBttn());
        click(getEditorPublishBttn());
        getPostPublishHeader();
        openPageWithWaiter(pageType == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
    }

    public int createNewPublishedPost(String postHeader, String postBody) {
        logger.log(Level.INFO, "performing crate new published post");
        int newPostId = saveDraftPost(postHeader, postBody);
        publishDraftFromEditScreen();
        openPageWithWaiter(pageType == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
        return newPostId;
    }

    public void switchPublishedPostToDraft() {
        logger.log(Level.INFO, "performing switch published post to draft");
        click(getToDraftBttn());
        click(getAreUSurePopupOkButton());
        waitForSuccessPopup();
        openPageWithWaiter(pageType == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());

    }

    @Override
    public void hover(SelenideElement element) {
        logger.log(Level.INFO, "hovering on "+element.getSearchCriteria());
        element.shouldBe(Condition.visible).hover();
    }

    @Override
    public void click(SelenideElement element) {
        logger.log(Level.INFO, "clicking on "+element.getSearchCriteria());
        element.shouldBe(Condition.visible).click();
    }

    @Override
    public boolean isVisible(SelenideElement element) {
        logger.log(Level.INFO, "performing visibility check of "+element.getSearchCriteria());
        return element.shouldBe(Condition.visible).isDisplayed();
    }

    @Override
    public void openPage() {
        logger.log(Level.INFO, "opening page '"+pageType.getUrl()+"'");
        open(pageType.getUrl());
    }

    @Override
    public void openPageWithWaiter(String url) {
        logger.log(Level.INFO, "opening page "+url+" with loading waiter");
        open(url);
        Selenide.Wait().until(ExpectedConditions.urlToBe(url));
    }

    @Override
    public void clickAndRedirectTo(SelenideElement element, String expectedUrl) {
        logger.log(Level.INFO, "redirecting to "+expectedUrl+" after click on "+element.getSearchCriteria());
        element.click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(expectedUrl));
    }

    public void close() {
        logger.log(Level.INFO, "closing browser");
        webdriver().driver().getWebDriver().quit();
    }


}
