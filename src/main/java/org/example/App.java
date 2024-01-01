package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Modal;
import org.example.pages.LoginPO;
import org.example.pages.PageType;
import org.example.pages.table.MediaPO;
import org.example.pages.table.UploadMediaPO;
import org.example.users.UserFactory;
import org.example.users.UserType;

import java.io.File;

public class App
{
    static {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
    }
    public static void main( String[] args )
    {
        LoginPO login = new LoginPO();
        MediaPO media = new MediaPO(PageType.MEDIA);
        UploadMediaPO upload = new UploadMediaPO(PageType.UPLOAD_MEDIA);
        File file = new File("src/Resources/pepe.jpg");

/*        login.openPage();
        login.userLoginWoRemember(UserFactory.getUser(UserType.EDITOR));

        media.openPage();
        media.clickAndRedirectTo(media.getAddNewBttn(), PageType.UPLOAD_MEDIA.getUrl());
        int id = upload.uploadFile(file);
        media.openPage();


        var mediaRow = media.getRowAsObject(media.getTableRowById(id));
        mediaRow.checkRow();
        media.deleteChecked();*/


        System.out.println();





        System.out.println();

/*        mainPage.hover(PageMenuFunc.getSideMenuElement(SideMenuElement.COMMENTS));
        System.out.println();*/


    }
}
