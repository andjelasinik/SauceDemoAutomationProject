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

    // kredencijali za login (koristim validnog usera sa sajta)
    String validUsername= "standard_user";
    String validPassword= "secret_sauce";

    @BeforeMethod
    public void pageSetUp() {
        ChromeOptions options = new ChromeOptions();

        // podešavam Chrome pre nego što se pokrene test
        // koristim ChromeOptions da bih kontrolisala ponašanje browsera

        // otvara Chrome u incognito modu
        // da nemam cookies, cache i prethodne sesije koje mogu da utiču na test
        options.addArguments("--incognito");

        // isključuje notifikacije u browseru
        // da popup "allow notifications" ne blokira elemente i klikove
        options.addArguments("--disable-notifications");

        // gasi blokiranje popup prozora
        // da Selenium može da vidi i radi sa popupovima ako se pojave
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        // otvaram sajt
        driver.navigate().to("https://www.saucedemo.com/");

        // pravim page objekte
        homePage = new LoginPage();
        productPage = new ProductPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        paymentPage = new PaymentPage();
        thankYouPage = new ThankYouPage();

        // login pre testova
        homePage.login(validUsername, validPassword);
    }

    @Test (priority = 10)
    public void userCanCheckoutSuccessfully() {

        // dodajem proizvode u cart (2 proizvoda da imam šta da testiram)
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();

        // idem u cart stranicu da proverim proizvode
        productPage.clickOnCartIcon();

        // čekam da checkout dugme bude klikabilno (da se stranica učitala)
        wait.until(ExpectedConditions.elementToBeClickable(cartPage.checkoutButton));

        // klik na checkout da krenem proces kupovine
        cartPage.clickOnCheckoutButton();

        // unos podataka za checkout formu
        checkoutPage.inputFirstName("Andjela");
        checkoutPage.inputLastName("Sinik");
        checkoutPage.postalCodeField.sendKeys("11000");

        // nastavak na overview stranicu
        checkoutPage.clickOnContinueButton();

        // završavam kupovinu
        paymentPage.clickOnFinishButton();

        // proveravam da sam uspešno završila checkout
        // očekujem success poruku nakon završene kupovine
        Assert.assertTrue(thankYouPage.thankYouMessage.isDisplayed());

        // proveravam da je checkout uspešno završen
        Assert.assertEquals(thankYouPage.completeMessage.getText(), "Checkout: Complete!");
    }

    @Test (priority = 20)
    public void userCannotCheckoutWithoutFirstName() {

        productPage.clickOnAddToCartA();
        productPage.clickOnCartIcon();
        cartPage.clickOnCheckoutButton();

        // ne unosim first name
        checkoutPage.inputLastName("Sinik");
        checkoutPage.postalCodeField.sendKeys("11000");

        checkoutPage.clickOnContinueButton();

        // proveravam da sistem ne dozvoljava checkout bez first name
        // očekujem error poruku
        Assert.assertTrue(driver.getPageSource().contains("Error: First Name is required"));
    }

    @Test(priority = 30)
    public void userCannotCheckoutWithoutLastName() {

        productPage.clickOnAddToCartA();
        productPage.clickOnCartIcon();
        cartPage.clickOnCheckoutButton();

        checkoutPage.inputFirstName("Andjela");
        checkoutPage.postalCodeField.sendKeys("11000");

        checkoutPage.clickOnContinueButton();

        // proveravam da sistem ne dozvoljava checkout bez first name
        // očekujem error poruku
        Assert.assertTrue(driver.getPageSource().contains("Error: Last Name is required"));
    }

    @Test(priority = 40)
    public void userCannotCheckoutWithoutPostalCode() {

        productPage.clickOnAddToCartA();
        productPage.clickOnCartIcon();
        cartPage.clickOnCheckoutButton();

        checkoutPage.inputFirstName("Andjela");
        checkoutPage.inputLastName("Sinik");

        checkoutPage.clickOnContinueButton();

        // proveravam da sistem ne dozvoljava checkout bez first name
        // očekujem error poruku
        Assert.assertTrue(driver.getPageSource().contains("Error: Postal Code is required"));
    }

    @Test (priority = 50)
    public void userCanCancelCheckout() {

        productPage.clickOnAddToCartA();
        productPage.clickOnCartIcon();
        cartPage.clickOnCheckoutButton();

        checkoutPage.inputFirstName("Andjela");
        checkoutPage.inputLastName("Sinik");
        checkoutPage.postalCodeField.sendKeys("11000");

        // klik na cancel dugme
        checkoutPage.clickOnCancelButton();

        // proveravam da sam vraćena na product page
        String expectedURL = "https://www.saucedemo.com/cart.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @Test (priority = 60)
    public void userCannotCheckoutWithEmptyCart() {


        // idem direktno u cart bez dodavanja proizvoda
        productPage.clickOnCartIcon();

        // proveravam da li je cart stvarno prazan
        Assert.assertEquals(cartPage.cartList.size(), 0);

        // pokušavam da krenem checkout
        cartPage.clickOnCheckoutButton();

        // OVO JE NEGATIVNA PROVERA
        // očekujem da NE mogu da nastavim dalje
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-one"));

        // BUG REPORT: User is able to proceed to checkout with empty cart
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
