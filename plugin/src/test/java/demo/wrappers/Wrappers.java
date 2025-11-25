package demo.wrappers;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {
    private ChromeDriver driver;
    private WebDriverWait wait;

    public Wrappers(ChromeDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterText(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
            System.out.println("Entered text: " + text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOnElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
