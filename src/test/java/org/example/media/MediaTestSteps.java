package org.example.media;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.login.UserLoginSteps;
import org.example.pages.PageFactory;
import org.example.pages.PageType;
import org.example.pages.pageobject.tablepage.MediaPO;
import org.example.pages.pageobject.UploadMediaPO;
import org.example.pages.pageobject.tablerow.MediaRow;
import org.example.users.User;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.switchTo;

public class MediaTestSteps {
    private final MediaPO mediaPage = PageFactory.getPage(PageType.MEDIA, MediaPO.class);
    private final UploadMediaPO uploadMediaPage = PageFactory.getPage(PageType.UPLOAD_MEDIA, UploadMediaPO.class);
    private final Logger logger = Logger.getLogger(MediaTestSteps.class);

    public void openMediaPage() {
        mediaPage.openPage();
    }

    public void closeMediaPage() {
        mediaPage.close();
    }

    public void login(User user) {
        UserLoginSteps.userLoginWoRemember(user);
    }

    public void goToUploadMediaPage() {
        mediaPage.clickAndRedirectTo(mediaPage.getAddNewBttn(), PageType.UPLOAD_MEDIA.getUrl());
    }

    public MediaRow getRowAsObject(SelenideElement trRoot) {
        logger.log(Level.INFO, "trying to get mediafile " + trRoot.getAttribute("id") + " as row object");
        var rowObject = new MediaRow(trRoot);
        rowObject.init();
        return rowObject;
    }

    public SelenideElement getTableRowById(int id) {
        logger.log(Level.INFO, "trying to get mediafile by id " + id);
        return mediaPage.getTableRows()
                .filter(Condition.id(String.format("post-%d", id)))
                .first().shouldBe(Condition.visible);
    }

    public void deleteChecked() {
        logger.log(Level.INFO, "performing delete checked rows");
        mediaPage.getBulkActionsDropdown().selectOptionByValue("delete");
        mediaPage.click(mediaPage.getBulkActionsApplyBttn());
        switchTo().alert().accept();
        switchTo().defaultContent();
        mediaPage.getConfirmationPopupWindow();
    }

    public int uploadFile(File file) {
        logger.log(Level.INFO, "performing upload file " + file.getAbsolutePath());
        uploadMediaPage.getFileInput().uploadFile(file);
        Selenide.Wait().withTimeout(Duration.ofSeconds(100)).until(webDriver -> uploadMediaPage.getUploadedFileWrapper().is(Condition.visible));

        return Integer.parseInt(uploadMediaPage.getUploadedFileWrapper().$x(".//a").getAttribute("href").split("=")[1].split("&")[0]);
    }

    public void acceptConfirm() {
        executeJavaScript(
                "window.confirm = function() {return true;};"
        );
    }

    public boolean isVisibleOnMediaPage(SelenideElement selenideElement) {
        return mediaPage.isVisible(selenideElement);
    }

    public String getConfirmationPopupMessage() {
        return mediaPage.getConfirmationPopupWindow().getText();
    }
}
