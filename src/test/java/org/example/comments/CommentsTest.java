package org.example.comments;

import io.qameta.allure.testng.AllureTestNg;
import org.example.pages.table.tablerow.CommentRow;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Instant;

@Listeners({AllureTestNg.class, CommentsListener.class})
public class CommentsTest extends CommentsTestSetup {

    @BeforeMethod
    public void openHomePage() {
        steps.openCommentsPage();
    }

    @Test(priority = 1,
            groups = {"smoke", "comments"})
    public void anonymousCommentTest() {
        int commentRow = testDataRows.stream().findFirst().orElse(null).getId();

        Assert.assertTrue(commentRow > 0, "New comment not displayed on page layout");
    }

    @Test(priority = 2,
            groups = {"smoke", "comments"})
    public void editCommentTest() {
        var commentRow = testDataRows.stream().findFirst().orElse(null);
        String oldCommentText = commentRow.getBodyText();
        commentRow.openQuickEditForm();
        steps.quickEdit(String.format("testCommentBody %d", Instant.now().getEpochSecond()));
        commentRow.init();

        Assert.assertNotEquals(commentRow.getBodyText(), oldCommentText, "Comment have not been edited");
    }

    @Test(priority = 2,
            groups = {"smoke", "comments"})
    public void approveCommentTest() {
        var commentRow = testDataRows.stream().filter(CommentRow::isPending).findFirst().orElse(null);

        commentRow.checkRow();
        steps.approveChecked();
        steps.goToApproved();
        commentRow.init();

        Assert.assertFalse(commentRow.isPending(), "Comment have not been approved");
    }

    @Test(priority = 2,
            groups = {"smoke", "comments"})
    public void spamCommentTest() {
        var commentRow = testDataRows.stream().filter(element -> !element.isSpam()).findFirst().orElse(null);

        commentRow.checkRow();
        steps.spamChecked();
        steps.goToSpam();
        commentRow.init();

        Assert.assertTrue(commentRow.isSpam(), "Comment have not been marked as spam");
    }

    @Test(priority = 3,
            groups = {"smoke", "comments"})
    public void moveCommentToTrashTest() {
        var commentRow = testDataRows.stream().filter(element -> !element.isTrash() && !element.isSpam()).findFirst().orElse(null);

        commentRow.checkRow();
        steps.moveCheckedToTrash();
        steps.goToTrash();
        commentRow.init();

        Assert.assertTrue(commentRow.isTrash(), "Comment have not been moved to trash");
    }
}
