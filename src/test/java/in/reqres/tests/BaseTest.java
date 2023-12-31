package in.reqres.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    static final String BASE_URL = "https://reqres.in/";
    static final String API_PATH = "api";

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = API_PATH;
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
    }
}
