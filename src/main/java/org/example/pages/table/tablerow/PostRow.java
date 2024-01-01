package org.example.pages.table.tablerow;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

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
        this.checkboxElement.$x(".//input").shouldBe(Condition.visible).click();
    }

    @Override
    public void openQuickEditForm() {
        getRowActions().$x("//button[@class='button-link editinline']").click();
    }

    @Override
    public String getBodyText() {
        return this.authorElement.$x(".//a[@class='row-title']").getText();
    }
}
