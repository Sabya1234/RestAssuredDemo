package com.epam.RestApi.AdvanceTest;

import com.epam.RestApi.Model.Category;
import com.epam.RestApi.Model.Pet;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class TestPostwithJsonSerialization {



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
     * This is example of serialization through jackson databind API and POST call
     */
    @Test
    public void createPet()
    {
        Category category= new Category( 1,"dog" );
        Pet pet = new Pet(11789,"Snowy",category,"available");
        Response post = given().
                body(pet).
                when().
                post("/pet");


        String id = post.path("id").toString();

        System.out.println("the generated id:"+id);


    }

    /**
     * Deserialization as we are getting stream of bytes in java object
     */
    @Test
    public void getPet()
    {
        Pet pet = get("/pet/11789").as(Pet.class);
        System.out.println(pet.getName()+" "+pet.getStatus());

    }

    /**
      The Below function is demonstration of PUT verb
     */
    @Test
    public void updatePet()
    {
        Category category= new Category( 1,"dog" );
        Pet pet = new Pet(11789,"Snowy",category,"pending");
        Response post = given().
                body(pet).
                when().
                put("/pet");
        String updatedStatus=post.path("status").toString();
        System.out.println(updatedStatus);
        Reporter.log("The Updated Status is -"+updatedStatus);

    }


}
