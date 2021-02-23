package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.GroupData;

public class ContactDeletionTest extends TestBase {


    @Test
    public void testContactDeletion() {

        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isGroupPresent()) {
            app.getGroupHelper().createGroup(new GroupData("test", "test2", null));
        }

        app.getNavigationHelper().goToContactPage();

        if (!app.getContactHelper().isContactPresent()) {
                 app.getContactHelper().createContact(new ContactData("Viktoria", "Rubanova",
                    "T-Systems", "+75555555555", "+75435678745",
                    "viktoria.artyushina@t-systems.com", "test"), true);
        }

        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().returnToContactPage();


    }


}
