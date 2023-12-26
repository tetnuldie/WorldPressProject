package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Visible;
import org.example.pages.topMenu.AdminBarElement;
import org.example.pages.sidemenu.SideMenuElement;

import static com.codeborne.selenide.Selenide.$x;

public interface PageMenuFunc {
    SelenideElement adminBar = $x("//div[@id='wpadminbar']");
    SelenideElement sideMenu = $x("//div[@id='adminmenuwrap']");

    static SelenideElement getSideMenuElement(SideMenuElement element) {
        return sideMenu
                .$x(String.format(".//li[@id='%s']", element.getId()))
                .shouldBe(Condition.visible);
    }

    static SelenideElement getAdminBarElement(AdminBarElement element) {
        return adminBar
                .$x(String.format(".//li[@id='%s']", element.getId()))
                .shouldBe(Condition.visible);
    }

    static ElementsCollection getSideMenuItemSubMenu(SideMenuElement element) {
        return getSideMenuElement(element).$$x(".//ul[@class='wp-submenu wp-submenu-wrap']//li");
    }

    static ElementsCollection getAdminBarItemSubMenu(AdminBarElement element) {
        return getAdminBarElement(element).$$x(".//div[@class='ab-sub-wrapper']//li");
    }

    static SelenideElement getSideMenuSubMenuElement(SideMenuElement element, String name) {
        return getSideMenuItemSubMenu(element)
                .filterBy(Condition.matchText(name))
                .first()
                .shouldBe(Condition.visible);
    }

    static SelenideElement getAdminBarItemSubMenuElement(AdminBarElement element, String name) {
        return getAdminBarItemSubMenu(element)
                .filterBy(Condition.matchText(name))
                .first()
                .shouldBe(Condition.visible);
    }
}
