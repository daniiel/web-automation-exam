package com.automation.exam.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.automation.exam.utils.WaitUtil.waitForElementToBeClickable;
import static com.automation.exam.utils.WaitUtil.waitForVisibilityOf;

public class Util {

    private Util() {}

    public static void sendKeys(WebElement elem, String value) {
        elem.clear();
        elem.sendKeys(value);
    }

    public static boolean isElementPresent(WebDriver driver, By by) {
        try{
            driver.findElement(by);
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    public static boolean dropdownHasAllOptions(WebElement dropdown, List<String> options) {
        Select s = new Select(dropdown);
        return s.getOptions().stream().map(opt -> opt.getText()).collect(Collectors.toList()).containsAll(options);
    }

    public static void selectDropdownOptionByValue(WebDriverWait wait, WebElement dropdown, String toSelect) {
        waitForElementToBeClickable(wait, dropdown);
        Select elem = new Select(dropdown);
        elem.selectByValue(toSelect);
    }

    public static void selectDateFromDatePicker(WebDriverWait wait, WebElement datePicker, LocalDate desiredDate) {
        WebElement calendarButton = datePicker.findElement(By.cssSelector("span.icon-calendar"));
        calendarButton.click(); // datePicker-dropdown pane shown

        WebElement datePickerDropdown = datePicker.findElement(By.className("datepicker-cal"));
        waitForVisibilityOf(wait, datePickerDropdown);

        changeMonthPanel(wait, datePickerDropdown, desiredDate);
        pickDay(datePicker, desiredDate);
    }

    private static void changeMonthPanel(WebDriverWait wait, WebElement datePickerDropdown, LocalDate desiredDate) {
        LocalDate currentDate = LocalDate.now();

        if (desiredDate.isBefore(currentDate)) {
            throw new RuntimeException("Date not allowed");
        }

        int monthsToChange = desiredDate.getMonthValue() - currentDate.getMonthValue();

        if (monthsToChange > 0) {
            WebElement nextMonthButton = getNextButton(datePickerDropdown);
            for (int i = 0; i < monthsToChange; i++) {
                nextMonthButton.click();
                // solve StaleElementReferenceException
                nextMonthButton = getNextButton(datePickerDropdown);
                waitForVisibilityOf(wait, nextMonthButton);
            }
        }
    }
    private static WebElement getNextButton(WebElement datePickerDropdown) {
        return datePickerDropdown.findElement(By.cssSelector("button.datepicker-next"));
    }

    private static void pickDay(WebElement datePicker, LocalDate dateDesired) {
        WebElement calendarPanel = datePicker.findElements(By.className("datepicker-cal-month")).get(0);
        WebElement dayBtn = calendarPanel.findElement(
                By.cssSelector("button[data-day=" + Quotes.escape(String.valueOf(dateDesired.getDayOfMonth())) +"]"));
        dayBtn.click();
    }

}
