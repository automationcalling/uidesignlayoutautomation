package com.automationcalling.testsuites;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GoogleHomepage {
    WebDriver driver;
    LayoutReport layoutReport;

    @BeforeTest
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://google.com");
        driver.manage().window().setSize(new Dimension(1024, 1366));
    }

    @Test
    public void responsiveSiteAutomation() throws IOException {
        layoutReport = Galen.checkLayout(driver, "src/main/resources/specs/googlepage.gspec", Arrays.asList("desktop"));

    }

    @AfterMethod()
    public void reportUpdate() throws IOException {
        List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();
        GalenTestInfo test = GalenTestInfo.fromString("Login page on Desktop device test");
        test.getReport().layout(layoutReport, "check layout on Desktop device");
        tests.add(test);

// Exporting all test reports to html
        new HtmlReportBuilder().build(tests, "target/galen-html-reports");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
