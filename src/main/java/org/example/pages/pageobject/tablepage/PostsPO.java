package org.example.pages.pageobject.tablepage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.example.pages.PageType;

public class PostsPO extends TablePagePO{

    public PostsPO(PageType pageType) {
        super(pageType);
    }
    public SelenideElement getAddNewBttn() {
        logger.log(Level.INFO, "trying to find Add New bttn");
        return getTablePageRoot().$x("./a[@class='page-title-action']").shouldBe(Condition.visible);
    }

    public ElementsCollection getDraftRows() {
        logger.log(Level.INFO, "trying to fetch draft posts");
        return getTableRows().filter(Condition.cssClass("status-draft"));
    }

    public ElementsCollection getPublishedRows() {
        logger.log(Level.INFO, "trying to fetch published posts");
        return getTableRows().filter(Condition.cssClass("status-publish"));
    }

    public ElementsCollection getTrashRows() {
        logger.log(Level.INFO, "trying to fetch trash posts");
        return getTableRows().filter(Condition.cssClass("status-trash"));
    }
}
