package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;


public class ContactModificationTest extends TestBase {
    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts-modify.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
            }.getType());
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }


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


    @Test(dataProvider = "validContactsFromJson")
    public void testContactModification(ContactData contact) {
        Groups group = app.db().groups();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        app.goTo().contactPage();

        // ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Viktoria73").withLastname("Rubanova").withGroup("Group");
        app.contact().modify(contact.withId(modifiedContact.getId()).inGroup(group.iterator().next()));
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        System.out.println("Before");
        for (ContactData b : before) {
            System.out.println(b);
        }
        System.out.println("After");
        for (ContactData a : after) {
            System.out.println(a);
        }
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }

}