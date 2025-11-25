package demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import demo.wrappers.Wrappers;

public class TestCases {

    ChromeDriver driver;
    Wrappers wrapper;
    WebDriverWait wait;

    @Test(priority = 2)
    public void uiValidation() throws InterruptedException {
        try {
            System.out.println("Test case start: TestCase01");
            driver.get("https://ernx-consumer.vercel.app/login");
            Thread.sleep(4000);

            String pageTitle = driver.getTitle(); // Get the page title to verify navigation correctness
            System.out.println("Page Title is: " + pageTitle);
            Assert.assertEquals(pageTitle, "ERNX - Parents"); // Assertion for page title validation

            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
            Assert.assertTrue(emailField.isDisplayed()); // Verify email field is displayed for UI validation
            wrapper.enterText(emailField, "nsmdina@gmail.com");
            Thread.sleep(4000);
            System.out.println("Email entered successfully.");

            WebElement nextButton = wait
                    .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Next']"))));
            // I used refreshed here to enssure the element is interactable after any potential page updates
                    wrapper.clickOnElement(nextButton); 
            Thread.sleep(4000);
            System.out.println("Clicked Next button.");

            // Manually enter the OTP here for the test to proceed
            WebElement loginButton = wait.until(
                    ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Sign In']"))));
            // Using refreshed to ensure the button is interactable after OTP entry
                    Thread.sleep(4000);
            wrapper.clickOnElement(loginButton);
            Thread.sleep(4000);
            System.out.println("Clicked Log In button.");

            System.out.println("Test case end: TestCase01");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 3, dependsOnMethods = "uiValidation")
    public void formValidation() throws InterruptedException {
        try {
            System.out.println("Test case start: TestCase02");
            // if first child is visible, click it; else click add another child
            boolean isAddFirstChildVisible = !driver.findElements(By.xpath("//h1[normalize-space(.)='Add first Child']")).isEmpty(); // Check if "Add first Child" button is present it returns first matching element or empty list
            if (!isAddFirstChildVisible) { // Check for "Add another Child" button
                WebElement addAnotherChildButton = wait
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[normalize-space(.)='Add another Child']")));
                Actions actions = new Actions(driver);
                actions.moveToElement(addAnotherChildButton).perform();
                wrapper.clickOnElement(addAnotherChildButton);
                Thread.sleep(4000);
                System.out.println("Clicked 'Add another Child' button");
            } else if(isAddFirstChildVisible) {  // Check for "Add first Child" button
                Thread.sleep(4000);
                WebElement addFirstChildButton = wait
                        .until(ExpectedConditions.elementToBeClickable(By.xpath("//h1[normalize-space(.)='Add first Child']")));
                wrapper.clickOnElement(addFirstChildButton);
                Thread.sleep(4000);
                System.out.println("Clicked 'Add first Child' button");
            }
            
            WebElement nickNameField = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Nickname']")));
            wrapper.enterText(nickNameField, "Dina");
            Thread.sleep(4000);
            System.out.println("Entered nickname: Dina");
            
            WebElement genderSelection = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space(text())='male']")));
            wrapper.clickOnElement(genderSelection);
            Thread.sleep(4000);
            String genderSelected = genderSelection.getText(); // Capture selected
            System.out.println("Selected gender: " + genderSelected);
            Thread.sleep(4000);

            WebElement nextButton = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Next']")));
            wrapper.clickOnElement(nextButton);
            Thread.sleep(4000);
            System.out.println("Clicked Next button after gender selection");
            // no DOM differences to find gender-specific avatars, so using index to select
            List<WebElement> gendersAvatars = wait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(By.xpath("//img[contains(@class, 'h-[60px] w-[60px]')]")));
            if (genderSelected.equalsIgnoreCase("male")) { // if gender is male
                wrapper.clickOnElement(gendersAvatars.get(1)); 
            } else { // if gender is female
                wrapper.clickOnElement(gendersAvatars.get(0));
            }
            Thread.sleep(4000);
            System.out.println("Selected avatar based on gender: " + genderSelected);

             WebElement nextButton1 = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Next']")));
           
            wrapper.clickOnElement(nextButton1);
            Thread.sleep(4000);
            System.out.println("Clicked Next button to move to rewards page");
            // Selecting first reward as no specific criteria provided
            WebElement reward = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class=' w-full gap-4 overflow-x-auto flex  flex-col h-full']")));
            wrapper.clickOnElement(reward);
            Thread.sleep(4000);
            System.out.println("Selected a reward");

             WebElement nextButton2 = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Next']")));
           
            wrapper.clickOnElement(nextButton2);
            Thread.sleep(4000);
            System.out.println("Clicked Next button to move to theme selection");
            // Selecting theme color
            String desiredColor = "green"; // Desired theme color
            List<WebElement> colorOptions = wait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@id, 'theme')]"))); // Locate theme color options
            for (WebElement colorOption : colorOptions) { // Iterate through options
                String color = colorOption.getAttribute("id").toLowerCase(); // Get id attribute
                if (color.contains(desiredColor)) { // Check if it matches desired color
                    wrapper.clickOnElement(colorOption);
                    Thread.sleep(4000);
                    System.out.println("Selected theme color: " + desiredColor);
                    break;
                }
            }

            WebElement finishButton = wait
                    .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Finish']"))));
            wrapper.clickOnElement(finishButton);
            Thread.sleep(4000);
            System.out.println("Clicked Finish button to complete form");

            System.out.println("Test case end: TestCase02");

        } catch (Exception e) {
            System.out.println("Error in formValidation test case");
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void verifyInvalidOTPValidation() throws InterruptedException {
        try {
            System.out.println("Test case start: TestCase03");
            driver.get("https://ernx-consumer.vercel.app/login");
            Thread.sleep(4000);
            
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
            Assert.assertTrue(emailField.isDisplayed());
            wrapper.enterText(emailField, "example@gmail.com");
            Thread.sleep(4000);
            System.out.println("Email entered successfully.");

            WebElement nextButton = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Next']")));
            wrapper.clickOnElement(nextButton);
            Thread.sleep(4000);
            System.out.println("Clicked Next button.");

            WebElement otpField1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("otp-0")));
            wrapper.enterText(otpField1, "1");
            WebElement otpField2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("otp-1")));
            wrapper.enterText(otpField2, "2");
            WebElement otpField3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("otp-2")));
            wrapper.enterText(otpField3, "3");
            WebElement otpField4 = wait.until(ExpectedConditions.elementToBeClickable(By.id("otp-3")));
            wrapper.enterText(otpField4, "4");
            Thread.sleep(4000);
            System.out.println("Entered OTP: 1234");

            String expectedErrorMessage = "OTP is Invalid"; // Expected error message
            WebElement errorMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'OTP is Invalid')]")));
            String actualErrorMessage = errorMessage.getText();
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message does not match."); // Assertion for validation
            System.out.println("Verified error message: " + actualErrorMessage);

            System.out.println("Test case end: TestCase03");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeTest
    public void startBrowser() {
        ChromeOptions options = new ChromeOptions(); 

        driver = new ChromeDriver(options);
        wrapper = new Wrappers(driver);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}
