package org.apitests.docshare;

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
public class TestC136343870DeleteFolder404 {

    @Test(groups = {"docshare", "tp1"})
    public void testC136343870DeleteFolder404() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("path", "/aaa");
        request.multiPart("deleteStrategy", "PHYSICAL");

        // Response and assertion
        Response response = request.delete("documents");
        Assert.assertEquals(response.getStatusCode(), 404, "Status code is not 404.");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status flag is not false");
        Assert.assertEquals(response.jsonPath().getString("_messages.text[0]"), "Document/Folder on path /aaa does not exist or is not accessible.", "Message text is not correct");

    }
}
