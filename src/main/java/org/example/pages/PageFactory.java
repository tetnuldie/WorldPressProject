package org.example.pages;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.pages.comments.PublicPostPO;
import org.example.pages.table.*;

import java.util.HashMap;
import java.util.Map;

public class PageFactory {
    private static Map<PageType, Page> repository;
    private static final Logger logger = LogManager.getLogger(PageFactory.class);

    static {
        repository = new HashMap<>();
        repository.put(PageType.LOGIN, new LoginPO());
        repository.put(PageType.MAIN, new MainPagePO());
        repository.put(PageType.PAGES, new PostsPO(PageType.PAGES));
        repository.put(PageType.NEW_PAGE, new CreatePostPO(PageType.NEW_PAGE));
        repository.put(PageType.POSTS, new PostsPO(PageType.POSTS));
        repository.put(PageType.NEW_POST, new CreatePostPO(PageType.NEW_POST));
        repository.put(PageType.COMMENTS, new CommentsPO(PageType.COMMENTS));
        repository.put(PageType.PUBLIC_POST, new PublicPostPO());
        repository.put(PageType.MEDIA, new MediaPO(PageType.MEDIA));
        repository.put(PageType.UPLOAD_MEDIA, new UploadMediaPO(PageType.MEDIA));
    }


    public static <T extends Page> T getPage(PageType pageType, Class<T> pageClass){
        Page page = repository.get(pageType);
        if (pageClass.isInstance(page)) {
            return pageClass.cast(page);
        } else {
            logger.log(Level.WARN, "Failed to cast "+pageType.toString()+ " to "+pageClass.getName());
            throw new IllegalArgumentException("Invalid page type");
        }
    }
}
