package ru.stqa.pft.addressbook.modul;

import java.util.Objects;

public class ContactData {
    private String firstname;
    private String lastname;
    private String company;
    private String home;
    private String work;
    private String mobile;
    private int id;
    private String group;
    private String allPhones;
    private String eMail1;
    private String eMail2;
    private String eMail3;
    private String allEmails;

    public String getAddress() {
        return address;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    private String address;

    public String getEmail1() {
        return eMail1;
    }

    public ContactData withEmail1(String eMail1) {
        this.eMail1 = eMail1;
        return this;
    }

    public String getEmail2() {
        return eMail2;
    }

    public ContactData withEmail2(String eMail2) {
        this.eMail2 = eMail2;
        return this;
    }

    public String getEmail3() {
        return eMail3;
    }

    public ContactData withEmail3(String eMail3) {
        this.eMail3 = eMail3;
        return this;
    }




    public String getAllEmails() {
        return allEmails;

    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public String getAllPhones() {
        return allPhones;

    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withHome(String home) {
        this.home = home;
        return this;
    }

    public ContactData withWork(String work) {
        this.work = work;

        return this;
    }

    public ContactData withMobile(String mobile) {
        this.mobile = mobile;

        return this;
    }


    public ContactData withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public ContactData withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public ContactData withCompany(String company) {
        this.company = company;
        return this;
    }

    public ContactData withGroup(String group) {
        this.group = group;
        return this;
    }

    public ContactData withId(int id) {

        this.id = id;
        return this;
    }


    @Override
    public String toString() {
        return "ContactData{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCompany() {
        return company;
    }

    public String getGroup() {
        return group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHome() {
        return home;
    }

    public String getWork() {
        return work;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, id);
    }
}
