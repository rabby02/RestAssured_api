import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

public class postApi {
	
	
	@Test
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
		.statusCode(200).body("id", equalTo(1));
				
	}

}
