package org.apitests.usermanagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.core.Globals;
import org.apitests.Token;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC127324235GetUserByID {

    @BeforeTest
    public void testGetUserByIDPrecondition() {
        TestC127321898GetAllUsersByLoginName testC127321898GetAllUsersByLoginName = new TestC127321898GetAllUsersByLoginName();
        testC127321898GetAllUsersByLoginName.testGetAllUsersByLoginName();
    }

    @Test(groups = {"usermanagement", "tp1"})
    public void testGetUserByID() {
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/abxusermanagement/admin-api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        Response response = request.get("/users/"+Globals.USER_ID);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }
}
