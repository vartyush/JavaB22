package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    FirefoxDriver wd;

    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;


    public void init() {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wd.get("http://localhost:8080/addressbook/");
        groupHelper = new GroupHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);
        contactHelper = new ContactHelper(wd);
        sessionHelper.login("Admin", "secret");

    }


    public void stop() {
        sessionHelper.logout();
        wd.quit();
    }


    public GroupHelper getGroupHelper() {

        return groupHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }


    public ContactHelper getContactHelper() {
        return contactHelper;
    }


}
