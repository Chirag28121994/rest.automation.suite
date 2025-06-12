package com.example.rest.automation.suite.demo;

import com.example.rest.automation.suite.BaseTest;
import com.example.rest.automation.suite.model.TestGroup;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

@SpringBootTest
public class DemoApiTest extends BaseTest {

    @Test(groups = TestGroup.REGRESSION)
    public void testGetEndpoint(){
        Response response = getRequestSpecification().get("https://google.com");
        response.then().statusCode(HttpStatus.OK.value());
    }

    @Test(groups = TestGroup.SMOKE)
    public void testPostEndpoint(){
        Response response = getRequestSpecification().post("https://google.com");
        response.then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
    }
}
