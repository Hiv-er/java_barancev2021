package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getContactHelper().openContact();
        app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "a@a.ru", "1234567"));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().returnToHomePage();
    }
}
