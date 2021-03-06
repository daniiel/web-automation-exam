package com.automation.exam.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.automation.exam.utils.Util.isElementPresent;
import static com.automation.exam.utils.WaitUtil.waitForPresenceOfElementLocated;

public class PaymentPage extends BasePage {

    @FindBy(css = ".location-info")
    private WebElement locationInformation;

    private By byWhoIsTravelingTitle = By.cssSelector("h2.faceoff-module-title");
    private By byFirstNameInput = By.id("firstname[0]");
    private By byCountryDropdown = By.id("country_code[0]");
    private By byTotalPrice = By.id("totalPriceForTrip");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            waitForPresenceOfElementLocated(getWait(), byWhoIsTravelingTitle);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPresentFirstNameInput() {
        return isElementPresent(getDriver(), byFirstNameInput);
    }

    public boolean isPresentCountryDropdown() {
        return isElementPresent(getDriver(), byCountryDropdown);
    }

    public boolean isPresentTotalPriceForTrip() {
        if (getDriver().findElements(byTotalPrice).isEmpty()) {
            return false;
        }
        return getDriver().findElement(byTotalPrice).getText().isEmpty() ? false : true;
    }

    public String getLocationInformation() {
        return locationInformation.getText();
    }

}
