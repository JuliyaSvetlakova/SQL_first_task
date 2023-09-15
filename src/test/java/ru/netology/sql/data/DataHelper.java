package ru.netology.sql.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo("vasya", "qwerty123");
    }

    private static String getRandomLogin() {
        String login = faker.name().username();
        return login;
    }

    private static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }

    public static AuthInfo getRandomUser() {
        return new AuthInfo(getRandomLogin(), getRandomPassword());
    }

    public static VerificationCode getRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }

    public static AuthInfo getUserWithRandomPassword() {
        return new AuthInfo("vasya", getRandomPassword());
    }


    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }
}

