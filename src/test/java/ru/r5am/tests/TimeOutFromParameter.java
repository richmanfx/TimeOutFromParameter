package ru.r5am.tests;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TimeOutFromParameter {

    private static final Logger log = LogManager.getLogger();
    private final long timeout;
    private final long waitTime;

    @DataProvider
    public static Object[][] dp() {
        return new Object[][]{
                new Object[]{1000},
                new Object[]{500},
        };
    }

    @Factory(dataProvider = "dp")
    public TimeOutFromParameter(long waitTime) {

        long envTimeout = Long.parseLong(System.getenv("TESTNG_TIMEOUT"), 10);
        log.info("TESTNG_TIMEOUT = {}", envTimeout);

        this.timeout = envTimeout;
        this.waitTime = waitTime;
    }

    @BeforeMethod
    public void setup(ITestResult result) {
        result.getMethod().setTimeOut(timeout);
    }

    @Test
    public void test() throws InterruptedException {
        log.info("timeout: {}, waitTime: {}", timeout, waitTime);
        Thread.sleep(waitTime);
    }

}
