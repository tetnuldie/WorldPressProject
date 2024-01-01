package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.pages.PageType;
import org.example.pages.table.tablerow.CommentRow;
import org.example.users.User;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CommentsPO extends TablePagePO {

    public CommentsPO(PageType pageType) {
        super(pageType);
    }

    @Override
    public SelenideElement getContentTableRoot() {
        return getTablePageRoot().$x("./form[@id='comments-form']/table").shouldBe(Condition.visible);
    }

    @Override
    public SelenideElement getTableRowById(int id) {
        return getTableRows()
                .filter(Condition.id(String.format("comment-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getQuickEditRow() {
        return getTableRows()
                .filter(Condition.id(String.format("replyrow")))
                .first();
    }

    public CommentRow getRowAsObject(SelenideElement trRoot) {
        var rowObject = new CommentRow(trRoot);
        rowObject.init();
        return rowObject;
    }

    @Override
    public void goToMine(User user) {
        getFilterRowOptionsRoot()
                .$x("./li[@class='mine']").shouldBe(Condition.visible)
                .click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(
                String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=mine&user_id=%d", user.getUserId())
        ));
    }

    @Override
    public void goToTrash() {
        getFilterRowOptionsRoot()
                .$x("./li[@class='trash']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=trash"
        ));
    }

    @Override
    public void quickEdit(String text) {
        getQuickEditRow().shouldBe(Condition.visible);
        getQuickEditRow().$x(".//textarea[@id = 'replycontent']").clear();
        getQuickEditRow().$x(".//textarea[@id = 'replycontent']").sendKeys(text);
        getQuickEditRow().$x(".//button[@class='save button button-primary']").click();
        Selenide.Wait().until(ExpectedConditions.invisibilityOf(getQuickEditRow()));
    }

    public void spamChecked() {
        getBulkActionsDropdown().selectOptionByValue("spam");
        click(getBulkActionsApplyBttn());
        click(getConfirmationPopupWindow().$x("./button"));
    }

    public void goToSpam() {
        getFilterRowOptionsRoot()
                .$x("./li[@class='spam']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=spam"
        ));
    }

    public void approveChecked() {
        getBulkActionsDropdown().selectOptionByValue("approve");
        click(getBulkActionsApplyBttn());
        click(getConfirmationPopupWindow().$x("./button"));
    }

    public void goToApproved() {
        getFilterRowOptionsRoot()
                .$x("./li[@class='approved']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=approved"
        ));
    }

}
