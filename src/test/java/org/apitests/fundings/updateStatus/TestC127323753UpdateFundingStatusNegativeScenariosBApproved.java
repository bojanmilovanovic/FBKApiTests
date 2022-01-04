package org.apitests.fundings.updateStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.DBHelper;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.stream.IntStream;


@Listeners({TestRailRunner.class})
public class TestC127323753UpdateFundingStatusNegativeScenariosBApproved {

    private String statusBefore = "B_approved";
    private String[] statusAfterB_approved = new String[]{"A_accepted", "A_finished", "A_return", "B_inwork", "C_finished", "C_inwork"};
    DBHelper dbHelper = new DBHelper();

    @BeforeMethod(alwaysRun = true)
    public void testC127323753UpdateFundingStatusNegativeScenariosBApprovedPrecondition() throws Exception {
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_fundings set funding_status = '"+statusBefore+"' where external_id = '"+ Globals.FUNDING_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"fundings", "tp1"})
    public void testC127323753UpdateFundingStatusNegativeScenariosBApproved(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        IntStream.range(0, statusAfterB_approved.length).forEach(i -> {
            String requestBody = "{ \"state\": \"" + statusAfterB_approved[i] + "\" }";
            request.body(requestBody);
            Response response = request.put("/fundings/" + Globals.FUNDING_ID + "/state");
            Assert.assertEquals(response.getStatusCode(), 400, "Status is not 400 for converting " + statusBefore + " to " + statusAfterB_approved[i] + ".");
            Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status is true");
        });

    }

    @AfterMethod(alwaysRun = true)
    public void testC127323753UpdateFundingStatusNegativeScenariosBApprovedPostcondition() throws Exception {
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_fundings set funding_status = 'A_accepted' where external_id = '"+ Globals.FUNDING_ID+"'");
        dbHelper.closeConnection();
    }
}
