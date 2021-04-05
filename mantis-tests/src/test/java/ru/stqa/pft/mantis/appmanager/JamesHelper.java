package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.*;

public class JamesHelper {

    private ApplicationManager app;
    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;

    private Session mailSession;
    private Store store;
    private String mailserver;

    public JamesHelper(ApplicationManager app) {
        this.app = app;
        telnet = new TelnetClient();
        mailSession = Session.getDefaultInstance(System.getProperties());

    }


    public void createUser(String name, String passwd) {
        initTelnetSession();
        write("adduser" + name + " " + passwd);
        String result = readUntil("User" + name + " added");
        closeTelnetSession();
    }

    private void initTelnetSession() {
        mailserver = app.getProperty("mailserver.host");
        int port = Integer.parseInt(app.getProperty("mailserver.port"));
        String login = app.getProperty("mailserver.adminlogin");
        String password = app.getProperty("mailserver.adminpassword");


        try {
            telnet.connect(mailserver, port);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        readUntil("Login id:");
        write("");
        readUntil("Password:");
        write(password);

        readUntil("Welcome " + login + ". HELP for a list of commands");

    }

    private String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {

                out.println(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                        }
                    }
                    ch=(char) in.read();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        return null;
        }

        private void  write (String value){
        try{
            out.println(value);
            out.flush();
            System.out.println(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        }

        private void closeTelnetSession(){write("quit");}

        private void  closeFolder(Folder folder) throws MessagingException {
        folder.close(true);
        store.close();
        }


        private Folder openInbox(String username, String password) throws MessagingException {
        store =mailSession.getStore("pop3");
        store.connect(mailserver, username, password);
        Folder folder =store.getDefaultFolder().getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        return folder;
        }



    public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + timeout) {
            List<MailMessage> allMail =getAllMail(username, password);

            if (allMail.size() >0) {
                return allMail;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Error("No mail");
    }

    private List<MailMessage> getAllMail(String username, String password) throws MessagingException {
        Folder inbox =openInbox(username, password);
        List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream().map((m)->toModelMail(m)).collect(Collectors.toList());
        closeFolder(inbox);
        return messages;
    }

    private static MailMessage toModelMail(Message m) {

        try {
            return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}
