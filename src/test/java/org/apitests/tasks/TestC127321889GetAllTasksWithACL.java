package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;

@Listeners({TestRailRunner.class})
public class TestC127321889GetAllTasksWithACL {

    @Test(groups = {"tasks", "tp1"})
    public void testC127321889GetAllTasksWithACL() {

        // Generate token and set up the host
        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        JSONObject requestParams = new JSONObject();
        request.body(requestParams.toString());

        // Response and assertion
        Response response = request.post("/tasks/search?calcPermissions=true");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));
//        File schema = new File("src/test/java/org/apitests/tasks/schema/GetAllTasksWithACLSchema.json");
//        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

    }

}
