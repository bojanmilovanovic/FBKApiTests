package org.apitests.tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.DBHelper;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC129277580CompleteTaskNegativeScenario {

    String MESSAGE_RESPONSE = "[Completion not possible for task in status - COMPLETED.]";

    @BeforeMethod
    public void testC129277580CompleteTaskNegativeScenarioPrecondition() throws Exception {
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDBConnectionTasks();
        dbHelper.runUpdate("update tsk_task set status = 'COMPLETED' where related_id = '"+ Globals.TASK_FORMROUTE_ID+"'");
        dbHelper.closeConnection();
    }

    @Test
    public void testC129277580CompleteTaskNegativeScenario() throws IOException {

        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/admin-api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        String body = "{ }";
        request.body(body);

        Response response = request.post("/tasks/"+Globals.TASK_ID+"/complete");

        Assert.assertEquals(response.getStatusCode(), 400, "Response status code is not 400");
        Assert.assertFalse(response.jsonPath().getBoolean("_status"), "Status is true instead of false");
        Assert.assertEquals(response.jsonPath().getString("_messages.text"), MESSAGE_RESPONSE, "Response message text is wrong");

    }

}
