package com.epam.RestApi.AdvanceTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class TestAllVerbforPetstore {



    /*
      in this methode we have verified each of the xml path key value pair in response body
      with help of hamcrest Matchers equalTo method
     */

    //AUT is: https://petstore.swagger.io
   @BeforeClass
    public void setup()
    {
        RestAssured.baseURI="https://petstore.swagger.io";
        RestAssured.basePath="/v2";

        RestAssured.requestSpecification=new RequestSpecBuilder().setContentType("application/json").build();

    }
    /*This is also xml response validation , here path parameters are passed through testNG dataprovider*/
    @Test
    public void getPetDetailsByID()
    {

        get("/pet/9223372000001099113").
        then().statusCode(200).body("status",equalTo("available"));
    }

    @Test
    public void deletePetDetails()
    {
        delete("pet/9223372000001099113").
                then().
                       statusCode(200);
    }

    @Test
    public void createPet()
    {
        Response post = given().
                body("{\"id\":0,\"category\":{\"id\":0,\"name\":\"string\"},\"name\":\"doggie-epam\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}").
                when().
                post("/pet");


        String id = post.path("id").toString();

        System.out.println("the generated id:"+id);


    }



}
