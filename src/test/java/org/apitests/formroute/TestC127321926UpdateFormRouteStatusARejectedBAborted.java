package org.apitests.formroute;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.DBHelper;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({TestRailRunner.class})
public class TestC127321926UpdateFormRouteStatusARejectedBAborted {

    private String statusBefore = "A_rejected";
    private String statusAfter = "B_aborted";

    @BeforeMethod
    public void testC127321926UpdateFormRouteStatusARejectedBAbortedPrecondition() throws Exception {
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_form_routes set formroute_status = '"+statusBefore+"' where external_id = '"+ Globals.FORMROUTE_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"formroute", "tp1"})
    public void testC127321926UpdateFormRouteStatusARejectedBAborted(){
        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        String requestBody = "{ \"state\": \""+statusAfter+"\" }";
        request.body(requestBody);

        Response response = request.put("/formroute/"+Globals.FORMROUTE_ID+"/state");

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));
    }
}
