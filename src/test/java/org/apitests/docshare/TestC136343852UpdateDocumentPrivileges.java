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
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;


@Listeners({TestRailRunner.class})
public class TestC136343852UpdateDocumentPrivileges {

    @BeforeMethod(alwaysRun = true)
    public void testC136343852UpdateDocumentPrivilegesPrecondition() throws IOException {
        TestC136343844SearchForDocumentsOfFunding testC136343844SearchForDocumentsOfFunding = new TestC136343844SearchForDocumentsOfFunding();
        testC136343844SearchForDocumentsOfFunding.testC136343844SearchForDocumentsOfFunding();
    }

    @Test(groups = {"docshare", "tp1"})
    public void testC136343852UpdateDocumentPrivileges() throws IOException {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/docshare/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/docshare/body/TestUpdateDocumentPrivilegesBody.json");
        String requestBody = Files.readFile(file);
        requestBody = requestBody.replace("DOCUMENT_DOWNLOAD", Globals.DOWNLOAD_DOCUMENT);
        requestBody = requestBody.replace("USER_ID", "ORG_"+Globals.USER_UUID);
        request.body(requestBody);

        // Response and assertion
        Response response = request.put("documents/privileges?path="+Globals.DOWNLOAD_DOCUMENT);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200.");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of the _status flag is not true");
        Assert.assertTrue(response.jsonPath().getString("_messages.text").contains("Privileges set for document with path "+Globals.DOWNLOAD_DOCUMENT), "Response message text is not correct");

    }


}
