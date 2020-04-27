package com.automation.exam.pages;

import com.automation.exam.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TripDetailPage extends BasePage {

    @FindBy(css = "div.flightSummary div.OD1")
    private WebElement returnInformation;

    private static final String pageTitle = "Trip Detail";
    private static final String cssPriceTotal = "#tripSummaryToggleContent-desktopView ~ .totalContainer .packagePriceTotal";
    private static final String cssDepartureInformation = "div.flightSummary div.OD0";
    private static final String cssReturnInformation = "div.flightSummary div.OD1";


    public TripDetailPage(WebDriver driver) {
        super(driver);
        changeToTripDetailTab();
    }

    public void changeToTripDetailTab() {
        String currentWindow = getDriver().getWindowHandle();
        Set<String> windows = getDriver().getWindowHandles();
        Iterator<String> iWindows = windows.iterator();
        String childWindow;

        while(iWindows.hasNext()) {
            childWindow = iWindows.next();

            if (!currentWindow.equalsIgnoreCase(childWindow)) {
                getDriver().switchTo().window(childWindow);
                if (getPageTitle().contains(pageTitle)) {
                    return;
                }
            }
        }
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public boolean isPresentTripTotalPrice() {
        List<WebElement> totalPrice = getDriver().findElements(By.cssSelector(cssPriceTotal));
        if (totalPrice.isEmpty()) {
            return false;
        }
        return totalPrice.get(0).getText().isEmpty() ? false : true;
    }

    public boolean isPresentDepartureAndReturnInformation() {
        return true;
    }

}
