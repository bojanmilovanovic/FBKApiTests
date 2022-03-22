package org.apitests.formroute;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({TestRailRunner.class})
public class TestCXXXXXXXStartFormRoute {

    @Test(groups = {"formroute", "tp1"}, enabled = false)
    public void testCXXXXXXXStartFormRoute(){

        // Generate token and set up the host
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/formroute/api/v1/"+Globals.TENANT;

        // Authentication and body set up
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(Globals.USER_TOKEN);
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fundingDefinition", "/fbk/Förderprogramme/TP1_CLXFunding");
        jsonObject.put("formRouteDefinition", "new");
        request.body(jsonObject);

        // Response and assertion
        Response response = request.post("formroute");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201, "Status is not 201.");

    }

}
