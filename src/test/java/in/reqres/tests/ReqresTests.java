package in.reqres.tests;

import in.reqres.models.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static in.reqres.specs.ReqResSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReqresTests extends BaseTest {

    @Test
    public void getListUsersSizeTest() {
        List<Integer> responseListSize = step("Отправка запроса getListUsers", () -> given()
                .param("page", "2")
                .get("users")
                .then()
                .spec(listUserResponse200Spec)
                .extract().jsonPath().getList("data.id")
        );
        step("Проверка ответа", () -> assertThat(responseListSize.size(), equalTo(6)));
    }

    @Test
    public void checkUserDataFromUsersListTest() {
        UsersListResponse response = step("Отправка запроса getListUsers", () -> given()
                .param("page", "2")
                .get("users")
                .then()
                .spec(listUserResponse200Spec)
                .extract().as(UsersListResponse.class)
        );
        step("Проверка ответа", () ->
                assertAll(
                        () -> assertThat(response.getPage(), equalTo(2)),
                        () -> assertThat(response.getPerPage(), equalTo(6)),
                        () -> assertThat(response.getData().size(), equalTo(6)),
                        () -> assertThat(response.getData().get(0).getId(),equalTo(7))
                )
        );
    }

    @Test
    public void successfulGetSingleUserTest() {
        SingleUserResponse response = step("Отправка запроса getSingleUser", () -> given()
                .get("users/2")
                .then()
                .spec(singleUserResponse200Spec)
                .extract().as(SingleUserResponse.class)
        );
        step("Проверка имени и фамилии пользователя", () ->
                assertAll(
                        () -> assertThat(response.getData().getFirstName(), equalTo("Janet")),
                        () -> assertThat(response.getData().getLastName(), equalTo("Weaver"))
                )
        );

    }

    @Test
    public void successfulRegisterTest() {
        RegisterRequest requestBody = RegisterRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        RegisterResponse response = step("Отправка запроса postRegister", () -> given(registerRequestSpec)
                .body(requestBody)
                .post("register")
                .then()
                .spec(successfulRegisterResponse200Spec)
                .extract().as(RegisterResponse.class)
        );

        step("Проверка идентификатора и токена пользователя", () ->
                assertAll(
                        () -> assertThat(response.getId(), equalTo(4)),
                        () -> assertThat(response.getToken(), equalTo("QpwL5tke4Pnpja7X4"))
                )
        );

    }

    @Test
    public void unsuccessfulRegisterWithoutPasswordTest() {
        RegisterRequest requestBody = RegisterRequest.builder()
                .email("sydney@fife")
                .build();

        String errorMessage = step("Отправка запроса postRegister", () -> given(registerRequestSpec)
                .body(requestBody)
                .post("register")
                .then()
                .spec(unsuccessfulRegisterResponse400Spec)
                .extract().jsonPath().getString("error")
        );
        step("Проверка сообщения об ошибке", () -> assertThat(errorMessage, equalTo("Missing password")));
    }

    @Test
    public void successfulLoginTest() {
        LoginRequest requestBody = LoginRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        String token = step("Отправка запроса postLogin", () -> given(loginRequestSpec)
                .body(requestBody)
                .post("login")
                .then()
                .spec(successfulLoginResponse200Spec)
                .extract().jsonPath().getString("token")
        );
        step("Проверка токена", () -> assertThat(token, equalTo("QpwL5tke4Pnpja7X4")));
    }

    @Test
    public void unsuccessfulLoginTest() {
        LoginRequest requestBody = LoginRequest.builder()
                .email("peter@klaven")
                .build();

        String errorMessage = step("Отправка запроса postLogin", () -> given(loginRequestSpec)
                .body(requestBody)
                .post("login")
                .then()
                .spec(unsuccessfulLoginResponse400Spec)
                .extract().jsonPath().getString("error")
        );
        step("Проверка сообщения об ошибке", () -> assertThat(errorMessage, equalTo("Missing password")));
    }
}
