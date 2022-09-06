package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import ru.netology.page.DashboardPage;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.String.valueOf;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String generateRandomLogin() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }

   @Value
   public static class AuthCode {
       String id;
       String user_id;
       String code;
       String date;
   }




}
