package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestRailRunner.class})
public class TestC127321887GetAllTasks {

    @Test(groups = {"tasks", "tp1"})
    public void testGetAllTasks() {

        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        JSONObject requestParams = new JSONObject();
        request.body(requestParams.toString());

        Response response = request.post("/tasks/search");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

}
