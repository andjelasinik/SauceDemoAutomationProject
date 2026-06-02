package Test;

import Base.BaseTest;
import Pages.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CheckoutTests extends BaseTest {

    //Valid credentials
    String validUsername= "standard_user";
    String validPassword= "secret_sauce";

    @BeforeMethod
    public void pageSetUp() {
        //Create Chrome options before starting browser
        ChromeOptions options = new ChromeOptions();
        //Open browser in incognito mode
        options.addArguments("--incognito");
        //Disable browser notification
        options.addArguments("--disable-notifications");
        //Disable popup blocking
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");

        //Initialize page objects
        homePage = new LoginPage();
        productPage = new ProductPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        paymentPage = new PaymentPage();
        thankYouPage = new ThankYouPage();

        //Login before each test
        homePage.login(validUsername, validPassword);
    }

    @Test (priority = 10)
    public void userCanCheckoutSuccessfully() {

        //Add products to cart
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();

        //Open cart page
        productPage.clickOnCartIcon();
        wait.until(ExpectedConditions.elementToBeClickable(cartPage.checkoutButton));

        //Start checkout process
        cartPage.clickOnCheckoutButton();

        //Enter customer information
        checkoutPage.inputFirstName("Andjela");
        checkoutPage.inputLastName("Sinik");
        checkoutPage.inputPostalCode("11000");

        //Continue to overview page
        checkoutPage.clickOnContinueButton();

        //Complete checkout and verify successful order information
        paymentPage.clickOnFinishButton();
        Assert.assertTrue(thankYouPage.thankYouMessage.isDisplayed());
        Assert.assertEquals(thankYouPage.completeMessage.getText(), "Checkout: Complete!");
    }

    @Test (priority = 20)
    public void userCannotCheckoutWithoutFirstName() {

        //Add products to cart
        productPage.clickOnAddToCartA();
        productPage.clickOnCartIcon();
        cartPage.clickOnCheckoutButton();

        //Leave first name field empty
        checkoutPage.inputLastName("Sinik");
        checkoutPage.inputPostalCode("11000");
        checkoutPage.clickOnContinueButton();

        //Verify validation message
        Assert.assertTrue(driver.getPageSource().contains("Error: First Name is required"));
    }

    @Test(priority = 30)
    public void userCannotCheckoutWithoutLastName() {

        //Add products to cart
        productPage.clickOnAddToCartA();
        productPage.clickOnCartIcon();
        cartPage.clickOnCheckoutButton();

        //Leave last name field empty
        checkoutPage.inputFirstName("Andjela");
        checkoutPage.inputPostalCode("11000");
        checkoutPage.clickOnContinueButton();

        //Verify validation message
        Assert.assertTrue(driver.getPageSource().contains("Error: Last Name is required"));
    }

    @Test(priority = 40)
    public void userCannotCheckoutWithoutPostalCode() {

        //Add products to cart
        productPage.clickOnAddToCartA();
        productPage.clickOnCartIcon();
        cartPage.clickOnCheckoutButton();

        //Leave postal code field empty
        checkoutPage.inputFirstName("Andjela");
        checkoutPage.inputLastName("Sinik");
        checkoutPage.clickOnContinueButton();

        //Verify validation message
        Assert.assertTrue(driver.getPageSource().contains("Error: Postal Code is required"));
    }

    @Test (priority = 50)
    public void userCanCancelCheckout() {

        //Add products to cart and enter checkout information
        productPage.clickOnAddToCartA();
        productPage.clickOnCartIcon();
        cartPage.clickOnCheckoutButton();
        checkoutPage.inputFirstName("Andjela");
        checkoutPage.inputLastName("Sinik");
        checkoutPage.inputPostalCode("11000");

        //Cancel checkout process and verify user is returned to cart page
        checkoutPage.clickOnCancelButton();
        String expectedURL = "https://www.saucedemo.com/cart.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @Test (priority = 60)
    public void userCannotCheckoutWithEmptyCart() {

        //Open empty cart and verify there are no products
        productPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.cartList.size(), 0);

        //Attempt to start checkout
        cartPage.clickOnCheckoutButton();

        //Negative test! Checkout should not be available
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-one"));

        // BUG REPORT: User is able to proceed to checkout with empty cart
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
