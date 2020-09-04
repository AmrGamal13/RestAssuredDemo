package RestAssuredTCs;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;

	import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJSON {
	
	@Test
	public void AddBookInLibraryAPI() throws IOException {

		RestAssured.baseURI ="http://216.10.245.166";
		String response = given().header("Content-Type" ,"application/json")
				//will pass the Path of the JSON file (payload of the request) in our machine
				.body(GenerateStringFromResource("/Users/adelmostafa/Documents/AddBookPayloadRequest.json"))
				.when().post("/Library/Addbook.php")
				.then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(response);

		JsonPath jss = new JsonPath (response);
		String ID = jss.get("ID");
		System.out.println(ID);

	}
	
	//read the JSON file and convert it into string
	public static String GenerateStringFromResource(String path) throws IOException {
		
		//return new String (Files.readAllBytes(Paths.get(path)));
		return new String(Files.readAllBytes(Paths.get(path)));
		
	}
}
