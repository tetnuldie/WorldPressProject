package org.example.pages.table.tablerow;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class MediaRow extends RowObject{
    private int id;
    private SelenideElement checkboxElement;
    private SelenideElement fileElement;
    private SelenideElement authorElement;
    private SelenideElement commentsElement;
    private SelenideElement dateElement;
    private SelenideElement smushElement;
    public MediaRow(SelenideElement root) {
        super(root);
    }

    public MediaRow init(){
        this.id = Integer.parseInt(root.getAttribute("id").split("-")[1]);
        this.checkboxElement = root.$x("./th[@class='check-column']");
        this.fileElement = root.$x("./td[@data-colname='File']");
        this.authorElement = root.$x("./td[@data-colname='Author']");
        this.commentsElement = root.$x("./td[@data-colname='Comments']");
        this.dateElement = root.$x("./td[@data-colname='Date']");
        this.smushElement = root.$x("./td[@data-colname='Smush']");
        return this;
    }

    @Override
    public void checkRow() {
        this.checkboxElement.$x(".//input").shouldBe(Condition.visible).click();
    }

    @Override
    public void openQuickEditForm() {

    }

    @Override
    public String getBodyText() {
        return this.fileElement.$x("./p").getText();
    }
}
