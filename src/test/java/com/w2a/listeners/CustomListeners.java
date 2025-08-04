package com.w2a.listeners;

import com.w2a.base.TestBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListeners implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());
        Object testClass = result.getInstance();
        TestBase base = (TestBase) testClass;
        String screenshotPath = base.captureScreenshot(result.getName());
        System.out.println("Screenshot saved at: " + screenshotPath);

    }
}

