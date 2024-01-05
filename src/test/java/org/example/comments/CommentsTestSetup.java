package org.example.comments;

import com.codeborne.selenide.Configuration;
import org.example.SuiteSetup;
import org.example.pages.comments.CommentObject;
import org.example.pages.pageobject.tablerow.CommentRow;
import org.example.users.User;
import org.example.users.UserFactory;
import org.example.users.UserType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;

public class CommentsTestSetup extends SuiteSetup {
    protected final User user = UserFactory.getUser(UserType.EDITOR);
    protected final CommentsTestSteps steps = new CommentsTestSteps();
    protected List<CommentObject> testDataComments = new ArrayList<>();
    protected List<CommentRow> testDataRows = new ArrayList<>();

    @BeforeTest
    public void login() {
        Configuration.timeout = 7000;
        testDataComments = steps.publishCommentXTimes(3);
        steps.login(user);
        steps.openCommentsPage();
        testDataComments.stream().forEach(element -> {
            testDataRows.add(steps.getRowAsObject(steps.getTableRowById(element.getId())));
        });
    }

    @AfterTest
    public void cleanup() {
        steps.openCommentsPage();
        testDataRows.stream()
                .filter(element -> !element.isTrash() && !element.isSpam())
                .forEach(CommentRow::checkRow);
        steps.moveCheckedToTrash();
        steps.goToSpam();
        testDataRows.stream()
                .filter(element -> !element.isTrash() && element.isSpam())
                .forEach(CommentRow::checkRow);
        steps.deleteChecked();
        steps.goToTrash();
        testDataRows.stream()
                .filter(element -> !element.isSpam())
                .forEach(CommentRow::checkRow);
        steps.deleteChecked();
        steps.closeCommentsPage();
    }
}
