package org.example.media;

import com.codeborne.selenide.Configuration;
import org.example.pages.LoginPO;
import org.example.pages.PageFactory;
import org.example.pages.PageType;
import org.example.pages.table.MediaPO;
import org.example.pages.table.UploadMediaPO;
import org.example.users.User;
import org.example.users.UserFactory;
import org.example.users.UserType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;

public class MediaTest {
    static {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
    }

    private final User user = UserFactory.getUser(UserType.AUTHOR);
    private final LoginPO loginPage = PageFactory.getPage(PageType.LOGIN, LoginPO.class);
    private final MediaPO mediaPage = PageFactory.getPage(PageType.MEDIA, MediaPO.class);
    private final UploadMediaPO uploadMediaPage = PageFactory.getPage(PageType.UPLOAD_MEDIA, UploadMediaPO.class);
    private final File file = new File("src/Resources/pepe.jpg");
    private int fileId;

    @BeforeClass
    public void login() {
        loginPage.userLoginWoRemember(user);
    }

    @BeforeMethod
    public void openHomePage() {
        mediaPage.openPage();
    }

    @AfterClass
    public void cleanup(){
        loginPage.close();
    }

    @Test
    public void uploadMediaTest(){
        mediaPage.clickAndRedirectTo(mediaPage.getAddNewBttn(), PageType.UPLOAD_MEDIA.getUrl());
        fileId = uploadMediaPage.uploadFile(file);
        mediaPage.openPage();
        var mediaRow = mediaPage.getRowAsObject(mediaPage.getTableRowById(fileId));
        mediaRow.init();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(mediaPage.isVisible(mediaRow.getCheckboxElement()), "Checkbox is not displayed");
        softAssert.assertTrue(mediaPage.isVisible(mediaRow.getFileElement()), "File element is not displayed");
        softAssert.assertTrue(mediaPage.isVisible(mediaRow.getAuthorElement()), "Author element is not displayed");
        softAssert.assertTrue(mediaPage.isVisible(mediaRow.getCommentsElement()), "Comments element is not displayed");
        softAssert.assertTrue(mediaPage.isVisible(mediaRow.getDateElement()), "Date element is not displayed");
        softAssert.assertTrue(mediaPage.isVisible(mediaRow.getSmushElement()), "Smush element is not displayed");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "uploadMediaTest")
    public void deleteMediaTest(){
        var mediaRow = mediaPage.getRowAsObject(mediaPage.getTableRowById(fileId));
        mediaRow.init();
        mediaRow.checkRow();
        mediaPage.deleteChecked();
        Assert.assertTrue(mediaPage.getConfirmationPopupWindow().getText().contains("Media file permanently deleted."));
    }
}
