package base;

import org.testng.Assert;

import files.payLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payLoad.CoursePrice());
		
		//1. Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//2.Print Purchase Amount
		int PurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(PurchaseAmount);
		
		//3. Print Title of the first course
		String FirstCourseTitle = js.getString("courses[0].title");
		System.out.println(FirstCourseTitle);
		
		//4. Print All course titles and their respective Prices
		for (int i =0; i<count; i++) {
			String Coursetitle = js.getString("courses["+i+"].title");
			int CoursePrice = js.getInt("courses["+i+"].price");
			System.out.println(Coursetitle+" price is "+  CoursePrice);
		}
		
		//5. Print no of copies sold by RPA Course
		int numberofRPACopies = js.getInt("courses[2].copies");
		System.out.println(numberofRPACopies);
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int TotalAmount = 0;
		for (int j = 0 ; j< count; j++) {
			int CoursePrice = js.getInt("courses["+j+"].price");
			int numberofCopies = js.getInt("courses["+j+"].copies");
			int Amountforeachcourse = CoursePrice*numberofCopies;
			TotalAmount = TotalAmount + Amountforeachcourse;
		}
		
		System.out.println(TotalAmount);
		Assert.assertEquals(TotalAmount, PurchaseAmount);

	}

}
