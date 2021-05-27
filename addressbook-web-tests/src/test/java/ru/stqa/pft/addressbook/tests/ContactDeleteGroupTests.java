package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactDeleteGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    Groups groups = app.db().groups();
    if (groups.size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }

    if (app.db().contacts().size() == 0) {
      app.contact().createContact(new ContactData()
          .withFirstName("test1").withLastName("test2").withAddress("test3")
          .withEmail("a@a.ru").withEmail2("").withEmail3("")
          .withHomePhone("1234567").withMobilePhone("").withWorkPhone("")
          .inGroups(groups.iterator().next()));
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactDeleteGroup() {
    Contacts contacts = app.db().contacts();
    ContactData contact = contacts.iterator().next();
    GroupData group = contact.getGroups().iterator().next();

    app.goTo().homePage();

    if (contact.getGroups().size() == 0) {
      app.contact().addToGroup(contact, group);
    }

    app.contact().removeFromGroup(contact, group);
    app.goTo().homePage();

    ContactData contactFromDB = app.db().getContactById(contact.getId());
    MatcherAssert.assertThat(contact.getGroups().size() - 1,
        CoreMatchers.equalTo(contactFromDB.getGroups().size()));

    MatcherAssert.assertThat(contact.getGroups().without(group),
        CoreMatchers.equalTo(contactFromDB.getGroups()));
  }
}
