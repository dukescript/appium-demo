package com.dukescript.demo.integration.testing;

import io.appium.java_client.AppiumDriver;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.File;
import java.util.Set;
import org.openqa.selenium.By;
import org.testng.Assert;

public class iOSIntegrationTest {

    public static URL url;
    public static DesiredCapabilities capabilities;
    public static AppiumDriver<MobileElement> driver;

    @BeforeSuite
    public void setupAppium() throws MalformedURLException, InterruptedException {
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
        url = new URL(URL_STRING);

        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID, "22aba3566feaca4be02cc8165a52f6761a866b09");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad (2)");
//        capabilities.setCapability("startIWDP", true);
        capabilities.setCapability(MobileCapabilityType.APP, new File("../client-ios/target/robovm/appium.ipa").getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.3");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability("clearSystemFiles", true);
        capabilities.setCapability("wdaStartupRetryInterval", "1000");
        capabilities.setCapability("useNewWDA", true);
        capabilities.setCapability("waitForQuiescence", false);
        capabilities.setCapability("shouldUseSingletonTestManager", false);
        capabilities.setCapability("autoWebview", "true");

        driver = new AppiumDriver<MobileElement>(url, capabilities);
    }

//    @AfterSuite
//    public void uninstallApp() throws InterruptedException {
//        driver.removeApp("com.dukescript.demo");
//    }
    @Test(enabled = true)
    public void myFirstTest() throws InterruptedException {

        MobileElement input = driver.findElement(By.cssSelector("#data-input"));
        input.clear();
        input.sendKeys("Appium works great with DukeScript Android");
    }
}
