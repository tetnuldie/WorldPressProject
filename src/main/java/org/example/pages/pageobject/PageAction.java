package org.example.pages.pageobject;

import com.codeborne.selenide.SelenideElement;

public interface PageAction {
    void openPage();
    void hover(SelenideElement element);
    void click(SelenideElement element);
    boolean isVisible(SelenideElement element);
    void openPageWithWaiter(String url);
    void clickAndRedirectTo(SelenideElement element, String expectedUrl);
    void close();
}
