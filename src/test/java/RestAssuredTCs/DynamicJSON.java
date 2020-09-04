package RestAssuredTCs;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJSON {

	// Adding book using POST Method 
	@Test (dataProvider = "BooksData")
	//two parameters here are for the 2 elements in the array of the DataProvider in the method of getData()
	public void AddBookInLibraryAPI(String isbn , String aisle) {

		RestAssured.baseURI ="http://216.10.245.166";
		//it will pass the isbn & aisle values of every array to payload of adding Book
		String response = given().header("Content-Type" ,"application/json")
				.body(PayLoad.RequestBodyAddingBook(isbn,aisle))
				.when().post("/Library/Addbook.php")
				.then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(response);

		JsonPath jss = new JsonPath (response);
		String ID = jss.get("ID");
		System.out.println(ID);

	}


	@DataProvider(name ="BooksData")
	public  Object[][] getData () {

		//array is a collection of elements
		//multidimensional array is a collection of arrays 
		//below is about 3 arrays and every array has elements that change all the time 
		// I need to  in the payload when I need to test POST method of Adding the book . 
		//so used Data provider of TestNg of that and that wil run 3 times based on the 3 arrays below
		return new Object[][] { { "Book2","23523" }, { "Book22","38292" }, { "Book23", "19220" } };
	}
}
