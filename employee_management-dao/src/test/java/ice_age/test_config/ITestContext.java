package ice_age.test_config;

import java.util.Properties;

import ua.com.fielden.platform.test.runners.AbstractDomainDrivenTestCaseRunner;

/**
 * A contract that defines methods to setup properties and post-test cleaning logic for tests that run with descendants of {@link AbstractDomainDrivenTestCaseRunner}.
 *
 * @author Generated
 */
public interface ITestContext {

    Properties mkDbProps(final String dbUri);
    void dbCleanUp();

}
