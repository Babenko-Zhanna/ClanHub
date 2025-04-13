package ClanHub.core;

import ClanHub.dto.RegistRequestDto;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestBaseAPI {

    Logger logger = LoggerFactory.getLogger(TestBaseAPI.class);

    public final String registrDto = "/auth/sign-up";

    @BeforeMethod
    public void init(Method method) {
        RestAssured.baseURI = "https://clanhub-sd6ys.ondigitalocean.app";
        RestAssured.basePath = "api";
        logger.info("Test is started: [" + method.getName() + "]");
    }

    @AfterMethod
    public void tearDown(Method method, ITestResult result) {
        if (result.isSuccess()) {
            logger.info("Test is PASSED: [" + method.getName() + "]");
        } else {
            logger.error("Test is FAILED: [" + method.getName() + "]");

            // Логируем исключение, если оно было выброшено (например, AssertionError)
            Throwable throwable = result.getThrowable();
            if (throwable != null) {
                // Логируем полную информацию об ошибке
                logger.error("Test failed with exception: " + throwable.getMessage());
            }
        }
    }

}

