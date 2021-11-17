package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC127321890GetTaskById {

    @BeforeMethod
    public void testGetUserByIDPrecondition() throws IOException {
        TestC127321882CreateATask testC127321882CreateATask = new TestC127321882CreateATask();
        testC127321882CreateATask.testCreateATask();
    }

    @Test
    public void testGetTaskById() throws IOException {

        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        JSONObject requestParams = new JSONObject();
        request.body(requestParams.toString());

        Response response = request.get("/tasks/"+Globals.TASK_ID);

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));
    }

}
