package org.apitests.partner;

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
public class TestC137032766UpdatePartner404 {

    @Test(groups = {"partner", "tp1"})
    public void testC137032766UpdatePartner404() throws IOException {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkpartner/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/partner/body/TestUpdatePartnerBody.json");
        String requestBody = readFile(file);
        request.body(requestBody);

        // Response and assertion
        Response response = request.put("/partner/aaa");
        Assert.assertEquals(response.getStatusCode(), 404, "Status code is not 404");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status is true");
        Assert.assertEquals(response.jsonPath().getString("_messages.text[0]"), "Entity not found: aaa", "Message text in the response is not correct");

    }

}
