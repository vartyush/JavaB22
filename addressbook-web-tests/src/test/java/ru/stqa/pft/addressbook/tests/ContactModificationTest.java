package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;

public class ContactModificationTest extends TestBase {


    @Test
    public void testContactModification() {

        app.getNavigationHelper().goToContactPage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initModificationContact();
        app.getContactHelper().fillContactForm(new ContactData("Viktoria", "Rubanova", "T-Systems", "+75555555555",
                "+75435678745", "viktoria.artyushina@t-systems.com"));
        app.getContactHelper().submitModificationContact();
        app.getContactHelper().returnToContactPage();
    }
}