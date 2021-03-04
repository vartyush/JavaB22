package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.GroupData;

import java.util.List;

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
                    "T-Systems",  "test"), true);
        }
        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(before.size() - 1);
//        System.out.println("Before");
//        for (ContactData b : before ) {
//            System.out.println(b);
//        }
//        System.out.println("After");
//        for (ContactData a : after ) {
//            System.out.println(a);
//        }

         Assert.assertEquals(before,after);
    }


}
