package org.apitests.fundings;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC137793281ExportFundingPaymentsXLSXByContract {

    @Test(groups = {"fundings", "tp1"})
    public void testC137793281ExportFundingPaymentsXLSXByContract() throws IOException {

        // Generate token and set up the host
        // User Token is used
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/funding/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(Globals.USER_TOKEN);
        request.header("Accept", "application/octet-stream");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/fundings/body/TestExportPaymentsByContractBody.json");
        String body = Files.readFile(file);
        request.body(body);

        // Response and assertion
        Response response = request.post("/fundings/payments/"+Globals.FUNDING_ADDITIONAL_TABS_ID+"/export?fileType=xlsx");
        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not 201");
        Assert.assertEquals(response.header("content-type"), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Response header is not excel xlsx");
        Assert.assertTrue(response.header("content-disposition").endsWith(".xlsx"),  "Response is not a xlsx file");

    }

}
