package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Random;
import java.util.UUID;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo(String login, String password) {
        return new AuthInfo(login, password);
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String cardId;
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static CardInfo getBadCardNumber() {
        Faker faker = new Faker();

        return new CardInfo(faker.business().creditCardNumber(), UUID.randomUUID().toString());
    }

    public static int getValidAmount(int balance) {
        Random random = new Random();
        return random.nextInt(balance);
    }

    public static int getInvalidAmount(int balance) {
        Faker faker = new Faker();

        return faker.random().nextInt(balance + 1, balance + balance);
    }
}
