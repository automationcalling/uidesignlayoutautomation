package com.automationcalling.stepdefinitions.googlefeatures;

import automationcalling.commonutil.GalenUtil;
import com.automationcalling.driverfactory.DriverInitalization;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GalenSamplePage extends DriverInitalization {
    private GalenUtil galenUtil;
    private String deviceType;

    public void driverInit(String browserDeviceType) {
        driver = getDriver(browserDeviceType);
        initalizeDriver(driver);
        Assert.assertNotNull(driver);
    }

    @Before
    public void init() {
        try {
            galenUtil = new GalenUtil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("I launch (.*?) browser with url (.*?)$")
    public void i_launch_browser_with_url(String browserDeviceType, String appURL) {
        // Write code here that turns the phrase above into concrete actions
        try {
            driverInit(browserDeviceType);
            if (browserDeviceType.equalsIgnoreCase("desktop")) {
                deviceType = "desktop";
            } else if (browserDeviceType.equalsIgnoreCase("mobile")) {
                deviceType = "mobile";
            }
            launchURL(appURL);
            maximizeWindow();
        } catch (Exception e) {
            e.getStackTrace();
            Assert.fail("Failed to Launch browser");
        }
    }

    @When("I load gspec file (.*?)$")
    public void i_load_gspec_file(String gSpecfileName) {
        // Write code here that turns the phrase above into concrete actions
        try {
            galenUtil.verifyLayout(driver, gSpecfileName, Arrays.asList(deviceType));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to load gspec file");
        }
    }

    @Then("Test Info (.*?) and Report Info (.*?) is updated")
    public void test_Info_and_Report_Info_is_updated(String testInfo, String reportInfo){
        // Write code here that turns the phrase above into concrete actions
        try {
            galenUtil.reportUpdate(testInfo, reportInfo);
        }catch (Exception e)
        {
            Assert.fail("Exception in report");
        }
    }


    @After
    public void tearDown() {
        destroyDriver();
    }


}
