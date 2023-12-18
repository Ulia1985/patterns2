package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.locks.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

public class AuthTest {


        @BeforeEach
        void setup() {
        open("http://localhost:9999");
        }

        @Test
        @DisplayName("Should successfully login with active registered user")
        void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
            $("[data-test-id='Login'] input").setValue(registeredUser.getLogin());
            $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
            $("button.button").click();
            $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
        }

        @Test
        @DisplayName("Should get error message if login with not registered user")
        void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
            $("[data-test-id='Login'] input").setValue(notRegisteredUser.getLogin());
            $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
            $("button.button").click();
            $("[data-test-id='error-notification'] .notification__content")
                    .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                    .shouldBe((Condition.visible));
        }


}