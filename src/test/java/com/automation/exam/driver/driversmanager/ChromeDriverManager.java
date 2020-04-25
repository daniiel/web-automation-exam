package com.automation.exam.driver.driversmanager;

import com.automation.exam.driver.DriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManager extends DriverManager {

    private boolean useOptions = false;

    @Override
    protected void createWebDriver() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH + "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        if (useOptions) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        this.driver = new ChromeDriver(options);
    }
}
