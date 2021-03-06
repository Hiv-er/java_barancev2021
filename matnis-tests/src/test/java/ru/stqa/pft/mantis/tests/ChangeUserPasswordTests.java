package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class ChangeUserPasswordTests extends TestBase {
  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangeUserPassword() throws IOException, MessagingException {
    String newPassword = "newPassword";
    Users users = app.db().users();
    users.removeIf(user -> user.getUsername().equals("administrator"));
    UserData user = users.iterator().next();

    app.session().login("administrator", "root");
    app.goTo().manageUsers();
    app.manageUsers().resetPassword(user);

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
    app.registration().finish(confirmationLink, newPassword);
    Assert.assertTrue(app.newSession().login(user.getUsername(), newPassword));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter(m -> m.to.contains(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
