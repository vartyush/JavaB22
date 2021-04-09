package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;
import ru.stqa.pft.addressbook.modul.GroupData;
import ru.stqa.pft.addressbook.modul.Groups;

import static org.testng.AssertJUnit.assertFalse;

public class ContactRemoveFromGroupTest extends TestBase {


    private ContactData selectedContact;
    private GroupData selectedGroup;

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
        Contacts contacts = app.db().contacts();
        int i = 0;
        for (ContactData contact : contacts) {
            if (contact.getGroups().size()==0) {
                i++;
            }
        }

        if (i == contacts.size()) {
             ContactData contact = app.db().contacts().iterator().next();
            GroupData group = app.db().groups().iterator().next();
            app.goTo().contactPage();
            app.contact().addToGroup(contact, group);
        }
    }

    @Test

    public void testContactRemoveFromGroup() {
        Groups groups = app.db().groups();

        Contacts contacts = app.db().contacts();
        for (ContactData contact : contacts) {
            for (GroupData group : groups) {
                if (contact.getGroups().contains(group)) {
                    selectedContact = contact;
                    selectedGroup = group;
                }
            }
        }
            app.goTo().contactPage();
           app.contact().removeFromGroup(selectedContact, selectedGroup);
            app.db().groups();
            app.db().contacts();
            assertFalse(selectedContact.getGroups().contains(selectedGroup));

        }
    }

