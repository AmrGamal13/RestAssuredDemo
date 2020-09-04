package RestAssuredTCs;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.RequestBuilder;
import org.testng.annotations.Test;

import POJO_Serialization.AddPlaceAPI_Request_Serialization;
import POJO_Serialization.location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderExample {

	@Test

	public void SpecBuilderAppPlaceAPI() 
	{

		AddPlaceAPI_Request_Serialization add = new AddPlaceAPI_Request_Serialization();
		location loc = new location();
		loc.setLat(-38.383494);
		loc.setLat(33.427362);
		add.setAccuracy(50);
		add.setName("welcome home");
		add.setPhone_number("(+91) 983 893 3937");
		add.setAddress("29, side layout, cohen 09");
		add.setWebsite("http://google.com");
		add.setLanguage("French-IN");
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		add.setTypes(mylist);
		add.setLocation(loc);

		//in body we will pass the object of our main class (AddPlaceAPI_Request_Serialization)
		//RestAssured.baseURI = "https://rahulshettyacademy.com";

		//request spec builder for the request includes(baseUri, content type , queryparam)
		RequestSpecification requestbuild = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		//response spec builder includes assertion of the status code & content type
		ResponseSpecification responsebuild = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		RequestSpecification req= given().spec(requestbuild)
				.body(add);

		Response response = req
				.when().post("/maps/api/place/add/json")
				.then().spec(responsebuild).extract().response();

		String responsedata = response.asString();
		System.out.println(responsedata);
	}

}
