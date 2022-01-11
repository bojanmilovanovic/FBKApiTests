package org.apitests.tasks;

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
public class TestC136343890DeleteATask404 {

    @Test(groups = {"tasks", "tp1"})
    public void testC136343888DeleteATask() {

        // Values needed for test
        String taskId = "__ID__64342d343538642d3233326630353764323d302b9d76f939366b03874f5ebc0e8321b3357981c787f6e91557917856f7c99a1bb7e41e664474653dec76f9ad695b476cd1";

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        // Response and assertion
        Response response = request.delete("/tasks/"+taskId);
        Assert.assertEquals(response.getStatusCode(), 404, "Status is not 404");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status flag is true when it should be false");
        Assert.assertEquals(response.jsonPath().getString("_messages.text[0]"), "Entity not found: Requested task does not exist.", "Response message text is not correct");
        File schema = new File("src/test/java/org/apitests/Response404Schema.json");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

    }

}
