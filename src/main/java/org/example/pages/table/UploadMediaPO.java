package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.pages.Page;
import org.example.pages.PageMenuFunc;
import org.example.pages.PageType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class UploadMediaPO implements PageMenuFunc, Page {
    private final PageType pageType;

    public UploadMediaPO(PageType pageType) {
        this.pageType = pageType;
    }
    public SelenideElement getFileInput() {
        return $x("//input[@type='file']");
    }
    public SelenideElement getDropBoxArea(){
        return $x("//div[@id='drag-drop-area']");
    }

    public SelenideElement getMediaItemsRoot(){
        return $x("//div[@id='media-items']");
    }

    public SelenideElement getUploadedFileWrapper(){
        return $x("//div[@class='media-item-wrapper']");
    }

    public int uploadFile(File file){
        getFileInput().uploadFile(file);
        Selenide.Wait().withTimeout(Duration.ofSeconds(100)).until(webDriver -> getUploadedFileWrapper().is(Condition.visible));

        return Integer.parseInt(getUploadedFileWrapper().$x(".//a").getAttribute("href").split("=")[1].split("&")[0]);
    }

    @Override
    public void openPage() {
        open(pageType.getUrl());
    }

    @Override
    public void hover(SelenideElement element) {
        element.hover();
    }

    @Override
    public void click(SelenideElement element) {
        element.click();
    }

    @Override
    public boolean isVisible(SelenideElement element) {
        return element.isDisplayed();
    }

    @Override
    public void openPageWithWaiter(String url) {
        open(url);
        Selenide.Wait().until(ExpectedConditions.urlToBe(url));
    }

    @Override
    public void clickAndRedirectTo(SelenideElement element, String expectedUrl) {
        element.click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(expectedUrl));
    }
}
