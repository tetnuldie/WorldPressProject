package org.example.tests.posts;

import io.qameta.allure.testng.AllureTestNg;
import org.example.pages.pageobject.tablerow.PostRow;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Instant;

@Listeners({AllureTestNg.class, PostsListener.class})
public class PostsTest extends PostsTestSetup {
    @BeforeMethod
    public void openHomePage() {
        steps.openPage();
    }

    @Test(priority = 1,
            groups = {"smoke", "post"})
    public void createNewPostAsDraftTest() {
        steps.goToNewPostPage();

        String ts = String.valueOf(Instant.now().getEpochSecond());
        String postHeader = String.format("TestPage %s", ts);
        String postBody = String.format("TestBody %s", ts);

        int postId = steps.saveDraftPostAndBack(postHeader, postBody);

        steps.goToMine(user);

        var newPostObject = steps.getRowAsObject(steps.getTableRowById(postId));
        testData.add(newPostObject);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(steps.isVisibleOnPostsPage(steps.getTableRowById(postId)), "Created post not present on Posts layout");
        softAssert.assertTrue(newPostObject.isDraft(), "Created post is not type - draft");
        softAssert.assertTrue(steps.isVisibleOnPostsPage(newPostObject.getTitleElement()), "Post title not displayed on Posts layout");
        softAssert.assertTrue(steps.isVisibleOnPostsPage(newPostObject.getDateElement()), "Date element is not displayed");
        softAssert.assertAll();
    }

    @Test(priority = 1,
            groups = {"smoke", "post"})
    public void createNewPostAsPublishedTest() {
        steps.goToNewPostPage();

        String ts = String.valueOf(Instant.now().getEpochSecond());
        String postHeader = String.format("TestPost %s", ts);
        String postBody = String.format("TestBody %s", ts);

        int postId = steps.createNewPublishedPost(postHeader, postBody);
        steps.goToMine(user);

        var newPostObject = steps.getRowAsObject(steps.getTableRowById(postId));
        testData.add(newPostObject);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(steps.isVisibleOnPostsPage(steps.getTableRowById(postId)), "Created post not present on Posts layout");
        softAssert.assertFalse(newPostObject.isDraft(), "Created post is not type - published");
        softAssert.assertTrue(steps.isVisibleOnPostsPage(newPostObject.getTitleElement()), "Post title not displayed on Posts layout");
        softAssert.assertTrue(steps.isVisibleOnPostsPage(newPostObject.getDateElement()), "Date element is not displayed");
        softAssert.assertAll();
    }

    @Test(priority = 2,
            groups = {"smoke", "post"})
    public void publishDraftPostTest() {
        steps.goToMine(user);
        var postObject = testData.stream().filter(PostRow::isDraft).findFirst().get();

        steps.openEditExistingDraftPost(postObject.getId());
        steps.publishDraftFromEditScreen();
        steps.goToMine(user);
        postObject.init();

        Assert.assertFalse(postObject.isDraft(), "Post status have not been changed");
    }

    @Test(priority = 2,
            groups = {"smoke", "post"})
    public void turnPublishedPostToDraftTest() {
        steps.goToMine(user);
        var postObject = testData.stream().filter(element -> !element.isDraft()).findFirst().get();

        steps.openEditExistingPublishedPost(postObject.getId());
        steps.switchPublishedPostToDraft();
        steps.goToMine(user);
        postObject.init();

        Assert.assertTrue(postObject.isDraft(), "Post status have not been changed");
    }

    @Test(priority = 3,
            groups = {"smoke", "post"})
    public void movePostToTrash() {
        steps.goToMine(user);
        var postObject = testData.stream().filter(element -> !element.isTrash()).findFirst().get();
        postObject.checkRow();
        steps.moveCheckedToTrash();
        steps.goToTrash();
        postObject.init();

        Assert.assertTrue(steps.isVisibleOnPostsPage(steps.getTableRowById(postObject.getId())), "Post not found in trash bin");

    }
}
