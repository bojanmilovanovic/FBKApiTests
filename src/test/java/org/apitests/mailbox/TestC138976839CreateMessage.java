package org.apitests.mailbox;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
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
public class TestC138976839CreateMessage {

    @Test(groups = {"tp1", "mailbox"})
    public void testC138976839CreateMessage(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkmessage/api/v1/"+Globals.TENANT;
        File attachmentFile = new File("src/test/java/org/apitests/mailbox/testfiles/MessageAttachment.pdf");
        File schema = new File("src/test/java/org/apitests/mailbox/schema/CreateMessageSchema.json");

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.contentType("multipart/form-data");
        request.multiPart("partnerNo", Globals.PARTNER_ID);
        request.multiPart("messageType", "NEW");
        request.multiPart("applicationNo", Globals.FUNDING_ID);
        request.multiPart("sender", "Tom Jones");
        request.multiPart("subject", "API Test");
        request.multiPart("body", "This is a message generated by an API test");
        request.multiPart("attachments", attachmentFile);

        // Response and assertion
        Response response = request.post("/message");
        Assert.assertEquals(response.statusCode(), 201, "Response code is not 201");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of the status flag is not true");
        Assert.assertEquals(response.jsonPath().getString("_messages[0].text"), "Successfully created message!", "Response text message is not correct");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

        // Global variable preparation for next tests
        Globals.MESSAGE_ID = response.jsonPath().getString("id");

    }

}
