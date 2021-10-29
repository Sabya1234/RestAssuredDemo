package com.epam.RestApi.AdvanceTest;

import com.epam.RestApi.Model.Category;
import com.epam.RestApi.Model.Pet;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class EndToEndPetStoreTest {



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

    /**
     * This is example of End to End test of all http verbs using HashMap this will eliminate of using serialization and deserialization
     *
     */
    @Test
    public void allTests()
    {

        Map<String, Object> petMap = getPetMap();

        //creating new Pet using post
        String newId = given().body(petMap).when().post("/pet").path("id").toString();
        System.out.println(newId);

        //validating the newly created pet with newID get call
        get("/pet/"+newId).then().statusCode(200).and().body("status",equalTo("pending"));

        //updating the pet status with respect to newId
        petMap.put("status","available");
        petMap.put("id",newId);
        given().body(petMap).when().put("/pet").then().statusCode(200);

        //validating the updated status code
        get("/pet/"+newId).then().statusCode(200).and().body("status",equalTo("available"));

        //deleting the newly created pet using DELETE verb
        delete("/pet/"+newId).then().statusCode(200);
        // validating the status code 404
        get("/pet/"+newId).then().statusCode(404);

    }

    /**
     * This method will create hash map of json objects
     * @return petMap
     */
    private Map<String, Object> getPetMap() {
        Map<String,Object> category=new HashMap<>();
        category.put("id",1);
        category.put("name","dog");
        Map<String,Object> petMap = new HashMap<>();
        petMap.put("name","snoopy");
        petMap.put("status","pending");
        petMap.put("category",category);
        return petMap;
    }


}
