package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;


public class UserCreationHelper extends HelperBase {


    public UserCreationHelper(ApplicationManager app) {
        super(app);

    }

    public void start(String user, String email) {
        click(By.xpath("//span[contains(text(), 'Manage')]"));
        click(By.xpath("//a[contains(text(), 'Manage Users')]"));
        click(By.xpath("//a[contains(text(), 'Create New Account')]"));
        type(By.name("username"), user);
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Create User']"));
    }

    public void finish(String confirmationLink, String user, String password) {
        wd.get(confirmationLink);
        type(By.name("realname"), user);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("//span[contains(text(),'Update User')]"));
    }


}

