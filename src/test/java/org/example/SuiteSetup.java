package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;


public class SuiteSetup {
    protected final Logger logger = Logger.getLogger(SuiteSetup.class);

    @BeforeSuite
    @Parameters({"browserType"})
    public void initSuite(String browserType) throws MalformedURLException {
        Configuration.remote = "http://34.118.117.38:4444/wd/hub";
        Configuration.browserSize = "1920x1080";
        switch (browserType) {
            case "chrome" -> {
                Configuration.browser = "chrome";
            }
            case "firefox" -> {
                Configuration.browser = "firefox";

            }
            case "opera" -> {

                ChromeOptions options = new ChromeOptions();
                options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                    put("enableVideo", true);
                    put("enableVNC", true);
                }});
                RemoteWebDriver driver = new RemoteWebDriver(new URL("http://34.118.117.38:4444/wd/hub"), options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(17));
                WebDriverRunner.setWebDriver(driver);

            }
            default -> {
                logger.log(Level.ERROR, "Wrong browser tag provided; Check *_testng.xml suite params");
            }
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        WebDriverRunner.getWebDriver().quit();
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
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                    put("enableVideo", true);
                    put("enableVNC", true);
                    put("videoName", String.format("%s_%s_%s", Configuration.browser, test, Instant.now()));
                }});
                Configuration.browserCapabilities = options;

            }
            case "opera" -> {
                ChromeOptions options = new ChromeOptions();
                options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                    put("enableVideo", true);
                    put("enableVNC", true);
                    put("videoName", String.format("%s_%s_%s", Configuration.browser, test, Instant.now()));
                }});
                Configuration.browserCapabilities = options;
            }
        }
    }
}
