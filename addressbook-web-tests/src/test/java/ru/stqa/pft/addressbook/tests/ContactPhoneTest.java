package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;
import ru.stqa.pft.addressbook.modul.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTest extends TestBase {

    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test"));
        }
   }

    @Test

    public void testContactPhones() {

        app.goTo().contactPage();
        ContactData contact=app.contact().all().iterator().next();
//        ContactData contact = new ContactData().withFirstname("Viktoria123").withLastname("Rubanova").withGroup("test")
//                .withHome("11111").withMobile("33-333").withWork("(123)46578").withEmail1("email1@test.ru")
//                .withEmail2("email2@test.ru").withEmail3("email3@test.ru").withAddress("AddressStreet");
//        app.contact().create(contact, true);
//        Contacts after = app.contact().all();
//        int max=after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
//        System.out.println(max);
//        contact.setId(max);
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
           }

    private String mergePhones(ContactData contact) {
      return  Arrays.asList(contact.getHome(), contact.getMobile(), contact.getWork())
                .stream().filter((s) -> !s.equals(""))
              .map(ContactPhoneTest::cleaned)
              .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone){

        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
