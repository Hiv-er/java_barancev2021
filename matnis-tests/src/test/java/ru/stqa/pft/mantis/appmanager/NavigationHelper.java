package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void manageUsers() {
        click(By.xpath("//span[(@class='menu-text') and (contains(text(), 'Управление'))]"));
        click(By.xpath("//a[contains(text(), 'Управление пользователями')]"));
    }
}
