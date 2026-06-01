package Test;

import Base.BaseTest;
import Pages.CartPage;
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


    //CREDENTIALS
    String validUsername= "standard_user";
    String validPassword= "secret_sauce";

    @BeforeMethod
    public void pageSetUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");

        homePage = new LoginPage();
        productPage = new ProductPage();
        cartPage = new CartPage();

        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();
    }

    @Test (priority = 10)
    public void userCanAddProductToCart(){

        productPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.cartList.size(), 0);
        cartPage.clickOnContinueShoppingButton();

        productPage.clickOnAddToCartA();
        Assert.assertTrue(productPage.cartIcon.getText().contains("1"));
        productPage.clickOnAddToCartB();
        Assert.assertTrue(productPage.cartIcon.getText().contains("2"));

        Assert.assertTrue(productPage.removeButtonA.getText().contains("Remove"));
        Assert.assertTrue(productPage.removeButtonB.getText().contains("Remove"));

        productPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.itemInCartA.getText(), "Sauce Labs Backpack");
        Assert.assertEquals(cartPage.itemInCartB.getText(), "Sauce Labs Bike Light");
    }

    @Test(priority = 20)
    public void userCanRemoveProductsFromProductPage(){
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();
        productPage.clickOnRemoveButtonA();
        Assert.assertTrue(productPage.cartIcon.getText().contains("1"));
        productPage.clickOnRemoveButtonB();
        Assert.assertFalse(productPage.cartIcon.getText().contains("1"));
        productPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.cartList.size(), 0);
    }

    @Test(priority = 30)
    public void userCanRemoveProductsFromCart(){
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();
        productPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIcon.getText().contains("2"));
        cartPage.clickOnRemoveButtonA();
        Assert.assertTrue(cartPage.cartIcon.getText().contains("1"));
        cartPage.clickOnRemoveButtonB();
        Assert.assertFalse(cartPage.cartIcon.getText().contains("1"));
        Assert.assertEquals(cartPage.cartList.size(), 0);
    }

    @Test(priority = 40)
    public void userCanContinueShoppingFromCart(){
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();
        productPage.clickOnCartIcon();
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


