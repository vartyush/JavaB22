package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {
    @BeforeTest
    public void skipTestIfNotFixed() throws RemoteException, ServiceException, MalformedURLException {
        skipIfNotFixed(0000002);
    }


    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }
    @BeforeMethod
    public void ensurePreconditions() {
        long now = System.currentTimeMillis();
        app.loginHelper().login("administrator", "root123");
        if (app.db().users().size() == 0) {
            String email = String.format("user%s@localhost.localdomain", now);
            String username = String.format("user1%s", now);
            app.userCreation().start(username, email);

        }
    }

    @Test
    public void testChangePassword() throws IOException, InterruptedException {
        UserData user = app.db().users().iterator().next();
        app.resetPassword().start(user.getUsername());

        //получение письма для смены пароля
        List<MailMessage> mailMessages1 = app.mail().waitForMail(1, 10000);
        String confirmationLink1 = findConfirmationLink(mailMessages1, user.getEmail());
        String password = "password";
        app.resetPassword().finish(confirmationLink1, user.getUsername(), password);
        assertTrue(app.newSession().login(user.getUsername(), password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        List<MailMessage> filtered = mailMessages.stream().filter((m) -> m.to.equals(email)).collect(Collectors.toList());
        System.out.println("size" +mailMessages.size());
        MailMessage mailMessage = filtered.get(mailMessages.size()-1);
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
