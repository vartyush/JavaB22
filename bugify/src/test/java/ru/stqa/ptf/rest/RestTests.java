package ru.stqa.ptf.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;


import static org.testng.Assert.assertEquals;

public class RestTests {
    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssue =getIssues();
        System.out.println("Issues  ");
        for (Issue is : oldIssue){
            System.out.println(is);
        }
        Issue newIssue=new Issue().withSubject("Test Vika issue").withDescription("New test issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues=getIssues();
        oldIssue.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssue);

    }

    private Set<Issue> getIssues() throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement ids = parsed.getAsJsonObject().get("id");
        System.out.println(ids);

        return new Gson().fromJson(ids,  new TypeToken<Set<Issue>>(){}.getType());
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }
    private int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();

    }
}
