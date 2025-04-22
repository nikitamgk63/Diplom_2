package data;

import models.User;
import models.Order;
import com.github.javafaker.Faker;

public class DataFactory {
    private static final Faker faker = new Faker();
    // Создание уникального пользователя
    public static User createUniqueUser() {
        return new User(faker.internet().emailAddress(), faker.internet().password(8, 12), faker.name().fullName());
    }

    // Создание пользователя без email
    public static User createUserWithoutEmail() {
        return new User(null, faker.internet().password(8, 12), faker.name().fullName());
    }

    // Создание пользователя без пароля
    public static User createUserWithoutPassword() {
        return new User(faker.internet().emailAddress(), null, faker.name().fullName());
    }

    // Создание пользователя без имени
    public static User createUserWithoutName() {
        return new User(faker.internet().emailAddress(), faker.internet().password(8, 12), null);
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

    // Создание пользователя с новым email
    public static User createUserWithNewEmail() {
        return new User(faker.internet().emailAddress(), null, null);
    }

    // Создание пользователя с новым именем
    public static User createUserWithNewName() {
        return new User(null, null, faker.name().fullName());
    }

    //  Создание пользователя с новым паролем
    public static User createUserWithNewPassword() {
        return new User(null, faker.internet().password(10, 16, true, true), null);
    }

    public static User createUserWithNewData() {
        return new User(faker.internet().emailAddress(), faker.internet().password(10, 16), faker.name().fullName());
    }
}