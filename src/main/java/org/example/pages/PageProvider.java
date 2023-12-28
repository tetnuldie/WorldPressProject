package org.example.pages;

import org.example.pages.pages.CreatePostPO;
import org.example.pages.pages.PostsPO;

import java.util.HashMap;
import java.util.Map;

public class PageProvider {
    private static Map<PageType, Page> repository;

    static {
        repository = new HashMap<>();
        repository.put(PageType.LOGIN, new LoginPO());
        repository.put(PageType.MAIN, new MainPagePO());
        repository.put(PageType.PAGES, new PostsPO(PageType.PAGES));
        repository.put(PageType.NEW_PAGE, new CreatePostPO(PageType.NEW_PAGE));
        repository.put(PageType.POSTS, new PostsPO(PageType.POSTS));
        repository.put(PageType.NEW_POST, new CreatePostPO(PageType.NEW_POST));
    }


    public static <T extends Page> T getPage(PageType pageType, Class<T> pageClass){
        Page page = repository.get(pageType);
        if (pageClass.isInstance(page)) {
            return pageClass.cast(page);
        } else {
            //log
            throw new IllegalArgumentException("Invalid page type");
        }
    }
}
