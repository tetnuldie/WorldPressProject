package org.example.login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.menuelements.AdminBarElement;
import org.example.pages.pageobject.LoginPO;
import org.example.pages.pageobject.MainPagePO;
import org.example.pages.PageFactory;
import org.example.pages.PageType;
import org.example.users.User;
import org.example.users.UserType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.example.pages.MainMenuFunc.getAdminBarElement;
import static org.example.pages.MainMenuFunc.getAdminBarItemSubMenu;

public class UserLoginSteps {
    private static final LoginPO loginPage = PageFactory.getPage(PageType.LOGIN, LoginPO.class);
    private final MainPagePO mainPage = PageFactory.getPage(PageType.MAIN, MainPagePO.class);
    private final static Logger logger = Logger.getLogger(UserLoginSteps.class);

    public static void userLoginWoRemember(User user) {
        logger.log(Level.INFO, "performing login of user " + user.getLogin() + " wo remember");
        loginPage.openPage();
        loginPage.getLoginField().clear();
        loginPage.getLoginField().sendKeys(user.getLogin());
        loginPage.getPassField().clear();
        loginPage.getPassField().sendKeys(user.getPassword());
        loginPage.clickAndRedirectTo(loginPage.getSubmitBttn(), user.getUserType() == UserType.SUBSCRIBER ? PageType.PROFILE.getUrl() : PageType.MAIN.getUrl());
    }

    public static void userLoginWithRemember(User user) {
        logger.log(Level.INFO, "performing login of user " + user.getLogin() + " with remember");
        loginPage.openPage();
        loginPage.getLoginField().sendKeys(user.getLogin());
        loginPage.getPassField().sendKeys(user.getPassword());
        loginPage.getRememberMeCheckbox().click();
        loginPage.clickAndRedirectTo(loginPage.getSubmitBttn(), user.getUserType() == UserType.SUBSCRIBER ? PageType.PROFILE.getUrl() : PageType.MAIN.getUrl());
    }

   public static void logOut(){
        logger.log(Level.INFO, "Starting logout");
        getAdminBarElement(AdminBarElement.USER).hover();
        getAdminBarItemSubMenu(AdminBarElement.USER).filter(Condition.id("wp-admin-bar-logout")).first().click();
        Selenide.Wait().until(ExpectedConditions.urlToBe("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-login.php?loggedout=true&wp_lang=en_US"));
    }

    public boolean isVisibleOnMainPage(SelenideElement selenideElement) {
        return mainPage.isVisible(selenideElement);
    }

    public void closePage() {
        loginPage.close();
    }
}
