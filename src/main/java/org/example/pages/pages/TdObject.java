package org.example.pages.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.util.Objects;

public class TdObject {
    private final int postId;
    private boolean isDraft;
    private boolean isTrash;
    private final SelenideElement checkboxElement;
    private final SelenideElement titleElement;
    private final SelenideElement authorElement;
    private final SelenideElement commentsElement;
    private final SelenideElement dateElement;

    public TdObject(SelenideElement trRoot){
        this.postId = Integer.parseInt(trRoot.getAttribute("id").split("-")[1]);
        this.checkboxElement = trRoot.$x("./th[@class='check-column']");
        this.titleElement = trRoot.$x("./td[@data-colname='Title']");
        this.authorElement = trRoot.$x("./td[@data-colname='Author']");
        this.commentsElement = trRoot.$x("./td[@data-colname='Comments']");
        this.dateElement = trRoot.$x("./td[@data-colname='Date']");
        this.isDraft = trRoot.getAttribute("class").toLowerCase().contains("status-draft");
        this.isTrash = trRoot.getAttribute("class").toLowerCase().contains("status-trash");
    }

    public int getPostId() {
        return postId;
    }

    public SelenideElement getCheckboxElement() {
        return checkboxElement.shouldBe(Condition.visible);
    }

    public SelenideElement getTitleElement() {
        return titleElement.shouldBe(Condition.visible);
    }

    public SelenideElement getAuthorElement() {
        return authorElement.shouldBe(Condition.visible);
    }

    public SelenideElement getCommentsElement() {
        return commentsElement.shouldBe(Condition.visible);
    }

    public SelenideElement getDateElement() {
        return dateElement.shouldBe(Condition.visible);
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public boolean isTrash() {
        return isTrash;
    }

    public void setTrash(boolean trash) {
        isTrash = trash;
    }
    public void checkPost(){
        this.checkboxElement.$x(".//input").shouldBe(Condition.visible).click();
    }
    public String getPostTitle(){
        return titleElement.$x(".//a[@class='row-title']").getText();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdObject tdObject)) return false;
        return getPostId() == tdObject.getPostId() && Objects.equals(getCheckboxElement(), tdObject.getCheckboxElement()) && Objects.equals(getTitleElement(), tdObject.getTitleElement()) && Objects.equals(getAuthorElement(), tdObject.getAuthorElement()) && Objects.equals(getCommentsElement(), tdObject.getCommentsElement()) && Objects.equals(getDateElement(), tdObject.getDateElement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getCheckboxElement(), getTitleElement(), getAuthorElement(), getCommentsElement(), getDateElement());
    }

    @Override
    public String toString() {
        return "TdObject{" +
                "id=" + postId +
                ", checkboxElement=" + checkboxElement +
                ", titleElement=" + titleElement +
                ", authorElement=" + authorElement +
                ", commentsElement=" + commentsElement +
                ", dateElement=" + dateElement +
                '}';
    }
}
