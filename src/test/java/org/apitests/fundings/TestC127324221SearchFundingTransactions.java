package org.apitests.fundings;

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

@Listeners({TestRailRunner.class})
public class TestC127324221SearchFundingTransactions {

    @Test(groups = {"fundings", "tp4"})
    public void testC127324221SearchFundingTransactions(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/funding/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File body = new File("src/test/java/org/apitests/fundings/body/TestSearchFindingsHappyPathBody.json");
        request.body(body);

        // Response and assertion
        Response response = request.post("/fundings/transactions/"+Globals.FUNDING_MONITORING_ID+"/search");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

}
