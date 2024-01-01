package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.pages.PageType;
import org.example.pages.table.tablerow.MediaRow;
import org.example.pages.table.tablerow.PostRow;
import org.example.users.User;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.switchTo;

public class MediaPO extends TablePagePO {
    public MediaPO(PageType pageType) {
        super(pageType);
    }

    public SelenideElement getAddNewBttn() {
        return getTablePageRoot().$x("./a[@class='page-title-action']").shouldBe(Condition.visible);
    }

    public SelenideElement getFilterRowOptionsRoot() {
        return null;
    }

    @Override
    public SelenideElement getContentTableRoot() {
        return getTablePageRoot().$x("./form[@id='posts-filter']/table").shouldBe(Condition.visible);
    }

    @Override
    public SelenideElement getTableRowById(int id) {
        return getTableRows()
                .filter(Condition.id(String.format("post-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public MediaRow getRowAsObject(SelenideElement trRoot) {
        var rowObject = new MediaRow(trRoot);
        rowObject.init();
        return rowObject;
    }

    public void deleteChecked() {
        getBulkActionsDropdown().selectOptionByValue("delete");
        click(getBulkActionsApplyBttn());
        switchTo().alert().accept();
        switchTo().defaultContent();
        getConfirmationPopupWindow();
    }

    public void acceptConfirm() {
        executeJavaScript(
                "window.confirm = function() {return true;};"
        );
    }


    @Override
    public void goToMine(User user) {
        //log

    }

    @Override
    public void goToTrash() {
        //log

    }

    @Override
    public void quickEdit(String text) {
        //log

    }
}
