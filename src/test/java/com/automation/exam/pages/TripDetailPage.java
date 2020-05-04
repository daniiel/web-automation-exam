package com.automation.exam.pages;

import com.automation.exam.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TripDetailPage extends BasePage {

    private static final String pageTitle = "Trip Detail";
    private static final String idBookingButton = "bookButton";
    private static final String cssPriceTotal = "#tripSummaryToggleContent-desktopView ~ .totalContainer .packagePriceTotal";
    private static final String cssPriceGuarantee = "#tripSummaryToggleContent-desktopView ~ .totalContainer .priceGuarantee";
    private static final String cssDepartureInformation = "div.flightSummary div.OD0";
    private static final String cssReturnInformation = "div.flightSummary div.OD1";

    private final String priceGuaranteeText = "Price Guarantee";

    @FindBy(id = idBookingButton)
    private WebElement bookingButton;

    Logger logger = LoggerFactory.getLogger(TripDetailPage.class);

    public TripDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        changeToTripDetailTab();
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
        if (getDriver().findElements(By.cssSelector(cssDepartureInformation)).isEmpty()
                || getDriver().findElements(By.cssSelector(cssReturnInformation)).isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isPresentPriceGuaranteeText() {
        if(Util.isElementPresent(getDriver(), By.cssSelector(cssPriceGuarantee))) {
            String priceText = getDriver().findElement(By.cssSelector(cssPriceGuarantee)).getText();
            logger.info("price Text: " + priceText);
        }
        if (getDriver().findElements(By.cssSelector(cssPriceGuarantee)).isEmpty()) {
            logger.info("Price guarantee !! empty");
            return false;
        }
        String priceText = getDriver().findElement(By.cssSelector(cssPriceGuarantee)).getText();

        return priceText.equals(priceGuaranteeText);
    }

    public PaymentPage clickBookingButton() {
        bookingButton.click();
        return new PaymentPage(getDriver());
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

}
