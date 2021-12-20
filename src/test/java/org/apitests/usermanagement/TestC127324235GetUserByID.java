package org.apitests.usermanagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestRailRunner.class})
public class TestC127324235GetUserByID {

    @BeforeTest(alwaysRun = true)
    public void testC127324235GetUserByIDPrecondition() {
        TestC127321898GetAllUsersByLoginName testC127321898GetAllUsersByLoginName = new TestC127321898GetAllUsersByLoginName();
        testC127321898GetAllUsersByLoginName.testC127321898GetAllUsersByLoginName();
    }

    @Test(groups = {"usermanagement", "tp1"})
    public void testC127324235GetUserByID() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/abxusermanagement/admin-api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        Response response = request.get("/users/"+Globals.USER_ID);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }
}
