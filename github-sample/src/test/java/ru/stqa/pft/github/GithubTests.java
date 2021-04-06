package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {
    @Test
    public void testCommits() throws IOException {
        Github github = new RtGithub("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");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("vartyush", "JavaB22")).commits();

for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())){
    System.out.println(new RepoCommit.Smart(commit).message()); }
}
    }

