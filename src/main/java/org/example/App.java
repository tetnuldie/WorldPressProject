package org.example;

import com.codeborne.selenide.Configuration;
import org.example.pages.pageobject.LoginPO;
import org.example.pages.PageType;
import org.example.pages.pageobject.tablepage.MediaPO;
import org.example.pages.pageobject.UploadMediaPO;

import java.io.File;
import static com.codeborne.selenide.Selenide.*;

import static com.codeborne.selenide.Selenide.open;

public class App
{
    static {
        System.setProperty("webdriver.chrome.driver", "src/geckodriver.exe");
        Configuration.browser = "firefox";
        Configuration.browserSize = "1920x1080";
    }
    public static void main( String[] args )
    {
        open("https://wordpress-test-app-for-selenium.azurewebsites.net/2023/12/30/do_not_delete/");
        System.out.println();



    }
}
