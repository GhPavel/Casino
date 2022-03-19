package casino.pages;

import casino.annotations.PageName;
import org.openqa.selenium.support.PageFactory;

import static casino.testData.TestData.getDriver;

@PageName("Main Page")
public class MainPage extends BasePage {

    public MainPage() {
        PageFactory.initElements(getDriver(), this);
    }




    @Override
    public void isLoaded() {

    }
}
