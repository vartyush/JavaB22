package ru.stqa.pft.addressbook.tests;


import com.google.protobuf.ServiceException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import org.openqa.selenium.remote.*;
import ru.stqa.pft.addressbook.modul.GroupData;
import ru.stqa.pft.addressbook.modul.Groups;
import ru.stqa.pft.addressbook.modul.Issue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {
    Logger logger= LoggerFactory.getLogger(TestBase.class);
    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
    }

    @AfterSuite(alwaysRun = true)

    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m){
        logger.info("Start test " +m.getName());

    }

    @AfterMethod(alwaysRun = true)
public void logTestStop(Method m){
        logger.info("Stop test " +m.getName());
    }

    public void verifyGroupListUI() {
        if(Boolean.getBoolean("verifyUI")){
        Groups dbGroups = app.db().groups();
        Groups uiGroups = app.group().all();
        assertThat(uiGroups, equalTo(dbGroups.stream()
                .map((g)-> new GroupData().withId(g.getId()).withName(g.getName()))
                .collect(Collectors.toSet())));
    }}

//
//    public void isIssueOpen( ) throws IOException, ServiceException {
//        Set<Issue> oldIssue = app.rest().getIssues();
////        if ((status.getName() == "Closed") || (status.getName() == "Resolved")) {
////            return false;
////        } else return true;
//
//    }
//
//    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
//        if (isIssueOpen(issueId)) {
//            throw new SkipException("Ignored because of issue " + issueId);
//        }
//    }




    }
