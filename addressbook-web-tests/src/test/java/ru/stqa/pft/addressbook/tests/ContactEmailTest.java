package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;
import ru.stqa.pft.addressbook.modul.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase {

    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test"));
        }
    }

    @Test

    public void testContactEmails() {

        app.goTo().contactPage();
        ContactData contact=app.contact().all().iterator().next();
//        ContactData contact = new ContactData().withFirstname("Viktoria123").withLastname("Rubanova").withGroup("test")
//                .withHome("11111").withMobile("33-333").withWork("(123)46578").withEmail1("email1@test.ru")
//                .withEmail2("email2@test.ru").withEmail3("email3@test.ru").withAddress("AddressStreet");
//        app.contact().create(contact, true);
//        Contacts after = app.contact().all();
//        int max=after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
//
//        System.out.println(max);
//        contact.setId(max);
//        System.out.println(contact.getId());
       ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
//        System.out.println("contactInfoFromEditFormID - "+ contactInfoFromEditForm.getId());
//        System.out.println("contactInfoFromEditForm" + contactInfoFromEditForm.getEmail1());
//        String merge =mergeEmails(contactInfoFromEditForm);
//        System.out.println(merge);
        System.out.println("contact.getAllEmails() - "+contact.getAllEmails());

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                 .map(ContactEmailTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String email){

        return email.replaceAll("\\s", "");
    }


}
