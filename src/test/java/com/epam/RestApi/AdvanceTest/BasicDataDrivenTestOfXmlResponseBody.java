package com.epam.RestApi.AdvanceTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.annotations.IBeforeClass;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class BasicDataDrivenTestOfXmlResponseBody {

    /*
      in this methode we have verified each of the xml path key value pair in response body
      with help of hamcrest Matchers equalTo method
     */

    //AUT is: https://petstore.swagger.io
    @BeforeClass
    public void setup()
    {
        RestAssured.requestSpecification=new RequestSpecBuilder().setBaseUri("https://petstore.swagger.io").
                setBasePath("/v2").setAccept("application/xml").build();
        RestAssured.responseSpecification= new ResponseSpecBuilder().expectStatusCode(200).
                expectContentType("application/xml").build();
    }
    /*This is also xml response validation , here path parameters are passed through testNG dataprovider*/
    @Test(dataProvider = "petData")
    public void getPetDetailsByID(String petId,String status)
    {
        given().pathParam("PetId",petId).
        get("/pet/{PetId}").
        then().body("pet.status",equalTo(status));
    }


   @DataProvider(name="petData")
    public Object[][] getPetData()
    {
        return new Object[][]{
               // {"1234567","available"},
                {"9223372000001099113","available"}
        };
    }

    @Test
    public void testXmlData()
    {
        given().baseUri("https://petstore.swagger.io").basePath("/v2").accept("application/xml")
                .when().get("/pet/1234567").then().statusCode(200).body("pet.status",equalTo("available"));
    }

    @Test
    public  void testJsonData(){
        given().baseUri("https://petstore.swagger.io").basePath("/v2").accept("application/json")
                .when().get("/pet/19625").then().statusCode(200).body("status",equalTo("available"));
    }

}
