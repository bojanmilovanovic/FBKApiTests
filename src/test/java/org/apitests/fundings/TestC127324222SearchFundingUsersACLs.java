package org.apitests.fundings;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apitests.Token;
import org.apitests.core.Globals;
import org.apitests.core.TestRailRunner;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;

@Listeners({TestRailRunner.class})
public class TestC127324222SearchFundingUsersACLs {

    @Test(groups = {"fundings", "tp1"})
    public void testSearchFundingUsersACLs() throws IOException {
        Token token = new Token("sap");
        RestAssured.baseURI = Globals.PROTOCOL+"://"+Globals.HOST+"/funding/api/v1/"+Globals.TENANT;
        RequestSpecification request = RestAssured.given();
        request.auth().oauth2(token.getTokenValue());
        request.header("Accept", "application/json");
        request.header("Content-Type", "application/json");
        File file = new File("src/test/java/org/apitests/fundings/body/TestSearchFindingUsersACLsBody.json");
        String body = Files.readFile(file);
        request.body(body);

        Response response = request.post("/fundings/user/acls/search");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("_status"));
    }

}
