package org.example;

import com.codeborne.selenide.Configuration;
import org.example.pages.pageobject.LoginPO;
import org.example.pages.PageType;
import org.example.pages.pageobject.tablepage.MediaPO;
import org.example.pages.pageobject.UploadMediaPO;

import java.io.File;

public class App
{
    static {
        System.setProperty("webdriver.gecko.driver", "src/geckodriver.exe");
        Configuration.browser = "firefox";
    }
    public static void main( String[] args )
    {

    }
}
