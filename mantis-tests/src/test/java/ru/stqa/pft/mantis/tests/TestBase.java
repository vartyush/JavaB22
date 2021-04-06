package ru.stqa.pft.mantis.tests;


import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class TestBase {
    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
        //    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak" );
    }

    @AfterSuite(alwaysRun = true)

    public void tearDown() throws IOException {
        //    app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }


    public boolean isIssueOpen(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        ObjectRef status = app.soap().getIssueInfo(issueId);
        if ((status.getName().equals("closed")) || (status.getName().equals("resolved"))) {
            return false;
        } else return true;

    }

    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

}
