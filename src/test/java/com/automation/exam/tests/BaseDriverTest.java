package com.automation.exam.tests;

import com.automation.exam.constanst.DriverType;
import com.automation.exam.driver.DriverManagerFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseDriver {

    private WebDriver driver;

    @BeforeTest(alwaysRun = true)
    @Parameters({"browser"})
    public void setupDriver(String browser) {
        driver = DriverManagerFactory.getDriverManager(DriverType.valueOf(browser)).getWebDriver();
        driver.manage().window().maximize();
    }

    @AfterTest(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
