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
public class TestC127321943UpdateFormRouteStatusNegativeScenarioAReturn {

    private String statusBefore = "A_return";
    private String[] statusAfter = new String[]{"A_accepted","A_error","A_finished","A_received","A_rejected","B_aborted","B_finished","B_inwork","C_deleted","C_finished","C_new"};

    @BeforeMethod(alwaysRun = true)
    public void testC127321943UpdateFormRouteStatusNegativeScenarioAReturnPrecondition() throws Exception {
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDBConnectionFundings();
        dbHelper.runUpdate("update fbk_form_routes set formroute_status = '"+statusBefore+"' where external_id = '"+ Globals.FORMROUTE_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"formroute", "tp1"})
    public void testC127321943UpdateFormRouteStatusNegativeScenarioAReturn(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkfundings/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        for (String s : statusAfter) {
            String requestBody = "{ \"state\": \"" + s + "\" }";
            request.body(requestBody);
            Response response = request.put("/formroute/" + Globals.FORMROUTE_ID + "/state");
            Assert.assertEquals(response.getStatusCode(), 400, "Status is not 400 for converting " + statusBefore + " to " + s + ".");
            Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status is true");
        }

    }

}
