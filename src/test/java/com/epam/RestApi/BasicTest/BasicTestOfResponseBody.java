package com.epam.RestApi.BasicTest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class BasicTestOfResponseBody {

    /*
      in this methode we have verified each of the json path key value pair in response body
      with help of hamcrest Matchers equalTo method
     */
    @Test
    public void verifyCountryDetails()
    {
        given().baseUri("http://api.zippopotam.us").
        when().get("/US/90210").
        then().assertThat().statusCode(200).
                            body("country",equalTo("United States")).
                            body("'country abbreviation'",equalTo("US")).
                            body("'post code'",equalTo("90210"));
    }
    @Test(priority=1)
    public void verifyPlaceDetails()
    {
        given().baseUri("http://api.zippopotam.us").
                when().get("/US/90210").
                then().assertThat().statusCode(200).
                body("places[0].state",equalTo("California")).
                body("places[0].'state abbreviation'",equalTo("CA"));
    }
    @Test
    public void verifyNegativeCase()
    {
        given().baseUri("http://api.zippopotam.us").
                when().get("/US/99999").
                then().assertThat().statusCode(404);

    }

    @Test
    public void verifyAPIResponseHeader()
    {
        given().baseUri("http://api.zippopotam.us").when().get("/US/90210").
                then().assertThat().statusCode(200).header("charset",equalTo("UTF-8")).
                contentType(equalTo("application/json"));
    }
}
