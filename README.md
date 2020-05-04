# Web Automation Testing Basics Exam

This is the code for the first point of the Web Automation testing basic training.

  - Java 8
  - Selenium
  - TestNG
  - SLF4J - log4j

### Exercise 1 

Begin the process of booking a flight till the complete credit card information page

Steps:

1. Search for a flight from LAS to LAX, 1 adult (clicking on Flight/Roundtrip). Dates should be at
least two month in the future and ​MUST​ be selected using the datepicker calendar.
2. Verify the result page using the following:
a. There is a box that allow you to order by Price, Departure, Arrival and Duration.
b. The select button is present on every result
c. Flight duration is present on every result
d. The flight detail and baggage fees is present on every result
3. Sort by duration > shorter. Verify the list was correctly sorted.
a. From this step select elements on the list using the button ​Select ​ (don’t use the
elements with Hotel ​Select Flight + Hotel​)
4. In the page (Select your departure to Los Angeles), select the first result.
5. In the new page (Select your departure to Las Vegas), select the third result.
a. if exists the pop-up asking for “flight with a Hotel”, select “no, thanks, maybe later”
6. Verify trip details in the new page, by:
a. Trip total price is present
b. Departure and return information is present
c. Price guarantee text is present
7. Press Continue Booking button.
8. Verify the “Who’s travelling” page is opened by choosing at least 5 validations to be
performed.

### Installation

The project requires download the webdrivers implementations [download drivers](https://www.selenium.dev/downloads/#browsersCollapse) to run.

Inside the ```resource```  folder you can find the Booking-flight-suite.xml.

#### Owner

Daniel Buitrago Caceres.
Java Developer.
TAE Academy.



