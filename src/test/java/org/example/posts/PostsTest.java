package org.example.posts;

import com.codeborne.selenide.Configuration;
import org.example.pages.LoginPO;
import org.example.pages.PageProvider;
import org.example.pages.PageType;
import org.example.pages.pages.CreatePostPO;
import org.example.pages.pages.PostsPO;
import org.example.pages.pages.TdObject;
import org.example.users.User;
import org.example.users.UserProvider;
import org.example.users.UserType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PostsTest {
    static {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
    }

    private final User user = UserProvider.getUser(UserType.EDITOR);
    private final LoginPO loginPage = PageProvider.getPage(PageType.LOGIN, LoginPO.class);
    private final PostsPO postsPage = PageProvider.getPage(PageType.POSTS, PostsPO.class);
    private final CreatePostPO createPostPage = PageProvider.getPage(PageType.NEW_POST, CreatePostPO.class);
    private static List<TdObject> testData = new ArrayList<>();

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
        postsPage.goToMinePosts(user);
        testData.stream()
                .filter(element -> !element.isTrash())
                .forEach(TdObject::checkPost);
        postsPage.moveCheckedPostToTrash();
        postsPage.goToTrash();
        testData.forEach(TdObject::checkPost);
        postsPage.deleteCheckedPost();

        loginPage.close();
    }

    @Test(priority = 1)
    public void createNewPageAsDraftTest() {
        postsPage.clickAndRedirectTo(postsPage.getAddNewBttn(), PageType.NEW_POST.getUrl());

        String ts = String.valueOf(Instant.now().getEpochSecond());
        String postHeader = String.format("TestPage %s", ts);
        String postBody = String.format("TestBody %s", ts);

        int postId = createPostPage.saveDraftPostAndBack(postHeader, postBody);

        postsPage.goToMinePosts(user);

        TdObject newPostObject = postsPage.getPostAsObject(postsPage.getPostRowById(postId));
        testData.add(newPostObject);

        Assert.assertTrue(postsPage.isVisible(postsPage.getPostRowById(postId)), "Created post not present on Posts layout");
        Assert.assertTrue(newPostObject.isDraft(), "Created post is not type - draft");
        Assert.assertTrue(postsPage.isVisible(newPostObject.getTitleElement()), "Post title not displayed on Posts layout");
        Assert.assertTrue(postsPage.isVisible(newPostObject.getDateElement()), "Date element is not displayed");
    }

    @Test(priority = 1)
    public void createNewPageAsPublishedTest() {
        postsPage.clickAndRedirectTo(postsPage.getAddNewBttn(), PageType.NEW_POST.getUrl());

        String ts = String.valueOf(Instant.now().getEpochSecond());
        String postHeader = String.format("TestPost %s", ts);
        String postBody = String.format("TestBody %s", ts);

        int postId = createPostPage.createNewPublishedPost(postHeader, postBody);
        postsPage.goToMinePosts(user);

        TdObject newPostObject = postsPage.getPostAsObject(postsPage.getPostRowById(postId));
        testData.add(newPostObject);

        Assert.assertTrue(postsPage.isVisible(postsPage.getPostRowById(postId)), "Created post not present on Posts layout");
        Assert.assertFalse(newPostObject.isDraft(), "Created post is not type - published");
        Assert.assertTrue(postsPage.isVisible(newPostObject.getTitleElement()), "Post title not displayed on Posts layout");
        Assert.assertTrue(postsPage.isVisible(newPostObject.getDateElement()), "Date element is not displayed");
    }

    @Test(priority = 2)
    public void publishDraftPageTest() {
        postsPage.goToMinePosts(user);
        TdObject item = testData.stream().filter(TdObject::isDraft).findFirst().get();
        testData.remove(item);

        postsPage.openEditExistingDraftPost(item.getPostId());
        createPostPage.publishDraftFromEditScreen();
        postsPage.goToMinePosts(user);

        TdObject newPostObject = postsPage.getPostAsObject(postsPage.getPostRowById(item.getPostId()));
        testData.add(newPostObject);

        Assert.assertFalse(newPostObject.isDraft(), "Post status have not been changed");
    }

    @Test(priority = 2)
    public void turnPublishedPageToDraftTest() {
        postsPage.goToMinePosts(user);
        TdObject item = testData.stream().filter(element -> !element.isDraft()).findFirst().get();
        testData.remove(item);

        postsPage.openEditExistingPublishedPost(item.getPostId());
        createPostPage.switchPublishedPostToDraft();
        postsPage.goToMinePosts(user);

        TdObject newPostObject = postsPage.getPostAsObject(postsPage.getPostRowById(item.getPostId()));
        testData.add(newPostObject);

        Assert.assertTrue(newPostObject.isDraft(), "Post status have not been changed");
    }

    @Test(priority = 3)
    public void movePageToTrash() {
        postsPage.goToMinePosts(user);
        TdObject item = testData.stream().filter(element -> !element.isTrash()).findFirst().get();
        item.checkPost();
        testData.remove(item);
        postsPage.moveCheckedPostToTrash();
        postsPage.goToTrash();

        TdObject newItem = postsPage.getPostAsObject(postsPage.getPostRowById(item.getPostId()));
        testData.add(newItem);

        Assert.assertTrue(postsPage.isVisible(postsPage.getPostRowById(item.getPostId())), "Post not found in trash bin");

    }
}
