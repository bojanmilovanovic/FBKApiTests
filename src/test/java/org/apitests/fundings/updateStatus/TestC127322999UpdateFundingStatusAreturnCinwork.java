package org.apitests.fundings.updateStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.DBHelper;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.*;


@Listeners({TestRailRunner.class})
public class TestC127322999UpdateFundingStatusAreturnCinwork {

    private String statusBefore = "A_return";
    private String statusAfter = "C_inwork";
    DBHelper dbHelper = new DBHelper();

    @BeforeMethod(alwaysRun = true)
    public void testC127322999UpdateFundingStatusAreturnCinworkPrecondition() throws Exception {
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_fundings set funding_status = '"+statusBefore+"' where external_id = '"+ Globals.FUNDING_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"fundings", "tp1"})
    public void testC127322999UpdateFundingStatusAreturnCinwork(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        String requestBody = "{ \"state\": \""+statusAfter+"\" }";
        request.body(requestBody);

        // Response and assertion
        Response response = request.put("/fundings/"+Globals.FUNDING_ID+"/state");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

}
