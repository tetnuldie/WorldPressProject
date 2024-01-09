package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.time.Instant;
import java.util.HashMap;


public class SuiteSetup {
    protected final Logger logger = Logger.getLogger(SuiteSetup.class);


    @BeforeSuite
    public void initSuite() {
        Configuration.remote = "http://34.118.117.38:4444/wd/hub";
        Configuration.browserSize = System.getProperty("browserSize");
        Configuration.browser = System.getProperty("browserType");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    protected void setOptions(String browserType, String test) {
        switch (browserType) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                    put("enableVideo", true);
                    put("enableVNC", true);
                    put("videoName", String.format("%s_%s_%s", Configuration.browser, test, Instant.now()));
                }});
                Configuration.browserCapabilities = options;
                Configuration.timeout = 15000;
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                    put("enableVideo", true);
                    put("enableVNC", true);
                    put("videoName", String.format("%s_%s_%s", Configuration.browser, test, Instant.now()));
                }});
                Configuration.browserCapabilities = options;
                Configuration.timeout = 35000;

            }
        }
    }
}
