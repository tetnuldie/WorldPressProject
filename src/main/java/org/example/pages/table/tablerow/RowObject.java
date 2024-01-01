package org.example.pages.table.tablerow;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class RowObject {
    protected final SelenideElement root;
    public abstract void checkRow();
    public abstract void openQuickEditForm();
    public abstract String getBodyText();

    public SelenideElement getRowActions(){
        root.hover();
        return root.$x(".//div[@class='row-actions']");
    }
}
