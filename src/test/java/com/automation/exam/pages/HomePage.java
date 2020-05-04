package com.automation.exam.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage {

    private static final String idFlightTab = "tab-flight-tab-hp";
    private static final String idHotelTab = "tab-hotel-tab-hp";
    private static final String idVacationPackagesTab = "tab-package-tab-hp";

    private static final String URL = "https://www.travelocity.com/";

    @FindBy(id = idFlightTab)
    private WebElement flightTab;
    @FindBy(id = idHotelTab)
    private WebElement hotelTab;
    @FindBy(id = idVacationPackagesTab)
    private WebElement vacationPackagesTab;

    public HomePage(WebDriver driver) {
        super(driver);
        driver.get(URL);
    }

    public void clickFlightTab() {
        flightTab.click();
    }

    public void clickHotelTab() {
        hotelTab.click();
    }

    public void clickVacationPackagesTab() {
        vacationPackagesTab.click();
    }

}
