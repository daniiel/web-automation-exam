package com.automation.exam.tests;

import com.automation.exam.pages.FlightFormPage;
import com.automation.exam.pages.FlightsListingPage;
import com.automation.exam.pages.HomePage;
import com.automation.exam.pages.PaymentPage;
import com.automation.exam.pages.TripDetailPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertTrue;

public class BookingFlight extends BaseTest {

    private FlightsListingPage flightsListing;
    private TripDetailPage tripSummary;
    private PaymentPage payment;

    @BeforeTest()
    public void selectFlightTab() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickFlightTab();
    }

    @Test
    @Parameters({"flightType", "flyingFrom", "flyingTo", "numberOfAdults"})
    public void searchForFlights(String flightType, String flyingFrom, String flyingTo, String numberOfAdults) {
        FlightFormPage flightForm = new FlightFormPage(getDriver());
        LocalDate departingDate = LocalDate.now().plusMonths(2);
        LocalDate returningDate = LocalDate.now().plusMonths(2).plusDays(5);

        flightForm.selectFlightType(flightType);
        flightForm.fillFlightForm(flyingFrom, flyingTo, departingDate, returningDate, numberOfAdults);
        this.flightsListing = flightForm.clickSearchButton();

        assertTrue(flightsListing.isLoaded(), "The flights listing page is not loaded");
    }

    @Test(dependsOnMethods = "searchForFlights")
    public void validateFlightModuleListComponents() {
        assertTrue(flightsListing.isPresentOrderByOption());
        assertTrue(flightsListing.sortByDropdownHasAllOptions());
        assertTrue(flightsListing.isPresentSelectBtnForAllOffers());
        assertTrue(flightsListing.isPresentFlightDurationForAllOffers());
        assertTrue(flightsListing.isPresentFlightDetailsAndBaggageFeesForAllOffers());
    }

    @Test(dependsOnMethods = "validateFlightModuleListComponents")
    @Parameters({"sortByDuration"})
    public void sortListByDuration(String sortByDuration) {
        flightsListing.sortOffersByValue(sortByDuration);
        assertTrue(flightsListing.isFlightDurationTimeOrderByShortest(sortByDuration));
    }

    @Test(dependsOnMethods = "sortListByDuration")
    public void pickOffers() {
        flightsListing.pickFirstDepartureLASOffer();
        tripSummary = flightsListing.pickThirdDepartureLAXOffer();
        assertTrue(tripSummary.getPageTitle().contains("Trip Detail"), "Trip detail tab wasn't selected");
    }

    @Test(dependsOnMethods = "pickOffers")
    public void validateTripDetailsComponents() {
        assertTrue(tripSummary.isPresentTripTotalPrice(), "Trip total price is not present");
        assertTrue(tripSummary.isPresentDepartureAndReturnInformation(), "Departure and return information is not present");
        assertTrue(tripSummary.isPresentPriceGuaranteeText(), "Price guarantee text is not present");
    }

    @Test(dependsOnMethods = "validateTripDetailsComponents")
    public void paymentValidation() {
        payment = tripSummary.clickBookingButton();
        assertTrue(payment.isLoaded(),"The Payment page is not loaded");
        assertTrue(payment.isPresentFirstNameInput());
        assertTrue(payment.isPresentCountryDropdown());
        assertTrue(payment.isPresentTotalPriceForTrip());
        assertTrue(payment.getLocationInformation().contains("Las Vegas (LAS) to"));
    }

}
