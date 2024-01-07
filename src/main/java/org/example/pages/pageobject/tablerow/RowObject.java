package org.example.pages.pageobject.tablerow;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@Getter
@RequiredArgsConstructor
public abstract class RowObject {
    protected final Logger logger = Logger.getLogger(RowObject.class);
    protected final SelenideElement root;
    public abstract void checkRow();
    public abstract void openQuickEditForm();
    public abstract String getBodyText();

    public SelenideElement getRowActions(){
        logger.log(Level.INFO, "trying to get row actions menu");
        root.hover();
        return root.$x(".//div[@class='row-actions']");
    }
}
