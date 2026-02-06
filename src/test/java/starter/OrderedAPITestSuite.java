package starter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
                UserAPITestSuite.class,
                AdminAPITestSuite.class

})
public class OrderedAPITestSuite {
}
