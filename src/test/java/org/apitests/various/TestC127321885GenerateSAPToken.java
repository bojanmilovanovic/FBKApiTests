package org.apitests.various;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.reporters.Files.readFile;


@Listeners({TestRailRunner.class})
public class TestC127321885GenerateSAPToken {

    @Test(groups = {"partner", "tp1"})
    public void testC127321885GenerateSAPToken() {

        // Generate token and set up the host
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/authserver/oauth/"+Globals.TENANT;
        String username = "tu_sap2portal";
        String password = "secret";

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().basic(username, password);
        request.contentType("multipart/form-data");
        request.multiPart("grant_type", "client_credentials");

        // Response and assertion
        Response response = request.post("/oauth/token");
        Assert.assertEquals(response.getStatusCode(), 200, "Response code is not 200");
        Assert.assertEquals(response.jsonPath().getString("token_type"), "Bearer", "Token type is not Bearer");

    }

}
