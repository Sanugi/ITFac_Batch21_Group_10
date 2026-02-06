package starter;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Complete Test Suite - UI then API")
@SelectClasses({
        UITestSuite.class,
        OrderedAPITestSuite.class
})
public class CucumberTestSuite {
}
