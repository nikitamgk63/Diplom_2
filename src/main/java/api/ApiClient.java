package api;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Order;
import models.User;
import models.UserLogin;

import static org.apache.http.HttpStatus.*;
import static io.restassured.RestAssured.given;

public class ApiClient {
    public ApiClient() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Step("Создание пользователя: {user.email}")
    public Response createUser(User user) {
        return given()
                .contentType("application/json")
                .body(user)
                .post(Endpoints.REGISTER);
    }

    @Step("Логин пользователя: {email}")
    public Response loginUser(String email, String password) {
        return given()
                .contentType("application/json")
                .body(new UserLogin(email, password))
                .post(Endpoints.LOGIN);
    }

    @Step("Удаление пользователя с токеном: {token}")
    public void deleteUser(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Токен не может быть null при удалении пользователя.");
        }

        given()
                .header("Authorization", token)
                .delete(Endpoints.USER)
                .then()
                .statusCode(SC_ACCEPTED); // Ожидаем код 202 (Accepted) для успешного удаления
    }


    @Step("Создание заказа")
    public Response createOrder(String token, Order order) {
        var request = given()
                .contentType("application/json")
                .body(order);
        // Добавляем заголовок Authorization только если токен не равен null
        if (token != null) {
            request.header("Authorization", token);
        }
        return request.post(Endpoints.ORDERS);
    }

    @Step("Получение заказов пользователя")
    public Response getUserOrders(String token) {
        var request = given();
        // Добавляем заголовок Authorization только если токен не равен null
        if (token != null) {
            request.header("Authorization", token);
        }
        return request.get(Endpoints.ORDERS);
    }

    @Step("Изменение данных пользователя")
    public Response updateUser(String token, User updatedUser) {
        var request = given()
                .contentType("application/json")
                .body(updatedUser);
        // Добавляем заголовок Authorization только если токен не равен null
        if (token != null) {
            request.header("Authorization", token);
        }
        return request.patch(Endpoints.USER);
    }
}