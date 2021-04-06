package ru.stqa.pft.addressbook.tests;

import com.google.protobuf.ServiceException;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.Issue;

import java.io.IOException;
import java.util.Set;

public class RestTest extends TestBase{
    @Test
   public void isIssueOpen( ) throws IOException, ServiceException {
        Issue issueStatus = app.rest().getIssueStatus(706);
        System.out.println();
    //   oldIssue.getState();
        System.out.println(issueStatus);
    }

}
