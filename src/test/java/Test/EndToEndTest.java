package Test;

import Base.BaseTest;
import Pages.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class EndToEndTest extends BaseTest {

    // Valid credentials
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
    }


    @Test
    public void userCanCompleteFullPurchaseFlow() {

        //Login
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();

        String expectedURL= "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

        //Verify successful login
        Assert.assertTrue(productPage.header.isDisplayed());
        Assert.assertTrue(productPage.header.getText().contains("Products"));

        // Add products to cart
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();

        // Verify cart badge
        Assert.assertEquals(productPage.cartIcon.getText(), "2");

        // Go to cart
        productPage.clickOnCartIcon();

        // Verify items are in cart
        Assert.assertTrue(cartPage.itemInCartA.isDisplayed());
        Assert.assertTrue(cartPage.itemInCartB.isDisplayed());

        // Click checkout
        cartPage.clickOnCheckoutButton();

        // Fill checkout form
        checkoutPage.inputFirstName("Andjela");
        checkoutPage.inputLastName("Sinik");
        checkoutPage.inputPostalCode("11000");

        // Continue to overview
        checkoutPage.clickOnContinueButton();

        // Finish purchase
        paymentPage.clickOnFinishButton();

        // Verify success message
        Assert.assertTrue(thankYouPage.thankYouMessage.isDisplayed());
        Assert.assertEquals(thankYouPage.completeMessage.getText(), "Checkout: Complete!");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

}
