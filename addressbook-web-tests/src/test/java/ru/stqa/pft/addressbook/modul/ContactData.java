package ru.stqa.pft.addressbook.modul;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "addressbook")
public class ContactData {
    @Id
    @Column(name = "id")
    private int id;
    @Expose
    @Column(name = "firstname")
    private String firstname;
    @Expose
    @Column(name = "lastname")
    private String lastname;
    private String company;
    @Expose
    @Column(name = "home")
    @Type(type = "text")
    private String home;
    @Expose
    @Column(name = "work")
    @Type(type = "text")
    private String work;
    @Expose
    @Column(name = "mobile")
    @Type(type = "text")
    private String mobile;
    @Expose


    @Transient
    private String allPhones;
    @Expose
    @Column(name = "email")
    @Type(type = "text")
    private String eMail1;

    @Expose
    @Column(name = "email2")
    @Type(type = "text")
    private String eMail2;

    @Expose
    @Column(name = "email3")
    @Type(type = "text")
    private String eMail3;

    @Transient
    private String allEmails;

    @Column(name = "photo")
    @Type(type = "text")
    private String photo;

    @Transient
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupData> groups = new HashSet<GroupData>();

    public Groups getGroups() {
        return new Groups(groups);
    }

    public File getPhoto() {
        return new File(photo);
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
    }


    public String getAddress() {
        return address;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }


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


    public ContactData withId(int id) {

        this.id = id;
        return this;
    }


    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", groups=" + groups +
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
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(home, that.home) &&
                Objects.equals(work, that.work) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(eMail1, that.eMail1) &&
                Objects.equals(eMail2, that.eMail2) &&
                Objects.equals(eMail3, that.eMail3) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, home, work, mobile, eMail1, eMail2, eMail3, address);
    }

    public ContactData inGroup(GroupData group) {
        groups.add(group);
        return this;
    }
}
