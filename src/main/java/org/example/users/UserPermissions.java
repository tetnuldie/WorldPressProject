package org.example.users;

import java.util.*;

public class UserPermissions {
    /**
     *     DASHBOARD("menu-dashboard"),
     *     POSTS("menu-posts"),
     *     MEDIA("menu-media"),
     *     PAGES("menu-pages"),
     *     COMMENTS("menu-comments"),
     *     APPEARANCE("menu-appearance"),
     *     PLUGINS("menu-plugins"),
     *     USERS("menu-users"),
     *     TOOLS("menu-tools"),
     *     SETTINGS("menu-settings"),
     *     PERFORMANCE("toplevel_page_w3tc_dashboard"),
     *     SMUSH("toplevel_page_smush"),
     *     COLLAPSE("collapse-menu");
     */
    private static Map<UserType, List<String>> repository;

    static {
        repository = new HashMap<>();
        repository.put(UserType.SUBSCRIBER, new ArrayList<>(Arrays.asList("Dashboard", "Profile", "Collapse")));
        repository.put(UserType.CONTRIBUTOR, new ArrayList<>(Arrays.asList("Dashboard", "Posts", "Comments", "Profile", "Tools", "Collapse")));
        repository.put(UserType.AUTHOR, new ArrayList<>(Arrays.asList("Dashboard", "Posts", "Media", "Comments", "Profile", "Tools", "Collapse")));
        repository.put(UserType.EDITOR, new ArrayList<>(Arrays.asList("Dashboard", "Posts", "Media", "Pages", "Comments", "Profile", "Tools", "Collapse")));
        repository.put(UserType.ADMIN, new ArrayList<>(Arrays.asList("Dashboard", "Posts", "Media", "Pages", "Comments", "Appearance", "Plugins", "Users", "Tools", "Settings", "Performance", "Smush", "Collapse")));
    }

    public static List<String> getUserPermissions(UserType userType){
        return repository.get(userType);
    }
}
