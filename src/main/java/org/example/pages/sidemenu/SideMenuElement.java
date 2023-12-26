package org.example.pages.sidemenu;

public enum SideMenuElement {
    DASHBOARD("menu-dashboard"),
    POSTS("menu-posts"),
    MEDIA("menu-media"),
    PAGES("menu-pages"),
    COMMENTS("menu-comments"),
    APPEARANCE("menu-appearance"),
    PLUGINS("menu-plugins"),
    USERS("menu-users"),
    TOOLS("menu-tools"),
    SETTINGS("menu-settings"),
    PERFORMANCE("toplevel_page_w3tc_dashboard"),
    SMUSH("toplevel_page_smush"),
    COLLAPSE("collapse-menu");

    SideMenuElement(String id) {
        this.id=id;
    }
    private String id;

   public String getId(){
        return id;
    }



}
