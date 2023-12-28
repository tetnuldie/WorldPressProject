package org.example;

import com.codeborne.selenide.Configuration;
import org.example.pages.*;
import org.example.pages.pages.CreatePostPO;
import org.example.pages.pages.PostsPO;
import org.example.pages.sidemenu.SideMenuElement;
import org.example.users.UserProvider;
import org.example.users.UserType;

import java.time.Instant;

public class App
{
    static {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
    }
    public static void main( String[] args )
    {



        System.out.println();

/*        mainPage.hover(PageMenuFunc.getSideMenuElement(SideMenuElement.COMMENTS));
        System.out.println();*/


    }
}
