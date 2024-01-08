package org.example.tests.login;

import com.codeborne.selenide.Configuration;
import org.example.SuiteSetup;
import org.example.steps.UserLoginSteps;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class LoginTestSetup extends SuiteSetup {
    protected UserLoginSteps steps = new UserLoginSteps();

    @BeforeTest
    public void init() {
        super.setOptions(Configuration.browser, this.getClass().getSimpleName());
    }

    @AfterTest
    public void close() {
        steps.closePage();
    }
}
