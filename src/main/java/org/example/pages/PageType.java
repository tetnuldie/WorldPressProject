package org.example.pages;

public enum PageType {
    LOGIN("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-login.php"),
    MAIN("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/"),
    PAGES("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php?post_type=page"),
    NEW_PAGE("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post-new.php?post_type=page"),
    POSTS("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit.php"),
    NEW_POST("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/post-new.php"),
    MEDIA("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/upload.php"),
    COMMENTS("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/edit-comments.php"),
    PROFILE("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-admin/profile.php");

    private String url;
    PageType(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
