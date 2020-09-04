package RestAssuredTCs;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
public class JiraApplication {


	@Test
	public void JiraTest() {
		//Get Session ID API

		RestAssured.baseURI  = "http://localhost:8080";
		//end point is: http://localhost:8080/rest/auth/1/session

		//first we need to send a post request with our credentials to get the session ID & Name and pass it in all
		//our requests later
		//there is a ready method for handling that called sessionFilter and that will listen to the session
		//store it and you can send it in your any requests , instead of the manual way (send a post request -then get 
		//the response -then parse JSON and take the session into string and save it a variable and then reuse it in 
		//any request

		SessionFilter session = new SessionFilter();

		given().relaxedHTTPSValidation().header("Content-Type", "application/json")
		.body("{ \"username\": \"Adel\", \"password\": \"adel01148494829\" }")
		.filter(session).log().all()
		.when().post("/rest/auth/1/session")
		.then().log().all();

		//Adding comment API to an existing issue 

		//end point is: http://localhost:8080/rest/api/2/issue/{IssueID_OR_KEY}/comment
		// as we deal with a path parameter we can add path parameter method and give it our value and pass its name
		//to the resource as below

		String MycommentMessage = "Hello, that's my comment via the API";

		String AddingCommentResponse=	given().pathParam("IssueID", "10002").header("Content-Type", "application/json")
				.filter(session).log().all()
				.body("{\n" + 
						"    \"body\" :\""+MycommentMessage+"\",\n" + 
						"    \"visibility\":{\n" + 
						"        \"type\": \"role\",\n" + 
						"        \"value\": \"Administrators\"\n" + 
						"    }\n" + 
						"}")
				.when().post("/rest/api/2/issue/{IssueID}/comment")
				.then().log().all().assertThat().statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath (AddingCommentResponse);
		//get The id of my comment
		String ID =	js.getString("id");

		// --------
		//Adding attachment API to an existing issue


		//content type is different because we send attachment file not a json
		//multpart here for sending the attachment file and send the path of it
		//filter(session) for getting the session ID
		given().log().all().header("Content-Type" , "multipart/form-data")
		.header("X-Atlassian-Token","no-check").
		pathParam("IssueID", "10002")
		.filter(session)
		.multiPart("file", new File("JiraAttachment"))
		.when().post("/rest/api/2/issue/{IssueID}/attachments")
		.then().log().all().assertThat().statusCode(200);



		//Get Issue Details 
		String response =given().pathParam("IssueID", "10002")
				.filter(session)
				.queryParam("fields", "comment")
				.when()
				.get("/rest/api/2/issue/{IssueID}")
				.then().log().all().extract().response().asString();
		System.out.println("------------------");
		System.out.println(response);

		//Asserting that the comment has been added successfully

		JsonPath jsObject = new JsonPath (response);
		//number of the comments
		int numberOfComments = jsObject.getInt("fields.comment.comments.size");
		System.out.println("Number of comments are " +numberOfComments);
		//get The id of every comment and if the ID is equal to the ID of my adding message,
		//it will get the message  and compare it to the message I sent of that ID of my adding message
		for (int i = 0; i < numberOfComments; i++) {

		String	IDOfCurrentMessag = jsObject.get("fields.comment.comments["+i+"].id").toString();
		
		if (IDOfCurrentMessag.equals(ID)) {
			String CurrentMessage = jsObject.getString("fields.comment.comments["+i+"].body");
			Assert.assertEquals(CurrentMessage, MycommentMessage);
			
			
			
			
		}

		}
	}
}
