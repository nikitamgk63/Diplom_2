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

public class UserCreationTest {

    private ApiClient apiClient;
    private User testUser;
    private Response creationResponse;

    @Before
    public void setUp() {
        apiClient = new ApiClient();
    }

    @After
    public void tearDown() {
        try {
            if (creationResponse != null && creationResponse.getStatusCode() == SC_OK) {
                String token = creationResponse.path("accessToken");
                if (token != null) {
                    apiClient.deleteUser(token);
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка создания пользователя с уникальными данными")
    public void testCreateUniqueUser() {
        // Создаем пользователя с уникальными данными
        testUser = DataFactory.createUniqueUser();
        // Отправляем запрос на создание пользователя
        creationResponse = apiClient.createUser(testUser);
        // Проверяем успешное создание
        creationResponse.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    @Description("Проверка ошибки при попытке создания пользователя, который уже существует")
    public void testCreateDuplicateUser() {
        // Создаем первого пользователя
        testUser = DataFactory.createUniqueUser();
        creationResponse = apiClient.createUser(testUser);
        // Пытаемся создать такого же пользователя повторно
        Response response = apiClient.createUser(testUser);
        // Проверяем ошибку дублирования
        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Проверка ошибки при попытке создать пользователя без указания email")
    public void testCreateUserWithoutEmail() {
        // Создаем пользователя без email
        User user = DataFactory.createUserWithoutEmail();
        // Отправляем запрос
        Response response = apiClient.createUser(user);
        // Проверяем ошибку валидации
        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверка ошибки при попытке создать пользователя без указания пароля")
    public void testCreateUserWithoutPassword() {
        // Создаем пользователя без пароля
        User user = DataFactory.createUserWithoutPassword();
        // Отправляем запрос
        Response response = apiClient.createUser(user);
        // Проверяем ошибку валидации
        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без имения")
    @Description("Проверка ошибки при попытке создать пользователя без указания имени")
    public void testCreateUserWithoutName() {
        // Создаем пользователя без имени
        User user = DataFactory.createUserWithoutName();
        // Отправляем запрос
        Response response = apiClient.createUser(user);
        // Проверяем ошибку валидации
        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

}