package org.example.pages;

import com.codeborne.selenide.*;
import org.example.users.User;
import org.example.users.UserType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;

public class LoginPO implements Page{

    public SelenideElement getLoginField(){
        return $x("//input[@id='user_login']").shouldBe(Condition.visible);
    }

    public SelenideElement getPassField(){
        return $x("//input[@id='user_pass']").shouldBe(Condition.visible);
    }

    public SelenideElement getRememberMeCheckbox(){
        return $x("//input[@id='rememberme']").shouldBe(Condition.visible);
    }
    public SelenideElement getSubmitBttn(){
        return $x("//input[@id='wp-submit']").shouldBe(Condition.visible);
    }

    public void openPage(){
        open(PageType.LOGIN.getUrl());
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

    public void close(){
        webdriver().driver().getWebDriver().quit();
    }

    public void userLoginWoRemember(User user){
        getLoginField().sendKeys(user.getLogin());
        getPassField().sendKeys(user.getPassword());
        click(getSubmitBttn());
        Selenide.Wait().until(urlToBe(user.getUserType() == UserType.SUBSCRIBER ? PageType.PROFILE.getUrl() : PageType.MAIN.getUrl()));
    }

    public void userLoginWithRemember(User user){
        getLoginField().sendKeys(user.getLogin());
        getPassField().sendKeys(user.getPassword());
        getRememberMeCheckbox().click();
        click(getSubmitBttn());
        Selenide.Wait().until(urlToBe(user.getUserType() == UserType.SUBSCRIBER ? PageType.PROFILE.getUrl() : PageType.MAIN.getUrl()));
    }

    private ExpectedCondition<Boolean> urlToBe(String expectedUrl) {
        return ExpectedConditions.urlToBe(expectedUrl);
    }
}
