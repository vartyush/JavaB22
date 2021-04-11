package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;
import ru.stqa.pft.addressbook.modul.GroupData;
import ru.stqa.pft.addressbook.modul.Groups;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;


public class ContactAddToGroupTest extends TestBase {

    private ContactData selectedContact;
    private GroupData selectedGroup;


    @BeforeMethod

    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("newGroup1"));
            app.goTo().contactPage();
        }

        if (app.db().contacts().size() == 0) {
            Groups groups = app.db().groups();
            app.contact().create(new ContactData().withFirstname("Viktoria123").withCompany("T-Systems")
                    .inGroup(groups.iterator().next()), true);
        }

        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        int i = 0;
        for (ContactData contact : contacts) {
            if (contact.getGroups().equals(groups)) {
                i++;
            }
        }


        if (i == contacts.size()) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("newGroup2"));
        }
    }


    @Test

    public void testContactAddToGroup() {
        Groups groups = app.db().groups();

        Contacts contacts = app.db().contacts();
        for (ContactData contact : contacts) {
            for (GroupData group : groups) {
                if (!contact.getGroups().contains(group)) {

                    selectedContact = contact;
                    selectedGroup = group;

                }

            }

        }
        Groups groupsOfContactBefore = selectedContact.getGroups();

        app.goTo().contactPage();
        app.contact().addToGroup(selectedContact, selectedGroup);

        Contacts contactsListAfter = app.db().contacts();

        for (ContactData contact : contactsListAfter) {

            if (contact.equals(selectedContact)) {
                Groups groupsOfContactAfter = contact.getGroups();

                assertThat(groupsOfContactBefore, equalTo(groupsOfContactAfter.without(selectedGroup)));

            }
        }
    }
}