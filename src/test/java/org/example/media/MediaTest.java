package org.example.media;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

@Listeners({AllureTestNg.class, MediaListener.class})
public class MediaTest extends MediaTestSetup {
    @BeforeMethod
    public void openHomePage() {
        steps.openMediaPage();
    }

    @Test(groups = {"smoke", "media"})
    public void uploadMediaTest() {
        steps.goToUploadMediaPage();
        fileId = steps.uploadFile(file);
        steps.openMediaPage();
        var mediaRow = steps.getRowAsObject(steps.getTableRowById(fileId));
        mediaRow.init();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(steps.isVisibleOnMediaPage(mediaRow.getCheckboxElement()), "Checkbox is not displayed");
        softAssert.assertTrue(steps.isVisibleOnMediaPage(mediaRow.getFileElement()), "File element is not displayed");
        softAssert.assertTrue(steps.isVisibleOnMediaPage(mediaRow.getAuthorElement()), "Author element is not displayed");
        softAssert.assertTrue(steps.isVisibleOnMediaPage(mediaRow.getCommentsElement()), "Comments element is not displayed");
        softAssert.assertTrue(steps.isVisibleOnMediaPage(mediaRow.getDateElement()), "Date element is not displayed");
        softAssert.assertTrue(steps.isVisibleOnMediaPage(mediaRow.getSmushElement()), "Smush element is not displayed");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "uploadMediaTest",
            groups = {"smoke", "media"})
    public void deleteMediaTest() {
        var mediaRow = steps.getRowAsObject(steps.getTableRowById(fileId));
        mediaRow.init();
        mediaRow.checkRow();
        steps.deleteChecked();
        Assert.assertTrue(steps.getConfirmationPopupMessage().contains("Media file permanently deleted."));
    }
}
