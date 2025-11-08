# TimeChainLabs Assignment - Automation Test Suite

## Project Overview
This project contains automated test cases for the **ERNX Consumer Web Application** using **Selenium WebDriver, TestNG, and Java**. The purpose of this assignment is to demonstrate automation skills, including UI validation, form interaction, and error validation.

The test suite covers:

1. Basic UI validation of the login page.
2. OTP validation (invalid OTP scenario).
3. Form interactions after login (adding child, selecting gender, avatar, rewards, and theme).

---

## Tools & Frameworks Used
- **Java** 11  
- **Selenium WebDriver** 4.21  
- **TestNG** 7.x  
- **ChromeDriver**  
- **Gradle** 8.x  
- **Logger (java.util.logging)** for test execution logging  

---

## Test Scenarios Covered

### 1. `verifyInvalidOTPValidation`
- **Objective:** Validate error message for invalid OTP.  
- **Steps:**
  1. Navigate to `https://ernx-consumer.vercel.app/login`.
  2. Enter email (`example@gmail.com`) and click Next.
  3. Enter an invalid OTP (`1234`).
  4. Verify that the error message "OTP is Invalid" is displayed.
- **Expected Outcome:** OTP error message appears.  
- **Actual Outcome:** Test passed; error message verified successfully.

---

### 2. `uiValidation`
- **Objective:** Validate login page UI and basic login functionality.  
- **Steps:**
  1. Navigate to login page.
  2. Verify page title is `"ERNX - Parents"`.
  3. Enter email (`nsmdina@gmail.com`) and click Next.
  4. Wait for OTP input and manually enter OTP to proceed.
  5. Click Sign In.
- **Expected Outcome:** User is navigated to the next page after login.  
- **Actual Outcome:** Test passed. Manual OTP entry is required.

---

### 3. `formValidation`
- **Objective:** Validate adding a child, selecting gender, avatar, rewards, and theme.  
- **Steps:**
  1. After login, check if "Add first Child" or "Add another Child" is visible.
  2. Click on the appropriate button to add child.
  3. Fill in **Nickname** field.
  4. Select **Gender**.
  5. Choose an **Avatar** based on gender.
  6. Select a **Reward**.
  7. Pick a **Theme Color**.
  8. Click Finish to complete the form.
- **Expected Outcome:** Child is successfully added with selections applied.  
- **Actual Outcome:** Test passed; all interactions executed successfully.

---

## Project Structure
