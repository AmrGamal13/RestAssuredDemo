package RestAssuredTCs;

import org.testng.annotations.Test;
import org.w3c.dom.Node;

import io.restassured.RestAssured;
import io.restassured.http.Header;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matcher;

public class CheckXML {
	
	
	
  @Test (priority = 1)
  public void testusingNormalparsing() {
	  
	  RestAssured.baseURI="http://www.thomas-bayer.com/";
	  
	  
	  given().pathParam("CustomerID", "15").header("Content-Type","application/json")
	  .when().get("/sqlrest/CUSTOMER/{CustomerID}")
	  .then().statusCode(200).log().all().body("CUSTOMER.ID", equalTo("15"));
	  
  }
  
  @Test(priority = 2)
  public void testusingXmlPathMethod() {
	  
	  RestAssured.baseURI="http://www.thomas-bayer.com/";
	  
	  
	  given().pathParam("CustomerID", "15").header("Content-Type","application/json")
	  .when().get("/sqlrest/CUSTOMER/{CustomerID}")
	  //we can write it as an xpath format ex:
	  .then().statusCode(200).log().all().body(hasXPath("/CUSTOMER/ID",containsString("15")));
	  
	 Matcher<Node> x= hasXPath("/INVOICEList/INVOICE");
	 
  }
  
  @Test(priority = 3)
  public void bearerToken() {
	  
	  RestAssured.baseURI="https://demoqa.com";
	  
	  
	  given().header(new Header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImFkZWxtb3N0YWZhIiwicGFzc3dvcmQiOiJHQG1hbGExMjMiLCJpYXQiOjE1OTg3OTQ2OTl9.yuQ7WKuktV_sIjfB2TSNv0x-9zJ0aRrEOKvCyRSPID8")).pathParam("CustomerID", "bbbd57c8-16d4-490a-a941-16033174626d").header("Content-Type","application/json")
	  .when().get("/Account/v1/User/{CustomerID}")
	  //we can write it as an xpath format ex:
	  .then().statusCode(200).log().all();
	 
  }
  
  
}
