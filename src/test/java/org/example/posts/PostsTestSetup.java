package org.example.posts;

import com.codeborne.selenide.Configuration;
import org.example.SuiteSetup;
import org.example.login.UserLoginSteps;
import org.example.pages.pageobject.tablerow.PostRow;
import org.example.users.User;
import org.example.users.UserFactory;
import org.example.users.UserType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;

public class PostsTestSetup extends SuiteSetup {
    protected final User user = UserFactory.getUser(UserType.AUTHOR);
    protected final PostsTestSteps steps = new PostsTestSteps();
    protected static List<PostRow> testData = new ArrayList<>();

    @BeforeTest
    public void login() {
        super.setOptions(Configuration.browser, this.getClass().getSimpleName());
        Configuration.timeout = 15000;
        UserLoginSteps.userLoginWoRemember(user);
    }

    @AfterTest
    public void cleanup() {
        steps.goToMine(user);
        testData.stream()
                .filter(element -> !element.isTrash())
                .forEach(PostRow::checkRow);
        steps.moveCheckedToTrash();
        steps.goToTrash();
        testData.forEach(PostRow::checkRow);
        steps.deleteChecked();

        steps.closePage();
    }

}
