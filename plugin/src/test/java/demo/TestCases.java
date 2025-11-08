package demo;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import demo.wrappers.Wrappers;
public class TestCases {
    private static final Logger log = Logger.getLogger(TestCases.class.getName());
    ChromeDriver driver;
    Wrappers wrapper;
    WebDriverWait wait;


     @Test(priority = 2)
    public void uiValidation() throws InterruptedException {
        try {
        log.info("Test case start: TestCase01");
        System.out.println("Test case start : TestCase01");
        driver.get("https://ernx-consumer.vercel.app/login"); // Navigates to the website.
        Thread.sleep(5000);
        String pageTitle = driver.getTitle();
        log.info("Page Title is: " + pageTitle);
        Assert.assertEquals(pageTitle, "ERNX - Parents"); // Basic UI validation: checking

        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        Assert.assertTrue(emailField.isDisplayed()); // Basic UI validation: element presence
        wrapper.enterText(emailField, "nsmdina@gmail.com"); // Interaction: filling out email field
        log.info("Email entered successfully.");


        WebElement nextButton = wait
                .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Next']"))));
        wrapper.clickOnElement(nextButton); // Interaction: clicking Next button
        log.info("Clicked Next button.");
        Thread.sleep(10000);

        // Here manually enter the OTP within the given time frame for the test to
        // proceed beacause will give public access in github

        WebElement loginButton = wait.until(
                ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Sign In']"))));
        Thread.sleep(10000);
        wrapper.clickOnElement(loginButton); // Interaction: clicking Verify button
        log.info("Clicked Log In button.");

        log.info("Test case end : TestCase01");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     @Test(priority = 3, dependsOnMethods = "uiValidation")
    public void formValidation() throws InterruptedException {
        try {
        log.info("Test case start : TestCase02");

        boolean isAddFirstChildVisible = !driver.findElements(By.xpath("//h1[normalize-space(.)='Add first Child']")).isEmpty();
        if (!isAddFirstChildVisible) {
            WebElement addAnotherChildButton = wait
                    .until(ExpectedConditions
                            .presenceOfElementLocated(By.xpath("//h1[normalize-space(.)='Add another Child']")));
             // Interaction: clicking Add another Child button
            Actions actions = new Actions(driver);
            actions.moveToElement(addAnotherChildButton).perform();
            wrapper.clickOnElement(addAnotherChildButton);
            log.info("Clicked 'Add another Child' button");
            Thread.sleep(5000);
        } else {
            WebElement addFirstChildButton = wait
                    .until(ExpectedConditions
                            .elementToBeClickable(By.xpath("//h1[normalize-space(.)='Add first Child']")));
            wrapper.clickOnElement(addFirstChildButton); // Interaction: clicking Add first Child button
            log.info("Clicked 'Add first Child' button");
            Thread.sleep(2000);
        }

        WebElement nickNameField = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Nickname']")));
        wrapper.enterText(nickNameField, "Dina"); // Interaction: filling out Nickname field
        log.info("Entered nickname: Dina");
        Thread.sleep(2000);
        WebElement genderSelection = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space(text())='male']")));
        wrapper.clickOnElement(genderSelection); // Interaction: selecting Gender
        String genderSelected = genderSelection.getText();
        log.info("Selected gender: " + genderSelected);
        Thread.sleep(5000);
        WebElement nextButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Next']")));
        wrapper.clickOnElement(nextButton); // Interaction: clicking Next button
        log.info("Clicked Next button after gender selection");
        Thread.sleep(2000);

        List<WebElement> gendersAvatars = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath("//img[contains(@class, 'h-[60px] w-[60px]')]")));
        if (genderSelected.equalsIgnoreCase("male")) {
            wrapper.clickOnElement(gendersAvatars.get(1)); // Selecting second avatar for male
        } else {
            wrapper.clickOnElement(gendersAvatars.get(0)); // Selecting first avatar for female
        }
        log.info("Selected avatar based on gender: " + genderSelected);
        // there is no difference in the avatars locators based on gender
        Thread.sleep(2000);

        wrapper.clickOnElement(nextButton); // Interaction: clicking Next button
        log.info("Clicked Next button to move to rewards page");
        WebElement reward = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class=' w-full gap-4 overflow-x-auto flex  flex-col h-full']\r\n" + //
                        "")));
        wrapper.clickOnElement(reward); // Interaction: selecting a reward
        log.info("Selected a reward");

        wrapper.clickOnElement(nextButton); // Interaction: clicking Next button
        log.info("Clicked Next button to move to theme selection");
        String desiredColor = "green";

        List<WebElement> colorOptions = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@id, 'theme')]")));
        for (WebElement colorOption : colorOptions) {
            String color = colorOption.getAttribute("id").toLowerCase();
            if (color.contains(desiredColor)) {
                wrapper.clickOnElement(colorOption); // Interaction: selecting the desired color
                log.info("Selected theme color: " + desiredColor);
                break;
            }
        }

        WebElement finishButton = wait
                .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Finish']"))));
        wrapper.clickOnElement(finishButton); // Interaction: clicking Finish button
        log.info("Clicked Finish button to complete form");
        Thread.sleep(5000);

        log.info("Test case end : TestCase02");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error in formValidation test case", e);
        }
    }

    @Test(priority = 1)
    public void verifyInvalidOTPValidation() throws InterruptedException {
        try {
        log.info("Test case start : TestCase03");
        driver.get("https://ernx-consumer.vercel.app/login"); // Navigates to the website.
        Thread.sleep(5000);

        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        Assert.assertTrue(emailField.isDisplayed()); // Basic UI validation: element presence
        wrapper.enterText(emailField, "example@gmail.com"); // Interaction: filling out email field
        log.info("Email entered successfully.");
        WebElement nextButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Next']")));
        wrapper.clickOnElement(nextButton); // Interaction: clicking Next button
        log.info("Clicked Next button.");
        Thread.sleep(5000);

        WebElement otpField1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("otp-0")));
        wrapper.enterText(otpField1, "1");
        WebElement otpField2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("otp-1")));
        wrapper.enterText(otpField2, "2");
        WebElement otpField3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("otp-2")));
        wrapper.enterText(otpField3, "3");
        WebElement otpField4 = wait.until(ExpectedConditions.elementToBeClickable(By.id("otp-3")));
        wrapper.enterText(otpField4, "4");
        log.info("Entered OTP: 1234");
        String expectedErrorMessage = "OTP is Invalid";

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'OTP is Invalid')]")));
        String actualErrorMessage = errorMessage.getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message does not match.");
        log.info("Verified error message: " + actualErrorMessage);

        log.info("Test case end : TestCase03");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

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
