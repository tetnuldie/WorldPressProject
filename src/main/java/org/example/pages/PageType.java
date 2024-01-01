package org.example.pages;

public enum PageType {
    LOGIN("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-login.php"),
    MAIN("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/"),
    PAGES("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_type=page"),
    NEW_PAGE("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post-new.php?post_type=page"),
    POSTS("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php"),
    NEW_POST("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post-new.php"),
    COMMENTS("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php"),
    PROFILE("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/profile.php"),
    PUBLIC_POST("https://wordpress-test-app-for-selenium.azurewebsites.net/2023/12/30/do_not_delete/"),
    MEDIA("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/upload.php?mode=list"),
    UPLOAD_MEDIA("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/media-new.php");

    private String url;
    PageType(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
