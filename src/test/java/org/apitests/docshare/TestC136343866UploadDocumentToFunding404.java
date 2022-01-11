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

import java.io.File;

@Listeners({TestRailRunner.class})
public class TestC136343866UploadDocumentToFunding404 {

    @Test(groups = {"docshare", "tp1"})
    public void testC136343866UploadDocumentToFunding404() {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;
        File document = new File("src/test/java/org/apitests/docshare/testfiles/TestFileDocShare.pdf");
        String metaInfo = "{\"owner\": \""+Globals.USER_UUID+"\"}";
        String path = "aaa";

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
        Assert.assertEquals(response.getStatusCode(), 404, "Status code is not 404");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Value of _status flag is not false");
        Assert.assertEquals(response.jsonPath().getString("_messages.text[0]"), "Folder on path aaa does not exist or is not accessible.", "Message text is not correct");

    }

}
