package org.apitests.fundings.updateStatus;

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
public class TestC127324218UpdateFundingStatusNegativeScenariosBPartlypaid {

    private String statusBefore = "B_partlypaid";
    private String[] statusAfterB_partlypaid = new String[]{"A_accepted", "A_finished", "A_return", "B_approved", "B_changed", "B_inwork", "B_legal", "B_paid", "C_finished", "C_inwork"};
    DBHelper dbHelper = new DBHelper();

    @BeforeMethod(alwaysRun = true)
    public void testC127324218UpdateFundingStatusNegativeScenariosBPartlypaidPrecondition() throws Exception {
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_fundings set funding_status = '"+statusBefore+"' where external_id = '"+ Globals.FUNDING_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"fundings", "tp1"})
    public void testC127324218UpdateFundingStatusNegativeScenariosBPartlypaid(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        for (String s : statusAfterB_partlypaid) {
            String requestBody = "{ \"state\": \"" + s + "\" }";
            request.body(requestBody);
            Response response = request.put("/fundings/" + Globals.FUNDING_ID + "/state");
            Assert.assertEquals(response.getStatusCode(), 400, "Status is not 400 for converting " + statusBefore + " to " + s + ".");
            Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status is true");
        }

    }

}
