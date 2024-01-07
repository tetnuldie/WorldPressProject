package org.example.pages.pageobject;

import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.MainMenuFunc;
import org.example.pages.PageType;

import static com.codeborne.selenide.Selenide.*;

public class UploadMediaPO extends AbstractPage implements MainMenuFunc {
    private final Logger logger = Logger.getLogger(UploadMediaPO.class);

    public UploadMediaPO(PageType pageType) {
        super(pageType);
    }

    public SelenideElement getFileInput() {
        logger.log(Level.INFO, "trying to find file input element");
        return $x("//input[@type='file']");
    }

    public SelenideElement getDropBoxArea() {
        logger.log(Level.INFO, "trying to find dropbox area");
        return $x("//div[@id='drag-drop-area']");
    }

    public SelenideElement getMediaItemsRoot() {
        logger.log(Level.INFO, "trying to find media items root element");
        return $x("//div[@id='media-items']");
    }

    public SelenideElement getUploadedFileWrapper() {
        logger.log(Level.INFO, "trying to find media items wrapper element");
        return $x("//div[@class='media-item-wrapper']");
    }
}
