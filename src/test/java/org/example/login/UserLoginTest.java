package org.example.login;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.testng.AllureTestNg;
import org.example.pages.*;
import org.example.pages.sidemenu.SideMenuElement;
import org.example.users.UserPermissions;
import org.example.users.UserFactory;
import org.example.users.UserType;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.webdriver;

@Listeners({AllureTestNg.class, LoginListener.class})
public class UserLoginTest extends LoginTestSetup {
    @Test(dataProvider = "userDataProvider",
            groups = {"smoke", "login"})
    public void userPermissionsTest(String user) {
        SoftAssert softAssert = new SoftAssert();
        steps.userLoginWoRemember(UserFactory.getUser(UserType.valueOf(user.toUpperCase())));

        UserPermissions.getUserPermissions(UserType.valueOf(user.toUpperCase())).forEach(element -> {
            softAssert.assertTrue(steps.isVisibleOnMainPage(PageMenuFunc.getSideMenuElement(SideMenuElement.valueOf(element.toUpperCase()))),
                    "Element " + element + " is not visible for user " + user);
        });

        softAssert.assertAll();

    }

    @AfterMethod
    public void logout() {
        PageMenuFunc.logOut();
    }


    @DataProvider
    public Object[] userDataProvider() {
        return new Object[]{
                "SUBSCRIBER",
                "CONTRIBUTOR",
                "AUTHOR",
                "EDITOR",
                "ADMIN"
        };
    }
}
