package org.apitests.dynamicform;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC127324231PutDynamicFormACL {

    @BeforeMethod(alwaysRun = true)
    public void testC127324231PutDynamicFormACLPrecondition() throws IOException {
        TestC127324224CreateDynamicForm testC127324224CreateDynamicForm = new TestC127324224CreateDynamicForm();
        testC127324224CreateDynamicForm.testC127324224CreateDynamicForm();
    }

    @Test(groups = {"dynamicform", "tp1"})
    public void testC127324231PutDynamicFormACL() {

        // Generate token and set up the host
        Token token = new Token("sap");
        String dynamicFormId = Globals.DYNAMIC_FORM_ID;
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/dynamicform/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.body("[ {\"userOrGroupName\": \""+Globals.USER_UUID+"\", \"permissions\": [\"DELETE\"]} ]");

        // Response and assertion
        Response response = request.put("/dynamicform/"+dynamicFormId+"/acl");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

    @AfterMethod(alwaysRun = true)
    public void testC127324231PutDynamicFormACLPostcondition() {
        TestC127324225DeleteDynamicForm testC127324225DeleteDynamicForm = new TestC127324225DeleteDynamicForm();
        testC127324225DeleteDynamicForm.testC127324225DeleteDynamicForm();
    }

}
