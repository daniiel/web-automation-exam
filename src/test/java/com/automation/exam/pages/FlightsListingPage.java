package com.automation.exam.pages;

import com.automation.exam.pages.base.BasePage;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.automation.exam.utils.Util.*;
import static com.automation.exam.utils.WaitUtil.*;

public class FlightsListingPage extends BasePage {

    @FindBy(id = "sortDropdown")
    private WebElement sortByDropdown;

    @FindBy(css = "#flightModuleList li[data-test-id='offer-listing']")
    private List<WebElement> flightOfferList;

    @FindBy(css = "span.duration-emphasis")
    List<WebElement> flightDurationList;

    private By idTitlePage = By.id("titleBar");
    private By cssSelectButton = By.cssSelector("[data-test-id='listing-summary'] button[data-test-id='select-button']");
    private By cssSelectFareButton = By.cssSelector(".basic-economy-footer button[data-test-id='select-button']");
    private By cssFlightDuration = By.cssSelector("span.duration-emphasis");

    // Offer elements
    private static final String cssFlightOfferList = "#flightModuleList li[data-test-id='offer-listing']";
    private static final String cssDetailsAndBaggageFees = "a[data-test-id='flight-details-link']";
    private static final String cssFlightDetailsContainer = ".details-container + .flight-details";
    private static final String idFlightDetail = "flight-details-tabs-offer";
    private static final String cssBaggageFeeDetails = ".details-baggage-fee-info";

    // Flight duration time has format '6h 0m'
    private DateTimeFormatter flightDurationFormatter = DateTimeFormatter.ofPattern("H'h' m'm'");

    Logger logger = LoggerFactory.getLogger(FlightsListingPage.class);

    public FlightsListingPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            waitForPresenceOfElementLocated(getWait(), idTitlePage);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPresentOrderByOption() {
        List<WebElement> sortBySelect = getDriver().findElements(By.id("sortDropdown"));
        return !sortBySelect.isEmpty();
    }

    public boolean isPresentSelectBtnForAllOffers() {
        waitForNumberOfElementsToBeMoreThan(getWait(), By.cssSelector(cssFlightOfferList), 0);
        return isPresentElementForAllOffers(cssSelectButton);
    }

    public boolean isPresentFlightDurationForAllOffers() {
        return isPresentElementForAllOffers(cssFlightDuration);
    }

    public void clickDetailsAndBaggageFeesToggle(WebElement offer) {
        WebElement toggle = offer.findElement(By.cssSelector(cssDetailsAndBaggageFees));
        toggle.click();
        getWait().until(ExpectedConditions.attributeContains(toggle, "class", "open"));
    }

    public boolean isFlightDetailsPresent(WebElement offer) {
        return !offer.findElements(By.id(idFlightDetail)).isEmpty();
    }

    public boolean isBaggageFeeDetailsPresent(WebElement offer) {
        return !offer.findElements(By.cssSelector(cssBaggageFeeDetails)).isEmpty();
    }

    public boolean isPresentFlightDetailsAndBaggageFeesForAllOffers() {

        for (WebElement offer: flightOfferList) {
            clickDetailsAndBaggageFeesToggle(offer);

            logger.info("offerId: " + offer.getAttribute("id"));
//            waitForVisibilityOf(getWait(), offer.findElement(By.cssSelector(cssFlightDetailsContainer)));

            if (!isFlightDetailsPresent(offer) || !isBaggageFeeDetailsPresent(offer)) {
                logger.info("offerId: " + offer.getAttribute("id") + " doesn't have flight or baggage fee details");
                return false;
            }
        }
        return true;
    }

    public boolean isPresentElementForAllOffers(By by) {
        for (WebElement offer: flightOfferList) {
            if (offer.findElements(by).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void sortOffersByValue(String value) {
        selectDropdownOptionByValue(getWait(), sortByDropdown, value);
    }

    private List<WebElement> getFlightOfferList() {
        return getDriver().findElements(By.cssSelector("ul#flightModuleList li[data-test-id='offer-listing']"));
    }

    public boolean isFlightDurationTimeOrderByShortest(String sortBy) {
        waitForElementToBeClickable(getWait(), sortByDropdown);
        List <LocalTime> flightDurationsList = flightDurationList.stream()
                .map(element -> LocalTime.parse(element.getText(), flightDurationFormatter))
                .collect(Collectors.toList());
        flightDurationsList.forEach(System.out::println);

        String orderType = sortBy.split(":")[1];
        switch (orderType) {
            case "desc":
                return Ordering.natural().reverse().isOrdered(flightDurationsList);
            default:
                // "asc"
                return Ordering.natural().isOrdered(flightDurationsList);
        }
    }

    public void pickDepartureLASOffer() {
        if (!flightOfferList.isEmpty()) {
            // Select First result (Departure to Los Angeles)
            confirmOffer(flightOfferList.get(0));
        }
    }

    public TripDetailPage pickDepartureLAXOffer() {
        // Departure to Las Vegas
        List<WebElement> returnOfferListing = getFlightOfferList();

        if (returnOfferListing.isEmpty()) {
            logger.error("Las vegas flight list empty");
            return null;
        }

        // Select Third result (Departure to Las Vegas)
        if (returnOfferListing.size() > 2 ) {
            confirmOffer(returnOfferListing.get(2));
        } else {
            confirmOffer(returnOfferListing.get(returnOfferListing.size() -1));
        }

        return new TripDetailPage(getDriver());
    }

    private void confirmOffer(WebElement offer) {
        boolean hasOfferDoubleConfirmation = !offer.findElements(cssSelectFareButton).isEmpty();
        offer.findElement(cssSelectButton).click();
        if (hasOfferDoubleConfirmation) {
            offer.findElement(cssSelectFareButton).click();
        }
    }

}
