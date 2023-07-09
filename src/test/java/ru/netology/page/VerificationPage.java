package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement code = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement error = $(".notification__content");

    public void validVerify(String VerifyCode) {
        verify(VerifyCode);
        new DashboardPage();
    }

    public void verificationPageVisibility() {
        code.shouldBe(visible);
    }

    public void getError() {

        error.shouldBe(visible);
        error.shouldHave(text("Неверно указан код! Попробуйте ещё раз"));
    }

    public void verify(String VerifyCode) {
        code.setValue(VerifyCode);
        verifyButton.click();
    }
}
