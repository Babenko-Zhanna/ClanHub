package ClanHub.core;

import ClanHub.pages.ProfilePage;
import ClanHub.pages.RegistrationPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class TestBase {
    protected final ApplicationManager app = new ApplicationManager();

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeMethod
    public void setUp(Method method) {
        logger.info("Test is started: [" + method.getName() + "]");
        app.init();
    }

    @AfterMethod(enabled = true)
    public void tearDown(Method method, ITestResult result) {
        if (result.isSuccess()) {
            logger.info("Test is PASSED: [" + method.getName() + "]");
        } else {
            logger.error("Test is FAILED: [" + method.getName() + "], Screenshot: [" + app.getBasePage().takeScreenshot() + "]");
            Throwable throwable = result.getThrowable();
            if (throwable != null) {
                logger.error("Test failed with exception: " + throwable.getMessage());
            }
        }
        app.stop();
    }
}
