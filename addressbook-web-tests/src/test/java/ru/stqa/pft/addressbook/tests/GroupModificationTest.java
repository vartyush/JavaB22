package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class GroupModificationTest extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();

        if (!app.getGroupHelper().isGroupPresent()) {
            app.getGroupHelper().createGroup(new GroupData("testNew", "test2", null));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size()-1);
        app.getGroupHelper().initGroupModification();
        GroupData group =new GroupData(before.get(before.size()-1).getId(),"test88", "test2", "test3");
        app.getGroupHelper().fillGroupForm(group);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());
        before.remove(before.size()-1);
        before.add(group);
        Comparator<? super GroupData> byId = (g1, g2)  -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

    }
}
