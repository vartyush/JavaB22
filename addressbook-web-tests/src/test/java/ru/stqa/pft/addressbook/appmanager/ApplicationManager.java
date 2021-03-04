package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.*;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    WebDriver wd;

    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }



    public void init() {
        if (browser.equals(BrowserType.FIREFOX)){
        wd = new FirefoxDriver();}
        else if (browser.equals(BrowserType.IE))
        { wd = new InternetExplorerDriver();}
        else wd = new ChromeDriver();

        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
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