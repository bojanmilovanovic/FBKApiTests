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


@Listeners({TestRailRunner.class})
public class TestC127323603UpdateFundingStatusNegativeScenariosAAccepted {

    private String statusBefore = "A_accepted";
    private String[] statusAfterA_accepted = new String[]{"B_approved", "B_changed", "B_inwork", "B_legal", "B_paid", "B_partlypaid", "C_finished", "C_inwork"};
    DBHelper dbHelper = new DBHelper();

    @BeforeMethod
    public void testUpdateFundingStatusNegativeScenariosAAcceptedPrecondition() throws Exception {
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_fundings set funding_status = '"+statusBefore+"' where external_id = '"+ Globals.FUNDING_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"fundings", "tp1"})
    public void testUpdateFundingStatusNegativeScenariosAAccepted(){
        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        for(int i=0; i<statusAfterA_accepted.length; i++) {
            String requestBody = "{ \"state\": \"" + statusAfterA_accepted[i] + "\" }";
            request.body(requestBody);
            Response response = request.put("/fundings/" + Globals.FUNDING_ID + "/state");
            Assert.assertEquals(response.getStatusCode(), 400, "Status is not 400 for converting "+statusBefore+" to "+statusAfterA_accepted[i]+".");
            Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status is true");
        }

    }

    @AfterMethod
    public void testUpdateFundingStatusNegativeScenariosAAcceptedPostcondition() throws Exception {
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_fundings set funding_status = 'A_accepted' where external_id = '"+ Globals.FUNDING_ID+"'");
        dbHelper.closeConnection();
    }
}
