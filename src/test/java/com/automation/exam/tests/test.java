package com.automation.exam.tests;

import com.automation.exam.utils.Util;
import org.testng.annotations.Test;

public class test {

    @Test
    public void dasd() {
        String id ="flight-module-2020-06-23t06:11:00-07:00-coach-las-lax-aa-2690_2020-08-28t16:55:00-07:00-coach-lax-las-aa-277_";
        System.out.println(Util.scapeColon(id));
    }
}
