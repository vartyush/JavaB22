package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.GroupData;

import java.util.List;

public class GroupDeletionTest extends TestBase {


    @Test
    public void testGroupDeletion()  {
        app.getNavigationHelper().gotoGroupPage();

        if (!app.getGroupHelper().isGroupPresent()) {
 app.getGroupHelper().createGroup(new GroupData("test", "test2", "test3"));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size()-1);
        app.getGroupHelper().deleteSelectedGroup();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove((before.size()-1));
            Assert.assertEquals(before, after);
        }
    }
