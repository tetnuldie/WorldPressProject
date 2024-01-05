package org.example.pages.pageobject.tablepage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.example.pages.PageType;

public class MediaPO extends TablePagePO {
    public MediaPO(PageType pageType) {
        super(pageType);
    }
    public SelenideElement getAddNewBttn() {
        logger.log(Level.INFO, "trying to get Add New bttn");
        return getTablePageRoot().$x("./a[@class='page-title-action']").shouldBe(Condition.visible);
    }
}
