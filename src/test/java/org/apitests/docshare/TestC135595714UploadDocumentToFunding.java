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

@Listeners({TestRailRunner.class})
public class TestC135595714UploadDocumentToFunding {

    @BeforeMethod(alwaysRun = true)
    public void testC135595714UploadDocumentToFundingPrecondition(){
        // Delete the document before uploading it to avoid conflicts
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("path", "/"+Globals.FUNDING_ID+"/"+"TestFileDocShare.pdf");
        request.multiPart("deleteStrategy", "PHYSICAL");
        request.delete("/documents");
    }


    @Test(groups = {"docshare", "tp1"})
    public void testC135595714UploadDocumentToFunding() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;
        File document = new File("src/test/java/org/apitests/docshare/testfiles/TestFileDocShare.pdf");
        String metaInfo = "{\"owner\": \""+Globals.USER_UUID+"\"}";
        String path = "/"+Globals.FUNDING_ID;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("document", document);
        request.multiPart("metaInfo", metaInfo, "application/json");
        request.multiPart("path", path);

        // Response and assertion
        Response response = request.post("/documents");
        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not 201");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of _status flag is not true");
        Assert.assertFalse(response.jsonPath().getString("_messages").isEmpty(), "There is no value for _messages tag");
        Assert.assertFalse(response.jsonPath().getString("_timestamp").isEmpty(), "There is no value for _messages tag");
        Assert.assertFalse(response.jsonPath().getString("document").isEmpty(), "There is no value for _messages tag");

    }

}
