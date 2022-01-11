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
public class TestC136343869DeleteFolder {

    @BeforeMethod(alwaysRun = true)
    public void testC136343869DeleteFolderPrecondition() throws IOException {
        TestC136343867CreateFolder testC136343867CreateFolder = new TestC136343867CreateFolder();
        testC136343867CreateFolder.testC136343867CreateFolder();
    }

    @Test(groups = {"docshare", "tp1"})
    public void testC136343869DeleteFolder() {

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
        request.multiPart("deleteStrategy", "PHYSICAL");

        // Response and assertion
        Response response = request.delete("documents");
        Assert.assertEquals(response.getStatusCode(), 204, "Status code is not 204.");

    }
}
