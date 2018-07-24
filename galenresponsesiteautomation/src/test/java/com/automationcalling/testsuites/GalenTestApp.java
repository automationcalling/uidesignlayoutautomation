package com.automationcalling.testsuites;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GalenTestApp {
    WebDriver driver;
    LayoutReport layoutReport;

    @BeforeTest
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://testapp.galenframework.com");
        driver.manage().window().setSize(new Dimension(1024, 1366));
    }

    @Test
    public void responsiveSiteAutomation() {
        try {
            layoutReport = Galen.checkLayout(driver, "src/test/resources/specs/welcomePage.gspec", Arrays.asList("desktop"));
            reportUpdate();
            if (layoutReport.errors() > 0) {
                Assert.fail("Responsive Layout test has been failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reportUpdate() throws IOException {
        List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();
        GalenTestInfo test = GalenTestInfo.fromString("Login page on Desktop device test");
        test.getReport().layout(layoutReport, "check layout on Desktop device");
        tests.add(test);
        new HtmlReportBuilder().build(tests, "target/galen-html-reports");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
