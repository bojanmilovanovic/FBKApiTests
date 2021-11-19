package org.apitests.dynamicform;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.reporters.Files.readFile;

@Listeners({TestRailRunner.class})
public class TestC127324224CreateDynamicForm {

    @Test(groups = {"dynamicform", "tp1"})
    public void testC127324224CreateDynamicForm() throws IOException {

        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/dynamicform/api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/dynamicform/body/TestCreateDynamicFormBody.json");
        String requestBody = readFile(file);

        request.body(requestBody);

        Response response = request.post("/dynamicform");

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));

        Globals.DYNAMIC_FORM_ID = response.jsonPath().getString("id");

    }

}
