package Test;

import Base.BaseTest;
import Pages.CartPage;
import Pages.DetailPage;
import Pages.LoginPage;
import Pages.ProductPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class CartTests extends BaseTest {

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
        detailPage = new DetailPage();

        //Login before each test
        homePage.login(validUsername, validPassword);
    }

    @Test (priority = 10)
    public void userCanAddProductToCart(){

        //Open cart and verify it is empty
        productPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.cartList.size(), 0);
        cartPage.clickOnContinueShoppingButton();

        //Add products and verify cart badge count
        productPage.clickOnAddToCartA();
        Assert.assertTrue(productPage.cartIcon.getText().contains("1"));
        productPage.clickOnAddToCartB();
        Assert.assertTrue(productPage.cartIcon.getText().contains("2"));

        //Verify "Add to cart" button changed to "Remove" button
        Assert.assertTrue(productPage.removeButtonA.getText().contains("Remove"));
        Assert.assertTrue(productPage.removeButtonB.getText().contains("Remove"));

        //Verify products are displayed in cart
        productPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.itemInCartA.getText(), "Sauce Labs Backpack");
        Assert.assertEquals(cartPage.itemInCartB.getText(), "Sauce Labs Bike Light");
    }

    @Test(priority = 15)
    public void userCanAddProductToCartFromDetailPage() {
        productPage.clickOnAItem("Sauce Labs Bike Light");
        detailPage.clickOnAddToCartButton();

        Assert.assertTrue(detailPage.removeButton.isDisplayed());
    }

    @Test(priority = 20)
    public void userCanRemoveProductsFromProductPage(){

        //Add products to cart
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();

        //Remove products from inventory page and verify cart badge count
        productPage.clickOnRemoveButtonA();
        Assert.assertTrue(productPage.cartIcon.getText().contains("1"));
        productPage.clickOnRemoveButtonB();

        //Verify cart is empty
        Assert.assertFalse(productPage.cartIcon.getText().contains("1"));
        productPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.cartList.size(), 0);
    }

    @Test(priority = 30)
    public void userCanRemoveProductsFromCart(){

        //Add products and open cart
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();
        productPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIcon.getText().contains("2"));

        //Remove products from cart page
        cartPage.clickOnRemoveButtonA();
        Assert.assertTrue(cartPage.cartIcon.getText().contains("1"));
        cartPage.clickOnRemoveButtonB();

        //Verify cart is empty
        Assert.assertFalse(cartPage.cartIcon.getText().contains("1"));
        Assert.assertEquals(cartPage.cartList.size(), 0);
    }

    @Test(priority = 40)
    public void userCanContinueShoppingFromCart(){

        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();

        //Open cart page
        productPage.clickOnCartIcon();

        //Return to products page and verify user is redirected to products page
        cartPage.clickOnContinueShoppingButton();
        String expectedURL="https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(),expectedURL);
        Assert.assertTrue(productPage.header.getText().contains("Products"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}


