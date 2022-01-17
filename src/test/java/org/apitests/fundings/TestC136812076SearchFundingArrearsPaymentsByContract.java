package org.apitests.fundings;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
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
public class TestC136812076SearchFundingArrearsPaymentsByContract {

    @Test(groups = {"fundings", "tp1"})
    public void testC136812076SearchFundingArrearsPaymentsByContract() throws IOException {

        // Generate token and set up the host
        // User Token is used
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/funding/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(Globals.USER_TOKEN);
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/fundings/body/SearchFundingArrearsPaymentsByContractBody.json");
        String body = Files.readFile(file);
        request.body(body);

        // Response and assertion
        Response response = request.post("/fundings/arrearspayments/"+Globals.FUNDING_ADDITIONAL_TABS_ID+"/search");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        File schema = new File("src/test/java/org/apitests/fundings/schema/SearchFundingArrearsPaymentsByContractSchema.json");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
        int n = response.jsonPath().getInt("pageResponse.numberOfElements");
        for (int i = 0; i < n; i++){
            Assert.assertEquals(response.jsonPath().getString("result["+i+"].contractNo"), "0000104004920", "Contract in the response is not the same from the filter");
        }

    }

}
