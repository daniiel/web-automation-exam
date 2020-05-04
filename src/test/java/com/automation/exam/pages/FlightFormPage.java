package com.automation.exam.pages;

import com.automation.exam.constanst.FlightType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;

import static com.automation.exam.utils.Util.*;
import static com.automation.exam.utils.WaitUtil.waitForElementToBeClickable;
import static com.automation.exam.utils.WaitUtil.waitForPresenceOfElementLocated;

public class FlightFormPage extends BasePage {

    @FindBy(id = "flight-type-roundtrip-label-hp-flight")
    private WebElement roundTripFlightType;
    @FindBy(id = "flight-type-one-way-label-hp-flight")
    private WebElement oneWayFlightType;
    @FindBy(id = "flight-type-multi-dest-label-hp-flight")
    private WebElement multiCityFlightType;
    @FindBy(id = "flight-origin-hp-flight")
    private WebElement flyingFromInput;
    @FindBy(id = "flight-destination-hp-flight")
    private WebElement flyingToInput;
    @FindBy(id = "flight-departing-wrapper-hp-flight")
    private WebElement departingDateWrapper;
    @FindBy(id = "flight-returning-wrapper-hp-flight")
    private WebElement returningDateWrapper;
    @FindBy(id = "flight-adults-hp-flight")
    private WebElement adultsDropdown;
    @FindBy(css = "#gcw-flights-form-hp-flight button[type='submit']")
    private WebElement searchButton;

    public FlightFormPage(WebDriver driver) {
        super(driver);
    }

    public void selectFlightType(String flightType) {
        waitForElementToBeClickable(getWait(), roundTripFlightType);
        FlightType type = FlightType.valueOfFlightType(flightType);

        switch (type) {
            case ROUND_TRIP:
                roundTripFlightType.click();
                break;
            case ONE_WAY:
                oneWayFlightType.click();
                break;
            case MULTI_CITY:
                multiCityFlightType.click();
                break;
        }
    }

    public void fillFlightForm(String flyingFrom, String flyingTo, LocalDate departingDate, LocalDate returningDate,
                               String numberOfAdults) {
        sendKeysUsingSuggestionsToolTip(this.flyingFromInput, flyingFrom);
        sendKeysUsingSuggestionsToolTip(this.flyingToInput, flyingTo);
        selectDateFromDatePicker(getWait(), departingDateWrapper, departingDate);
        selectDateFromDatePicker(getWait(), returningDateWrapper, returningDate);
        selectDropdownOptionByValue(getWait(), adultsDropdown, numberOfAdults);
    }

    public FlightsListingPage clickSearchButton() {
        searchButton.click();
        return new FlightsListingPage(getDriver());
    }

    private void sendKeysUsingSuggestionsToolTip(WebElement element, String value) {
        sendKeys(element, value);
        waitForPresenceOfElementLocated(getWait(), By.cssSelector("#typeaheadDataPlain"));
        element.sendKeys(Keys.TAB);
    }

}
