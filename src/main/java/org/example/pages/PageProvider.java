package org.example.pages;

import java.util.HashMap;
import java.util.Map;

public class PageProvider {
    private static Map<PageType, Page> repository;

    static {
        repository = new HashMap<>();
        repository.put(PageType.LOGIN, new LoginPO());
        repository.put(PageType.MAIN, new MainPagePO());
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
