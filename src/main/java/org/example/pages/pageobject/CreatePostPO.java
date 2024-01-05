package org.example.pages.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Level;
import org.example.pages.pageobject.AbstractPage;
import org.example.pages.PageType;

import static com.codeborne.selenide.Selenide.*;

public class CreatePostPO extends AbstractPage {
    public CreatePostPO(PageType pageType) {
        super(pageType);
    }
    public PageType getPageType() {
        return pageType;
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

    public SelenideElement getSettingsBttn() {
        logger.log(Level.INFO, "trying to get 'Settings' bttn");
        return $x("//div[@class='edit-post-header__settings']//button[@class='components-button has-icon']").shouldBe(Condition.visible);
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

}
