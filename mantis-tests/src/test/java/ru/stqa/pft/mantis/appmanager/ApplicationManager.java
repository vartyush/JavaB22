package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import ru.stqa.pft.mantis.model.Project;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.MatchResult;

public class ApplicationManager {
    private final Properties properties;
    private WebDriver wd;
    private String browser;
    private RegistrationHelper registrationHelper;
    private FtpHelper ftp;
    private MailHelper mailHelper;
    private JamesHelper jamesHelper;
    private UserCreationHelper userCreation;
    private LoginHelper loginHelper;
    private DbHelper dbHelper;
    private ResetPasswordHelper resetPswHelper;
    private SoapHelper soapHelper;

    public Properties getProperties() {
        return properties;
    }

    public ApplicationManager(String browser) {

        this.browser = browser;
        properties = new Properties();
    }


    public void init() throws IOException {
   //     dbHelper = new DbHelper();
        dbHelper = new DbHelper();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }


    public void stop() {
        if (wd != null) {
            wd.quit();
        }
    }


    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public UserCreationHelper userCreation() {
        if (userCreation == null) {
            userCreation = new UserCreationHelper(this);
        }
        return userCreation;
    }

    public LoginHelper loginHelper() {
        if (loginHelper == null) {
            loginHelper = new LoginHelper(this);
        }
        return loginHelper;
    }
    public ResetPasswordHelper resetPassword() {
        if (resetPswHelper == null) {
            resetPswHelper = new ResetPasswordHelper(this);
        }
        return resetPswHelper;
    }
//
//    public FtpHelper ftp() {
//        if (ftp == null) {
//            ftp = new FtpHelper(this);
//        }
//        return ftp;
//    }


    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public JamesHelper james(){
        if (jamesHelper == null) {
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }

    public SoapHelper soap() {
        if (soapHelper == null) {
            soapHelper = new SoapHelper(this);
        }
        return soapHelper;
    }


    public WebDriver getDriver() {
        if (wd == null) {
            if (browser.equals(BrowserType.CHROME)) {
                wd = new FirefoxDriver();
            } else if (browser.equals(BrowserType.IE)) {
                wd = new InternetExplorerDriver();
            } else wd = new ChromeDriver();

            wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wd.get(properties.getProperty("web.baseUrl"));

        }
        return wd;
    }

    public DbHelper db(){
        return dbHelper;
    }
}
