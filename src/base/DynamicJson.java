package base;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="PlaceName")
	public void AddBook(String Name) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payLoad.AddPlaceHost(Name)).when().post(payLoad.AddPlaceResource())
					.then().assertThat().statusCode(200).extract().response().asString();
				
				JsonPath js = new JsonPath(response);
				String PlaceId = js.getString("place_id");
				System.out.println(PlaceId);
				
				
				//delete created resource
				
				String response1 = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
						.body(payLoad.DeleteAddrees(PlaceId)).when().delete(payLoad.DeleteAddressResource())
							.then().assertThat().statusCode(200).extract().response().asString();
						
						JsonPath js1 = new JsonPath(response1);
						String status = js1.getString("status");
						System.out.println(status);
	
	}
	/*
	@Test(dataProvider="DeleteAddress")
	public void DeleteBook(String ID) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payLoad.DeleteAddrees(ID)).when().delete(payLoad.DeleteAddressResource())
					.then().assertThat().statusCode(200).extract().response().asString();
				
				JsonPath js = new JsonPath(response);
				String status = js.getString("status");
				System.out.println(status);
	}
	
	@DataProvider(name = "DeleteAddress")
	public Object [] DeleteAddress() {
		return new Object [] {"e3ebed24e5c7a8ff4ed42e69477020d4","93bba87943dfdf2f6fff8a95ee5faaba","af234643271978bdde5ec599f8390fd7"};
	}
	*/
	@DataProvider(name = "PlaceName")
	public Object [] getData() {
		return new Object [] {"Hello1","Hello2","Hello3"};
	}
}
