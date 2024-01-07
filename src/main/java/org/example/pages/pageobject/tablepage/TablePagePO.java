package org.example.pages.pageobject.tablepage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.pageobject.AbstractPage;
import org.example.pages.MainMenuFunc;
import org.example.pages.PageType;

import static com.codeborne.selenide.Selenide.*;

public class TablePagePO extends AbstractPage implements MainMenuFunc {

    protected final Logger logger = Logger.getLogger(TablePagePO.class);
    public TablePagePO(PageType pageType) {
        super(pageType);
    }
    public PageType getPageType() {
        return pageType;
    }
    public SelenideElement getTablePageRoot() {
        logger.log(Level.INFO, "trying to find page root element");
        return $x("//div[@class='wrap']").shouldBe(Condition.visible);
    }

    public SelenideElement getFilterRowOptionsRoot() {
        logger.log(Level.INFO, "trying to find filter row element");
        return getTablePageRoot().$x("./ul[@class='subsubsub']").shouldBe(Condition.visible);
    }

    public SelenideElement getBulkActionsDropdown() {
        logger.log(Level.INFO, "trying to find bulk actions dropdown");
        return $x("//div[@class='tablenav top']//select[@id='bulk-action-selector-top']").shouldBe(Condition.visible);
    }

    public SelenideElement getBulkActionsApplyBttn() {
        logger.log(Level.INFO, "trying to find bulk actions apply bttn");
        return $x("//div[@class='tablenav top']//input[@id='doaction']").shouldBe(Condition.visible);
    }

    public SelenideElement getConfirmationPopupWindow() {
        logger.log(Level.INFO, "trying to find confirmation popup");
        return getTablePageRoot().$x(".//div[@class='updated notice is-dismissible']").shouldBe(Condition.visible);
    }

    public SelenideElement getContentTableRoot() {
        logger.log(Level.INFO, "trying to get page table root element");
        return getTablePageRoot().$x("./form[@id='posts-filter']/table").shouldBe(Condition.visible);
    }

    public ElementsCollection getTableRows() {
        logger.log(Level.INFO, "trying to find table rows");
        return getContentTableRoot().$$x("./tbody/tr");
    }

}
