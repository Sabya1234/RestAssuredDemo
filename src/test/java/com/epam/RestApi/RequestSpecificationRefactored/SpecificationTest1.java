package com.epam.RestApi.RequestSpecificationRefactored;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class SpecificationTest1 {
/* so for refactoring given statement , instead of everytime in test method we can define particular endpoint
into RequestSpecification .set BaseUriMethod
 */
    private RequestSpecification requestSpecification=new RequestSpecBuilder().setBaseUri("http://api.zippopotam.us").
        setContentType("application/json").build();

    private ResponseSpecification responseSpecification = new ResponseSpecBuilder().
            expectStatusCode(200).expectContentType("application/json")
            .expectResponseTime(lessThan(2000L))
            .build();
    private ResponseSpecification responseSpecificationError= new ResponseSpecBuilder().expectStatusCode(404).build();




    /*
      in this methode we have verified each of the json path key value pair in response body
      with help of hamcrest Matchers equalTo method
     */
    @Test
    public void verifyCountryDetails()
    {
        //setting the endpoint through given().spec(object of Request specification)

        given().spec(requestSpecification).
        when().get("/US/90210").
        then().spec(responseSpecification).
                            body("country",equalTo("United States")).
                            body("'country abbreviation'",equalTo("US")).
                            body("'post code'",equalTo("90210"));
    }
    @Test(priority=1)
    public void verifyPlaceDetails()
    {
        given().spec(requestSpecification).
                when().get("/US/90210").
                then().spec(responseSpecification).
                body("places[0].state",equalTo("California")).
                body("places[0].'state abbreviation'",equalTo("CA"));
    }
    @Test
    public void verifyNegativeCase()
    {
        given().spec(requestSpecification).
                when().get("/US/99999").
                then().spec(responseSpecificationError);

    }

    @Test
    public void verifyAPIResponseHeader()
    {
        given().spec(requestSpecification).when().get("/US/90210").
                then().spec(responseSpecification).header("charset",equalTo("UTF-8"));
                //contentType(equalTo("application/json"));
    }
}
