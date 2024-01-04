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

    @Override
    public SelenideElement getTableRowById(int id) {
        logger.log(Level.INFO, "trying to get comment row by id "+id);
        return getTableRows()
                .filter(Condition.id(String.format("comment-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getQuickEditRow() {
        logger.log(Level.INFO, "trying to get quick edit form");
        return getTableRows()
                .filter(Condition.id(String.format("replyrow")))
                .first();
    }

    public CommentRow getRowAsObject(SelenideElement trRoot) {
        logger.log(Level.INFO, "trying to get post "+trRoot.getAttribute("id") + " as row object");
        var rowObject = new CommentRow(trRoot);
        rowObject.init();
        return rowObject;
    }

    @Override
    public void goToMine(User user) {
        logger.log(Level.INFO, "opening 'Mine' section");
        getFilterRowOptionsRoot()
                .$x("./li[@class='mine']").shouldBe(Condition.visible)
                .click();
        Selenide.Wait().until(ExpectedConditions.urlToBe(
                String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=mine&user_id=%d", user.getUserId())
        ));
    }

    @Override
    public void goToTrash() {
        logger.log(Level.INFO, "opening 'Trash' section");
        getFilterRowOptionsRoot()
                .$x("./li[@class='trash']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=trash"
        ));
    }

    @Override
    public void quickEdit(String text) {
        logger.log(Level.INFO, "performing comment quick edit");
        getQuickEditRow().shouldBe(Condition.visible);
        getQuickEditRow().$x(".//textarea[@id = 'replycontent']").clear();
        getQuickEditRow().$x(".//textarea[@id = 'replycontent']").sendKeys(text);
        getQuickEditRow().$x(".//button[@class='save button button-primary']").click();
        Selenide.Wait().until(ExpectedConditions.invisibilityOf(getQuickEditRow()));
    }

    public void spamChecked() {
        logger.log(Level.INFO, "performing mark selected rows as spam");
        getBulkActionsDropdown().selectOptionByValue("spam");
        click(getBulkActionsApplyBttn());
        click(getConfirmationPopupWindow().$x("./button"));
    }

    public void goToSpam() {
        logger.log(Level.INFO, "opening 'Spam' section");
        getFilterRowOptionsRoot()
                .$x("./li[@class='spam']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=spam"
        ));
    }

    public void approveChecked() {
        logger.log(Level.INFO, "performing approve selected comment rows");
        getBulkActionsDropdown().selectOptionByValue("approve");
        click(getBulkActionsApplyBttn());
        click(getConfirmationPopupWindow().$x("./button"));
    }

    public void goToApproved() {
        logger.log(Level.INFO, "opening 'Approved' section");
        getFilterRowOptionsRoot()
                .$x("./li[@class='approved']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php?comment_status=approved"
        ));
    }

}
