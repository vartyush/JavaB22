package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.modul.GroupData;
import ru.stqa.pft.addressbook.modul.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupDeletionTest extends TestBase {


    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();

        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }

    }

    @Test
    public void testGroupDeletion() {
        Groups before = app.group().all();
        GroupData deletedGrouup = before.iterator().next();
        app.group().delete(deletedGrouup);
        Groups after = app.group().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove((deletedGrouup));
        assertThat(after, equalTo(before.without(deletedGrouup)));
    }


}

