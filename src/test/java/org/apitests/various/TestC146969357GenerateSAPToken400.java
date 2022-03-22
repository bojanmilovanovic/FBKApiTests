package org.apitests.various;

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
public class TestC146969357GenerateSAPToken400 {

    @Test(groups = {"partner", "tp1"})
    public void testC146969357GenerateSAPToken400() {

        // Generate token and set up the host
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/authserver/oauth/"+Globals.TENANT;
        String username = "tu_sap2portal";
        String password = "secret";

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().basic(username, password);

        // Response and assertion
        Response response = request.post("/oauth/token");
        Assert.assertEquals(response.getStatusCode(), 400, "Response code is not 400");
        Assert.assertEquals(response.jsonPath().getString("error"), "invalid_request", "Returned error should be invalid_request");
        Assert.assertEquals(response.jsonPath().getString("error_description"), "Missing grant type", "Returned error_description should be Missing grant type");

    }

}
