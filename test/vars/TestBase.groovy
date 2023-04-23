import com.lesfurets.jenkins.unit.*
import groovy.json.*
import java.io.File
import org.junit.*
  
class TestBase extends BasePipelineTest {
    Script script
    String shResponse
    Boolean fileExists
  
    @Before
    void setUp() {
        super.setUp()
        helper.registerAllowedMethod('withCredentials', [Map.class, Closure.class], null)
        helper.registerAllowedMethod('withCredentials', [List.class, Closure.class], null)
        helper.registerAllowedMethod('usernamePassword', [Map.class], null)
        helper.registerAllowedMethod("sh", [Map.class], { c -> shResponse })
        helper.registerAllowedMethod("fileExists", [String.class], { f -> fileExists })

        // load all steps from vars directory
        new File("vars").eachFile { file ->
            // pass the --info flag to gradle to see this output
            println "loading: " + file.name

            def name = file.name.replace(".groovy", "")

            def loadedScript = loadScript(file.path)

            switch(name) {
                case 'say': {
                    // register step with no args
                    helper.registerAllowedMethod(name, []) { ->
                        loadScript(file.path)()
                    }
                    // register step with Map arg
                    helper.registerAllowedMethod(name, [Map]) { opts ->
                        loadScript(file.path)(opts)
                    }
                    // register step with string arg
                    helper.registerAllowedMethod(name, [String]) { opts ->
                        loadScript(file.path)(opts)
                    }
                }
                break
            }

            // attempt to register each call method using reflection
            // loadedScript.metaClass.methods.each {
            //     if (it.declaringClass.getTheClass() == loadedScript.class
            //         && ! it.name.contains('$')
            //         && it.name != 'main'
            //         && it.name != 'run') {
            //         // this.metaClass."$it.name" = script.&"$it.name"
            //         println it.name
            //         println it.parameters
            //     }
            // }
        }
    }
}