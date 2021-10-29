package com.epam.RestApi.BasicTest;

import org.testng.Assert;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.;
//import  io.restassured.*;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BasicTestOfStatusCode {

    //http://api.zippopotam.us/US/00210

    @Test
    public void ValidateStatusCode() {
        given().
                baseUri("http://api.zippopotam.us").

                when().get("/US/90210").

                then().statusCode(200).statusLine("HTTP/1.1 200 OK");

    }

    /*
    Boilerplate code example like for every test code we need to define URl class object also
    setting up connection with HttpUrlConnection class , then setting requestmethod for everytime , instea dof that we can simply
    use RestAssured
     */
    @Test
    public void ValidateStatusCodeWithCoreJava() throws IOException {
        URL url = new URL("http://api.zippopotam.us/US/90210");
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod("GET");
        int statusCode=huc.getResponseCode();
        Assert.assertEquals(statusCode,200);

    }

}
