package org.example.pages.menuelements;

public enum AdminBarElement {
    LOGO("wp-admin-bar-wp-logo"),
    HOME("wp-admin-bar-site-name"),
    UPDATES("wp-admin-bar-updates"),
    COMMENTS("wp-admin-bar-comments"),
    NEW("wp-admin-bar-new-content"),
    PERFORMANCE("wp-admin-bar-w3tc"),
    USER("wp-admin-bar-my-account");

    private String id;

    AdminBarElement(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}
