package org.apitests.docshare;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC136343846DownloadDocument {

    @BeforeMethod(alwaysRun = true)
    public void testC136343846DownloadDocumentPrecondition() throws IOException {
        TestC136343844SearchForDocumentsOfFunding testC136343844SearchForDocumentsOfFunding = new TestC136343844SearchForDocumentsOfFunding();
        testC136343844SearchForDocumentsOfFunding.testC136343844SearchForDocumentsOfFunding();
    }

    @Test(groups = {"docshare", "tp1"})
    public void testC136343846DownloadDocument() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");

        // Response and assertion
        Response response = request.get("/documents/download?path="+Globals.DOWNLOAD_DOCUMENT);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        Assert.assertEquals(response.header("content-type"), "application/pdf", "Header of the response is not application/pdf");

    }

}
