package com.epam.RestApi.RequestSpecificationRefactored;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class ExtractDataFromResponseTest {
/* so for refactoring given statement , instead of everytime in test method we can define particular endpoint
into RequestSpecification .set BaseUriMethod
 */
    @BeforeClass
    public void setup()
    {
        RestAssured.requestSpecification=new RequestSpecBuilder().setBaseUri("http://api.zippopotam.us").
                setContentType("application/json").build();

        RestAssured.responseSpecification=new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType("application/json")
                .expectResponseTime(lessThan(3000L))
                .build();
    }



    /*
      in this methode we have verified each of the json path key value pair in response body
      with help of hamcrest Matchers equalTo method
     */

    /* Here we are extracting Data from Response instead of asserting it and manipulating two data and creating
    a different string that may be required for another test method */
    @Test
    public void verifyExtractData()
    {

        Response response = get("/US/90210");
                            //then().extract().response();

          int statusCode= response.statusCode();
          String Country= response.path("country");
          String state= response.path("places[0].state");
          System.out.println(Country+" - "+state);
          System.out.println(statusCode);

    }


}
