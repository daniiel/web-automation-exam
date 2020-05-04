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

    private static final String cssAutocomplete = "#typeaheadDataPlain";
    private static final String idRoundTripFlightType = "flight-type-roundtrip-label-hp-flight";
    private static final String idOneWayFlightType = "flight-type-one-way-label-hp-flight";
    private static final String idMultiCityFlightType = "flight-type-multi-dest-label-hp-flight";
    private static final String idFlyingFromInput = "flight-origin-hp-flight";
    private static final String idFlyingToInput = "flight-destination-hp-flight";
    private static final String idDepartingDateWrapper = "flight-departing-wrapper-hp-flight";
    private static final String idReturningDateWrapper = "flight-returning-wrapper-hp-flight";
    private static final String idAdultsDropdown = "flight-adults-hp-flight";
    private static final String cssSearchButton = "#gcw-flights-form-hp-flight button[type='submit']";

    @FindBy(id = idRoundTripFlightType)
    private WebElement roundTripFlightType;
    @FindBy(id = idOneWayFlightType)
    private WebElement oneWayFlightType;
    @FindBy(id = idMultiCityFlightType)
    private WebElement multiCityFlightType;
    @FindBy(id = idFlyingFromInput)
    private WebElement flyingFromInput;
    @FindBy(id = idFlyingToInput)
    private WebElement flyingToInput;
    @FindBy(id = idDepartingDateWrapper)
    private WebElement departingDateWrapper;
    @FindBy(id = idReturningDateWrapper)
    private WebElement returningDateWrapper;
    @FindBy(id = idAdultsDropdown)
    private WebElement adultsDropdown;
    @FindBy(css = cssSearchButton)
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

    public void sendKeysUsingSuggestionsToolTip(WebElement element, String value) {
        sendKeys(element, value);
        waitForPresenceOfElementLocated(getWait(), By.cssSelector(cssAutocomplete));
        element.sendKeys(Keys.TAB);
    }

}
