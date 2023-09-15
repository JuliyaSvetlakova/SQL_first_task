package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.data.SQLHelper;
import ru.netology.sql.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.sql.data.SQLHelper.cleanAuthCodes;
import static ru.netology.sql.data.SQLHelper.cleanDatabase;

public class PersonalAccountLoginTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }
    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @Test
    void successFulLogin() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void checkingTheVisibilityOfTheDashboardPageTitle() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.visibilityOfTheDashboardPageTitle();
    }

    @Test
    void errorWhenLoggingInWithRandomUsernameAndPassword() {
        var authInfo = DataHelper.getRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    @Test
    void errorWhenLoggingInWithRandomPassword() {
        var authInfo = DataHelper.getUserWithRandomPassword();
        loginPage.invalidLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    @Test
    void errorWhenLoggingInThreeTimesWithRandomPassword() {
        var authInfo1 = DataHelper.getUserWithRandomPassword();
        loginPage.invalidLogin(authInfo1);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
        loginPage.clean();
        var authInfo2 = DataHelper.getUserWithRandomPassword();
        loginPage.invalidLogin(authInfo2);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
        loginPage.clean();
        var authInfo3 = DataHelper.getUserWithRandomPassword();
        loginPage.invalidLogin(authInfo3);
        loginPage.verifyErrorNotification("Ошибка! \nВход в личный кабинет заблокирован");
    }

    @Test
    void errorWhenLoggingInWithRandomCode() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз.");
    }

}
