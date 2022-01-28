package org.apitests.mailbox;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
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
public class TestC138976843GetMessage {

    @BeforeMethod(alwaysRun = true)
    public void testC138976843GetMessagePrecondition(){
        TestC138976839CreateMessage testC138976839CreateMessage = new TestC138976839CreateMessage();
        testC138976839CreateMessage.testC138976839CreateMessage();
    }

    @Test(groups = {"tp1", "mailbox"})
    public void testC138976843GetMessage(){

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/fbkmessage/api/v1/"+Globals.TENANT;
        File schema = new File("src/test/java/org/apitests/mailbox/schema/GetMessageSchema.json");

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        Response response = request.get("/message/"+Globals.MESSAGE_ID);
        Assert.assertEquals(response.statusCode(), 200, "Response code is not 200");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of the status flag is not true");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

    }

}
