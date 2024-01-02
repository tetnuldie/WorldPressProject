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

    @Override
    public SelenideElement getTableRowById(int id) {
        logger.log(Level.INFO, "trying to get mediafile by id "+id);
        return getTableRows()
                .filter(Condition.id(String.format("post-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public MediaRow getRowAsObject(SelenideElement trRoot) {
        logger.log(Level.INFO, "trying to get mediafile "+trRoot.getAttribute("id") + " as row object");
        var rowObject = new MediaRow(trRoot);
        rowObject.init();
        return rowObject;
    }

    public void deleteChecked() {
        logger.log(Level.INFO, "performing delete checked rows");
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
        logger.log(Level.INFO, "not implemented");

    }

    @Override
    public void goToTrash() {
        logger.log(Level.INFO, "not implemented");

    }

    @Override
    public void quickEdit(String text) {
        logger.log(Level.INFO, "not implemented");

    }
}
