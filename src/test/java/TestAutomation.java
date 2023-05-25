import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;
public class TestAutomation {
	
	
	@Test(priority=1)
    public  void verifyGetApiIsReturningValidResponse(){
		System.out.println("1st Api Test Cases");

       /* given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Authors/12")
                .then()
                .statusCode(200).body("id",equalTo(12)).body("idBook", equalTo(4));*/
		given().params("Authors","2").auth().none()              
             .header("Content-Type",  "application/json")
             .contentType(ContentType.JSON).when().
             get("https://fakerestapi.azurewebsites.net/api/v1/Authors/2").
             then().statusCode(200).body("id",equalTo(2)).body("idBook", equalTo(1));
		
		System.out.println("1st Api Test Cases done Successfully");

    }
	
	@Test(priority=2)
	public void BodyValidation()
	{
		System.out.println("2nd Api Test Cases");
		
		given().params("page","2").auth().none().header("Content-Type","application/json")
		.contentType(ContentType.JSON).when().get("https://reqres.in/api/users?page=2").then().statusCode(200).
		body("page", equalTo(2)).body("per_page", equalTo(6)).body("total", equalTo(12)).
		body("total_pages", equalTo(2));
		System.out.println("2nd Api Test Cases done Successfully");
	}
	
	@Test(priority=3)
	public void ResponseValidation()
	{
		
		Response getResponse=given().params("page","2").auth().none().header("Content-Type","application/json")
				.contentType(ContentType.JSON).when().get("https://reqres.in/api/users?page=2");
		getResponse.prettyPrint();
		System.out.println(getResponse.statusCode());
		System.out.println(getResponse.getTime());
	}
	
	@Test(priority=4)
	public void ValidatePostApi()
	{
		String p="{\n"
				+ "  \"id\": 1,\n"
				+ "  \"title\": \"string\",\n"
				+ "  \"dueDate\": \"2023-05-14T06:27:31.402Z\",\n"
				+ "  \"completed\": true\n"
				+ "}";
		
		given().auth().none().header("ContenType","application/json").contentType(ContentType.JSON)
		.when().body(p).post("https://fakerestapi.azurewebsites.net/api/v1/Activities").then()
		.statusCode(200).body("id", equalTo(1)).body("title", equalTo("string"));
				
	}
	
	@Test(priority=5)
	public void PostApi()
	{
		String p="{\n"
				+ "  \"id\": 1,\n"
				+ "  \"title\": \"string\",\n"
				+ "  \"dueDate\": \"2023-05-14T06:27:31.402Z\",\n"
				+ "  \"completed\": true\n"
				+ "}";
		
		Response res=given().auth().none().header("ContenType","application/json").contentType(ContentType.JSON)
		.when().body(p).post("https://fakerestapi.azurewebsites.net/api/v1/Activities");
		System.out.println("Get Post Api Json Schema"+ res.asString());
		System.out.println("Get Post Api Json Schema"+ res.getTime()+" And Response code is"+ res.statusCode());
	}
	
	@Test(priority=6)
	public void ValidatePostApiUsingHashMap()
	{
		
		HashMap<String,String>hs=new HashMap<String,String>();
		hs.put("id","1");
		hs.put("title", "string");
		hs.put("dueDate", "2023-05-14T06:27:31.402Z");
		hs.put("completed", "true");
		Response r=given().auth().none().header("ContenType","application/json").contentType(ContentType.JSON)
				.when().body(hs).post("https://fakerestapi.azurewebsites.net/api/v1/Activities");
				System.out.println( r.asString());
			//	System.out.println("Get Post Api Json Schema"+ res.getTime()+" And Response code is"+ res.statusCode());
		
		
	}
	@Test(priority=7)
	public void PostComplexApi()
	{
		HashMap<String,String>h=new HashMap<String,String>();
		h.put("name", "Yasin");
		h.put("job", "SDET");
		Response r=given().auth().none().header("ContentType","application/json").contentType(ContentType.JSON).when().body(h)
				.post("https://reqres.in/api/users");
		System.out.println(r.asString());
		r.prettyPeek();
		
	}
	
