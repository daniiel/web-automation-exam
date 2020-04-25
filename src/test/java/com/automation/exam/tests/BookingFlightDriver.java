package com.automation.exam.tests;

import com.automation.exam.pages.BookingFlightPage;
import com.automation.exam.pages.FlightsListingPage;
import com.automation.exam.pages.HomePage;
import com.automation.exam.pages.TripSummaryPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookingFlightDriver extends BaseDriver {

    private FlightsListingPage flightModuleList;
    private TripSummaryPage tripSummary;

    @BeforeTest()
    public void selectFlightTab() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickFlightTab();
    }

    @Test
    @Parameters({"flightType", "flyingFrom", "flyingTo", "numberOfAdults"})
    public void searchForFlights(String flightType, String flyingFrom, String flyingTo, String numberOfAdults) {
        BookingFlightPage bookingFlightPage = new BookingFlightPage(getDriver());
        LocalDate departing = LocalDate.now().plusMonths(2);
        LocalDate returning = LocalDate.now().plusMonths(2).plusDays(5);

        bookingFlightPage.selectFlightType(flightType);
        bookingFlightPage.fillFlightForm(flyingFrom, flyingTo, departing, returning, numberOfAdults);
        this.flightModuleList = bookingFlightPage.clickSearchButton();

        assertTrue(flightModuleList.isLoaded(), "The flight module list is loaded");
    }

    @Test(dependsOnMethods = "searchForFlights")
    public void validateFlightModuleListComponents() {
        assertTrue(flightModuleList.isPresentOrderByOption());
        assertTrue(flightModuleList.isPresentSelectBtnForAllOffers());
        assertTrue(flightModuleList.isPresentFlightDurationForAllOffers());
        assertTrue(flightModuleList.isPresentFlightDetailsAndBaggageFeesForAllOffers());
    }

    @Test(dependsOnMethods = "validateFlightModuleListComponents")
    public void sortListByDurationShortest() {
        flightModuleList.sortByValue("duration:asc");
        assertTrue(flightModuleList.isFlightDurationTimeOrderByShortest());
    }

    @Test(dependsOnMethods = "sortListByDurationShortest")
    public void pickOffers() {
        flightModuleList.pickDepartureLasOffer();
        tripSummary = flightModuleList.pickDepartureLaxOffer();
        tripSummary.changeToSummaryTab();
        assertEquals(tripSummary.getTitlePage(), "Review your trip");
    }

 /*
    @Test(dependsOnMethods = "pickOffers")
    public void tripSummary() {
        tripSummary.getTitlePage();
    }
*/


}
