package org.example.media;

import com.codeborne.selenide.Configuration;
import org.example.SuiteSetup;
import org.example.users.User;
import org.example.users.UserFactory;
import org.example.users.UserType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class MediaTestSetup extends SuiteSetup {
    protected final User user = UserFactory.getUser(UserType.AUTHOR);
    protected final MediaTestSteps steps = new MediaTestSteps();
    protected final File file = new File("src/Resources/pepe.jpg");
    protected int fileId;

    @BeforeTest
    public void login() {
        Configuration.timeout = 7000;
        steps.login(user);
    }

    @AfterTest
    public void cleanup() {
        steps.closeMediaPage();
    }
}
