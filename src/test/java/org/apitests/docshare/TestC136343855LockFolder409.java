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
public class TestC136343855LockFolder409 {

    @BeforeMethod(alwaysRun = true)
    public void testC136343855LockFolder409Precondition() throws IOException {
        TestC136343867CreateFolder testC136343867CreateFolder = new TestC136343867CreateFolder();
        testC136343867CreateFolder.testC136343867CreateFolder();
        TestC136343854LockFolder testC136343854LockFolder = new TestC136343854LockFolder();
        testC136343854LockFolder.testC136343854LockFolder();
    }

    @Test(groups = {"docshare", "tp1"})
    public void testC136343855LockFolder409() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("path", "/"+Globals.FUNDING_ID+"/"+Globals.FOLDER_NAME);
        request.multiPart("lock", true);

        // Response and assertion
        Response response = request.put("documents/lock");
        Assert.assertEquals(response.getStatusCode(), 409, "Status code is not 409.");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Value of the _status flag is not false");
        Assert.assertEquals(response.jsonPath().getString("_messages.text[0]"), "Folder with path /"+Globals.FUNDING_ID+"/"+Globals.FOLDER_NAME+" already locked by tu_sap2portal.", "Message text in the response is not correct");

    }
}
