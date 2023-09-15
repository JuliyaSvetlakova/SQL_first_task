package ru.netology.sql.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id='dashboard']");
    private final SelenideElement dashboardHeading = $(byText("Личный кабинет"));

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public void visibilityOfTheDashboardPageTitle() {
        dashboardHeading.shouldBe(visible);
    }
}
