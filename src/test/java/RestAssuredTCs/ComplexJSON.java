package RestAssuredTCs;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ComplexJSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int puchaseamount;

		JsonPath jsOb = new JsonPath(PayLoad.coursePrice());

		//1- Print No of courses returned by API

		int numberofcourses =jsOb.getInt("courses.size()");
		System.out.println(numberofcourses);

		//2- Print Purchase Amount

		 puchaseamount =	jsOb.getInt("dashboard.purchaseAmount");
		System.out.println(puchaseamount);


		//3- Print Title of the first course

		String firstcourseTitle=jsOb.get("courses[0].title");
		System.out.println(firstcourseTitle);


		//4- Print All course titles and their respective Prices
		//will loop over the courses and get the title and price of every (i)
		for (int i = 0; i < numberofcourses; i++) {

			String TitleName =	jsOb.getString("courses["+i+"].title");
			System.out.println("Title name is  " +TitleName);

			int price = jsOb.getInt("courses["+i+"].price");
			System.out.println("Price is  " +price);
		}

		//5- Print no of copies sold by RPA Course

		for (int i = 0; i < numberofcourses; i++) {

			String everyTitleName =	jsOb.getString("courses["+i+"].title");
			if (everyTitleName.equalsIgnoreCase("RPA")) {
				int nuOfCopies =	jsOb.getInt("courses["+i+"].copies");
				System.out.println("Number of cobies is " +nuOfCopies);
				break;
			}

		}
		
		
		//6- Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for (int i = 0; i < numberofcourses; i++) {
			int price = jsOb.getInt("courses["+i+"].price");
			int nuOfCopies =	jsOb.getInt("courses["+i+"].copies");
			
			int total = price * nuOfCopies;
			sum = sum +total;
			
		}

		System.out.println(sum);
		Assert.assertEquals(sum, puchaseamount);
	}

}
