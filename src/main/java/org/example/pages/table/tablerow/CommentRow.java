package org.example.pages.table.tablerow;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class CommentRow extends RowObject{
    private int id;
    private boolean isPending;
    private boolean isTrash;
    private boolean isSpam;
    private SelenideElement checkboxElement;
    private SelenideElement authorElement;
    private SelenideElement commentsElement;
    private SelenideElement dateElement;
    private SelenideElement responseElement;

    public CommentRow(SelenideElement root){
        super(root);
    }

    public CommentRow init() {
        this.id = Integer.parseInt(root.getAttribute("id").split("-")[1]);
        this.checkboxElement = root.$x("./th[@class='check-column']");
        this.authorElement = root.$x("./td[@data-colname='Author']");
        this.commentsElement = root.$x("./td[@data-colname='Comment']");
        this.responseElement = root.$x("./td[@data-colname='In response to']");
        this.dateElement = root.$x("./td[@data-colname='Submitted on']");
        this.isPending = root.getAttribute("class").toLowerCase().contains("unapproved");
        this.isSpam = root.getAttribute("class").toLowerCase().contains("spam");
        this.isTrash = root.getAttribute("class").toLowerCase().contains("trash");
        return this;
    }

    public void checkRow() {
        this.checkboxElement.$x(".//input").shouldBe(Condition.visible).click();
    }

    @Override
    public void openQuickEditForm() {
        getRowActions().$x(".//button[@data-action='edit']").click();

    }

    @Override
    public String getBodyText() {
        return this.commentsElement.$x("./p").getText();
    }
}
