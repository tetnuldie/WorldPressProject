package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.example.pages.Page;
import org.example.pages.PageType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;

public class CreatePostPO implements Page {

    private final PageType pageType;

    public CreatePostPO(PageType pageType) {
        this.pageType = pageType;
    }

    public SelenideElement getPostHeaderInputField() {
        return $x("/html/body//h1[@role='textbox']").shouldBe(Condition.visible);
    }

    public SelenideElement getPostBodyInputField() {
        return $x("/html/body//p").shouldBe(Condition.visible);
    }

    public SelenideElement getSaveDraftBttn() {
        return $x("//div[@class='edit-post-header__settings']/button[contains(@class, 'is-tertiary')]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("aria-disabled", "false"));
    }

    public SelenideElement getPublishBttn() {
        return $x("//div[@class='edit-post-header__settings']/button[contains(@class, 'editor-post-publish-button__button')]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("aria-disabled", "false"));
    }

    public SelenideElement getEditorPublishForm() {
        return $x("//div[@class='editor-post-publish-panel']")
                .shouldBe(Condition.visible);
    }

    public SelenideElement getEditorPublishBttn() {
        return getEditorPublishForm().$x(".//button[contains(@class, 'is-primary')]")
                .shouldBe(Condition.visible);
    }

    public SelenideElement getPostPublishHeader() {
        return $x("//div[@class='post-publish-panel__postpublish']/div[1]")
                .shouldBe(Condition.visible);
    }

    public int getNewPostIdAfterDraft() {
        return Integer.parseInt(WebDriverRunner.url().split("=")[1].split("&")[0]);
    }

    public void fillPostContent(String textHeader, String textBody) {
        switchTo().frame("editor-canvas");
        getPostHeaderInputField().sendKeys(textHeader);
        getPostBodyInputField().sendKeys(textBody);
        switchTo().defaultContent();
    }

    public SelenideElement getSuccessPopup() {
        return $x("//div[@class='components-snackbar__content']")
                .should(Condition.appear);
    }

    public SelenideElement getToDraftBttn() {
        return $x("//div[@class='components-panel']//button[contains(@class, 'switch-to-draft')]").shouldBe(Condition.visible);
    }

    public SelenideElement getAreUSurePopupOkButton() {
        return $x("//div[@class='components-modal__content hide-header']")
                .should(Condition.appear)
                .$x(".//button[contains(@class, 'is-primary')]")
                .shouldBe(Condition.visible);
    }

    public int saveDraftPost(String postHeader, String postBody) {
        fillPostContent(postHeader, postBody);
        click(getSaveDraftBttn());
        getSuccessPopup();
        return getNewPostIdAfterDraft();
    }

    public int saveDraftPostAndBack(String postHeader, String postBody) {
        fillPostContent(postHeader, postBody);
        click(getSaveDraftBttn());
        getSuccessPopup();
        int id = getNewPostIdAfterDraft();
        openPageWithWaiter(pageType == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
        return id;
    }

    public void publishDraftFromEditScreen() {
        click(getPublishBttn());
        click(getEditorPublishBttn());
        getPostPublishHeader();
        openPageWithWaiter(pageType == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
    }

    public int createNewPublishedPost(String postHeader, String postBody) {
        int newPostId = saveDraftPost(postHeader, postBody);
        publishDraftFromEditScreen();
        openPageWithWaiter(pageType == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());
        return newPostId;
    }

    public void switchPublishedPostToDraft() {
        click(getToDraftBttn());
        click(getAreUSurePopupOkButton());
        getSuccessPopup();
        openPageWithWaiter(pageType == PageType.NEW_POST ? PageType.POSTS.getUrl() : PageType.PAGES.getUrl());

    }

    @Override
    public void openPage() {
        open(pageType.getUrl());
    }

    @Override
    public void hover(SelenideElement element) {
        element.hover();
    }

    @Override
    public void click(SelenideElement element) {
        element.click();
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
