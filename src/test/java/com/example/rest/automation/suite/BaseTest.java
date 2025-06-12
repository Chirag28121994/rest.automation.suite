package com.example.rest.automation.suite;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
    public static RequestSpecification getRequestSpecification() {
        return RestAssured.given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter());
    }
}
