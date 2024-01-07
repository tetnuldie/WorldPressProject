package org.example.pages.pageobject;

import com.codeborne.selenide.*;
import org.apache.log4j.Level;
import org.example.pages.PageType;

import static com.codeborne.selenide.Selenide.*;

public class LoginPO extends AbstractPage {
    public LoginPO(PageType pageType) {
        super(pageType);
    }

    public SelenideElement getLoginField() {
        logger.log(Level.INFO, "trying to get login input field");
        return $x("//input[@id='user_login']").shouldBe(Condition.visible);
    }

    public SelenideElement getPassField() {
        logger.log(Level.INFO, "trying to get password input field");
        return $x("//input[@id='user_pass']").shouldBe(Condition.visible);
    }

    public SelenideElement getRememberMeCheckbox() {
        logger.log(Level.INFO, "trying to get remember-me checkbox");
        return $x("//input[@id='rememberme']").shouldBe(Condition.visible);
    }

    public SelenideElement getSubmitBttn() {
        logger.log(Level.INFO, "trying to get submit bttn");
        return $x("//input[@id='wp-submit']").shouldBe(Condition.visible);
    }
}
