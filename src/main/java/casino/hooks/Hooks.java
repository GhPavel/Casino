package casino.hooks;


import casino.configReader.PropertiesReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static casino.configReader.ChromeConfig.getChromeDriver;
import static casino.testData.TestData.getDriver;
import static casino.testData.TestData.setDriver;
import static casino.utils.Stash.clearStash;

@Slf4j
public class Hooks {

    public static PropertiesReader confReader = new PropertiesReader();

    @Before
    public void test() {
//        setDriver(getChromeDriver());
//        getDriver().get(confReader.getValue("url"));
    }

    @After
    public void closeDriver() {
        if (getDriver() != null) {
            getDriver().close();
        }
        clearStash();
    }
}
