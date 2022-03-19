package casino.pages;

import casino.annotations.ElementTitle;
import casino.annotations.PageName;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static casino.testData.TestData.getDriver;

@PageName("Players Page")
public class PlayersPage extends MainPage {

    public PlayersPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @ElementTitle("Players Table")
    @FindBy(xpath = "//table[contains(@class,'table')]//tbody")
    public WebElement playersTable;

    public static WebElement getTableHeadElement(String elemName) {
        return getDriver().findElement(By.xpath("//table[contains(@class,'table')]//a[.='" + elemName + "']"));
    }

    @Override
    public void isLoaded() {
        if (!playersTable.isDisplayed()) {
            Assertions.fail("Страница не загружена!");
        }
    }
}
