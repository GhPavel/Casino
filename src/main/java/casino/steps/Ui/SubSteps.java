package casino.steps.Ui;

import casino.annotations.PageName;
import casino.pages.BasePage;
import casino.pages.MainPage;
import casino.pages.PlayersPage;
import casino.utils.Stash;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.reflections.Reflections;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static casino.pages.BasePage.getFieldByAnnotation;
import static casino.pages.MainPage.waitTableLoading;
import static casino.testData.TestData.getDriver;

public class SubSteps {

    /**
     * Поиск элемента по аннотации ElementTitle и клик
     *
     * @param elementName имя элемента с аннотацией ElementTitle
     */
    public void clickToElement(String elementName) {
        ((WebElement) getFieldByAnnotation(BasePage.currentPage.getClass(), elementName)).click();
    }

    /**
     * Поиск элемента по аннотации ElementTitle и заполнение поля
     *
     * @param elementName имя элемента с аннотацией ElementTitle
     * @param value       значение
     */
    public void sendKeysToField(String elementName, String value) {
        ((WebElement) getFieldByAnnotation(BasePage.currentPage.getClass(), elementName)).sendKeys(value);
    }

    /**
     * Нажатие на элемент меню
     */
    @SneakyThrows
    public void clickToMenuElement(String elementName) {
        MainPage.getMenuButton(elementName).click();
        Thread.sleep(500);
    }

    /**
     * Нажатие на элемент подменю
     */
    public void clickToSubMenuElement(String elementName) {
        MainPage.getSubMenuButton(elementName).click();
    }

    /**
     * Нажатие на заголовок таблицы (сортировка)
     */
    public void clickToTableHeaderElement(String elementName) {
        PlayersPage.getTableHeadElement(elementName).click();
        waitTableLoading();
    }

    /**
     * Проверка сортировки
     */
    public void checkSorted(String elementName, String sortType) {
        WebElement table = getFieldByAnnotation(BasePage.currentPage.getClass(), "Players Table");
        List<String> valuesList;
        switch (elementName) {
            case ("Username"): {
                valuesList = table.findElements(By.xpath(".//td[2]")).stream().map(WebElement::getText).collect(Collectors.toList());
                break;
            }
            case ("External ID"): {
                valuesList = table.findElements(By.xpath(".//td[3]")).stream().map(WebElement::getText).collect(Collectors.toList());
                break;
            }
            case ("Name"): {
                valuesList = table.findElements(By.xpath(".//td[4]")).stream().map(WebElement::getText).collect(Collectors.toList());
                break;
            }
            case ("Last name"): {
                valuesList = table.findElements(By.xpath(".//td[5]")).stream().map(WebElement::getText).collect(Collectors.toList());
                break;
            }
            case ("E-mail"): {
                valuesList = table.findElements(By.xpath(".//td[6]")).stream().map(WebElement::getText).collect(Collectors.toList());
                break;
            }
            case ("Registration date"): {
                valuesList = table.findElements(By.xpath(".//td[10]")).stream().map(WebElement::getText).collect(Collectors.toList());
                break;
            }
            default: {
                throw new RuntimeException("Неверное название колонки");
            }
        }
        List<String> expectedValue = new ArrayList<>(valuesList);
        expectedValue.sort(sortType.equalsIgnoreCase("возрастанию")
                ? getAscComparator()
                : getDescComparator());

        for (int i = 0; i < valuesList.size(); i++) {
            Assertions.assertEquals(expectedValue.get(i), valuesList.get(i), "Неверная сортировка!");
        }

    }

    /**
     * init page
     *
     * @param pageName
     */
    public void pageIsLoaded(String pageName) {
        Reflections reflections = new Reflections("casino.pages");

        Set<Class<? extends BasePage>> allPages = reflections.getSubTypesOf(BasePage.class);

        for (Class<? extends BasePage> currentPage : allPages
        ) {
            if (currentPage.getAnnotation(PageName.class).value().equals(pageName)) {
                try {
                    BasePage page = currentPage.getConstructor().newInstance();
                    page.isLoaded();
                    BasePage.currentPage = page;
                } catch (Exception e) {
                    throw new RuntimeException("Ошибка при инициализации страницы!");
                }
            }
        }
    }

    private Comparator<String> getDescComparator() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM.DD HH:MM:SS");
        return (s1, s2) -> {
            try {
                Date d1 = simpleDateFormat.parse(s1);
                Date d2 = simpleDateFormat.parse(s2);
                return d2.compareTo(d1);
            } catch (ParseException e) {
                if (s1.isEmpty()) {
                    return -1;
                } else if (s2.isEmpty()) {
                    return 1;
                }
                return s2.toLowerCase().compareTo(s1.toLowerCase());
            }
        };
    }

    private Comparator<String> getAscComparator() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM.DD HH:MM:SS");
        return (s1, s2) -> {
            try {
                Date d1 = simpleDateFormat.parse(s1);
                Date d2 = simpleDateFormat.parse(s2);
                return d1.compareTo(d2);
            } catch (ParseException e) {
                if (s1.isEmpty()) {
                    return 1;
                } else if (s2.isEmpty()) {
                    return -1;
                }
                return s1.toLowerCase().compareTo(s2.toLowerCase());
            }
        };
    }

}
