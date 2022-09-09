package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class BankTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    void shouldSuccesLogin() {
        LoginPageV1 loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verifyCode.getCode());
    }

    @Test
    void shouldErrorWhenLoginWithRandomUser() {
        LoginPageV1 loginPage = new LoginPageV1();
        var authInfo = DataHelper.getOtherAuthInfo();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisiblity();
    }

    @Test
    void shouldErrorWhenLoginWithActiveUserAndRandomCodeVerification() {
        LoginPageV1 loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verifyCode.getCode());
        verificationPage.verifyErrorNotificationVisibility();
    }
}
