package com.automation.exam.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage {

    private static final String URL = "https://www.travelocity.com/";

    @FindBy(id = "tab-flight-tab-hp")
    private WebElement flightTab;
    @FindBy(id = "tab-hotel-tab-hp")
    private WebElement hotelTab;
    @FindBy(id = "tab-package-tab-hp")
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
