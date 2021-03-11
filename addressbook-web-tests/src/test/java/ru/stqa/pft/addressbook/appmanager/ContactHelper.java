package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToContactPage() {
        click(By.linkText("home"));

    }

    public void submitContactForm() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("company"), contactData.getCompany());

        if (creation) {

             new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());

        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }


    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }


    private void selectContactById(int id) {

        wd.findElement(By.cssSelector("input[value='"+id +"']")).click();
    }



    public void initModificationContact(int id) {
       wd.findElement(By.xpath("//input[@value='"+id +"']/parent::td/following-sibling::td/a/img[@alt='Edit']")).click();
    }

    public void submitModificationContact() {
        click(By.name("update"));
    }

    public void create(ContactData contact, boolean creation) {
        NavigationHelper navigationHelper = new NavigationHelper(wd);
        navigationHelper.goToContactAddingPage();
        fillContactForm(contact, creation);
        submitContactForm();
        returnToContactPage();
    }


    public void modify(ContactData contact ) {
        selectContactById(contact.getId());
        initModificationContact(contact.getId());
        fillContactForm(contact, false);
        submitModificationContact();
       returnToContactPage();
    }


    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        returnToContactPage();
    }



    public Contacts all() {
        Contacts contacts =new Contacts();
        List<WebElement> elements=wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements ){
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String lastName =element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String firstName =element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            contacts.add(new ContactData().withId(id).withFirstname(firstName).withLastname(lastName));

        }
        return contacts;
    }


}
