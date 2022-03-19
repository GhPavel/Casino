package casino;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = "src/test/resources/feature",
        glue = {
                "casino.steps",
                "casino.hooks"
        },
        tags = "@Api_6",
        stepNotifications = true
)
public class CucumberRunnerTest {
}
