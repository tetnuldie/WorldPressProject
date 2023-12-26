package org.example.login;

import org.example.pages.*;
import org.example.pages.sidemenu.SideMenuElement;
import org.example.users.UserProvider;
import org.example.users.UserType;
import org.testng.annotations.Test;

public class UserLoginTest {
    private LoginPO page =  PageProvider.getPage(PageType.LOGIN, LoginPO.class);
    private MainPagePO main = PageProvider.getPage(PageType.MAIN, MainPagePO.class);
    @Test
    public void userSubscriberTest(){
        page.userLoginWoRemember(UserProvider.getUser(UserType.SUBSCRIBER));
        

    }
}
