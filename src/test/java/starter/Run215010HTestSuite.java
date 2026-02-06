// java
package starter.suites;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "starter",
    tags = "@215010H",
    plugin = { "pretty" }
)
public class Run215010HTestSuite { }