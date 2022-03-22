package org.apitests;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import org.apitests.core.Globals;
import org.testng.Reporter;


public class Token {

    // If no parameter to the constructor is added, the default tu_sap2portal token is generated
    public Token() {
        if(Globals.TOKEN_VALUE.isEmpty()) {
            String body = "grant_type=client_credentials";
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            authScheme.setUserName("tu_sap2portal");
            authScheme.setPassword("secret");
            RestAssured.authentication = authScheme;
            Reporter.log("Generating a sap token", true);
            Response response = RestAssured.given().with().header("Content-Type", "application/x-www-form-urlencoded").body(body).post(Globals.PROTOCOL + "://" + Globals.HOST + "/authserver/oauth/" + Globals.TENANT + "/oauth/token");
            Globals.TOKEN_TYPE = response.path("token_type");
            Globals.TOKEN_VALUE = response.path("access_token");
        }
    }

    // Provide a parameter to the constructor so a specific token is generated
    public Token(String tokenName) {
        if (tokenName.equalsIgnoreCase("server")) {
            String body = "grant_type=client_credentials";
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            switch (tokenName) {
                case "server":
                    Reporter.log("Generating a server token", true);
                    authScheme.setUserName("tu_server2server");
                    break;
                case "tasks":
                    Reporter.log("Generating a tasks token", true);
                    authScheme.setUserName("tu_tasks");
                    break;
                case "fbk":
                    Reporter.log("Generating a fbk token", true);
                    authScheme.setUserName("tu_fbk");
                    break;
                case "funding":
                    Reporter.log("Generating a funding token", true);
                    authScheme.setUserName("tu_fundings");
                    break;
                default:
                    Reporter.log("Generating a sap token", true);
                    authScheme.setUserName("tu_sap2portal");
                    break;
            }
            authScheme.setPassword("secret");
            RestAssured.authentication = authScheme;
            Response response = RestAssured.given().with().header("Content-Type", "application/x-www-form-urlencoded").body(body).post(Globals.PROTOCOL + "://" + Globals.HOST + "/authserver/oauth/" + Globals.TENANT + "/oauth/token");
            Globals.TOKEN_TYPE = response.path("token_type");
            Globals.TOKEN_VALUE = response.path("access_token");
        }
    }

    public String getTokenType() {
        return Globals.TOKEN_TYPE;
    }

    public String getTokenValue() {
        return Globals.TOKEN_VALUE;
    }

}
