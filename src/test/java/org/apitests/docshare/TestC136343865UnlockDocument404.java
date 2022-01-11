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

import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC136343865UnlockDocument404 {

    @BeforeMethod(alwaysRun = true)
    public void testC136343865UnlockDocument404Precondition() {
        TestC136343863UnlockDocument testC136343863UnlockDocument = new TestC136343863UnlockDocument();
        testC136343863UnlockDocument.testC136343863UnlockDocumentPrecondition();
        testC136343863UnlockDocument.testC136343863UnlockDocument();
    }

    @Test(groups = {"docshare", "tp1"})
    public void testC136343865UnlockDocument404() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("path", "/aaa/aaa.pdf");
        request.multiPart("lock", "false");

        // Response and assertion
        Response response = request.put("documents/lock");
        Assert.assertEquals(response.getStatusCode(), 404, "Status code is not 404.");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Value of the status flag is true");
        Assert.assertEquals(response.jsonPath().getString("_messages.text[0]"), "Document/Folder on path /aaa/aaa.pdf does not exist or is not accessible.", "Message text in the response is not correct");

    }
}
