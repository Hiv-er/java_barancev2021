package ru.stqa.pft.rest.tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import org.testng.SkipException;

public class TestBase {

  public boolean isIssueOpen(int issueId) {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues/" + issueId + ".json").asString();
    JsonElement issues = JsonParser.parseString(json).getAsJsonObject().get("issues");
    String status = issues.getAsJsonArray().get(0).getAsJsonObject().get("state_name").getAsString();
    return !status.equals("Closed");
  }

  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
