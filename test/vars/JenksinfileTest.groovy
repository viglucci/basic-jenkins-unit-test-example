package vars

import com.lesfurets.jenkins.unit.*
import org.junit.Before
import org.junit.Test

class JenkinsfileTest extends TestBase {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp()
    }

    @Test
    public void should_run() throws Exception {
        runScript('test/jenkinsfiles/demo.groovy')
    }
}