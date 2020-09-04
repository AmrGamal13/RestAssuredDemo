package RestAssuredTCs;

import org.testng.annotations.Test;
import POJO_Serialization.AddPlaceAPI_Request_Serialization;
import POJO_Serialization.location;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeTest_Using_POJO {
	@Test
	public void AddPlaceAPI() {
		//create objects of the pojo classes we create
		AddPlaceAPI_Request_Serialization add = new AddPlaceAPI_Request_Serialization();
		location loc = new location();

		loc.setLat(-38.383494);
		loc.setLat(33.427362);

		//using set methods for adding the values
		add.setAccuracy(50);
		add.setName("new york man house");
		add.setPhone_number("(+91) 983 893 3937");
		add.setAddress("29, side layout, cohen 09");
		add.setWebsite("http://google.com");
		add.setLanguage("French-IN");

		//types key has json array so it expects list, so we create a list and adding the values of it then passed 
		//to set method
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		add.setTypes(mylist);

		//set location is has object return type , so we added the object of it's class after using for setting method
		add.setLocation(loc);

		//in body we will pass the object of our main class (AddPlaceAPI_Request_Serialization)
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		Response re =  given().log().all().queryParam("key", "qaclick123")
				.body(add)
				.when().post("/maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response();

		String response = re.asString();
		System.out.println(response);
	}
}