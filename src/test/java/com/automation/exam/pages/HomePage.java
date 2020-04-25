package com.automation.exam.pages;

import com.automation.exam.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage {

    @FindBy(id = "tab-flight-tab-hp")
    private WebElement flightTab;

    @FindBy(id = "tab-hotel-tab-hp")
    private WebElement hotelTab;

    @FindBy(id = "tab-package-tab-hp")
    private WebElement vacationPackagesTab;

    private static String URL = "https://www.travelocity.com/";

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
