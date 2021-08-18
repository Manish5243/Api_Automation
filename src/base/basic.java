package base;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.JSONReuseableMethod;
import files.payLoad;

public class basic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Given, when, then
		//https://rahulshettyacademy.com/maps/api/place/add/json?key =qaclick123
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payLoad.AddPlaceHost("Hello")).when().post(payLoad.AddPlaceResource())
			.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
			.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String PlaceId = js.getString("place_id");
		System.out.println(PlaceId);
		
		//Update
		
		String NewAdd = "70 winter walk, SoutheAfrica";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+PlaceId+"\",\r\n"
				+ "\"address\":\""+NewAdd+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put(payLoad.UpdatePlaceResource())
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//get Place
		String ResponseAddress = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceId)
		.when().get(payLoad.GetPlaceResource())
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = JSONReuseableMethod.rawToJson(ResponseAddress);
		String Address = js1.getString("address");
		System.out.println(Address);
		
		Assert.assertEquals(Address, NewAdd);
		
		
		
	}

}
