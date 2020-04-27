package com.automation.exam.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;

public class Util {

    private Util() {}

    public static String scapeColon(String toScape) {
        return toScape.replace(":", "\\:");
    }

    public static void selectDropdownOptionByValue(WebDriverWait wait, WebElement dropdown, String toSelect) {
        WaitUtil.waitForElementToBeClickable(wait, dropdown);
        Select elem = new Select(dropdown);
        elem.selectByValue(toSelect);
    }

    public static void selectDateFromDatePicker(WebDriverWait wait, WebElement datePicker, LocalDate desiredDate) {
        WebElement calendarBtn = datePicker.findElement(By.cssSelector("span.icon-calendar"));
        calendarBtn.click(); // datePicker-dropdown pane shown

        WebElement datePickerCalendar = datePicker.findElement(By.className("datepicker-cal"));
        wait.until(ExpectedConditions.visibilityOf(datePickerCalendar));

        changeMonthPanel(wait, datePicker, desiredDate);
        pickDay(datePicker, desiredDate);
    }

    private static void changeMonthPanel(WebDriverWait wait, WebElement datePicker, LocalDate desiredDate) {
        LocalDate currentDate = LocalDate.now();
        if (desiredDate.isBefore(currentDate)) {
            throw new RuntimeException("Date is not allowed");
        }

        int monthsToChange = desiredDate.getMonthValue() - currentDate.getMonthValue();

        if (monthsToChange > 0) {
            WebElement nextMonthBtn = datePicker.findElement(By.cssSelector("button.datepicker-next"));
            for (int i = 0; i< monthsToChange; i++) {
                nextMonthBtn.click();
                nextMonthBtn = datePicker.findElement(By.cssSelector("button.datepicker-next"));
                wait.until(ExpectedConditions.visibilityOf(nextMonthBtn));
            }
        }
    }

    private static void pickDay(WebElement datePicker, LocalDate dateDesired) {
        WebElement calendarPanel = datePicker.findElements(By.className("datepicker-cal-month")).get(0);
        WebElement dayBtn = calendarPanel.findElement(
                By.xpath(".//button[@data-day=" + Quotes.escape(String.valueOf(dateDesired.getDayOfMonth())) +"]"));
        dayBtn.click();
    }



}
