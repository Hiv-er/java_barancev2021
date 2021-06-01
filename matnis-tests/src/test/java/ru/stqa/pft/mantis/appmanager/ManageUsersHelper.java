package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

public class ManageUsersHelper extends HelperBase {
  public ManageUsersHelper(ApplicationManager app) {
    super(app);
  }

  public void resetPassword(UserData user) {
    click(By.linkText(user.getUsername()));
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }
}
