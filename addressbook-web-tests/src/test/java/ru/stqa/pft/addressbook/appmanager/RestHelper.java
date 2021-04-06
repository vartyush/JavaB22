package ru.stqa.pft.addressbook.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.JsonObject;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.modul.Issue;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class RestHelper {
    private ApplicationManager app;
    public RestHelper(ApplicationManager app) {
        this.app = app;
    }
    public String getIssueStatus(int id) throws IOException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues/" + Integer.toString(id) +".json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement state = parsed.getAsJsonObject().getAsJsonArray("issues").get(0).getAsJsonObject().get("state_name");
        String s = state.getAsJsonPrimitive().getAsString();
        // JsonElement state = issues.getAsJsonObject().get("state_name");
    //    System.out.println(state);
       // Set<Issue> stateList = new Gson().fromJson(state, new TypeToken<Set<Issue>>(){}.getType());

        return  s;
    }


    public Executor getExecutor() {
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

//    public ObjectRef getIssueInfo(int issueId) throws MalformedURLException, ServiceException, RemoteException {
//        MantisConnectPortType mc = getMantisConnect();
//        IssueData issueData = mc.mc_issue_get("administrator", "root123", BigInteger.valueOf(issueId));
//        ObjectRef status = issueData.getStatus();
//        return status;
//    }
}
