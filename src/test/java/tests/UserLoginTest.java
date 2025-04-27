package tests;

import api.ApiClient;
import data.DataFactory;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest {

    private ApiClient apiClient;
    private User createdUser;
    private String createdUserToken;

    @Before
    public void setUp() {
        apiClient = new ApiClient();
        createdUser = DataFactory.createUniqueUser();
        Response response = apiClient.createUser(createdUser);
        createdUserToken = response.path("accessToken");
    }

    @After
    public void tearDown() {
        if (createdUserToken != null) {
            apiClient.deleteUser(createdUserToken);
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Проверка успешного входа в систему для существующего пользователя")
    public void testLoginExistingUser() {
        // Пытаемся авторизоваться с правильными данными
        Response loginResponse = apiClient.loginUser(createdUser.getEmail(), createdUser.getPassword());
        // Проверяем успешную авторизацию
        loginResponse.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Проверка ошибки при вводе неверного пароля")
    public void testLoginWithWrongPassword() {
        // Пытаемся авторизоваться с неверным паролем
        Response loginResponse = apiClient.loginUser(createdUser.getEmail(), "wrongPassword");
        // Проверяем ошибку авторизации
        loginResponse.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин с неверным email")
    @Description("Проверка ошибки при вводе неверного email")
    public void testLoginWithWrongEmail() {
        // Пытаемся авторизоваться с неверным email
        Response loginResponse = apiClient.loginUser("wrong@email.com", createdUser.getPassword());
        // Проверяем ошибку авторизации
        loginResponse.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}