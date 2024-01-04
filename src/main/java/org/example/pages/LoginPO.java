package org.example.pages;

import com.codeborne.selenide.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.users.User;
import org.example.users.UserType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;

public class LoginPO implements Page {
    private final PageType pageType = PageType.LOGIN;
    private final Logger logger = Logger.getLogger(LoginPO.class);

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

    public void close() {
        logger.log(Level.INFO, "closing browser");
        webdriver().driver().getWebDriver().quit();
    }

    @Override
    public void hover(SelenideElement element) {
        logger.log(Level.INFO, "hovering on "+element.getSearchCriteria());
        element.shouldBe(Condition.visible).hover();
    }

    @Override
    public void click(SelenideElement element) {
        logger.log(Level.INFO, "clicking on "+element.getSearchCriteria());
        element.shouldBe(Condition.visible).click();
    }

    @Override
    public boolean isVisible(SelenideElement element) {
        logger.log(Level.INFO, "performing visibility check of "+element.getSearchCriteria());
        return element.shouldBe(Condition.visible).isDisplayed();
    }

    @Override
    public void openPage() {
        logger.log(Level.INFO, "opening page '"+pageType.getUrl()+"'");
        open(pageType.getUrl());
    }

    @Override
    public void openPageWithWaiter(String url) {
        logger.log(Level.INFO, "opening page "+pageType.getUrl()+" with loading waiter");
        open(url);
        Selenide.Wait().until(ExpectedConditions.urlToBe(url));
    }

    @Override
    public void clickAndRedirectTo(SelenideElement element, String expectedUrl) {
        logger.log(Level.INFO, "redirecting to "+pageType.getUrl()+" after click on "+element.getSearchCriteria());
        element.click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(expectedUrl));
    }

}
