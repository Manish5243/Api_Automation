package base;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import files.payLoad;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest {

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
		
		///////////////////////////////////////////
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		
		ResponseSpecification resspece = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		//////////////////////////////////////////
		
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
		RequestSpecification response = given().spec(req)
		.body(p);
		
		Response res = response.when().post(payLoad.AddPlaceResource())
			.then().spec(resspece).extract().response();
		
		
		String responseString = res.asString();
		System.out.println(responseString);
		
		
	}

}
