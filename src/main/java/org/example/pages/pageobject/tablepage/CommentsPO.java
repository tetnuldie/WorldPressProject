package org.example.pages.pageobject.tablepage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.example.pages.PageType;

public class CommentsPO extends TablePagePO {
    public CommentsPO(PageType pageType) {
        super(pageType);
    }

    public ElementsCollection getTableRows() {
        logger.log(Level.INFO, "trying to find table rows");
        return getContentTableRoot().$$x("./tbody/tr");
    }

    public SelenideElement getContentTableRoot() {
        logger.log(Level.INFO, "trying to find page table root element");
        return getTablePageRoot().$x("./form[@id='comments-form']/table").shouldBe(Condition.visible);
    }

    public SelenideElement getQuickEditRow() {
        logger.log(Level.INFO, "trying to get quick edit form");
        return getTableRows()
                .filter(Condition.id("replyrow"))
                .first();
    }
}
