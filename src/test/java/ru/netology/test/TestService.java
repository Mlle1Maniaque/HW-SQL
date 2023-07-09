package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SqlHelper.cleanDB;

public class TestService {
    @AfterAll
    static void shouldClean() {
        cleanDB();
    }

    @Test
    void shouldLoginSuccess() {
        open("http://localhost:9999", LoginPage.class);
        var loginPage = new LoginPage();
        var authUser = DataHelper.getAuthUser();
        var verificationPage = loginPage.validLogin(authUser);
        verificationPage.verificationPageVisibility();
        var verifyCode = SqlHelper.getVerifyCode();
        verificationPage.validVerify(verifyCode.getCode());
    }

    @Test
    void shouldLoginRandomUser() {
        open("http://localhost:9999", LoginPage.class);
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.getError();
    }

    @Test
    void shouldPutInvalidPass() {
        open("http://localhost:9999", LoginPage.class);
        var loginPage = new LoginPage();
        var authUser = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUser);
        loginPage.getError();
    }

    @Test
    void shouldPutInvalidVerificationCode() {
        open("http://localhost:9999", LoginPage.class);
        var loginPage = new LoginPage();
        var authUser = DataHelper.getAuthUser();
        var verificationPage = loginPage.validLogin(authUser);
        verificationPage.verificationPageVisibility();
        var verifyCode = DataHelper.getRandomVerifyCode().getCode();
        verificationPage.verify(verifyCode);
        verificationPage.getError();
    }

    @Test
    void shouldBlockAfterThreeTimes() {
        open("http://localhost:9999", LoginPage.class);
        var loginPage = new LoginPage();
        var authUserFirst = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserFirst);
        loginPage.getError();
        loginPage.cleanForm();
        clearBrowserCookies();
        var authUserSecond = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserSecond);
        loginPage.getError();
        loginPage.cleanForm();
        clearBrowserCookies();
        var authUserThird = new DataHelper.AuthUser(DataHelper.getAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserThird);
        loginPage.getBlock();
    }
}
