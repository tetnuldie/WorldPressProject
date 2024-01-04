package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.MainPagePO;
import org.example.pages.PageType;
import org.example.pages.table.tablerow.CommentRow;
import org.example.users.User;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CommentsPO extends TablePagePO {
    private final Logger logger = Logger.getLogger(CommentsPO.class);

    public CommentsPO(PageType pageType) {
        super(pageType);
    }

    @Override
    public SelenideElement getContentTableRoot() {
        logger.log(Level.INFO, "trying to find page table root element");
        return getTablePageRoot().$x("./form[@id='comments-form']/table").shouldBe(Condition.visible);
    }

    public SelenideElement getQuickEditRow() {
        logger.log(Level.INFO, "trying to get quick edit form");
        return getTableRows()
                .filter(Condition.id(String.format("replyrow")))
                .first();
    }
}
