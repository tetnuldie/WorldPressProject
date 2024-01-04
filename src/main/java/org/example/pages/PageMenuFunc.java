package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.pages.topMenu.AdminBarElement;
import org.example.pages.sidemenu.SideMenuElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.$x;

public interface PageMenuFunc {
    SelenideElement adminBar = $x("//div[@id='wpadminbar']");
    SelenideElement sideMenu = $x("//div[@id='adminmenuwrap']");
    Logger logger = Logger.getLogger(PageMenuFunc.class);

    static SelenideElement getSideMenuElement(SideMenuElement element) {
        logger.log(Level.INFO, "trying to get element "+element.toString()+" on page side menu");
        return sideMenu
                .$x(String.format(".//li[@id='%s']", element.getId()))
                .shouldBe(Condition.visible);
    }

    static SelenideElement getAdminBarElement(AdminBarElement element) {
        logger.log(Level.INFO, "trying to get element "+element.toString()+" on page top menu");
        return adminBar
                .$x(String.format(".//li[@id='%s']", element.getId()))
                .shouldBe(Condition.visible);
    }

    static ElementsCollection getSideMenuItemSubMenu(SideMenuElement element) {
        logger.log(Level.INFO, "trying to get side menu element "+element.toString()+" sub-menu");
        return getSideMenuElement(element).$$x(".//ul[@class='wp-submenu wp-submenu-wrap']//li");
    }

    static ElementsCollection getAdminBarItemSubMenu(AdminBarElement element) {
        logger.log(Level.INFO, "trying to get top-menu element "+element.toString()+" sub-menu");
        return getAdminBarElement(element).$$x(".//div[@class='ab-sub-wrapper']//li");
    }

    static SelenideElement getSideMenuSubMenuElement(SideMenuElement element, String name) {
        logger.log(Level.INFO, "trying to get side menu element "+element.toString()+" by name "+name);
        return getSideMenuItemSubMenu(element)
                .filterBy(Condition.matchText(name))
                .first()
                .shouldBe(Condition.visible);
    }

    static SelenideElement getAdminBarItemSubMenuElement(AdminBarElement element, String name) {
        logger.log(Level.INFO, "trying to get top menu element "+element.toString()+" by name "+name);
        return getAdminBarItemSubMenu(element)
                .filterBy(Condition.matchText(name))
                .first()
                .shouldBe(Condition.visible);
    }

    static void logOut(){
        logger.log(Level.INFO, "Starting logout");
        getAdminBarElement(AdminBarElement.USER).hover();
        getAdminBarItemSubMenu(AdminBarElement.USER).filter(Condition.id("wp-admin-bar-logout")).first().click();
        Selenide.Wait().until(ExpectedConditions.urlToBe("https://wordpress-test-app-for-selenium.azurewebsites.net/wp-login.php?loggedout=true&wp_lang=en_US"));
    }
}
