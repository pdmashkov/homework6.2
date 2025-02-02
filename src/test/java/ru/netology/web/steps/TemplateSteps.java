package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;

    private DataHelper.CardInfo firstCard;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void authLogin(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);

        var authInfo = new DataHelper.AuthInfo(login, password);

        verificationPage = loginPage.validLogin(authInfo);

        var verificationCode = DataHelper.getVerificationCode(authInfo);

        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Когда("пользователь переводит {int} рублей с карты с номером 5559 0000 0000 0002 на свою 1 карту с главной страницы")
    public void transferMoney(int amount) {
        var cardToTransfer = DataHelper.getSecondCard();
        firstCard = DataHelper.getFirstCard();

        var transferPage = dashboardPage.selectCardToTransfer(cardToTransfer);
        transferPage.transferMoney(amount, firstCard);
    }

    @Тогда("баланс его 1 карты из списка на главной странице должен стать {int} рублей")
    public void assertBalance(int balance) {
        int expected = dashboardPage.getCardBalance(firstCard);

        Assertions.assertEquals(expected, balance);
    }
}
