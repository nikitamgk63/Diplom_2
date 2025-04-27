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

public class UserUpdateTest {

    private ApiClient apiClient;
    private String createdUserToken;

    @Before
    public void setUp() {
        apiClient = new ApiClient();
        User user = DataFactory.createUniqueUser();
        createdUserToken = apiClient.createUser(user).path("accessToken");
    }

    @After
    public void tearDown() {
        if (createdUserToken != null) {
            apiClient.deleteUser(createdUserToken);
        }
    }

    @Test
    @DisplayName("Успешное обновление email пользователя")
    @Description("Проверка изменения email авторизованного пользователя")
    public void testUpdateUserEmail() {
        // Подготовка данных: генерируем новый email
        User emailUpdate = DataFactory.createUserWithNewEmail();
        // Отправка запроса на обновление
        Response response = apiClient.updateUser(createdUserToken, emailUpdate);
        // Проверки:
        response.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(emailUpdate.getEmail()));
    }

    @Test
    @DisplayName("Успешное обновление имени пользователя")
    @Description("Проверка изменения имени пользователя")
    public void testUpdateUserName() {
        // Подготовка данных: генерируем новое имя
        User nameUpdate = DataFactory.createUserWithNewName();
        // Отправка запроса на обновление
        Response response = apiClient.updateUser(createdUserToken, nameUpdate);
        // Проверки:
        response.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.name", equalTo(nameUpdate.getName()));
    }

    @Test
    @DisplayName("Успешное обновление пароля пользователя")
    @Description("Проверка изменения пароля пользователя")
    public void testUpdateUserPassword() {
        // Подготовка данных: генерируем новый пароль
        User passwordUpdate = DataFactory.createUserWithNewPassword();
        // Отправка запроса на обновление
        Response response = apiClient.updateUser(createdUserToken, passwordUpdate);
        // Проверки:
        response.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Попытка обновления без авторизации")
    @Description("Проверка ошибки при обновлении данных без токена авторизации")
    public void testUpdateUserWithoutAuthorization() {
        // Подготовка данных: генерируем новые данные
        User updates = DataFactory.createUserWithNewData();
        // Отправка запроса без токена
        Response response = apiClient.updateUser(null, updates);
        // Проверки:
        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Попытка обновления с неверным токеном")
    @Description("Проверка ошибки при обновлении с невалидным токеном")
    public void testUpdateUserWithInvalidToken() {
        // Подготовка данных
        User updates = DataFactory.createUserWithNewEmail();
        // Отправка запроса с неверным токеном
        Response response = apiClient.updateUser("invalid_token", updates);
        // Проверки:
        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}