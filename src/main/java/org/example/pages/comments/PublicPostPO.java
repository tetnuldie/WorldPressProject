package org.example.pages.comments;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.Page;
import org.example.pages.PageType;
import org.openqa.selenium.support.ui.ExpectedConditions;


import static com.codeborne.selenide.Selenide.*;

public class PublicPostPO implements Page {
    private final PageType pageType = PageType.PUBLIC_POST;
    protected final Logger logger = Logger.getLogger(PublicPostPO.class);

    public SelenideElement getLeaveCommentForm() {
        logger.log(Level.INFO, "Trying to get 'Leave Comment' form");
        return $x("//div[@class='wp-block-post-comments']")
                .shouldBe(Condition.visible);
    }

    public SelenideElement getCommentBody() {
        logger.log(Level.INFO, "Trying to get comment body element");
        return getLeaveCommentForm().$x(".//*[@id='comment']");
    }

    public SelenideElement getAuthorField() {
        logger.log(Level.INFO, "Trying to get comment 'Author' field");
        return getLeaveCommentForm().$x(".//input[@id='author']");
    }

    public SelenideElement getEmailField() {
        logger.log(Level.INFO, "Trying to get comment 'E-mail' field");
        return getLeaveCommentForm().$x(".//input[@id='email']");
    }

    public SelenideElement getSiteField() {
        logger.log(Level.INFO, "Trying to get comment 'Site' field");
        return getLeaveCommentForm().$x(".//input[@id='url']");
    }

    public SelenideElement getPublishBttn() {
        logger.log(Level.INFO, "Trying to get comment 'Publish' bttn");
        return getLeaveCommentForm().$x(".//input[@id='submit']");
    }

    public SelenideElement getCommentPreview() {
        logger.log(Level.INFO, "Trying to get comment preview element");
        return $x("//ol[@class='commentlist']").shouldBe(Condition.visible);
    }

    public String getCommentPreviewMessage() {
        logger.log(Level.INFO, "Trying to get comment preview element message");
        return $x("//em [@class='comment-awaiting-moderation']").getText();
    }

    public SelenideElement getErrorPage(){
        return $x("//*[@id='error-page']");
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