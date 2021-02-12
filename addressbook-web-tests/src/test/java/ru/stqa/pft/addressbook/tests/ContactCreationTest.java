package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;

public class ContactCreationTest extends TestBase {


    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToContactAddingPage();
        app.getContactHelper().fillContactForm(new ContactData("Viktoria", "Rubanova", "T-Systems", "+75555555555",
                "+75435678745", "viktoria.artyushina@t-systems.com"));
        app.getContactHelper().submitContactForm();
        app.getContactHelper().returnToContactPage();
    }


}
