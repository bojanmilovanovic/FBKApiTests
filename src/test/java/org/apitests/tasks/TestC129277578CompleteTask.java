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

@Listeners({TestRailRunner.class})
public class TestC129277578CompleteTask {

    @BeforeMethod(alwaysRun = true)
    public void testC129277578CompleteTaskPrecondition() throws Exception {
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDBConnectionTasks();
        dbHelper.runUpdate("update tsk_task set status = 'READY' where related_id = '"+ Globals.TASK_ID+"'");
        dbHelper.closeConnection();
    }

    @Test(groups = {"tasks", "tp1"})
    public void testC129277578CompleteTask() {

        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/admin-api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        String body = "{ }";
        request.body(body);

        Response response = request.post("/tasks/"+Globals.TASK_ID+"/complete");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

}
