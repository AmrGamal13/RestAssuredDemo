package RestAssuredTCs;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import POJO.GetCourseAPI_Response_Rahul_Deserialization;
import groovyjarjarantlr4.v4.parse.ANTLRParser.sync_return;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuth2_0_Using_POJO {


	@Test
	public void Testwithpojo() {


		//1- that's the URL that includes the authentication code
		String url="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
		//splitting to extract the code
		String partialcode = url.split("code=")[1];
		String actualcode= partialcode.split("&scope")[0];


		//2- using the code to generate the access token
		//IF you we will use (Client Credentials) OAuth 2.0 we will use step 2 & 3 Only we will pass only: 
		//(Client_ID, Client Secret ID, Access Token URL (in post request) & Scope 
		String responsenew = given().urlEncodingEnabled(false).queryParam("code", actualcode)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("state", "verifyfjdss")
				.queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
				.queryParam("grant_type", "authorization_code")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.when().post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(responsenew);
		String accessToken=	js.getString("access_token");

		System.out.println(accessToken);

		//3- the actual getCourse API that I need to run and with using the access token
		//will call the class that contains the main Json response
		//line 45 is that the response that we are passing is JSON format so we are parsing JSON
		GetCourseAPI_Response_Rahul_Deserialization response =given().queryParam("access_token", accessToken)
				.expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourseAPI_Response_Rahul_Deserialization.class);

		//if I want to get the value of the linkedIn key, easily i will need to pass it like that
		System.out.println(response.getLinkedIn()); 
		//the same for the instructor
		System.err.println(response.getInstructor());

		//get the name of the course title in api called (soap ui)

		System.out.println(response.getCourses().getApi().get(1).getCourseTitle());

		//get the price of the (Soap UI Webservices testing) course title in api
		int sizeofapi = response.getCourses().getApi().size();

		for (int i = 0; i < sizeofapi; i++) {
			if (response.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("Soap UI Webservices testing")) {

				System.out.println(response.getCourses().getApi().get(i).getPrice());
				break;
			} 
			//-----------------
			//get all the course titles of webautomation
			
			int sizeofWebautomation = response.getCourses().getWebAutomation().size();
			
			List<String> options = new ArrayList<String>();
			
			for (int j = 0; j < sizeofWebautomation; j++) {
				
			String nameofcourse = response.getCourses().getWebAutomation().get(j).getCourseTitle();
			options.add(nameofcourse);
				
			}
		}
	}
}
