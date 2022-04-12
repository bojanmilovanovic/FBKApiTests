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

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.reporters.Files.readFile;

@Listeners({TestRailRunner.class})
public class TestC135595713GetTasksByRelatedID {

    private String relatedID = "";

    @BeforeMethod(alwaysRun = true)
    public void testC135595713GetTasksByRelatedIDPrecondition() throws SQLException, ClassNotFoundException, IOException {
        DBHelper dbHelper = new DBHelper();
        dbHelper.openDBConnectionTasks();
        ResultSet resultSet = dbHelper.runQuery("select * from tsk_task where owner_enterprise_customer_id = '" + Globals.USER_UUID + "' and rownum = 1");
        while(resultSet.next()) {
            relatedID = resultSet.getString("RELATED_ID");
        }
        dbHelper.closeConnection();
    }


    @Test(groups = {"tasks", "tp1"})
    public void testC135595713GetTasksByRelatedID() throws IOException {

        // Generate token and set up the host
        Token token = new Token();
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/tasks/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/tasks/body/TestGetTasksByRelatedID.json");
        String requestBody = readFile(file);
        requestBody = requestBody.replace("FUNDING_ID", relatedID);
        request.body(requestBody);

        // Response and assertion
        Response response = request.post("/tasks/search");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        Assert.assertTrue(response.jsonPath().getBoolean("_status"), "Value of the _status flag is not true");

        int resposeSize = response.jsonPath().getList("tasks").size();
        for(int i = 0; i < resposeSize; i++){
            Assert.assertEquals(response.jsonPath().getString("tasks["+i+"].relatedId"), relatedID, "Related ID in the response does not match the one from the filter");
        }


    }

}
