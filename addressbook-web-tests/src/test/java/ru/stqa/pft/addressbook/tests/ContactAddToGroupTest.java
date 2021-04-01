package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;
import ru.stqa.pft.addressbook.modul.GroupData;
import ru.stqa.pft.addressbook.modul.Groups;


public class ContactAddToGroupTest extends TestBase {

    @BeforeMethod

    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
            app.goTo().contactPage();
        }

        if (app.db().contacts().size() == 0) {
            Groups group = app.db().groups();
            app.contact().create(new ContactData().withFirstname("Viktoria123").withCompany("T-Systems")
                    .inGroup(group.iterator().next()), true);
        }

    }


    @Test

    public void testContactAddToGroup() {
        Groups group = app.db().groups();

        Contacts contact = app.db().contacts();
       GroupData newGroup= new GroupData().withName("Retest1");
        ContactData addingContact = contact.iterator().next();
        if (addingContact.getGroups().equals(group)) {
            app.goTo().groupPage();
            app.group().create(newGroup);
            Groups group1 = app.db().groups();
            app.goTo().contactPage();
            app.contact().addToGroup(contact, group1);
            Assert.assertTrue(addingContact.getGroups().iterator().next().equals(group1.iterator().next()));
        } else {
            app.goTo().contactPage();
            app.contact().addToGroup(contact, group);
            Assert.assertTrue(addingContact.getGroups().iterator().next().equals(group.iterator().next()));

        }

    }
}
