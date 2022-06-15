package ru.netology.testMode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testMode.data.DataGenerator;
import ru.netology.testMode.data.RegistrationInfo;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testMode.data.DataGenerator.Registration.*;

public class TestModeTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen=true;
    }

    @Test
    void shouldSuccessfullySendForm() {
        RegistrationInfo validUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(appear);

    }

    @Test
    void shouldGetErrorWithBlockedUser(){
        RegistrationInfo blockedUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(appear);
    }

    @Test
    void noValidLogin() {
        var registeredUser = shouldGetRegisteredUser("active");
        var wrongLogin = RandomLogin();
        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void noValidPassword() {
        var registeredUser = shouldGetRegisteredUser("active");
        var wrongPassword = RandomPassword();
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
