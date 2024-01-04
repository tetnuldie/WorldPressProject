package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.Page;
import org.example.pages.PageMenuFunc;
import org.example.pages.PageType;
import org.example.users.User;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;

public abstract class TablePagePO implements PageMenuFunc, Page {

    protected final PageType pageType;
    protected final Logger logger = Logger.getLogger(TablePagePO.class);

    public TablePagePO(PageType pageType) {
        this.pageType = pageType;
    }

    public PageType getPageType() {
        return pageType;
    }

    public abstract SelenideElement getContentTableRoot();

    public SelenideElement getTablePageRoot() {
        logger.log(Level.INFO, "trying to find page root element");
        return $x("//div[@class='wrap']").shouldBe(Condition.visible);
    }

    public SelenideElement getFilterRowOptionsRoot() {
        logger.log(Level.INFO, "trying to find filter row element");
        return getTablePageRoot().$x("./ul[@class='subsubsub']").shouldBe(Condition.visible);
    }

    public ElementsCollection getTableRows() {
        logger.log(Level.INFO, "trying to find table rows");
        return getContentTableRoot().$$x("./tbody/tr");
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

    @Override
    public void hover(SelenideElement element) {
        logger.log(Level.INFO, "hovering on " + element.getSearchCriteria());
        element.shouldBe(Condition.visible).hover();
    }

    @Override
    public void click(SelenideElement element) {
        logger.log(Level.INFO, "clicking on " + element.getSearchCriteria());
        element.shouldBe(Condition.visible).click();
    }

    @Override
    public boolean isVisible(SelenideElement element) {
        logger.log(Level.INFO, "performing visibility check of " + element.getSearchCriteria());
        return element.shouldBe(Condition.visible).isDisplayed();
    }

    @Override
    public void openPage() {
        logger.log(Level.INFO, "opening page '"+pageType.getUrl()+"'");
        open(pageType.getUrl());
    }

    @Override
    public void openPageWithWaiter(String url) {
        logger.log(Level.INFO, "opening page " + pageType.getUrl() + " with loading waiter");
        open(url);
        Selenide.Wait().until(ExpectedConditions.urlToBe(url));
    }

    @Override
    public void clickAndRedirectTo(SelenideElement element, String expectedUrl) {
        logger.log(Level.INFO, "redirecting to " + pageType.getUrl() + " after click on " + element.getSearchCriteria());
        element.click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(expectedUrl));
    }

    public void close() {
        logger.log(Level.INFO, "closing browser");
        webdriver().driver().getWebDriver().quit();
    }
}
