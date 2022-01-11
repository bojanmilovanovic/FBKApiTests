package org.apitests.docshare;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.StringHelper;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC136343867CreateFolder {

    @Test(groups = {"docshare", "tp1"})
    public void testC136343867CreateFolder() throws IOException {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        String folderName = StringHelper.getRandomStringStartingWith("folder");
        File file = new File("src/test/java/org/apitests/docshare/body/TestCreateFolderBody.json");
        String requestBody = Files.readFile(file);
        requestBody = requestBody.replace("FUNDING_ID", Globals.FUNDING_ID);
        requestBody = requestBody.replace("FOLDER_NAME", folderName);
        request.body(requestBody);

        // Response and assertion
        Response response = request.post("documents/folders");
        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not 201.");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of the _status flag is not true");

        // Setup variables for future tests
        Globals.FOLDER_NAME = folderName;

    }
}
