package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {


    @Test
    public void testContactModification() {

        app.getNavigationHelper().goToContactPage();
        ContactData contact =new ContactData("Viktoria123", "Rubanova",
                "T-Systems", "test");

        if (!app.getContactHelper().isContactPresent()) {
            app.getContactHelper().createContact(contact, false);
        }
        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().initModificationContact(before.size()-1);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitModificationContact();
        app.getContactHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());
        before.remove(before.size()-1);
        before.add(contact);

        Comparator<? super ContactData> byId =(c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);

        System.out.println("Before");
        for (ContactData b : before ) {
            System.out.println(b);
        }
        System.out.println("After");
        for (ContactData a : after ) {
            System.out.println(a);
        }

        Assert.assertEquals(before, after);

    }
}