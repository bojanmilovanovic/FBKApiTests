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
public class TestC127324227GetDynamicFormACL {

    @BeforeMethod
    public void testGetDynamicFormACLPrecondition() throws IOException {
        TestC127324224CreateDynamicForm testC127324224CreateDynamicForm = new TestC127324224CreateDynamicForm();
        testC127324224CreateDynamicForm.testCreateDynamicForm();
    }

    @Test(groups = {"dynamicform", "tp1"})
    public void testGetDynamicFormACL() {

        Token token = new Token("sap");
        String dynamicFormId = Globals.DYNAMIC_FORM_ID;
        String permissions = "[[DELETE, READ, WRITE, ACL]]";
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/dynamicform/api/v1/"+Globals.TENANT;

        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");

        Response response = request.get("/dynamicform/"+dynamicFormId+"/acl");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));
        Assert.assertEquals(response.jsonPath().getString("listPermissions.permissionRoles"), permissions, "Permissions are not correctly returned.");
    }

    @AfterMethod
    public void testGetDynamicFormACLPostcondition() {
        TestC127324225DeleteDynamicForm testC127324225DeleteDynamicForm = new TestC127324225DeleteDynamicForm();
        testC127324225DeleteDynamicForm.testDeleteDynamicForm();
    }

}
