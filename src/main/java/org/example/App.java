package org.example;

import com.codeborne.selenide.Configuration;
import org.example.pages.LoginPO;
import org.example.pages.MainPagePO;
import org.example.pages.Page;
import org.example.pages.PageMenuFunc;
import org.example.pages.sidemenu.SideMenuElement;
import org.example.users.UserProvider;
import org.example.users.UserType;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$x;

public class App
{
    static {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        Configuration.startMaximized = true;
    }
    public static void main( String[] args )
    {
/*        LoginPO page = new LoginPO();
        Page mainPage = new MainPagePO();
        page.openPage();
        page.userLoginWoRemember(UserProvider.getUser(UserType.EDITOR));*/




        System.out.println();

/*        mainPage.hover(PageMenuFunc.getSideMenuElement(SideMenuElement.COMMENTS));
        System.out.println();*/

 //       page.close();

    }
}
