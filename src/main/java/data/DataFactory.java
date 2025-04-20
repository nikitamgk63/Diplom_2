package data;

import models.User;
import models.Order;

public class DataFactory {

    // Создание уникального пользователя
    public static User createUniqueUser() {
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@example.com";
        return new User(uniqueEmail, "password123", "UniqueUser");
    }

    // Создание пользователя без email (для тестов с ошибками)
    public static User createUserWithMissingField() {
        return new User(null, "password123", "NoEmailUser");
    }

    // Создание заказа с валидными ингредиентами
    public static Order createOrderWithValidIngredients() {
        return new Order(new String[]{
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f"
        });
    }

    // Создание заказа с неверными ингредиентами
    public static Order createOrderWithInvalidIngredients() {
        return new Order(new String[]{"invalid_ingredient_id"});
    }

    // Создание заказа без ингредиентов
    public static Order createOrderWithoutIngredients() {
        return new Order(new String[]{});
    }
}