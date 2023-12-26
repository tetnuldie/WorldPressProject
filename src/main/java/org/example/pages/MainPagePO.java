package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.open;

public class MainPagePO implements Page, PageMenuFunc {

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
        open(PageType.MAIN.getUrl());
    }
}
