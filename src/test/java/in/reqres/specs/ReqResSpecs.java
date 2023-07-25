package in.reqres.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ReqResSpecs {
    public static ResponseSpecification listUserResponse200Spec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("listUsersResponse.json"))
            .build();

    public static ResponseSpecification singleUserResponse200Spec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("singleUserResponse.json"))
            .build();

    public static RequestSpecification registerRequestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .build();

    public static ResponseSpecification successfulRegisterResponse200Spec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("successfulRegisterResponse.json"))
            .build();

    public static ResponseSpecification unsuccessfulRegisterResponse400Spec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath("unsuccessfulRegisterResponse.json"))
            .build();

    public static RequestSpecification loginRequestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .build();

    public static ResponseSpecification successfulLoginResponse200Spec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("successfulLoginResponse.json"))
            .build();

    public static ResponseSpecification unsuccessfulLoginResponse400Spec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath("unsuccessfulRegisterResponse.json"))
            .build();
}

