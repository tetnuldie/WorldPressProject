package org.example.comments;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.login.UserLoginSteps;
import org.example.pages.LoginPO;
import org.example.pages.PageException;
import org.example.pages.PageFactory;
import org.example.pages.PageType;
import org.example.pages.comments.CommentObject;
import org.example.pages.comments.PublicPostPO;
import org.example.pages.table.CommentsPO;
import org.example.pages.table.tablerow.CommentRow;
import org.example.users.User;
import org.example.users.UserFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CommentsTestSteps {
    protected final LoginPO loginPage = PageFactory.getPage(PageType.LOGIN, LoginPO.class);
    protected final CommentsPO commentsPage = PageFactory.getPage(PageType.COMMENTS, CommentsPO.class);
    protected final PublicPostPO publicPostPage = PageFactory.getPage(PageType.PUBLIC_POST, PublicPostPO.class);
    private final Logger logger = Logger.getLogger(CommentsTestSteps.class);

    public void openCommentsPage() {
        commentsPage.openPage();
    }

    public void openPublicPostPage() {
        publicPostPage.openPage();
    }

    public void closeCommentsPage() {
        commentsPage.close();
    }

    public void login(User user) {
        UserLoginSteps.userLoginWoRemember(user);
    }

    public CommentRow getRowAsObject(SelenideElement trRoot) {
        logger.log(Level.INFO, "trying to get post " + trRoot.getAttribute("id") + " as row object");
        var rowObject = new CommentRow(trRoot);
        rowObject.init();
        return rowObject;
    }

    public SelenideElement getTableRowById(int id) {
        logger.log(Level.INFO, "trying to get comment row by id "+id);
        return commentsPage.getTableRows()
                .filter(Condition.id(String.format("comment-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public void moveCheckedToTrash() {
        logger.log(Level.INFO, "performing move checked rows to trash");
        commentsPage.getBulkActionsDropdown().selectOptionByValue("trash");
        commentsPage.click(commentsPage.getBulkActionsApplyBttn());
        commentsPage.click(commentsPage.getConfirmationPopupWindow().$x("./button"));
    }

    public void deleteChecked() {
        logger.log(Level.INFO, "performing delete checked rows");
        commentsPage.getBulkActionsDropdown().selectOptionByValue("delete");
        commentsPage.click(commentsPage.getBulkActionsApplyBttn());
    }

    public void goToMine(User user) {
        logger.log(Level.INFO, "opening 'Mine' section");
        commentsPage.getFilterRowOptionsRoot()
                .$x("./li[@class='mine']").shouldBe(Condition.visible)
                .click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(
                String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=mine&user_id=%d", user.getUserId())
        ));
    }

    public void goToTrash() {
        logger.log(Level.INFO, "opening 'Trash' section");
        commentsPage.getFilterRowOptionsRoot()
                .$x("./li[@class='trash']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=trash"
        ));
    }

    public void quickEdit(String text) {
        logger.log(Level.INFO, "performing comment quick edit");
        commentsPage.getQuickEditRow().shouldBe(Condition.visible);
        commentsPage.getQuickEditRow().$x(".//textarea[@id = 'replycontent']").clear();
        commentsPage.getQuickEditRow().$x(".//textarea[@id = 'replycontent']").sendKeys(text);
        commentsPage.getQuickEditRow().$x(".//button[@class='save button button-primary']").click();
        Selenide.Wait().until(ExpectedConditions.invisibilityOf(commentsPage.getQuickEditRow()));
    }

    public void spamChecked() {
        logger.log(Level.INFO, "performing mark selected rows as spam");
        commentsPage.getBulkActionsDropdown().selectOptionByValue("spam");
        commentsPage.click(commentsPage.getBulkActionsApplyBttn());
        commentsPage.click(commentsPage.getConfirmationPopupWindow().$x("./button"));
    }

    public void goToSpam() {
        logger.log(Level.INFO, "opening 'Spam' section");
        commentsPage.getFilterRowOptionsRoot()
                .$x("./li[@class='spam']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=spam"
        ));
    }

    public void approveChecked() {
        logger.log(Level.INFO, "performing approve selected comment rows");
        commentsPage.getBulkActionsDropdown().selectOptionByValue("approve");
        commentsPage.click(commentsPage.getBulkActionsApplyBttn());
        commentsPage.click(commentsPage.getConfirmationPopupWindow().$x("./button"));
    }

    public void goToApproved() {
        logger.log(Level.INFO, "opening 'Approved' section");
        commentsPage.getFilterRowOptionsRoot()
                .$x("./li[@class='approved']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=approved"
        ));
    }


    public CommentObject publishComment(String comment, String author, String email, String url) {
        logger.log(Level.INFO, "Starting publish new comment\n text- " + comment + "Author - " + author + "E-mail - " + email + "Site- " + url);
        CommentObject commentObj = new CommentObject(comment, author, email, url);
        publicPostPage.openPage();
        publicPostPage.getCommentBody().sendKeys(commentObj.getComment());
        publicPostPage.getAuthorField().sendKeys(commentObj.getAuthor());
        publicPostPage.getEmailField().sendKeys(commentObj.getEmail());
        publicPostPage.getSiteField().sendKeys(commentObj.getUrl());
        publicPostPage.click(publicPostPage.getPublishBttn());
        commentObj.setId(Integer.parseInt(publicPostPage.getCommentPreview().$x("./li").getAttribute("id").split("-")[1]));
        return commentObj;
    }

    public CommentObject publishComment() throws PageException {
        String ts = String.valueOf(Instant.now().getEpochSecond());
        CommentObject commentObj = new CommentObject("testCommentBody " + ts, "testAnonymous " + ts, "anonymos@mail.com", "anonymous.com");
        logger.log(Level.INFO, "Starting publish new comment\n" + commentObj);
        publicPostPage.openPage();
        publicPostPage.getCommentBody().sendKeys(commentObj.getComment());
        publicPostPage.getAuthorField().sendKeys(commentObj.getAuthor());
        publicPostPage.getEmailField().sendKeys(commentObj.getEmail());
        publicPostPage.getSiteField().sendKeys(commentObj.getUrl());
        publicPostPage.click(publicPostPage.getPublishBttn());
        try {
            publicPostPage.getCommentPreview();
        } catch (ElementNotFound | RuntimeException e) {
            if (publicPostPage.getErrorPage().isDisplayed()) {
                publicPostPage.click(publicPostPage.getErrorPage().$x(".//a"));
                throw new PageException("Too quick commenting");
            }
        }
        commentObj.setId(Integer.parseInt(publicPostPage.getCommentPreview().$x("./li").getAttribute("id").split("-")[1]));
        return commentObj;
    }

    public List<CommentObject> publishCommentXTimes(int x) {
        List<CommentObject> result = new ArrayList<>();
        for (int i = 0; i < x; ) {
            publicPostPage.openPage();
            try {
                result.add(publishComment());
            } catch (PageException e) {
                continue;
            }
            i++;
        }
        return result;
    }
}
