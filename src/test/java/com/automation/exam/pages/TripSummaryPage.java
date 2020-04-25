package com.automation.exam.pages;

import com.automation.exam.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Iterator;
import java.util.Set;

public class TripSummaryPage extends BasePage {

    @FindBy(className = "h1.section-header-main")
    private WebElement pageTitle;

    @FindBy(css = ".tripTotals span.packagePriceTotal")
    private WebElement tripTotalPrice;

    @FindBy(css = "div.flightSummary div.OD0")
    private WebElement departureInformation;

    @FindBy(css = "div.flightSummary div.OD1")
    private WebElement returnInformation;

    public TripSummaryPage(WebDriver driver) {
        super(driver);
    }

    public void changeToSummaryTab() {
        String currentWindow = getDriver().getWindowHandle();
        Set<String> windows = getDriver().getWindowHandles();
        Iterator<String> iWindows = windows.iterator();
        String childWindow;

        while(iWindows.hasNext()) {
            childWindow = iWindows.next();

            if (!currentWindow.equalsIgnoreCase(childWindow)) {
                getDriver().switchTo().window(childWindow);
                if(getDriver().getTitle().contains("Trip Detail")) {
                    return;
                }
            }
        }
    }

    public String getTitlePage() {
        By by = By.className("h1.section-header-main");
        getWait().until(ExpectedConditions.presenceOfElementLocated(by));
        return pageTitle.getText();
    }
}
