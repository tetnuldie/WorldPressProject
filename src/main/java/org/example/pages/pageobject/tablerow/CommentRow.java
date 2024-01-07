package org.example.pages.pageobject.tablerow;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.apache.log4j.Level;

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
        logger.log(Level.INFO, "trying to fetch selected row object properties");
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
        logger.log(Level.INFO, "trying to check row checkbox element");
        this.checkboxElement.$x(".//input").shouldBe(Condition.visible).click();
    }

    @Override
    public void openQuickEditForm() {
        logger.log(Level.INFO, "opening quick edit form");
        getRowActions().$x(".//button[@data-action='edit']").click();

    }

    @Override
    public String getBodyText() {
        logger.log(Level.INFO, "trying to get row text");
        return this.commentsElement.$x("./p").getText();
    }
}
