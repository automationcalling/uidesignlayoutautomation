package automationcalling.commonutil;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class SeleniumCommon {

    private WebDriver driver;

    public void initalizeDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void launchURL(String url) throws InterruptedException {
        driver.get(url);
        maximizeWindow();
        Thread.sleep(5000);
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void setDimensionWindow(int width, int height) {

        driver.manage().window().setSize(new Dimension(width, height));
    }
}
