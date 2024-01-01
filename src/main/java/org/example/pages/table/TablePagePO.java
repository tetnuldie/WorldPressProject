package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.pages.Page;
import org.example.pages.PageMenuFunc;
import org.example.pages.PageType;
import org.example.users.User;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public abstract class TablePagePO implements PageMenuFunc, Page {
    protected final PageType pageType;
    public TablePagePO(PageType pageType) {
        this.pageType = pageType;
    }
    public abstract SelenideElement getContentTableRoot();
    public abstract SelenideElement getTableRowById(int id);
    public abstract void goToMine(User user);
    public abstract void goToTrash();
    public abstract void quickEdit(String text);

    public SelenideElement getTablePageRoot() {//common
        return $x("//div[@class='wrap']").shouldBe(Condition.visible);
    }
    public SelenideElement getFilterRowOptionsRoot() {//common
        return getTablePageRoot().$x("./ul[@class='subsubsub']").shouldBe(Condition.visible);
    }
    public ElementsCollection getTableRows() {
        return getContentTableRoot().$$x("./tbody/tr");
    }
    public SelenideElement getBulkActionsDropdown() {
        return $x("//div[@class='tablenav top']//select[@id='bulk-action-selector-top']").shouldBe(Condition.visible);
    }
    public SelenideElement getBulkActionsApplyBttn() {
        return $x("//div[@class='tablenav top']//input[@id='doaction']").shouldBe(Condition.visible);
    }
    public SelenideElement getConfirmationPopupWindow() {
        return getTablePageRoot().$x(".//div[@class='updated notice is-dismissible']").shouldBe(Condition.visible);
    }
    public void moveCheckedToTrash() {
        getBulkActionsDropdown().selectOptionByValue("trash");
        click(getBulkActionsApplyBttn());
        click(getConfirmationPopupWindow().$x("./button"));
    }
    public void deleteChecked() {
        getBulkActionsDropdown().selectOptionByValue("delete");
        click(getBulkActionsApplyBttn());
    }

    @Override
    public void openPage() {
        open(pageType.getUrl());
    }

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
        return element.isDisplayed();
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
