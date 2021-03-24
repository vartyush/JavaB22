package ru.stqa.pft.addressbook.tests;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import static org.hamcrest.MatcherAssert.*;


public class ContactCreationTest extends TestBase {
    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
       try (BufferedReader reader = new BufferedReader(new FileReader(new File(app.getProperties().getProperty("web.contactsData"))))) {
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

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
       try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))) {
           String json = "";
           String line = reader.readLine();
           while (line != null) {
               json += line;
               line = reader.readLine();
           }
           Gson gson = new Gson();
           List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
           }.getType());
           return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
       }
    }

    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("Group"));
        }
        app.goTo().contactPage();
    }


    @Test (dataProvider = "validContactsFromJson")

    public void testContactCreation(ContactData contact) {

        Contacts before = app.contact().all();
        File photo=new File("src/test/resources/stru.png");
      //  ContactData contact = new ContactData().withFirstname("Viktoria123").withLastname("Rubanova").withCompany("T-Systems").withPhoto(photo);
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        System.out.println("Before");
        for (ContactData b : before) {
            System.out.println(b);
        }
        System.out.println("After");
        for (ContactData a : after) {
            System.out.println(a);
        }
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    }

    @Test
    public void testCurrentDir() {

        File currentDir=new File(".");
        System.out.println(currentDir.getAbsolutePath());
    }
}
