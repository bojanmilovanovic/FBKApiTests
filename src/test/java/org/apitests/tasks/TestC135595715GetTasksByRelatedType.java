package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.reporters.Files.readFile;

@Listeners({TestRailRunner.class})
public class TestC135595715GetTasksByRelatedType {

    @Test(groups = {"tasks", "tp1"})
    public void testC135595715GetTasksByRelatedType() throws IOException {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/tasks/body/TestGetTasksByRelatedType.json");
        String requestBody = readFile(file);
        request.body(requestBody);

        // Response and assertion
        Response response = request.post("/tasks/search");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of the _status flag is not true");

        int resposeSize = response.jsonPath().getList("tasks").size();
        for(int i = 0; i < resposeSize; i++){
            Assert.assertEquals(response.jsonPath().getString("tasks["+i+"].relatedType"), "FUNDINGS", "Related Type in the response is not FUNDINGS as stated in the filter");
        }

    }

}
