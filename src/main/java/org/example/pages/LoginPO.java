package org.example.pages;

import com.codeborne.selenide.*;
import org.example.users.User;
import org.example.users.UserType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;

public class LoginPO implements Page {
    private final PageType pageType = PageType.LOGIN;

    public SelenideElement getLoginField() {
        return $x("//input[@id='user_login']").shouldBe(Condition.visible);
    }

    public SelenideElement getPassField() {
        return $x("//input[@id='user_pass']").shouldBe(Condition.visible);
    }

    public SelenideElement getRememberMeCheckbox() {
        return $x("//input[@id='rememberme']").shouldBe(Condition.visible);
    }

    public SelenideElement getSubmitBttn() {
        return $x("//input[@id='wp-submit']").shouldBe(Condition.visible);
    }

    public void openPage() {
        open(pageType.getUrl());
    }

    @Override
    public void hover(SelenideElement element) {
        element.shouldBe(Condition.visible).hover();
    }

    @Override
    public void click(SelenideElement element) {
        element.shouldBe(Condition.visible).click();

    }

    @Override
    public boolean isVisible(SelenideElement element) {
        return element.isDisplayed();
    }

    @Override
    public void openPageWithWaiter(String url) {
        open(url);
        Selenide.Wait().until(ExpectedConditions.urlToBe(url));
    }

    public void close() {
        webdriver().driver().getWebDriver().quit();
    }

    public void userLoginWoRemember(User user) {
        openPage();
        getLoginField().sendKeys(user.getLogin());
        getPassField().sendKeys(user.getPassword());
        clickAndRedirectTo(getSubmitBttn(), user.getUserType() == UserType.SUBSCRIBER ? PageType.PROFILE.getUrl() : PageType.MAIN.getUrl());
    }

    public void userLoginWithRemember(User user) {
        openPage();
        getLoginField().sendKeys(user.getLogin());
        getPassField().sendKeys(user.getPassword());
        getRememberMeCheckbox().click();
        clickAndRedirectTo(getSubmitBttn(), user.getUserType() == UserType.SUBSCRIBER ? PageType.PROFILE.getUrl() : PageType.MAIN.getUrl());
    }

    public ExpectedCondition<Boolean> urlToBe(String expectedUrl) {
        return ExpectedConditions.urlToBe(expectedUrl);
    }

    @Override
    public void clickAndRedirectTo(SelenideElement element, String expectedUrl) {
        element.click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(expectedUrl));
    }
}
