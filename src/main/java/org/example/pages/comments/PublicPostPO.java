package org.example.pages.comments;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.TimeoutException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.Page;
import org.example.pages.PageException;
import org.example.pages.PageType;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    public CommentObject publishComment(String comment, String author, String email, String url) {
        logger.log(Level.INFO, "Starting publish new comment\n text- "+comment+"Author - "+author+"E-mail - "+email+"Site- "+url);
        CommentObject commentObj = new CommentObject(comment, author, email, url);
        openPage();
        getCommentBody().sendKeys(commentObj.getComment());
        getAuthorField().sendKeys(commentObj.getAuthor());
        getEmailField().sendKeys(commentObj.getEmail());
        getSiteField().sendKeys(commentObj.getUrl());
        click(getPublishBttn());
        commentObj.setId(Integer.parseInt(getCommentPreview().$x("./li").getAttribute("id").split("-")[1]));
        return commentObj;
    }

    public CommentObject publishComment() throws PageException {
        String ts = String.valueOf(Instant.now().getEpochSecond());
        CommentObject commentObj = new CommentObject("testCommentBody " + ts, "testAnonymous " + ts, "anonymos@mail.com", "anonymous.com");
        logger.log(Level.INFO, "Starting publish new comment\n"+commentObj);
        openPage();
        getCommentBody().sendKeys(commentObj.getComment());
        getAuthorField().sendKeys(commentObj.getAuthor());
        getEmailField().sendKeys(commentObj.getEmail());
        getSiteField().sendKeys(commentObj.getUrl());
        click(getPublishBttn());
        try {
            getCommentPreview();
        } catch (ElementNotFound | RuntimeException e){
            if(getErrorPage().isDisplayed()) {
                click(getErrorPage().$x(".//a"));
                throw new PageException("Too quick commenting");
            }
        }
        commentObj.setId(Integer.parseInt(getCommentPreview().$x("./li").getAttribute("id").split("-")[1]));
        return commentObj;
    }

    public SelenideElement getErrorPage(){
        return $x("//*[@id='error-page']");
    }

    public List<CommentObject> publishCommentXTimes(int x){
        List<CommentObject> result = new ArrayList<>();
        for (int i = 0; i < x; ) {
            openPage();
            try {
                result.add(publishComment());
            } catch (PageException e) {
                continue;
            }
            i++;
        }
        return result;
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
