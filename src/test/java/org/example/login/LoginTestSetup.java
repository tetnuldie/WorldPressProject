package org.example.login;

import com.codeborne.selenide.Configuration;
import org.example.SuiteSetup;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class LoginTestSetup extends SuiteSetup {
    protected UserLoginSteps steps = new UserLoginSteps();

    @BeforeTest
    public void init() {
        Configuration.timeout = 7000;
    }

    @AfterTest
    public void close() {
        steps.closePage();
    }
}
