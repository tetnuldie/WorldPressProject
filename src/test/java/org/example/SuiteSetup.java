package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import java.time.Instant;
import java.util.HashMap;


public class SuiteSetup {
    protected final Logger logger = Logger.getLogger(SuiteSetup.class);

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        WebDriverRunner.getWebDriver().quit();
    }

    protected void setOptions(String browserType, String test) {
        switch (browserType) {
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
            default -> {
                ChromeOptions options = new ChromeOptions();
                options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                    put("enableVideo", true);
                    put("enableVNC", true);
                    put("videoName", String.format("%s_%s_%s", Configuration.browser, test, Instant.now()));
                }});
                Configuration.browserCapabilities = options;
                Configuration.timeout = 15000;
            }

        }
    }
}
