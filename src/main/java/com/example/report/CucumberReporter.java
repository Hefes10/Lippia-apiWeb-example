package com.example.report;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberGenericAdapter;
import com.example.services.apiServices.BaseService;
import org.openqa.selenium.TakesScreenshot;
import com.crowdar.driver.DriverManager;
import org.openqa.selenium.OutputType;

public class CucumberReporter extends ExtentCucumberGenericAdapter {

    public CucumberReporter(String arg) {
        super(arg);
    }

    @Override
    public String getScreenshotBase64() {
        return  ((TakesScreenshot) DriverManager.getDriverInstance()).getScreenshotAs(OutputType.BASE64);
    }

    @Override
    public Boolean isScreenshotDisable() {
        return BaseService.getScreenshotDisable() == null || BaseService.getScreenshotDisable();
    }
}
