package RestAssuredTCs;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class PostRequestDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Testing Adding City API
		//Validate if Add place API working as expected

		//Given = All input Details  (query params , Headers of the request , Body of the request)
		//When = Submit the API (Resource & HTTP Methods:Get - Put-POST-DELETE-PATCH)
		//Then = Validate the response

		RestAssured.baseURI ="https://rahulshettyacademy.com";
		//query parameters & Header & Body of the request
		//store the response body in a response string
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body("{\n" + 
						"  \"location\": {\n" + 
						"    \"lat\": -38.383494,\n" + 
						"    \"lng\": 33.427362\n" + 
						"  },\n" + 
						"  \"accuracy\": 50,\n" + 
						"  \"name\": \"newyork new house\",\n" + 
						"  \"phone_number\": \"(+91) 983 893 3937\",\n" + 
						"  \"address\": \"29, side layout, cohen 09\",\n" + 
						"  \"types\": [\n" + 
						"    \"shoe park\",\n" + 
						"    \"shop\"\n" + 
						"  ],\n" + 
						"  \"website\": \"http://google.com\",\n" + 
						"  \"language\": \"French-IN\"\n" + 
						"}")
				.when().post("/maps/api/place/add/json") // Http method & Resource 
				.then().assertThat().statusCode(200).body("scope", equalTo("APP")).extract().response().asString(); //extract the response

		System.out.println(response);

		JsonPath js = new JsonPath(response); // to parse the Json and the parameter is the string that you need to parse
		String PlaceId =js.getString("place_id");
		System.out.println("place_id is   " + PlaceId);

		//verifying that the status code of the response is 200
		//and verifying that the in the body response the (scope) key has a value (APP)


		//update place

		String thenewaddress = "New Egypt, Cairo";
		given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		//getting the place ID from the above string to update the address 
		.body("{\n" + 
				"\"place_id\":\"" +PlaceId+"\",\n" + 
				"\"address\":\""+thenewaddress+"\",\n" + 
				"\"key\":\"qaclick123\"\n" + 
				"}\n" + 
				"")
		.when().put("/maps/api/place/update/json")
		//verifying that the msg key in response body has a value (address successfully..)
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));



		//Get The place address

		//get the response body of the get request
		String GetResponse =given().queryParam("key", "qaclick123").queryParam("place_id", PlaceId)
				.when().put("/maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();

		JsonPath jsnew = new JsonPath(GetResponse);
		//get the value of (address)key from the response body
		String theaddress=	jsnew.getString("address");
		System.out.println(theaddress);
		//verify the address that i used to update is the address that retrieved from GET request
		Assert.assertEquals(theaddress, thenewaddress);


	}

}
