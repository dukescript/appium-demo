package com.dukescript.demo.integration.testing;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.File;
import java.util.Set;
import org.openqa.selenium.By;
import org.testng.Assert;

public class AndroidIntegrationTest {

    public static URL url;
    public static DesiredCapabilities capabilities;
    public static AndroidDriver<MobileElement> driver;

    @BeforeSuite
    public void setupAppium() throws MalformedURLException, InterruptedException {
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
        url = new URL(URL_STRING);
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        capabilities.setCapability(MobileCapabilityType.APP, new File("../client-android/target/appium-android-1.0-SNAPSHOT.apk").getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("appWaitActivity", "com.dukescript.presenters.Android");
        driver = new AndroidDriver<MobileElement>(url, capabilities);
    }

//    @AfterSuite
//    public void uninstallApp() throws InterruptedException {
//        driver.removeApp("com.dukescript.demo");
//    }

    @Test(enabled = true)
    public void myFirstTest() throws InterruptedException {
        MobileElement webview = driver.findElementByClassName("android.webkit.WebView");
        Assert.assertNotNull(webview);

        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            if (contextName.startsWith("WEBVIEW")) {
                driver.context(contextName);
            }
        }
        MobileElement input = driver.findElement(By.cssSelector("#data-input"));
        input.clear();
        input.sendKeys("Appium works great with DukeScript Android");
    }
}
