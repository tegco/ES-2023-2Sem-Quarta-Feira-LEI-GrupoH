package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ConversorCSVJSONApplicationTest.class, FileManagementServiceApplicationTest.class,
		 HorarioControllerApplicationTest.class })
public class AllTests {

}
