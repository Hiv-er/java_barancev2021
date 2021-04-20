package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToContactPage();
        app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "a@a.ru", "1234567"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();
    }
}
