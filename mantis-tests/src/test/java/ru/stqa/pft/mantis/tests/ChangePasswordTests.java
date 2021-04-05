package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {
    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, InterruptedException {
        long now = System.currentTimeMillis();
        String email = String.format("user%s@localhost.localdomain", now);
        String username = String.format("user1%s", now);
        String password = "password";
        app.loginHelper().login("administrator", "root123");

        app.userCreation().start(username, email);
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.userCreation().finish(confirmationLink, username, password);
        app.loginHelper().login("administrator", "root123");
        app.resetPassword().start(username);

        List<MailMessage> mailMessages1 = app.mail().waitForMail(2, 10000);
        String confirmationLink1 = findConfirmationLink(mailMessages1, email);
        System.out.println("confirmationLink" +confirmationLink1);
        app.resetPassword().finish(confirmationLink1, username, password);
        assertTrue(app.newSession().login(username, password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        List<MailMessage>filtered = mailMessages.stream().filter((m) -> m.to.equals(email)).collect(Collectors.toList());
        MailMessage mailMessage = filtered.get(mailMessages.size() - 1);
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
