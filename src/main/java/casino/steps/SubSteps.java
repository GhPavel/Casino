package casino.steps;

import casino.annotations.PageName;
import casino.pages.BasePage;
import casino.utils.Stash;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.reflections.Reflections;

import java.util.Set;

import static casino.pages.BasePage.getFieldByAnnotation;
import static casino.testData.TestData.getDriver;

public class SubSteps {

    /**
     * Поиск элемента по аннотации ElementTitle и клик
     *
     * @param elementName
     */
    public void clickToElement(String elementName) {
        ((WebElement) getFieldByAnnotation(BasePage.currentPage.getClass(), elementName)).click();
    }

    /**
     * Поиск элемента по аннотации ElementTitle, наведение и клик
     *
     * @param elementName
     */
    public void hoverAndClickToElem(String elementName) {
        WebElement elem = getFieldByAnnotation(BasePage.currentPage.getClass(), elementName);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(elem).build().perform();
        elem.click();
    }

    public void saveElemValueToStash(String elemName, String stashKey) {
        String value = ((WebElement) getFieldByAnnotation(BasePage.currentPage.getClass(), elemName)).getText();
        Stash.put(stashKey, value);
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
                    page.currentPage = page;
                } catch (Exception e) {
                    throw new RuntimeException("Ошибка при инициализации страницы!");
                }
            }
        }
    }

}
