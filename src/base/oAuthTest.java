package base;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oAuthTest {
	
	public static void main(String[] srgs) throws InterruptedException {
		
		
		String ExpcourseTitles[] = {"Selenium Webdriver Java","Cypress","Protractor"} ;
		
		/*
		 System.setProperty("webdriver.chrome.driver", "H:/DSandALGO/Selenium/chromedriver_win32/chromedriver.exe");
		 WebDriver driver = new ChromeDriver();
		 driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		 driver.findElement(By.cssSelector("input[type=email]")).sendKeys("gcpformanish1@gmail.com");
		 driver.findElement(By.cssSelector("input[type=email]")).sendKeys(Keys.ENTER);
		 Thread.sleep(3000);
		 driver.findElement(By.cssSelector("input[type=password]")).sendKeys("Manish5243@");
		 driver.findElement(By.cssSelector("input[type=password]")).sendKeys(Keys.ENTER);
		 Thread.sleep(3000);
		 String url = driver.getCurrentUrl();
		 */
		
		//Get this url by running get code uri
		//https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		 String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g5T5JevDpJjzCifSfjTE1J4zARGkbvxtUsdqlfRMCBXDM_LHVg24rJs6ELH0kJdAw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=3&prompt=none#";
		 String Partialcode = url.split("code=")[1];
		 String code = Partialcode.split("&scope")[0];
		 System.out.println(code);
		 
		 
		
		String accessToken =given().urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessToken);
		String token = js.getString("access_token");
		
		
		
		
		GetCourse gc = given().queryParam("access_token", token).expect().defaultParser(Parser.JSON)
		.when()
		//.get("https://rahulshettyacademy.com/getCourse.php").asString();
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		//System.out.println(response);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getExpertise());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		
		List<Api> ApiCourses = gc.getCourses().getApi();
		for(int i=0; i<ApiCourses.size(); i++) {
			
			if(ApiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				
				System.out.println(ApiCourses.get(i).getPrice());
			}
		}
		
		
		List<WebAutomation> WebAutomationCourses=gc.getCourses().getWebAutomation();
		ArrayList <String> actcourseTitle = new ArrayList<String>();
		
		for(int j = 0; j<WebAutomationCourses.size(); j++) {
			
			//System.out.println("<----------------------GetWebAutomation------------------->");
			//System.out.println(WebAutomationCourses.get(j).getCourseTitle());
			actcourseTitle.add(WebAutomationCourses.get(j).getCourseTitle());
		}
		
		List<String>expectedList = Arrays.asList(ExpcourseTitles);
		
		Assert.assertTrue(actcourseTitle.equals(expectedList));
		
		System.out.println("Assertion Done");
		
		
	}
}
