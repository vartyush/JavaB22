package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;
import ru.stqa.pft.addressbook.modul.GroupData;
import ru.stqa.pft.addressbook.modul.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ContactRemoveFromGroupTest extends TestBase {


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

    public void testContactRemoveFromGroup() {
        Groups groups = app.db().groups();

        Contacts contacts = app.db().contacts();
        ContactData removedContact = contacts.iterator().next();

        if (removedContact.getGroups().size() > 0) {
            app.goTo().contactPage();
            app.contact().removeFromGroup(removedContact);
        } else {
            app.goTo().contactPage();
            app.contact().addToGroup(contacts, groups);
            app.contact().removeFromGroup(removedContact);
        }
        System.out.println("+++" +groups.iterator().next());
        Assert.assertFalse(removedContact.getGroups().contains(groups.iterator().next()));

    }
}