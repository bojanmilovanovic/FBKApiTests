package org.apitests.dynamicform;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC127324233SealDynamicForm {

    @BeforeMethod
    public void testSealDynamicFormPrecondition() throws IOException {
        TestC127324224CreateDynamicForm testC127324224CreateDynamicForm = new TestC127324224CreateDynamicForm();
        testC127324224CreateDynamicForm.testCreateDynamicForm();
    }

    @Test(groups = {"dynamicform", "tp1"})
    public void testSealDynamicForm() {

        Token token = new Token("sap");
        String dynamicFormId = Globals.DYNAMIC_FORM_ID;
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/dynamicform/api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        request.body("{\"readonly\": true}");

        Response response = request.put("/dynamicform/"+dynamicFormId+"/seal");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

    @AfterTest
    public void testSealDynamicFormPostcondition() {
        TestC127324225DeleteDynamicForm testC127324225DeleteDynamicForm = new TestC127324225DeleteDynamicForm();
        testC127324225DeleteDynamicForm.testDeleteDynamicForm();
    }

}
