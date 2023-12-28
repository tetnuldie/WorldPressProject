package org.example.login;

import com.codeborne.selenide.Configuration;
import org.example.pages.*;
import org.example.pages.sidemenu.SideMenuElement;
import org.example.users.UserPermissions;
import org.example.users.UserProvider;
import org.example.users.UserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.webdriver;

public class UserLoginTest {
    static {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        Configuration.startMaximized = true;
    }
    private LoginPO loginPage =  PageProvider.getPage(PageType.LOGIN, LoginPO.class);
    private MainPagePO mainPage = PageProvider.getPage(PageType.MAIN, MainPagePO.class);
    @Test(dataProvider = "userDataProvider")
    public void userPermissionsTest(String user){
        SoftAssert softAssert = new SoftAssert();
        loginPage.userLoginWoRemember(UserProvider.getUser(UserType.valueOf(user.toUpperCase())));

        UserPermissions.getUserPermissions(UserType.valueOf(user.toUpperCase())).forEach(element -> {
            softAssert.assertTrue(mainPage.isVisible(PageMenuFunc.getSideMenuElement(SideMenuElement.valueOf(element.toUpperCase()))),
                    "Element "+ element + " is not visible for user "+ user);
        });

        softAssert.assertAll();

    }
    @AfterMethod
    public void close(){
        webdriver().driver().getWebDriver().quit();
    }

    @DataProvider
    public Object[] userDataProvider(){
        return new Object[]{
                "SUBSCRIBER",
                "CONTRIBUTOR",
                "AUTHOR",
                "EDITOR",
                "ADMIN"
        };
    }
}
