package org.example.pages.pageobject;

import org.apache.log4j.Logger;
import org.example.pages.MainMenuFunc;
import org.example.pages.PageType;

public class MainPagePO extends AbstractPage implements MainMenuFunc {
    private final Logger logger = Logger.getLogger(MainPagePO.class);


    public MainPagePO(PageType pageType) {
        super(pageType);
    }
}
