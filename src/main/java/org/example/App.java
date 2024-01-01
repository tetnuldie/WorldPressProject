package org.example;

import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.open;

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
