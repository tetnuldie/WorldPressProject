package org.example.comments;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.example.pages.LoginPO;
import org.example.pages.PageFactory;
import org.example.pages.PageType;
import org.example.pages.comments.CommentObject;
import org.example.pages.comments.PublicPostPO;
import org.example.pages.table.CommentsPO;
import org.example.pages.table.tablerow.CommentRow;
import org.example.users.User;
import org.example.users.UserFactory;
import org.example.users.UserType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CommentsTest {
    static {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
    }

    private final User user = UserFactory.getUser(UserType.EDITOR);
    private final LoginPO loginPage = PageFactory.getPage(PageType.LOGIN, LoginPO.class);
    private final CommentsPO commentsPage = PageFactory.getPage(PageType.COMMENTS, CommentsPO.class);
    private final PublicPostPO publicPostPage = PageFactory.getPage(PageType.PUBLIC_POST, PublicPostPO.class);
    private List<CommentObject> testDataComments = new ArrayList<>();
    private List<CommentRow> testDataRows = new ArrayList<>();

    @BeforeClass
    public void login() {
        for (int i = 0; i < 3; i++) {
            publicPostPage.openPage();
            testDataComments.add(publicPostPage.publishComment());
            Selenide.sleep(7500);
        }
        loginPage.userLoginWoRemember(user);
        commentsPage.openPage();
        testDataComments.stream().forEach(element -> {
            testDataRows.add(commentsPage.getRowAsObject(commentsPage.getTableRowById(element.getId())));
        });
    }

    @BeforeMethod
    public void openHomePage() {
        commentsPage.openPage();
    }

    @AfterClass
    public void cleanup() {
        commentsPage.openPage();
        testDataRows.stream()
                .filter(element -> !element.isTrash() && !element.isSpam())
                .forEach(CommentRow::checkRow);
        commentsPage.moveCheckedToTrash();
        commentsPage.goToSpam();
        testDataRows.stream()
                .filter(element -> !element.isTrash() && element.isSpam())
                .forEach(CommentRow::checkRow);
        commentsPage.deleteChecked();
        commentsPage.goToTrash();
        testDataRows.stream()
                .filter(element -> !element.isSpam())
                .forEach(CommentRow::checkRow);
        commentsPage.deleteChecked();
        loginPage.close();
    }

    @Test(priority = 1)
    public void anonymousCommentTest() {
        var commentRow = testDataRows.stream().findFirst().orElse(null).getId();

        Assert.assertNotNull(commentRow, "New comment not displayed on page layout");
    }

    @Test(priority = 2)
    public void editCommentTest() {
        var commentRow = testDataRows.stream().findFirst().orElse(null);
        String oldCommentText = commentRow.getBodyText();
        commentRow.openQuickEditForm();
        commentsPage.quickEdit(String.format("testCommentBody %d", Instant.now().getEpochSecond()));
        commentRow.init();

        Assert.assertNotEquals(commentRow.getBodyText(), oldCommentText, "Comment have not been edited");
    }

    @Test(priority = 2)
    public void approveCommentTest() {
        var commentRow = testDataRows.stream().filter(CommentRow::isPending).findFirst().orElse(null);

        commentRow.checkRow();
        commentsPage.approveChecked();
        commentsPage.goToApproved();
        commentRow.init();

        Assert.assertFalse(commentRow.isPending(), "Comment have not been approved");
    }

    @Test(priority = 2)
    public void spamCommentTest() {
        var commentRow = testDataRows.stream().filter(element -> !element.isSpam()).findFirst().orElse(null);

        commentRow.checkRow();
        commentsPage.spamChecked();
        commentsPage.goToSpam();
        commentRow.init();

        Assert.assertTrue(commentRow.isSpam(), "Comment have not been marked as spam");
    }

    @Test(priority = 3)
    public void moveCommentToTrashTest() {
        var commentRow = testDataRows.stream().filter(element -> !element.isTrash() && !element.isSpam()).findFirst().orElse(null);

        commentRow.checkRow();
        commentsPage.moveCheckedToTrash();
        commentsPage.goToTrash();
        commentRow.init();

        Assert.assertTrue(commentRow.isTrash(), "Comment have not been moved to trash");
    }


}
