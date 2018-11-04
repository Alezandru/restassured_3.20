package tutorials;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class toolsQA {
    /**
     *    1. Use the RestAssured class to generate a RequestSpecification for the URL
     *    2. Specify the HTTP Method type
     *    3. Send the Request to the Server
     *    4. Get the Response back from the server
     *    5. Print the returned Response’s Body
     */
    @Test
    public void GetWeatherBody(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        //1. Use the RestAssured class to generate a RequestSpecification for the URL
        RequestSpecification httpRequest = RestAssured.given();
        //3. Send the Request to the Server && 2. Specify the HTTP Method type ===>GET<===
        Response response = httpRequest.request(Method.GET, "/Bucharest");
        //4. Get the Response back from the server
        String responseBody = response.getBody().asString();
        //5. Print the returned Response’s Body
        System.out.println("Response body is: \n \n" + responseBody);
    }

    @Test
    public void GetWeatherStatusDetails(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/Bucharest");

        String statusLine = response.getStatusLine();
        System.out.println(" Status line is: " + statusLine);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Correct status code returned.");
        System.out.println("\n Status code is: "+ statusCode + " OK");
        System.out.println("Correct status code returned.");


        String responseBody = response.getBody().asString();
        System.out.println("\n Response body is: \n" + responseBody);
    }

    @Test
    public void GetWeatherInvalidCity(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/123123123123123");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400, "Correct status code returned.");
        System.out.println("Status code is: "+ statusCode + " Bad Request");
        System.out.println("Correct status code returned.");

        String responseBody = response.getBody().asString();
        System.out.println("\n Response body is: \n" + responseBody);
    }

    @Test
    public void GetWeatherHeaders(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/Bucharest");

        String contentType = response.getHeader("Content-Type");
        System.out.println("\n Content-Type value is: " + contentType);

        String serverType = response.header("Server");
        System.out.println("\n Server value is: " + serverType);

        String acceptedLanguage = response.header("Content-Encoding");
        System.out.println("\n Content-Encoding: " + acceptedLanguage);
    }

    @Test
    public void IteratingOverHeaders(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/Bucharest");

        Headers allHeaders = response.headers();

        for (Header header : allHeaders) {
            System.out.println("\n Key: " + header.getName() + "\n Value: " + header.getValue());
        }
    }

    @Test
    public void GetWeatherHeadersAssertions(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/Bucharest");

        String contentType = response.getHeader("Content-Type");
        Assert.assertEquals(contentType, "application/json");
        System.out.println("\n Content-Type value is: " + contentType);

        String serverType = response.header("Server");
        Assert.assertEquals(serverType, "nginx/1.12.2");
        System.out.println("\n Server value is: " + serverType);

        String acceptedLanguage = response.header("Content-Encoding");
        Assert.assertEquals(acceptedLanguage, "gzip");
        System.out.println("\n Content-Encoding: " + acceptedLanguage);
    }

    @Test
    public void WeatherMessageBody(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/Bucharest");

        String responseBody = response.getBody().asString();
        Assert.assertEquals(responseBody.contains("Bucharest"), true);
        Assert.assertEquals(responseBody.toLowerCase().contains("bucharest"), true);
        System.out.println("Response body contains \"Bucharest\".");

    }

    @Test
    public void VerifyCityInJsonBody(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Bucharest");

        JsonPath jsonPathEvaluator = response.jsonPath();
        String city = jsonPathEvaluator.get("City");

        Assert.assertEquals(city, "Bucharest");
        System.out.println("City received from Response is: " + city);
    }

    @Test
    public void DisplayAllNodesInWeatherAPI(){
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Bucharest");

        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println("City received from Response " + jsonPathEvaluator.get("City"));
        System.out.println("Temperature received from Response " + jsonPathEvaluator.get("Temperature"));
        System.out.println("Humidity received from Response " + jsonPathEvaluator.get("Humidity"));
        System.out.println("Weather description received from Response " + jsonPathEvaluator.get("Weather"));
        System.out.println("WindSpeed received from Response " + jsonPathEvaluator.get("WindSpeed"));
        System.out.println("Wind Direction Degree received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));
    }

    @Test
    public void PostRequest(){
        RestAssured.baseURI ="http://restapi.demoqa.com/customer";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Johnn");
        requestParams.put("LastName", "Doee");
        requestParams.put("UserName", "JohnDoe2018b");
        requestParams.put("Password", "password123");
        requestParams.put("Email",  "alezandru.qa1@gmail.com");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/register");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: \n" + responseBody);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, "200");
        String successCode = response.jsonPath().get("SuccessCode");
        Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
    }

    @Test
    public void RegistrationSuccessful1()
    {
        RestAssured.baseURI ="http://restapi.demoqa.com/customer";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "John");
        requestParams.put("LastName", "Doe");
        requestParams.put("UserName", "JohnDoe2018");
        requestParams.put("Password", "password123");
        requestParams.put("Email",  "alezandru.qa@gmail.com");

        request.body(requestParams.toJSONString());
        Response response = request.post("/register");

        System.out.println("The status code recieved: " + response.statusCode());
        System.out.println("Response body: " + response.body().asString());
    }

    @Test
    public void AuthenticationBasics()
    {
        RestAssured.baseURI = "http://restapi.demoqa.com/authentication/CheckForAuthentication";
        RequestSpecification request = RestAssured.given();
        Response response = request.get();
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Status message " + response.body().asString());
    }

    @Test
    public class RegistrationSuccessResponse {

        // Variable where value of SuccessCode node will be copied
        // Note: The name should be exactly as the node name is present in the Json
        public String SuccessCode;

        // Variable where value of Message node will be copied
        // Note: The name should be exactly as the node name is present in the Json
        public String Message;
    }

    @Test
    public void RegistrationSuccessful() {
        RestAssured.baseURI ="http://restapi.demoqa.com/customer";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Virender");
        requestParams.put("LastName", "Singh");
        requestParams.put("UserName", "63userf2d3d2011");
        requestParams.put("Password", "password1");
        requestParams.put("Email",  "ed26dff39@gmail.com");

        request.body(requestParams.toJSONString());
        Response response = request.post("/register");

        ResponseBody body = response.getBody();

        // Deserialize the Response body into RegistrationSuccessResponse
        RegistrationSuccessResponse responseBody = body.as(RegistrationSuccessResponse.class);

        // Use the RegistrationSuccessResponse class instance to Assert the values of
        // Response.
        Assert.assertEquals("OPERATION_SUCCESS", responseBody.SuccessCode);
        Assert.assertEquals("Operation completed successfully", responseBody.Message);
    }


}
