package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        app.contact().initContactCreation();
        ContactData contact = new ContactData().withFirstName("test1").withLastName("test2").withAddress("test3").withEmail("a@a.ru").withHomePhone("1234567").withGroup("test1");
        app.contact().fillContactForm(contact, true);
        app.contact().submitContactCreation();
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size() + 1);

        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
    }
}
