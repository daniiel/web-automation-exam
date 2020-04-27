package com.automation.exam.tests;

import com.automation.exam.pages.BookingFlightPage;
import com.automation.exam.pages.FlightsListingPage;
import com.automation.exam.pages.HomePage;
import com.automation.exam.pages.PaymentPage;
import com.automation.exam.pages.TripDetailPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookingFlight extends BaseTest {

    private FlightsListingPage flightModuleList;
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
        BookingFlightPage bookingFlightPage = new BookingFlightPage(getDriver());
        LocalDate departingDate = LocalDate.now().plusMonths(2);
        LocalDate returningDate = LocalDate.now().plusMonths(2).plusDays(5);

        bookingFlightPage.selectFlightType(flightType);
        bookingFlightPage.fillFlightForm(flyingFrom, flyingTo, departingDate, returningDate, numberOfAdults);
        this.flightModuleList = bookingFlightPage.clickSearchButton();

        assertTrue(flightModuleList.isLoaded(), "The flight module list is loaded");
    }

    @Test(dependsOnMethods = "searchForFlights")
    public void validateFlightModuleListComponents() {
        assertTrue(flightModuleList.isPresentOrderByOption());
        assertTrue(flightModuleList.isPresentSelectBtnForAllOffers());
        assertTrue(flightModuleList.isPresentFlightDurationForAllOffers());
//        assertTrue(flightModuleList.isPresentFlightDetailsAndBaggageFeesForAllOffers());
    }

    @Test(dependsOnMethods = "validateFlightModuleListComponents")
    @Parameters({"sortByDuration"})
    public void sortListByDuration(String sortByDuration) {
        flightModuleList.sortOffersByValue(sortByDuration);
        assertTrue(flightModuleList.isFlightDurationTimeOrderByShortest(sortByDuration));
    }

    @Test(dependsOnMethods = "sortListByDuration")
    public void pickOffers() {
        flightModuleList.pickDepartureLASOffer();
        tripSummary = flightModuleList.pickDepartureLAXOffer();
        assertTrue(tripSummary.getPageTitle().contains("Trip Detail"), "Trip Detail tab selected correctly");
    }

    @Test(dependsOnMethods = "pickOffers")
    public void validateTripDetailsComponents() {
        assertTrue(tripSummary.isPresentTripTotalPrice(), "Trip total price is not present");
        assertTrue(tripSummary.isPresentDepartureAndReturnInformation(), "Departure and return information is not present");
        assertTrue(tripSummary.isPresentPriceGuaranteeText(), "Price guarantee text is present");
    }

    @Test(dependsOnMethods = "validateTripDetailsComponents")
    public void paymentValidation() {
        payment = tripSummary.clickBookingButton();
        assertTrue(payment.isLoaded(),"The Payment page is not loaded");
        assertTrue(payment.isPresentFirstNameInput());
        assertTrue(payment.isPresentCountryDropdown());
        assertTrue(payment.isPresentTotalPriceForTrip());
        assertEquals(payment.getLocationInformation(), "Las Vegas (LAS) to Los Angeles (LAX)");
    }

}
