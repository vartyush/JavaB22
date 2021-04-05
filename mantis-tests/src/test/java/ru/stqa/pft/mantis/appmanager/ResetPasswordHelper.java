package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;


public class ResetPasswordHelper extends HelperBase {
    public ResetPasswordHelper(ApplicationManager app) {
        super(app);

    }


    public void start(String username) throws InterruptedException {
        click(By.xpath("//a[contains(text(), 'Manage Users')]"));
        click(By.xpath("//a[contains(text(),'" + username + "')]"));
        Thread.sleep(3000);
        click(By.cssSelector("input[value='Reset Password']"));
    }

    public void finish(String confirmationLink, String user, String password) {
        wd.get(confirmationLink);
        type(By.name("realname"), user);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("//span[contains(text(),'Update User')]"));
    }
}
