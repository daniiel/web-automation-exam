package com.automation.exam.driver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    protected WebDriver driver;

    public static final String DRIVER_PATH = "C:\\docker\\udemy\\webdrivers\\";

    protected abstract void createWebDriver();

    public WebDriver getWebDriver() {
        if (driver == null) {
            createWebDriver();
        }
        return driver;
    }
}
