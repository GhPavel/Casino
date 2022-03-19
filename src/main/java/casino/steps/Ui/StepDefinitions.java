package casino.steps.Ui;

import io.cucumber.java.ru.Когда;

import static casino.hooks.Hooks.confReader;
import static casino.hooks.Hooks.initDriver;

public class StepDefinitions {

    SubSteps subSteps = new SubSteps();

    @Когда("Пользователь авторизован на сайте")
    public void loginToSite() {
        initDriver();
        subSteps.pageIsLoaded("Login Page");
        subSteps.sendKeysToField("Login", confReader.getValue("login"));
        subSteps.sendKeysToField("Password", confReader.getValue("password"));
        subSteps.clickToElement("Sign In");
    }

    @Когда("Открыта страница {string}")
    public void pageOpen(String pageName) {
        subSteps.pageIsLoaded(pageName);
    }

    @Когда("Развернуть элемент меню {string}")
    public void expandMenu(String menuElemName) {
        subSteps.clickToMenuElement(menuElemName);
    }

    @Когда("Кликнуть элемент подменю {string}")
    public void expandSubMenu(String subMenuElemName) {
        subSteps.clickToSubMenuElement(subMenuElemName);
    }

    @Когда("Отсортировать по полю {string}")
    public void sortByField(String fieldName) {
        subSteps.clickToTableHeaderElement(fieldName);
    }

    @Когда("Проверить сортировку поля {string} по {string}")
    public void checkSortedByField(String fieldName, String sortType) {
        subSteps.checkSorted(fieldName, sortType);
    }


}
