package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;
import ru.stqa.pft.addressbook.modul.GroupData;

import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class ContactModificationTest extends TestBase {


    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test"));
        }

        app.goTo().contactPage();

        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Viktoria123").withCompany("T-Systems"), true);
        }

    }


    @Test
    public void testContactModification() {


        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();

        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Viktoria73").withLastname("Rubanova").withGroup("Group");
        app.contact().modify(contact);
        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size());

        System.out.println("Before");
        for (ContactData b : before) {
            System.out.println(b);
        }
        System.out.println("After");
        for (ContactData a : after) {
            System.out.println(a);
        }

        assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contact)));

    }

}