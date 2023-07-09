package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    public static AuthUser getAuthUser() {
        return new AuthUser("vasya", "qwerty123");
    }

    private static Faker faker = new Faker(new Locale("en"));

    @Value
    public static class AuthUser {
        String login;
        String password;
    }

    private static String getRandomLogin() {
        return faker.name().username();
    }

    private static String getRandomPass() {
        return faker.internet().password();
    }

    public static AuthUser getRandomUser() {
        return new AuthUser(getRandomLogin(), getRandomPass());
    }

    @Value
    public static class VerifyCode {
        String code;
    }

    public static VerifyCode getRandomVerifyCode() {
        return new VerifyCode(faker.numerify("111111"));
    }
}
