package org.example.pages.pageobject.tablerow;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.apache.log4j.Level;

@Getter
public class PostRow extends RowObject{
    private int id;
    private boolean isDraft;
    private boolean isTrash;
    private SelenideElement checkboxElement;
    private SelenideElement titleElement;
    private SelenideElement authorElement;
    private SelenideElement commentsElement;
    private SelenideElement dateElement;

    public PostRow(SelenideElement root){
        super(root);
    }

    public PostRow init(){
        logger.log(Level.INFO, "trying to fetch selected row object properties");
        this.id = Integer.parseInt(root.getAttribute("id").split("-")[1]);
        this.checkboxElement = root.$x("./th[@class='check-column']");
        this.titleElement = root.$x("./td[@data-colname='Title']");
        this.authorElement = root.$x("./td[@data-colname='Author']");
        this.commentsElement = root.$x("./td[@data-colname='Comments']");
        this.dateElement = root.$x("./td[@data-colname='Date']");
        this.isDraft = root.getAttribute("class").toLowerCase().contains("status-draft");
        this.isTrash = root.getAttribute("class").toLowerCase().contains("status-trash");
        return this;
    }

    public void checkRow(){
        logger.log(Level.INFO, "trying to check row checkbox element");
        this.checkboxElement.$x(".//input").shouldBe(Condition.visible).click();
    }

    @Override
    public void openQuickEditForm() {
        logger.log(Level.INFO, "opening quick edit form");
        getRowActions().$x("//button[@class='button-link editinline']").click();
    }

    @Override
    public String getBodyText() {
        logger.log(Level.INFO, "trying to get row text");
        return this.authorElement.$x(".//a[@class='row-title']").getText();
    }
}
