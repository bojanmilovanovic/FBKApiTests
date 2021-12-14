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
public class TestC127321900CreatePartner {

    @Test(groups = {"partner", "tp1"})
    public void testC127321900CreatePartner() throws IOException {

        // Generate token and set up the host
        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkpartner/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/partner/body/TestUpdatePartnerBody.json");
        String requestBody = readFile(file);
        requestBody = requestBody.replace("USERUUID", Globals.USER_UUID);
        request.body(requestBody);

        // Response and assertion
        Response response = request.post("/partners");
        if(response.getStatusCode()==422) {
            Assert.assertEquals(response.getStatusCode(), 422);
            Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status is true although the user already has a partner assigned");
        }else if(response.getStatusCode()==201){
            Assert.assertEquals(response.getStatusCode(), 201);
            Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Status is false although partner is setup for the user");
        }else{
            Assert.assertEquals(response.getStatusCode(), 201);
            Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Status is false");
        }

    }

}
