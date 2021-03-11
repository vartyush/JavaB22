package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper extends HelperBase{




    public NavigationHelper(WebDriver wd) {
       super(wd);
    }

    public void groupPage() {
   click(By.linkText("groups"));
    }

    public void goToContactAddingPage() {
        click(By.linkText("add new"));

    }

    public void contactPage() {
        click(By.linkText("home"));
    }



}
