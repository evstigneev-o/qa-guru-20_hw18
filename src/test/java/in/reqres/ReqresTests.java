package in.reqres;

import in.reqres.models.RegisterRequest;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTests extends BaseTest {

    @Test
    public void getListUsersValidateJsonSchemaTest() {
        given()
                .param("page", "2")
                .get("users")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("listUsersResponse.json"));
    }

    @Test
    public void successfulGetSingleUserTest() {
        given()
                .get("users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"));
    }

    @Test
    public void successfulRegisterTest() {
        RegisterRequest requestBody = RegisterRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("register")
                .then()
                .statusCode(200)
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void unsuccessfulRegisterWithoutPasswordTest() {
        RegisterRequest requestBody = RegisterRequest.builder()
                .email("sydney@fife")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("register")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void successfulLoginTest() {
        RegisterRequest requestBody = RegisterRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("login")
                .then()
                .statusCode(200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void unsuccessfulLoginTest() {
        RegisterRequest requestBody = RegisterRequest.builder()
                .email("peter@klaven")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}
