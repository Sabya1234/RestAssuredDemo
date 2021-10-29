package com.epam.RestApi.RequestSpecificationRefactored;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class DataDrivenTest {
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
                .expectResponseTime(lessThan(2000L))
                .build();
    }



    /*
      in this methode we have verified each of the json path key value pair in response body
      with help of hamcrest Matchers equalTo method
     */

    @Test(dataProvider = "ZipCodeData")
    public void verifyPlaceDetails(String countryCode,String zipCode,String state)
    {
            given().pathParam("CountryCode",countryCode).
                    pathParam("ZipCode",zipCode).
               get("/{CountryCode}/{ZipCode}").
                then().
                body("places[0].state",equalTo(state));

    }

     @DataProvider(name="ZipCodeData")
     public  Object[][] getZipCodeData()
     {
         return new Object[][] {
                 {"US","90210","California"},
                 {"US","12345","New York"},
                 {"DE","01067","Sachsen"},
                 {"GB","AB1","Scotland"}
         };
     }

}
