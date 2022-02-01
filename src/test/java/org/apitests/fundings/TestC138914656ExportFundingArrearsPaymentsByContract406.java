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
public class TestC138914656ExportFundingArrearsPaymentsByContract406 {

    @Test(groups = {"fundings", "tp1"})
    public void testC138914656ExportFundingArrearsPaymentsByContract406() throws IOException {

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
        Response response = request.post("/fundings/arrearspayments/"+Globals.FUNDING_ADDITIONAL_TABS_ID+"/export?fileType=txt");
        Assert.assertEquals(response.getStatusCode(), 406, "Status code is not 406");

    }

}
