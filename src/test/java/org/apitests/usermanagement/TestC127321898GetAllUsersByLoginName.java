package org.apitests.usermanagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.core.Globals;
import org.apitests.Token;
import org.apitests.core.TestRailRunner;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC127321898GetAllUsersByLoginName {

    @Test(groups = {"usermanagement", "tp1"})
    public void testGetAllUsersByLoginName() {

        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/abxusermanagement/admin-api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.param("loginName", Globals.LOGIN_NAME);

        Response response = request.get("/users");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

        Globals.USER_ID = response.jsonPath().getString("user.id");

    }

}
