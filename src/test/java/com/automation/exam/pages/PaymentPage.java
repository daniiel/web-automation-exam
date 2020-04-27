package com.automation.exam.pages;

import com.automation.exam.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.automation.exam.utils.Util.isElementPresent;
import static com.automation.exam.utils.WaitUtil.waitForPresenceOfElementLocated;

public class PaymentPage extends BasePage {

    @FindBy(className = "location-info")
    private WebElement locationInformation;

    private By cssWhoIsTravelingTitle = By.cssSelector("h2.faceoff-module-title");
    private By idFirstNameInput = By.id("firstname[0]");
    private By idCountryDropdown = By.id("country_code[0]");
    private By idTotalPrice = By.id("totalPriceForTrip");
    private By idCompleteBookingButton = By.id("complete-booking");


    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            waitForPresenceOfElementLocated(getWait(), cssWhoIsTravelingTitle);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPresentFirstNameInput() {
        return isElementPresent(getDriver(), idFirstNameInput);
    }

    public boolean isPresentCountryDropdown() {
        return isElementPresent(getDriver(), idCountryDropdown);
    }

    public boolean isPresentTotalPriceForTrip() {
        if (getDriver().findElements(idTotalPrice).isEmpty()) {
            return false;
        }
        return getDriver().findElement(idTotalPrice).getText().isEmpty() ? false : true;
    }

    public String getLocationInformation() {
        return locationInformation.getText();
    }
}
