package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;
import ru.stqa.pft.addressbook.modul.GroupData;
import ru.stqa.pft.addressbook.modul.Groups;


import static org.testng.Assert.assertTrue;


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
        Groups groups = app.db().groups();

        Contacts contacts = app.db().contacts();
        GroupData newGroup = new GroupData().withName("Retest1");

        int i = 0;
        for (ContactData contact : contacts) {
            if (!contact.getGroups().equals(groups)) {
                Groups groupsOfContactBefore = contact.getGroups();
                GroupData group = app.contact().addToGroup(contact, groups);
                Groups groupsOfContactAfter = contact.getGroups();
                assertTrue(groupsOfContactBefore.equals(groupsOfContactAfter.without(group)));
                break;
            }
            i++;

        }
        if (i == contacts.size()) {
            app.goTo().groupPage();
            app.group().create(newGroup);
            ContactData contact = app.db().contacts().iterator().next();
            app.goTo().contactPage();
            Groups groupsOfContactBefore = contact.getGroups();
            GroupData group = app.contact().addToGroup(contact, groups);
            Groups groupsOfContactAfter = contact.getGroups();
            assertTrue(groupsOfContactBefore.equals(groupsOfContactAfter.without(group)));
        }


    }
}
