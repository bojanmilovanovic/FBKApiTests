package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.apitests.core.TimeHelper;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC129430161CreateATaskWithoutAttachments {

    @Test(groups = {"tasks", "tp1"})
    public void testC129430161CreateATaskWithoutAttachments() throws IOException {

        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;
        String applicationNo = Globals.FUNDING_ID;
        File file = new File("src/test/java/org/apitests/tasks/body/TestCreateATaskBody.json");
        String requestBody = Files.readFile(file);
        requestBody = requestBody.replace("FUNDING_ID", applicationNo);
        String date = TimeHelper.getNextWorkingDay();

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("subject", "test_without_attachment");
        request.multiPart("content", "This task without attachment is created by API");
        request.multiPart("category", "TestApiCategory");
        request.multiPart("deadline", date);
        request.multiPart("resubmissionDate", date);
        request.multiPart("context", requestBody, "application/json");

        Response response = request.post("/taskbulk");

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

}
