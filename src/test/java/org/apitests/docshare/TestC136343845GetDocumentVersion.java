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
public class TestC136343845GetDocumentVersion {

    @Test(groups = {"docshare", "tp1"})
    public void testC136343845GetDocumentVersion() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");

        // Response and assertion
        Response response = request.get("/documents/version?path=/"+Globals.FUNDING_ID);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of _status flag is not true");
        Assert.assertFalse(response.jsonPath().getString("versions.name").isEmpty(), "Name in response is empty");
        Assert.assertFalse(response.jsonPath().getString("versions.version").isEmpty(), "Version in response is empty");
        Assert.assertFalse(response.jsonPath().getString("versions.size").isEmpty(), "Version in response is empty");
        Assert.assertFalse(response.jsonPath().getString("versions.creationDate").isEmpty(), "Version in response is empty");
        Assert.assertFalse(response.jsonPath().getString("versions.author").isEmpty(), "Version in response is empty");
        Assert.assertFalse(response.jsonPath().getString("versions.authorName").isEmpty(), "Version in response is empty");

    }

}
