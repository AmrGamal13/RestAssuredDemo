package RestAssuredTCs;

public class PayLoad {

	
	//mock response (dummy response) to know how to deal with nested json
	public static String coursePrice() {
		
		
		return "{\n" + 
				"  \"dashboard\": {\n" + 
				"    \"purchaseAmount\": 910,\n" + 
				"    \"website\": \"rahulshettyacademy.com\"\n" + 
				"  },\n" + 
				"  \"courses\": [\n" + 
				"    {\n" + 
				"      \"title\": \"Selenium Python\",\n" + 
				"      \"price\": 50,\n" + 
				"      \"copies\": 6\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"title\": \"Cypress\",\n" + 
				"      \"price\": 40,\n" + 
				"      \"copies\": 4\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"title\": \"RPA\",\n" + 
				"      \"price\": 45,\n" + 
				"      \"copies\": 10\n" + 
				"    }\n" + 
				"  ]\n" + 
				"}";
	}
	
	
	
	public static String RequestBodyAddingBook(String isbnValue , String aisleValue ) {
		
		String body = "{\n" + 
				"    \"name\" : \"Learn Automation with Java\",\n" + 
				"    \"isbn\" : \""+isbnValue+"\",\n" + 
				"    \"aisle\" : \""+aisleValue+"\",\n" + 
				"    \"author\" : \"John Foo\"\n" + 
				"}";
		
		return body;
		
		
	}
}
