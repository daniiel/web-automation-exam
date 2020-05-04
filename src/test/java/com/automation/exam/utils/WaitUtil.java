package com.automation.exam.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {

    private WaitUtil() {
    }

    public static void waitForElementToBeClickable(WebDriverWait wait, WebElement elem) {
        wait.until(ExpectedConditions.elementToBeClickable(elem));
    }

    public static void waitForPresenceOfElementLocated(WebDriverWait wait, By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitForVisibilityOf(WebDriverWait wait, WebElement elem) {
        wait.until(ExpectedConditions.visibilityOf(elem));
    }

    public static void waitForNumberOfElementsToBeMoreThan(WebDriverWait wait, By locator, int value) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, value));
    }

    public static void waitForAttributeContains(WebDriverWait wait, WebElement elem, String attribute, String value) {
        wait.until(ExpectedConditions.attributeContains(elem, attribute, value));
    }
}
