package com.automation.exam.pages;

import com.automation.exam.utils.Util;
import com.automation.exam.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightsListingPage extends BasePage {

    private By titleBar = By.id("titleBar");

    @FindBy(id = "sortDropdown")
    private WebElement sortByDropdown;

    @FindBy(css = "#flightModuleList li[data-test-id='offer-listing']")
    private List<WebElement> flightList;

    private By bySelectBtn = By.cssSelector("div[data-test-id='listing-summary'] button[data-test-id='select-button']");

    private By bySelectThisFareBtn = By.cssSelector(".basic-economy-footer button[data-test-id='select-button']");

    private By byFlightDuration = By.cssSelector("span.duration-emphasis");

    // Flight duration time has format '6h 0m'
    private DateTimeFormatter flightDurationFormatter = DateTimeFormatter.ofPattern("H'h' m'm'");

    public FlightsListingPage(WebDriver driver) {
        super(driver);
//        waitForFlightListingModule();
    }

    public void waitForFlightListingModule() {
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("skeleton-listing")));
        getWait().until(ExpectedConditions.stalenessOf(getDriver().findElement(By.id("skeleton-listing"))));
        System.out.println("skeleton-listing disappeared");
    }

    public boolean isLoaded() {
        try {
            getWait().until(ExpectedConditions.presenceOfElementLocated(titleBar));
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
        return isPresentElementForAllOffers(bySelectBtn);
    }

    public boolean isPresentFlightDurationForAllOffers() {
        return isPresentElementForAllOffers(byFlightDuration);
    }

    public boolean isPresentFlightDetailsAndBaggageFeesForAllOffers() {
        List<WebElement> baggageFeeDetails;
        List<WebElement> flightDetails;
        WebElement detailsAndBaggageFeesToggle;

        for (WebElement offer: flightList) {
            detailsAndBaggageFeesToggle = offer.findElement(By.cssSelector("a[data-test-id='flight-details-link']"));
            detailsAndBaggageFeesToggle.click();
            System.out.println("** : " + offer.getAttribute("id"));
//            try {
//                Thread.sleep(600);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            String selector = "#" + Util.scapeColon(offer.getAttribute("id")) + " .flight-details";

//            getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
            getWait().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.cssSelector(selector))));

            flightDetails = offer.findElements(By.id("flight-details-tabs-offer"));
            baggageFeeDetails = offer.findElements(By.className("details-baggage-fee-info"));

            if (flightDetails.isEmpty() ||  baggageFeeDetails.isEmpty()) {
                System.out.println("[Offer] " + offer.findElement(By.cssSelector("span[data-test-id='airline-name'")).getText()
                        + " - " + offer.findElement(byFlightDuration).getText() + " doesn't have flight or baggage fee details");
                return false;
            }
        }
        return true;
    }

    public boolean isPresentElementForAllOffers(By by) {
        List<WebElement> item;
        for (WebElement offer: flightList) {
            item = offer.findElements(by);
            if (item.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void sortByValue(String value) {
        Select sortBy = new Select(sortByDropdown);
        sortBy.selectByValue("duration:asc");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<WebElement> getFlightList() {
        return getDriver().findElements(By.cssSelector("ul#flightModuleList li[data-test-id='offer-listing']"));
    }

    public boolean isFlightDurationTimeOrderByShortest() {
        LocalTime previousOfferTime = getFirstFlightDuration();
        LocalTime currentOfferTime;
        String durationFlight;

        for (WebElement offer: flightList) {
            durationFlight = offer.findElement(By.className("duration-emphasis")).getText();
            currentOfferTime = LocalTime.parse(durationFlight, flightDurationFormatter);

            if (!isCurrentOfferTimeGreaterOrEqualThanPreviousOfferTime(currentOfferTime, previousOfferTime)) {
                System.out.println("[ERROR] Ordering fails: previousOfferTime: "+previousOfferTime + " currentOfferTime: " + currentOfferTime);
                return false;
            }
            previousOfferTime = currentOfferTime;
        }
        return true;
    }

    private LocalTime getFirstFlightDuration() {
        String firstOfferTime = flightList.get(0).findElement(By.className("duration-emphasis")).getText();
        return LocalTime.parse(firstOfferTime, flightDurationFormatter);
    }

    private boolean isCurrentOfferTimeGreaterOrEqualThanPreviousOfferTime(LocalTime current, LocalTime previous) {
        return current.compareTo(previous) >= 0 ? true : false;
    }

    public void pickDepartureLasOffer() {
        if (!flightList.isEmpty()) {
            // Departure to Los Angeles
            WebElement offerToSelect = flightList.get(0);
            confirmOffer(offerToSelect);
            By by = By.cssSelector("#flightModuleList li[data-test-id='offer-listing']");
            getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(by,0));
        }
    }

    public TripSummaryPage pickDepartureLaxOffer() {
        // Departure to Las Vegas
        List<WebElement> returnOfferListing = getFlightList();

        if (returnOfferListing.isEmpty()) {
            System.out.println( "Departure flight list empty");
            return null;
        }

        WebElement offerToSelect;

        if (returnOfferListing.size() > 2 ) {
            offerToSelect = returnOfferListing.get(2);
        } else {
            offerToSelect = returnOfferListing.get(returnOfferListing.size() -1);
        }

        confirmOffer(offerToSelect);
        return new TripSummaryPage(getDriver());
    }

    private void confirmOffer(WebElement offer) {
        List<WebElement> fareBtn = offer.findElements(bySelectThisFareBtn);
        offer.findElement(bySelectBtn).click();
        acceptRulesAndRestrictionsIfExist(fareBtn);
    }

    private void acceptRulesAndRestrictionsIfExist(List<WebElement> fareBtn) {
        if (!fareBtn.isEmpty()) {
            fareBtn.get(0).click();
        }
    }

}
