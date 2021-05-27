package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Iterator;

public class ContactAddToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.contact().createContact(new ContactData()
          .withFirstName("test1").withLastName("test2").withAddress("test3")
          .withEmail("a@a.ru").withEmail2("").withEmail3("")
          .withHomePhone("1234567").withMobilePhone("").withWorkPhone(""));
      app.goTo().homePage();
    }

    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
  }

  @Test
  public void testContactAddToGroup() {
    Groups groups = app.db().groups();
    Contacts contacts = app.db().contacts();

    Iterator<ContactData> iterator = contacts.iterator();
    ContactData contact = iterator.next();
    while (contact.getGroups().size() == groups.size() && iterator.hasNext()) {
      contact = iterator.next();
    }

    /* Если прошлись по всем контактам, но так и не нашли контакт,
        в котором не добавлены уже все существующие группы, то создаем новую группу
     */
    if (contact.getGroups().size() == groups.size()) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
      groups = app.db().groups();
    }

    GroupData group = app.contact().getNonAddedGroup(contact, groups);

    app.goTo().homePage();
    app.contact().addToGroup(contact, group);
    app.goTo().homePage();

    ContactData contactFromDB = app.db().getContactById(contact.getId());
    MatcherAssert.assertThat(contact.getGroups().size() + 1,
        CoreMatchers.equalTo(contactFromDB.getGroups().size()));

    MatcherAssert.assertThat(contact.getGroups().withAdded(group),
        CoreMatchers.equalTo(contactFromDB.getGroups()));
  }
}
