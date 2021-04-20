package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getContactHelper().openContact();
        app.getContactHelper().deleteOpenedContact();
        app.getNavigationHelper().returnToHomePage();
    }
}
