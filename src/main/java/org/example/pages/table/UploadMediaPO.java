package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.LoginPO;
import org.example.pages.Page;
import org.example.pages.PageMenuFunc;
import org.example.pages.PageType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class UploadMediaPO implements PageMenuFunc, Page {
    private final PageType pageType;
    private final Logger logger = Logger.getLogger(UploadMediaPO.class);

    public UploadMediaPO(PageType pageType) {
        this.pageType = pageType;
    }

    public SelenideElement getFileInput() {
        logger.log(Level.INFO, "trying to find file input element");
        return $x("//input[@type='file']");
    }

    public SelenideElement getDropBoxArea() {
        logger.log(Level.INFO, "trying to find dropbox area");
        return $x("//div[@id='drag-drop-area']");
    }

    public SelenideElement getMediaItemsRoot() {
        logger.log(Level.INFO, "trying to find media items root element");
        return $x("//div[@id='media-items']");
    }

    public SelenideElement getUploadedFileWrapper() {
        logger.log(Level.INFO, "trying to find media items wrapper element");
        return $x("//div[@class='media-item-wrapper']");
    }

    @Override
    public void hover(SelenideElement element) {
        logger.log(Level.INFO, "hovering on " + element.getSearchCriteria());
        element.shouldBe(Condition.visible).hover();
    }

    @Override
    public void click(SelenideElement element) {
        logger.log(Level.INFO, "clicking on " + element.getSearchCriteria());
        element.shouldBe(Condition.visible).click();
    }

    @Override
    public boolean isVisible(SelenideElement element) {
        logger.log(Level.INFO, "performing visibility check of " + element.getSearchCriteria());
        return element.shouldBe(Condition.visible).isDisplayed();
    }

    @Override
    public void openPage() {
        logger.log(Level.INFO, "opening page '"+pageType.getUrl()+"'");
        open(pageType.getUrl());
    }

    @Override
    public void openPageWithWaiter(String url) {
        logger.log(Level.INFO, "opening page " + url + " with loading waiter");
        open(url);
        Selenide.Wait().until(ExpectedConditions.urlToBe(url));
    }

    @Override
    public void clickAndRedirectTo(SelenideElement element, String expectedUrl) {
        logger.log(Level.INFO, "redirecting to " + expectedUrl + " after click on " + element.getSearchCriteria());
        element.click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(expectedUrl));
    }

    public void close() {
        logger.log(Level.INFO, "closing browser");
        webdriver().driver().getWebDriver().quit();
    }
}
