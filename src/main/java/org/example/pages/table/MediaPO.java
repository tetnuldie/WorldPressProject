package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.example.pages.PageType;
import org.example.pages.table.tablerow.MediaRow;
import org.example.users.User;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.switchTo;

public class MediaPO extends TablePagePO {
    public MediaPO(PageType pageType) {
        super(pageType);
    }

    public SelenideElement getAddNewBttn() {
        logger.log(Level.INFO, "trying to get Add New bttn");
        return getTablePageRoot().$x("./a[@class='page-title-action']").shouldBe(Condition.visible);
    }

    public SelenideElement getFilterRowOptionsRoot() {
        logger.log(Level.WARN, "method 'getFilterRowOptionsRoot' not implemented for "+this.getClass().getName());
        return null;
    }

    @Override
    public SelenideElement getContentTableRoot() {
        logger.log(Level.INFO, "trying to get page table root element");
        return getTablePageRoot().$x("./form[@id='posts-filter']/table").shouldBe(Condition.visible);
    }


}
