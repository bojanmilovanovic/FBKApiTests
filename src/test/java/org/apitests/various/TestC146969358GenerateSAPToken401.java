package org.apitests.various;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({TestRailRunner.class})
public class TestC146969358GenerateSAPToken401 {

    @Test(groups = {"partner", "tp1"})
    public void testC146969357GenerateSAPToken400() {

        // Generate token and set up the host
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/authserver/oauth/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.contentType("multipart/form-data");
        request.multiPart("grant_type", "client_credentials");

        // Response and assertion
        Response response = request.post("/oauth/token");
        Assert.assertEquals(response.getStatusCode(), 401, "Response code is not 401");
        Assert.assertEquals(response.jsonPath().getString("error"), "unauthorized_user", "Returned error should be unauthorized_user");
        Assert.assertEquals(response.jsonPath().getString("error_description"), "Unauthorized access", "Returned error_description should be Unauthorized access");
        Assert.assertEquals(response.jsonPath().getString("message"), "Unauthorized", "Returned message should be Unauthorized");
        Assert.assertEquals(response.jsonPath().getString("path"), "/authserver/oauth/"+Globals.TENANT+"/oauth/token", "Returned path is not correct");

    }

}
