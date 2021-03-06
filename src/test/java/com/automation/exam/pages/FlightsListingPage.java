package com.automation.exam.pages;

import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.automation.exam.utils.Util.dropdownHasAllOptions;
import static com.automation.exam.utils.Util.selectDropdownOptionByValue;
import static com.automation.exam.utils.WaitUtil.*;

public class FlightsListingPage extends BasePage {

    private static final String idSortDropdown = "sortDropdown";
    private static final String cssFlightOfferList = "#flightModuleList li[data-test-id='offer-listing']";
    private static final String cssFlightDuration = "span.duration-emphasis";

    @FindBy(id = idSortDropdown)
    private WebElement sortByDropdown;

    @FindBy(css = "sortDropdown option")
    private List<WebElement> sortByOptions;

    @FindBy(css = cssFlightOfferList)
    private List<WebElement> flightOfferList;

    @FindBy(css = cssFlightDuration)
    List<WebElement> flightDurationList;

    private By byTitlePage = By.id("titleBar");
    private By bySelectButton = By.cssSelector("[data-test-id='listing-summary'] button[data-test-id='select-button']");
    private By bySelectFareButton = By.cssSelector(".basic-economy-footer button[data-test-id='select-button']");
    private By byFlightDuration = By.cssSelector(cssFlightDuration);


    private static final List<String> SORT_BY_OPTIONS_TEXT =
            Arrays.asList("Price (Lowest)", "Price (Highest)", "Duration (Shortest)", "Duration (Longest)",
                    "Departure (Earliest)", "Departure (Latest)", "Arrival (Earliest)", "Arrival (Latest)");

    // Flight duration time has format '6h 0m'
    private DateTimeFormatter flightDurationFormatter = DateTimeFormatter.ofPattern("H'h' m'm'");

    Logger logger = LoggerFactory.getLogger(FlightsListingPage.class);

    public FlightsListingPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            waitForPresenceOfElementLocated(getWait(), byTitlePage);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPresentOrderByOption() {
        return !getDriver().findElements(By.id(idSortDropdown)).isEmpty();
    }

    public boolean sortDropdownHasAllOptions() {
        return dropdownHasAllOptions(sortByDropdown, SORT_BY_OPTIONS_TEXT);
    }

    public boolean isPresentSelectBtnForAllOffers() {
        waitForNumberOfElementsToBeMoreThan(getWait(), By.cssSelector(cssFlightOfferList), 0);
        return isPresentElementForAllOffers(bySelectButton);
    }

    public boolean isPresentFlightDurationForAllOffers() {
        return isPresentElementForAllOffers(byFlightDuration);
    }

    public boolean isPresentFlightDetailsAndBaggageFeesForAllOffers() {
        for (WebElement offer: flightOfferList) {
            clickDetailsAndBaggageFeesToggle(offer);
            logger.info("offerId: " + offer.getAttribute("id"));

            if (!isFlightDetailsPresent(offer) || !isBaggageFeeDetailsPresent(offer)) {
                logger.info("offerId: " + offer.getAttribute("id") + " doesn't have flight or baggage fee details");
                return false;
            }
        }
        return true;
    }

    private void clickDetailsAndBaggageFeesToggle(WebElement offer) {
        WebElement toggle = offer.findElement(By.cssSelector("a[data-test-id='flight-details-link']"));
        toggle.click();
        waitForAttributeContains(getWait(), toggle, "class", "open");
    }

    private boolean isFlightDetailsPresent(WebElement offer) {
        return !offer.findElements(By.id("flight-details-tabs-offer")).isEmpty();
    }

    private boolean isBaggageFeeDetailsPresent(WebElement offer) {
        return !offer.findElements(By.cssSelector(".details-baggage-fee-info")).isEmpty();
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

    public boolean isFlightDurationTimeOrderByShortest(String sortBy) {
        waitForElementToBeClickable(getWait(), sortByDropdown);
        List <LocalTime> flightDurationsList = flightDurationList.stream()
                .map(element -> LocalTime.parse(element.getText(), flightDurationFormatter))
                .collect(Collectors.toList());

        String orderType = sortBy.split(":")[1];
        switch (orderType) {
            case "desc":
                return Ordering.natural().reverse().isOrdered(flightDurationsList);
            default: // "asc"
                return Ordering.natural().isOrdered(flightDurationsList);
        }
    }

    public void pickFirstDepartureLASOffer() {
        if (!flightOfferList.isEmpty()) {
            // Select First result (Departure to Los Angeles)
            logger.info("Offer (LAS): " + flightOfferList.get(0).getAttribute("id"));
            confirmOffer(flightOfferList.get(0));
        }
    }

    public TripDetailPage pickThirdDepartureLAXOffer() {
        // Departure to Las Vegas
        waitForNumberOfElementsToBeMoreThan(getWait(), By.cssSelector(cssFlightOfferList), 0);

        int numberOffers = flightOfferList.size();
        int indexOffer = numberOffers > 2 ? 2 : numberOffers -1;
        logger.info("Offer (LAX): " + flightOfferList.get(indexOffer).getAttribute("id"));
        confirmOffer(flightOfferList.get(indexOffer));

        return new TripDetailPage(getDriver());
    }

    private void confirmOffer(WebElement offer) {
        boolean hasOfferDoubleConfirmation = !offer.findElements(bySelectFareButton).isEmpty();
        offer.findElement(bySelectButton).click();
        if (hasOfferDoubleConfirmation) {
            offer.findElement(bySelectFareButton).click();
        }
    }

}
