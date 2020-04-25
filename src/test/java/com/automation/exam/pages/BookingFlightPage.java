package com.automation.exam.pages;

import com.automation.exam.constanst.FlightType;
import com.automation.exam.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;

import static com.automation.exam.utils.Util.selectByValueFromDropdown;
import static com.automation.exam.utils.Util.selectDateFromDatePicker;
import static com.automation.exam.utils.WaitUtil.waitForElementToBeClickable;

public class BookingFlightPage extends BasePage {

    @FindBy(id = "flight-type-roundtrip-label-hp-flight")
    private WebElement flightType_roundTrip;

    @FindBy(id = "flight-type-one-way-label-hp-flight")
    private WebElement flightType_oneWay;

    @FindBy(id = "flight-type-multi-dest-label-hp-flight")
    private WebElement flightType_multiCity;

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

    public BookingFlightPage(WebDriver driver) {
        super(driver);
    }

    public void selectFlightType(String flightType) {
        waitForElementToBeClickable(getWait(), flightType_roundTrip);
//        getWait().until(ExpectedConditions.elementToBeClickable(flightType_roundTrip));
        FlightType type = FlightType.valueOfFlightType(flightType);

        switch (type) {
            case ROUND_TRIP:
                flightType_roundTrip.click();
                break;
            case ONE_WAY:
                flightType_oneWay.click();
                break;
            case MULTI_CITY:
                flightType_multiCity.click();
                break;
        }
    }

    public void fillFlightForm(String flyingFrom, String flyingTo, LocalDate departingDate, LocalDate returningDate,
                               String numberOfAdults) {
        this.flyingFromInput.sendKeys(flyingFrom);
        this.flyingToInput.sendKeys(flyingTo);
        selectDateFromDatePicker(getWait(), departingDateWrapper, departingDate);
        selectDateFromDatePicker(getWait(), returningDateWrapper, returningDate);
        selectByValueFromDropdown(getWait(), adultsDropdown, numberOfAdults);
    }

    public FlightsListingPage clickSearchButton() {
        searchButton.click();
        return new FlightsListingPage(getDriver());
    }

}
