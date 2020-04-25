package com.automation.exam.driver;

import com.automation.exam.driver.driversmanager.FirefoxDriverManager;
import com.automation.exam.driver.driversmanager.ChromeDriverManager;
import com.automation.exam.constanst.DriverType;

public class DriverManagerFactory {

    public static DriverManager getDriverManager(DriverType driverType) {

        DriverManager driverManager;

        switch (driverType) {
            case CHROME:
                driverManager = new ChromeDriverManager();
                break;
            case FIREFOX:
                driverManager = new FirefoxDriverManager();
                break;
            default:
                throw new UnsupportedOperationException("Not supported driver");
        }

        return driverManager;
    }

}
