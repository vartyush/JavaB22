package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.modul.GroupData;

public class GroupCreationTest extends TestBase {


    @Test
  public void testGroupCreation()  {

    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().createGroup(new GroupData("test", "test2", null));


  }


}
