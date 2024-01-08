package org.example.pages.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.example.pages.PageType;


import static com.codeborne.selenide.Selenide.*;

public class PublicPostPO extends AbstractPage {

    public PublicPostPO(PageType pageType) {
        super(pageType);
    }

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

}
