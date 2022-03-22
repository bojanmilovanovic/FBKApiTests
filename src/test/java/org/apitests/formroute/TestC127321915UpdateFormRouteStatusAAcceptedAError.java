package org.apitests.formroute;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.DBHelper;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;


@Listeners({TestRailRunner.class})
public class TestC127321915UpdateFormRouteStatusAAcceptedAError {

    private String statusBefore = "A_accepted";
    private String statusAfter = "A_error";

    @BeforeMethod(alwaysRun = true)
    public void testC127321915UpdateFormRouteStatusAAcceptedAErrorPrecondition() throws Exception {
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_form_routes set formroute_status = '"+statusBefore+"' where external_id = '"+ Globals.FORMROUTE_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"formroute", "tp1"})
    public void testC127321915UpdateFormRouteStatusAAcceptedAError(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", statusAfter);
        request.body(jsonObject);

//      Response and assertion
        Response response = request.put("/formroute/"+Globals.FORMROUTE_ID+"/state");
        Assert.assertEquals(response.getStatusCode(), 201, "Returned status code should be 201");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Returned status flag in body should be true");

//        RestAssured.given().auth().oauth2(token.getTokenValue()).contentType(ContentType.JSON)
//                .body(jsonObject)
//                .when().put("/formroute/"+Globals.FORMROUTE_ID+"/state")
//                .then().body("_status", equalTo(true)).statusCode(201);


    }

}