	@Test(priority=8)
	public void CreateComplex_post_Api()
	{
		HashMap<String,Object>Maps=new HashMap<String,Object>();
		//Object can store any types of DataType//
		Maps.put("name", "Yasin");
		Maps.put("job", "Sdet");
		
		ArrayList<String>arr=new ArrayList<String>();
		arr.add("Java");
		arr.add("c");
		Maps.put("skills", arr);
		HashMap<String, String>ma=new HashMap<String, String>();
		ma.put("CompanyName", "DD");
		ma.put("EmailId", "yasinrabby3@gmail.com");
		
		Maps.put("details", ma);
		
		Response response=given().auth().none().header("ContentType","application/json")
				.contentType(ContentType.JSON).when().body(Maps).post("https://reqres.in/api/users");
		
		
		System.out.println(response.asString());
		System.out.println(response.statusCode());
		System.out.println(response.getTime());
		System.out.println(response.getBody().path("job"));
		System.out.println(response.getBody().path("skills[0]"));
		AssertJUnit.assertEquals(response.getBody().path("name"), "Yasin");
		System.out.println(response.getBody().path("details.CompanyName"));
		
		
		
		
	}
	
	@Test(priority=9,testName="Fetch all the data from Auhors")
	public void getAllDataofAuthor()
	{
		
		Response rep=given().auth().none().header("ContentType","Application/json").
				contentType(ContentType.JSON).when().get("https://fakerestapi.azurewebsites.net/api/v1/Authors");
		System.out.println(rep.asString());
		
		rep.prettyPeek();
		

		
		
		
		
		
	}
	
	@Test(priority=10,testName="Validate status code on authors")
	public void ValidateGetStatusCode()
	{
		Response rep=given().auth().none().header("ContentType","Application/json").
				contentType(ContentType.JSON).when().get("https://fakerestapi.azurewebsites.net/api/v1/Authors");
		
		int statuscode=rep.getStatusCode();
		AssertJUnit.assertEquals(statuscode, 200);
		
	}
	
	@Test(priority=11)
	public void ValidateResponseBodyOfBooks()
	{
		
		Response rep=given().auth().none().header("ContentType","Application").contentType(ContentType.JSON).when()
				.
				get("https://fakerestapi.azurewebsites.net/api/v1/Authors/authors/books/1");
		rep.prettyPeek();
		ResponseBody rb=rep.getBody();
		String str=rb.asString();
		System.out.println(str);
		AssertJUnit.assertTrue(str.contains("First Name 2"));
		
		//Assert.assertEquals(rep.getBody().path("id"), 3);
	
		
		
		
	}
	
	@Test(priority=12,testName="Post method for creating request for adding authors ")
	public void PostAuthors()
	{
		

		HashMap<String,String>h=new HashMap<String,String>();
		
		
		h.put("firstName", "Yasin");
		h.put("lastName", "Rabby");
		
		
		Response resp=given().auth().none().header("ContentType","Application/json").contentType(ContentType.JSON)
				.when().body(h).post("https://fakerestapi.azurewebsites.net/api/v1/Authors");
		
		String idbook=resp.getBody().path("firstName");
		System.out.println(idbook);
		AssertJUnit.assertEquals(idbook, "Yasin");
		AssertJUnit.assertEquals(resp.getBody().path("lastName"), "Rabby");
		int statucode=resp.statusCode();
		AssertJUnit.assertEquals(statucode, 200);
		
	
	}
	
	@Test(priority=13,testName="Creating request for adding Books")
	public void CreatePostAddingBooks()
	{

		HashMap<String,String>hs=new HashMap<String,String>();
		hs.put("id", "2");
		hs.put("title", "Qa Lead");
		hs.put("description", "find a bug");
		hs.put("pageCount", "40");
		hs.put("excerpt", "string");
		hs.put("publishDate", "2023-05-25T03:52:08.417Z");
		Response r=given().auth().none().header("ContentType","application/json").contentType(ContentType.JSON).when().body(hs)
				.post("https://fakerestapi.azurewebsites.net/api/v1/Books");
		
		r.prettyPeek();
		int sc=r.statusCode();
		
		AssertJUnit.assertEquals(sc, 200);
		AssertJUnit.assertEquals(r.getBody().path("title"), "Qa Lead");
		AssertJUnit.assertEquals(r.getBody().path("description"), "find a bug");
		
			   
		
		
	}
	
	@Test(priority=14)
	public void ModifyUsingPut()
	{
		
		HashMap<String,String>hs=new HashMap<String,String>();
		hs.put("id", "2");
		hs.put("title", "Qa Lead");
		hs.put("description", "find a bug");
		hs.put("pageCount", "40");
		hs.put("excerpt", "string");
		hs.put("publishDate", "2023-05-25T03:52:08.417Z");
		Response r=given().auth().none().header("ContentType","application/json").contentType(ContentType.JSON).when().body(hs)
				.put("https://fakerestapi.azurewebsites.net/api/v1/Books/3");
		
		AssertJUnit.assertEquals(r.getStatusCode(), 200);
		
	}
	
	

}
