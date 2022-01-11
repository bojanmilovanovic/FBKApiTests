package org.apitests.docshare;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;


@Listeners({TestRailRunner.class})
public class TestC136343844SearchForDocumentsOfFunding {

    @Test(groups = {"docshare", "tp1"})
    public void testC136343844SearchForDocumentsOfFunding() throws IOException {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/docshare/body/TestSearchForDocumentsOfFundingBody.json");
        String requestBody = Files.readFile(file);
        requestBody = requestBody.replace("FUNDING_ID", Globals.FUNDING_ID);
        request.body(requestBody);

        // Response and assertion
        Response response = request.post("documents/search");
        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not 201.");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of the _status flag is not true");

        int listSize = response.jsonPath().getList("documents").size();
        for(int i=0; i<listSize; i++){
            Assert.assertTrue(response.jsonPath().getString("documents["+i+"].name").contains(Globals.FUNDING_ID), "Name of the document does not match with filter");
            Assert.assertTrue(response.jsonPath().getString("documents["+i+"].mimeType").contains("application/pdf"), "Mime type of the document does not match with filter");
        }

        // Prepare variables for following tests
        Globals.DOWNLOAD_DOCUMENT = response.jsonPath().getString("documents[0].path");

    }


}
