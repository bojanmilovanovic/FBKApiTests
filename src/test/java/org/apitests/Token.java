package org.apitests;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import org.apitests.core.Globals;

//TO DO: reimplement the two methods into one. Currently the token with tu_server2server is required for tasks.
public class Token {

    String tokenType = "";
    String tokenValue = "";

    public Token() {
        System.out.println("Token constructor is running");
        String body = "grant_type=client_credentials";
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("tu_sap2portal");
        authScheme.setPassword("secret");
        RestAssured.authentication = authScheme;

        Response response = RestAssured.given().with().header("Content-Type", "application/x-www-form-urlencoded").body(body).post(Globals.PROTOCOL + "://" + Globals.HOST + "/authserver/oauth/" + Globals.TENANT + "/oauth/token");
        tokenType = response.path("token_type");
        tokenValue = response.path("access_token");
    }

    public Token(String tokenName) {
        String body = "grant_type=client_credentials";
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        if (tokenName.equalsIgnoreCase("server")) {
            System.out.println("Generating a server token");
            authScheme.setUserName("tu_server2server");
        }
        else {
            System.out.println("Generating a sap token");
            authScheme.setUserName("tu_sap2portal");
        }
        authScheme.setPassword("secret");
        RestAssured.authentication = authScheme;

        Response response = RestAssured.given().with().header("Content-Type", "application/x-www-form-urlencoded").body(body).post(Globals.PROTOCOL + "://" + Globals.HOST + "/authserver/oauth/" + Globals.TENANT + "/oauth/token");
        tokenType = response.path("token_type");
        tokenValue = response.path("access_token");
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

}
