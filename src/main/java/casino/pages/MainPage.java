package casino.pages;

import casino.annotations.ElementTitle;
import casino.annotations.PageName;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static casino.testData.TestData.getDriver;
import static java.time.temporal.ChronoUnit.SECONDS;

@PageName("Main Page")
public class MainPage extends BasePage {

    public MainPage() {
        PageFactory.initElements(getDriver(), this);
    }


    @ElementTitle("Main menu")
    @FindBy(xpath = "//ul[@class='main-side-menu']")
    public WebElement mainMenu;

    public static WebElement getMenuButton(String buttonName) {
        return getDriver().findElement(By.xpath("//span[.='" + buttonName + "']"));
    }

    public static WebElement getSubMenuButton(String buttonName) {
        return getDriver().findElement(By.xpath("//a[.='" + buttonName + "']"));
    }

    public static void waitTableLoading() {
        wait.withTimeout(Duration.of(30, SECONDS))
                .pollingEvery(Duration.of(2, SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading')]")));
    }

    @Override
    public void isLoaded() {
        if (!mainMenu.isDisplayed()) {
            Assertions.fail("Страница не загружена!");
        }
    }
}
