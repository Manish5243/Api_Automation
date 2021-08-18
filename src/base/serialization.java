package base;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import files.payLoad;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

public class serialization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("Hello Manish123");
		p.setLanguage("Hindi");
		p.setPhone_number("(+91) 983 893 4545");
		p.setWebsite("www.abc.com");
		p.setName("Manish");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe Park");
		myList.add("shop");
		
		p.setTypes(myList);
		
		Location location = new Location();
		location.setLat(-38.90878);
		location.setLng(33.34567);
		
		p.setLocation(location);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(p)
		.when()
		.post(payLoad.AddPlaceResource())
			.then().assertThat().statusCode(200).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
		
	}

}
