package org.example;

import com.codeborne.selenide.Configuration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class SuiteSetup {
    protected final Logger logger = Logger.getLogger(SuiteSetup.class);
    @BeforeSuite
    @Parameters({"browserType"})
    public void init(String browserType){

        switch (browserType){
            case "chrome" -> {
                System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
                Configuration.browser = "chrome";
            }
            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", "src/geckodriver.exe");
                Configuration.browser = "firefox";
            }
            default -> {
                logger.log(Level.ERROR, "Wrong browser tag provided; Check *_testng.xml suite params");
            }
        }
  //      Configuration.startMaximized = true;
        Configuration.browserSize = "1920x1080";
    }
}
