package com.automationcalling.driverfactory;

import automationcalling.commonutil.SeleniumCommon;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class DriverInitalization extends SeleniumCommon implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {
    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    protected WebDriver driver;

    public WebDriver getDriver(String testType) {
        try {
            if (testType.equalsIgnoreCase("desktop")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (testType.equalsIgnoreCase("mobile")) {
                driver = new RemoteWebDriver(new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:443" + "/wd/hub"),setMobileBrowserCapabilities());
                String id = ((RemoteWebDriver) driver).getSessionId().toString();
                sessionId.set(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    protected synchronized void destroyDriver() {
        try {
            driver.quit();
            driver = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSessionId() {
        return sessionId.get();
    }

    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);

    //Hardcoded Android version. This can be implemented dynamically if required.
    public DesiredCapabilities setMobileBrowserCapabilities() {
        DesiredCapabilities caps = null;
            caps = DesiredCapabilities.android();
            caps.setCapability("platformName", "Android");
            caps.setCapability("browserName", "Chrome");
            caps.setCapability("deviceName", "Samsung Galaxy S8 GoogleAPI Emulator");
            caps.setCapability("platformVersion", "7.1");
            caps.setCapability("appiumVersion", "1.7.2");
            caps.setCapability("deviceOrientation", "portrait");
        return caps;
    }
}
