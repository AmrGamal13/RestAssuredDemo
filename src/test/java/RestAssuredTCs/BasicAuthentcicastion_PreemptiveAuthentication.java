package RestAssuredTCs;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class BasicAuthentcicastion_PreemptiveAuthentication {


	@Test
	public void f() {

		//Endpoint: http://restapi.demoqa.com/authentication/CheckForAuthentication

		//preemptive basic authentication here is basic authentication for passing the credentials (username & password)
		//to authroize yourself
		RestAssured.baseURI = "https://int-2.con.bmw";

		given().relaxedHTTPSValidation().log().all()
		.auth().preemptive().basic("stage2user", "Wdin4twf")
		.when()
		.get("/modelrangeselection.html")
		.then();


				
				
		
	}
}
