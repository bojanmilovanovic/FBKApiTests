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

import static org.testng.reporters.Files.readFile;

@Listeners({TestRailRunner.class})
public class TestC127324226GetDynamicForm {

    @BeforeMethod
    public void testGetDynamicFormPrecondition() throws IOException {
        System.out.println("Before test is run on Get Dynamic Form");
        TestC127324224CreateDynamicForm testC127324224CreateDynamicForm = new TestC127324224CreateDynamicForm();
        testC127324224CreateDynamicForm.testCreateDynamicForm();
    }

    @Test(groups = {"dynamicform", "tp1"})
    public void testGetDynamicForm() {

        Token token = new Token("sap");
        String dynamicFormId = Globals.DYNAMIC_FORM_ID;
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/dynamicform/api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        Response response = request.get("/dynamicform/"+dynamicFormId);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

    }

    @AfterMethod
    public void testGetDynamicFormPostcondition() {
        TestC127324225DeleteDynamicForm testC127324225DeleteDynamicForm = new TestC127324225DeleteDynamicForm();
        testC127324225DeleteDynamicForm.testDeleteDynamicForm();
    }

}
