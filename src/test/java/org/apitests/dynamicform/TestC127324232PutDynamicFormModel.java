package org.apitests.dynamicform;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static org.testng.reporters.Files.readFile;

@Listeners({TestRailRunner.class})
public class TestC127324232PutDynamicFormModel {

    @BeforeMethod
    public void testPutDynamicFormModelPrecondition() throws IOException {
        TestC127324224CreateDynamicForm testC127324224CreateDynamicForm = new TestC127324224CreateDynamicForm();
        testC127324224CreateDynamicForm.testCreateDynamicForm();
    }

    @Test
    public void testPutDynamicFormModel() throws IOException {

        Token token = new Token("sap");
        String dynamicFormId = Globals.DYNAMIC_FORM_ID;
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/dynamicform/api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/dynamicform/body/TestUpdateDynamicFormModelBody.json");
        String requestBody = readFile(file).replace("{{dynamicFormID}}", dynamicFormId);
        request.body(requestBody);

        Response response = request.put("/dynamicform/meta");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));
        Assert.assertEquals(response.jsonPath().getBoolean("updateInfo[0].updated"), true, "Update flag is not correctly returned.");

    }

    @AfterMethod
    public void testPutDynamicFormModelPostcondition() throws IOException {
        TestC127324225DeleteDynamicForm testC127324225DeleteDynamicForm = new TestC127324225DeleteDynamicForm();
        testC127324225DeleteDynamicForm.testDeleteDynamicForm();
    }

}
