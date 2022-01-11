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


@Listeners({TestRailRunner.class})
public class TestC136343857LockDocument {

    @BeforeMethod(alwaysRun = true)
    public void testC136343857LockDocumentPrecondition() {
        TestC135595714UploadDocumentToFunding testC135595714UploadDocumentToFunding = new TestC135595714UploadDocumentToFunding();
        testC135595714UploadDocumentToFunding.testC135595714UploadDocumentToFundingPrecondition();
        testC135595714UploadDocumentToFunding.testC135595714UploadDocumentToFunding();
    }

    @Test(groups = {"docshare", "tp1"})
    public void testC136343857LockDocument() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("path", "/"+Globals.FUNDING_ID+"/"+"TestFileDocShare.pdf");
        request.multiPart("lock", "true");

        // Response and assertion
        Response response = request.put("documents/lock");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200.");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of the status flag is false");
        Assert.assertEquals(response.jsonPath().getString("_messages.text[0]"), "Document "+"/"+Globals.FUNDING_ID+"/"+"TestFileDocShare.pdf"+" is successfully locked", "Message text in the response is not correct");

    }
}
