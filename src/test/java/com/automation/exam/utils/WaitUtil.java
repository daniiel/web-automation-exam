package com.automation.exam.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {

    private WaitUtil() {
    }

    public static void waitForElementToBeClickable(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForPresenceOfElementLocated(WebDriverWait wait, By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
