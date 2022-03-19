package casino.pages;

import casino.annotations.ElementTitle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Arrays;

import static casino.testData.TestData.getDriver;
import static java.time.temporal.ChronoUnit.SECONDS;


public abstract class BasePage {

    public static BasePage currentPage;
    public static FluentWait<WebDriver> wait;

    public abstract void isLoaded();

    public BasePage() {
        wait = new FluentWait<>(getDriver())
                .pollingEvery(Duration.of(3, SECONDS))
                .withTimeout(Duration.of(60, SECONDS));
    }


    public static <T> T getFieldByAnnotation(Class<? extends BasePage> page, String fieldName) {
        String field = Arrays.stream(page.getFields())
                .filter(f -> f.isAnnotationPresent(ElementTitle.class))
                .filter(f -> f.getAnnotation(ElementTitle.class).value().equals(fieldName))
                .findFirst().orElseThrow(() -> new RuntimeException(String.format("Ошибка при поиске поля '%s' и аннотации ElementTitle", fieldName))).getName();

        Object obj = null;
        try {
            obj = getField(field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    public static Object getField(String fieldName) {
        try {
            Object field = currentPage.getClass().getDeclaredField(fieldName).get(currentPage);
            return field;
        } catch (NullPointerException | NoSuchFieldException | IllegalAccessException npe) {
            throw new RuntimeException("Поле не найдено!");
        }
    }
}
