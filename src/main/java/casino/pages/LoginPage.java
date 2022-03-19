package casino.pages;

import casino.annotations.ElementTitle;
import casino.annotations.PageName;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static casino.testData.TestData.getDriver;

@PageName("Login Page")
public class LoginPage extends BasePage {

    public LoginPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @ElementTitle("Login")
    @FindBy(id = "UserLogin_username")
    public WebElement login;

    @ElementTitle("Password")
    @FindBy(id = "UserLogin_password")
    public WebElement password;

    @ElementTitle("Sign In")
    @FindBy(xpath = "//input[@value='Sign in']")
    public WebElement signInButton;

    @Override
    public void isLoaded() {
        if(!signInButton.isDisplayed()) {
            Assertions.fail("Страница не загружена!");
        }
    }
}

