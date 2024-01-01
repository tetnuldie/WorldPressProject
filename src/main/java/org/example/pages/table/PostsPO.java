package org.example.pages.table;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.pages.PageType;
import org.example.pages.table.tablerow.PostRow;
import org.example.users.User;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PostsPO extends TablePagePO{

    public PostsPO(PageType pageType) {
        super(pageType);
    }
    public SelenideElement getAddNewBttn() {
        return getTablePageRoot().$x("./a[@class='page-title-action']").shouldBe(Condition.visible);
    }

    public SelenideElement getContentTableRoot() {
        return getTablePageRoot().$x("./form[@id='posts-filter']/table").shouldBe(Condition.visible);
    }

    public ElementsCollection getDraftRows() {
        return getTableRows().filter(Condition.cssClass("status-draft"));
    }

    public ElementsCollection getPublishedRows() {
        return getTableRows().filter(Condition.cssClass("status-publish"));
    }

    public ElementsCollection getTrashRows() {
        return getTableRows().filter(Condition.cssClass("status-trash"));
    }

    public SelenideElement getPostDraftRowByTitleAndUser(String postHeader, String userLogin) {
        return getDraftRows()
                .filter(Condition.matchText(postHeader + " — Draft\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getPostPublishedRowByTitleAndUser(String postHeader, String userLogin) {
        return getPublishedRows()
                .filter(Condition.matchText(postHeader + "\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getPostTrashRowByTitleAndUser(String postHeader, String userLogin) {
        return getTrashRows()
                .filter(Condition.matchText(postHeader + "\\n" + userLogin))
                .first().shouldBe(Condition.visible);
    }

    public SelenideElement getTableRowById(int id) {
        return getTableRows()
                .filter(Condition.id(String.format("post-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public PostRow getRowAsObject(SelenideElement trRoot) {
        var rowObject = new PostRow(trRoot);
        rowObject.init();
        return rowObject;
    }

    public void openEditExistingDraftPost(int draftId) {
        clickAndRedirectTo(getDraftRows()
                .filter(Condition.id(String.format("post-%d", draftId)))
                .first().$x(".//a[@class='row-title']"), String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post.php?post=%d&action=edit", draftId));
    }

    public void openEditExistingPublishedPost(int publishedId) {
        clickAndRedirectTo(getPublishedRows()
                .filter(Condition.id(String.format("post-%d", publishedId)))
                .first().$x(".//a[@class='row-title']"), String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post.php?post=%d&action=edit", publishedId));
    }


    public void goToMine(User user) {
        getFilterRowOptionsRoot()
                .$x("./li[@class='mine']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                pageType == PageType.PAGES ?
                        String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_type=page&author=%d", user.getUserId())
                        : String.format("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_type=post&author=%d", user.getUserId())
        ));
    }

    public void goToTrash() {
        getFilterRowOptionsRoot()
                .$x("./li[@class='trash']").shouldBe(Condition.visible)
                .click();

        Selenide.Wait().until(ExpectedConditions.urlToBe(
                this.pageType == PageType.PAGES ?
                        "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_status=trash&post_type=page"
                        : "https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_status=trash&post_type=post"
        ));
    }

    @Override
    public void quickEdit(String text) {

    }
}
