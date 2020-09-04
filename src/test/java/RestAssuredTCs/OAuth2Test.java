package RestAssuredTCs;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class OAuth2Test {
	@Test
	public void TestOAuth2_Rahul() {

		//Mechanism: we will need to test getCourse API and that API uses OAuth2.0 for authentication and for doing that
		//we need first to get the Access token to send it as a query parameter in the getCourse API
		//but to get the access token you need to have the authentication code 
		//but to get that code you need to have the URl for the authorization server of (Google/Facebook) and login in
		//via the browser, then after login , the code will be presented in the URL and then use it


		//For the demo as we can't automate that part of login  from the browser we will get the URL response (that contains
		//the code) and work normally

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
				.queryParam("greant_type", "authorization_code")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.when().post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(responsenew);
		String accessToken=	js.getString("access_token");

		System.out.println(accessToken);

		//3- the actual getCourse API that I need to run and with using the access token
		String response =given().queryParam("access_token", accessToken)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);
	}



}
