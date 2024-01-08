package org.example.tests.login;

import io.qameta.allure.testng.AllureTestNg;
import org.example.pages.*;
import org.example.pages.menuelements.SideMenuElement;
import org.example.users.UserPermissions;
import org.example.users.UserFactory;
import org.example.users.UserType;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static org.example.steps.UserLoginSteps.userLoginWoRemember;
import static org.example.steps.UserLoginSteps.logOut;

@Listeners({AllureTestNg.class, LoginListener.class})
public class UserLoginTest extends LoginTestSetup {
    @Test(dataProvider = "userDataProvider",
            groups = {"smoke", "login"})
    public void userPermissionsTest(String user) {
        SoftAssert softAssert = new SoftAssert();
        userLoginWoRemember(UserFactory.getUser(UserType.valueOf(user.toUpperCase())));

        UserPermissions.getUserPermissions(UserType.valueOf(user.toUpperCase())).forEach(element -> {
            softAssert.assertTrue(steps.isVisibleOnMainPage(MainMenuFunc.getSideMenuElement(SideMenuElement.valueOf(element.toUpperCase()))),
                    "Element " + element + " is not visible for user " + user);
        });

        softAssert.assertAll();

    }

    @AfterMethod
    public void logout() {
        logOut();
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
