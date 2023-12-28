package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.open;

public class MainPagePO implements Page, PageMenuFunc {
    private final PageType pageType = PageType.MAIN;

    @Override
    public void hover(SelenideElement element) {
        element.shouldBe(Condition.visible).hover();
    }

    @Override
    public void click(SelenideElement element) {
        element.shouldBe(Condition.visible).click();
    }

    @Override
    public boolean isVisible(SelenideElement element) {
        return element.shouldBe(Condition.visible).isDisplayed();
    }

    @Override
    public void openPage() {
        open(pageType.getUrl());
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
