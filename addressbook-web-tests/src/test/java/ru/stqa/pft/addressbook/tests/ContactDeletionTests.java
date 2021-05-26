package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void endurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.contact().createContact(new ContactData().withFirstName("test1").withLastName("test2").withAddress("test3").withEmail("a@a.ru").withHomePhone("1234567").withGroup("test1"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.goTo().homePage();
        app.contact().delete(deletedContact);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        Contacts after = app.db().contacts();

        assertThat(after, equalTo(before.without(deletedContact)));
    }
}
