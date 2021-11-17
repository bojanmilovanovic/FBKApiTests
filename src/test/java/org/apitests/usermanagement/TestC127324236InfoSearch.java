package org.apitests.usermanagement;

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
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC127324236InfoSearch {

    @Test(groups = {"usermanagement", "tp1"})
    public void testInfoSearch() throws IOException {

        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/abxusermanagement/api/v1/"+Globals.TENANT;
        File file = new File("src/test/java/org/apitests/tasks/body/TestCreateATaskBody.json");
        String requestBody = Files.readFile(file);

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.body("{ }");

        Response response = request.post("/info/search");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

}
