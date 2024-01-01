package org.example.pages.comments;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.pages.Page;
import org.example.pages.PageType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Instant;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class PublicPostPO implements Page {
    private final PageType pageType = PageType.PUBLIC_POST;

    public SelenideElement getLeaveCommentForm() {
        return $x("//div[@class='wp-block-post-comments']")
                .shouldBe(Condition.visible);
    }

    public SelenideElement getCommentBody() {
        return getLeaveCommentForm().$x(".//*[@id='comment']");
    }

    public SelenideElement getAuthorField() {
        return getLeaveCommentForm().$x(".//input[@id='author']");
    }

    public SelenideElement getEmailField() {
        return getLeaveCommentForm().$x(".//input[@id='email']");
    }

    public SelenideElement getSiteField() {
        return getLeaveCommentForm().$x(".//input[@id='url']");
    }

    public SelenideElement getPublishBttn() {
        return getLeaveCommentForm().$x(".//input[@id='submit']");
    }

    public SelenideElement getCommentPreview() {
        return $x("//ol[@class='commentlist']").shouldBe(Condition.visible);
    }

    public String getCommentPreviewMessage() {
        return $x("//em [@class='comment-awaiting-moderation']").getText();
    }

    public CommentObject publishComment(String comment, String author, String email, String url) {
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

    public CommentObject publishComment() {
        String ts = String.valueOf(Instant.now().getEpochSecond());
        CommentObject commentObj = new CommentObject("testCommentBody " + ts, "testAnonymous " + ts, "anonymos@mail.com", "anonymous.com");
        openPage();
        getCommentBody().sendKeys(commentObj.getComment());
        getAuthorField().sendKeys(commentObj.getAuthor());
        getEmailField().sendKeys(commentObj.getEmail());
        getSiteField().sendKeys(commentObj.getUrl());
        click(getPublishBttn());
        commentObj.setId(Integer.parseInt(getCommentPreview().$x("./li").getAttribute("id").split("-")[1]));
        return commentObj;
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
