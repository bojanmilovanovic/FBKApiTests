package org.apitests.mailbox;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestRailRunner.class})
public class TestC138976844GetMessage422 {

    @Test(groups = {"tp1", "mailbox"})
    public void testC138976844GetMessage422(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkmessage/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        Response response = request.get("/message/aaa");
        Assert.assertEquals(response.statusCode(), 422, "Response code is not 422");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Value of the status flag is not false");
        Assert.assertEquals(response.jsonPath().getString("_messages[0].text"), "Failed to fetch message data for messageId: aaa. Please try again later.", "Response message is not correct");

    }

}
