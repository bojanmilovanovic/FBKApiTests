package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TimeHelper;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC127321882CreateATaskWithAttachments {

    @Test
    public void testCreateATaskWithAttachments() throws IOException {

        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;
        String applicationNo = Globals.FUNDING_ID;
        File file = new File("src/test/java/org/apitests/tasks/body/TestCreateATaskBody.json");
        String requestBody = Files.readFile(file);
        requestBody = requestBody.replace("FUNDING_ID", applicationNo);
        File attachmentFile = new File("src/test/java/org/apitests/tasks/testfiles/TestFile.pdf");
        String date = TimeHelper.getNextWorkingDay();

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("attachmentName", "testAttachment");
        request.multiPart("attachmentMimeType", "application/pdf");
        request.multiPart("attachmentFile", attachmentFile);
        request.multiPart("subject", "test_with_attachment");
        request.multiPart("content", "This task with attachment is created by API");
        request.multiPart("category", "TestApiCategory");
        request.multiPart("deadline", date);
        request.multiPart("resubmissionDate", date);
        request.multiPart("context", requestBody, "application/json");

        Response response = request.post("/taskbulk");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

}
