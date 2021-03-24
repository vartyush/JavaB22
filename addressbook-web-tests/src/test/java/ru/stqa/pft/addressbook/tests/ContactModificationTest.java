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
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test"));
        }

        app.goTo().contactPage();

        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Viktoria123").withCompany("T-Systems"), true);
        }

    }


    @Test(dataProvider = "validContactsFromJson")
    public void testContactModification(ContactData contact ) {

        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();

       // ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Viktoria73").withLastname("Rubanova").withGroup("Group");
        app.contact().modify(contact.withId(modifiedContact.getId()));
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
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