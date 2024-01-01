package org.example.page;

import com.codeborne.selenide.Configuration;
import org.example.pages.*;
import org.example.pages.table.CreatePostPO;
import org.example.pages.table.PostsPO;
import org.example.pages.table.tablerow.PostRow;
import org.example.users.User;
import org.example.users.UserFactory;
import org.example.users.UserType;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PagesTest {
    static {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
    }

    private final User user = UserFactory.getUser(UserType.EDITOR);
    private final LoginPO loginPage = PageFactory.getPage(PageType.LOGIN, LoginPO.class);
    private final PostsPO postsPage = PageFactory.getPage(PageType.PAGES, PostsPO.class);
    private final CreatePostPO createPostPage = PageFactory.getPage(PageType.NEW_PAGE, CreatePostPO.class);
    private static List<PostRow> testData = new ArrayList<>();

    @BeforeClass
    public void login() {
        loginPage.userLoginWoRemember(user);
    }

    @BeforeMethod
    public void openHomePage() {
        postsPage.openPage();
    }

    @AfterClass
    public void cleanup() {
        postsPage.goToMine(user);
        testData.stream()
                .filter(element -> !element.isTrash())
                .forEach(PostRow::checkRow);
        postsPage.moveCheckedToTrash();
        postsPage.goToTrash();
        testData.forEach(PostRow::checkRow);
        postsPage.deleteChecked();

        loginPage.close();
    }

    @Test(priority = 1)
    public void createNewPageAsDraftTest() {
        postsPage.clickAndRedirectTo(postsPage.getAddNewBttn(), PageType.NEW_PAGE.getUrl());

        String ts = String.valueOf(Instant.now().getEpochSecond());
        String postHeader = String.format("TestPage %s", ts);
        String postBody = String.format("TestBody %s", ts);

        int postId = createPostPage.saveDraftPostAndBack(postHeader, postBody);

        postsPage.goToMine(user);

        var newPostObject = postsPage.getRowAsObject(postsPage.getTableRowById(postId));
        testData.add(newPostObject);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(postsPage.isVisible(postsPage.getTableRowById(postId)), "Created page not present on Pages layout");
        softAssert.assertTrue(newPostObject.isDraft(), "Created page is not type - draft");
        softAssert.assertTrue(postsPage.isVisible(newPostObject.getTitleElement()), "Page title not displayed on Pages layout");
        softAssert.assertTrue(postsPage.isVisible(newPostObject.getDateElement()), "Date element is not displayed");
        softAssert.assertAll();
    }

    @Test(priority = 1)
    public void createNewPageAsPublishedTest() {
        postsPage.clickAndRedirectTo(postsPage.getAddNewBttn(), PageType.NEW_PAGE.getUrl());

        String ts = String.valueOf(Instant.now().getEpochSecond());
        String postHeader = String.format("TestPage %s", ts);
        String postBody = String.format("TestBody %s", ts);

        int postId = createPostPage.createNewPublishedPost(postHeader, postBody);
        postsPage.goToMine(user);

        var newPostObject = postsPage.getRowAsObject(postsPage.getTableRowById(postId));
        testData.add(newPostObject);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(postsPage.isVisible(postsPage.getTableRowById(postId)), "Created page not present on Pages layout");
        softAssert.assertFalse(newPostObject.isDraft(), "Created page is not type - published");
        softAssert.assertTrue(postsPage.isVisible(newPostObject.getTitleElement()), "Page title not displayed on Pages layout");
        softAssert.assertTrue(postsPage.isVisible(newPostObject.getDateElement()), "Date element is not displayed");
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void publishDraftPageTest() {
        postsPage.goToMine(user);
        var postObject = testData.stream().filter(PostRow::isDraft).findFirst().get();

        postsPage.openEditExistingDraftPost(postObject.getId());
        createPostPage.publishDraftFromEditScreen();
        postsPage.goToMine(user);
        postObject.init();

        Assert.assertFalse(postObject.isDraft(), "Post status have not been changed");
    }

    @Test(priority = 2)
    public void turnPublishedPageToDraftTest() {
        postsPage.goToMine(user);
        var postObject = testData.stream().filter(element -> !element.isDraft()).findFirst().get();

        postsPage.openEditExistingPublishedPost(postObject.getId());
        createPostPage.switchPublishedPostToDraft();
        postsPage.goToMine(user);
        postObject.init();

        Assert.assertTrue(postObject.isDraft(), "Post status have not been changed");
    }

    @Test(priority = 3)
    public void movePageToTrash() {
        postsPage.goToMine(user);
        var postObject = testData.stream().filter(element -> !element.isTrash()).findFirst().get();
        postObject.checkRow();
        postsPage.moveCheckedToTrash();
        postsPage.goToTrash();

        postObject.init();
        Assert.assertTrue(postsPage.isVisible(postsPage.getTableRowById(postObject.getId())), "Page not found in trash bin");

    }
}
