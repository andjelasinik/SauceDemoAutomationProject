# SauceDemo

SauceDemo is a demo e-commerce website used for practice of UI test automation. The main goal of this project is to test basic user flows such as login, product selection, cart management and checkout process.

Website under test:  
https://www.saucedemo.com/

## Dependencies

- Windows 10 / Windows 11
- IntelliJ IDEA Community Edition
- Google Chrome browser
- Java 21
- Apache Maven
- Selenium WebDriver
- TestNG

## Installation

Open terminal in IDE and clone the repository:

git clone https://github.com/andjelasinik/SauceDemoAutomationProject.git

Install Maven dependencies and open the project in IntelliJ IDEA.

## Executing Program

Run all tests from terminal:

mvn test

Run specific test class:

mvn test -Dtest="LoginTests"

Run TestNG suite file:

mvn clean test -DsuiteXmlFile="testng.xml"

## Framework Walkthrough

Packages:
- Base - contains base setup and driver configuration
- Pages - contains Page Object Model classes
- Tests - contains test classes

Pages:
- LoginPage
- ProductPage
- DetailPage
- CartPage
- CheckoutPage
- PaymentPage
- ThankYouPage

Tests:
- LoginTests
- CartTests
- CheckoutTests
- OpenDetailPageTest
- SortingProductsTests
- SideMenuTests
- EndToEndTest

Files:
- pom.xml - contains project dependencies
- testng.xml - main test suite file
- .gitignore - ignored files for Git

## Test Scenarios

Login:
- Login with valid credentials
- Login with invalid credentials

Products:
- Sorting products by price (low to high / high to low)
- Opening product detail page

Cart:
- Adding products to cart
- Removing products from cart and product page
- Continue shopping from cart

Checkout:
- Successful checkout process
- Validation of required fields
- Cancel checkout process
- Checkout with empty cart (negative test)

Side Menu:
- Logout functionality
- Navigation to All Items
- Reset App State functionality

End-to-End:
- Complete purchase flow from login to order confirmation

## Naming Convention

- Class names are written in CamelCase
- Method names are written in camelCase
- Test methods are named based on test scenarios
- Page classes follow Page Object Model structure

## Other

- Tests are written using Page Object Model (POM)
- Chrome browser is used for execution
- Incognito mode is enabled
- Browser notifications are disabled
- Explicit waits are used where needed
- Assertions are used for validation