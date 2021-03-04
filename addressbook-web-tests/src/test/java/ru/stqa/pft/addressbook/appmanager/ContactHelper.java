package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.modul.ContactData;
import ru.stqa.pft.addressbook.modul.GroupData;

import java.util.ArrayList;
import java.util.List;

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

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }


    public void initModificationContact(int index) {
       wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void submitModificationContact() {
        click(By.name("update"));
    }

    public void createContact(ContactData contact, boolean creation) {
        NavigationHelper navigationHelper = new NavigationHelper(wd);
        navigationHelper.goToContactAddingPage();
        fillContactForm(contact, creation);
        submitContactForm();
        returnToContactPage();
    }

    public boolean isContactPresent() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts =new ArrayList<ContactData>();
        List<WebElement> elements=wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements ){
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String lastName =element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String firstName =element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            ContactData contact =new ContactData(id, firstName,lastName, null, null );
            contacts.add(contact);

        }
        return contacts;
    }
}
