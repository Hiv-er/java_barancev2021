package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void endurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.contact().createContact(new ContactData()
                .withFirstName("test1").withLastName("test2").withAddress("test3")
                .withEmail("a@a.ru").withEmail2("").withEmail3("")
                .withHomePhone("1234567").withMobilePhone("").withWorkPhone(""));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withFirstName("test3").withLastName("test3").withAddress("test3")
            .withEmail("aaaaa@a.ru").withEmail2("").withEmail3("")
            .withHomePhone("1234567").withMobilePhone("").withWorkPhone("");
        app.goTo().homePage();
        app.contact().modify(contact);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();

        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
