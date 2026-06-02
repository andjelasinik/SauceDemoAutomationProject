package Test;

import Base.BaseTest;
import Pages.CartPage;
import Pages.DetailPage;
import Pages.LoginPage;
import Pages.ProductPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SideMenuTests extends BaseTest {

    //Valid credentials
    String validUsername = "standard_user";
    String validPassword = "secret_sauce";

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
        detailPage = new DetailPage();
        cartPage = new CartPage();

        //Login before each test
        homePage.login(validUsername, validPassword);
    }

    @Test (priority = 1)
    public void userCanLogout() throws InterruptedException {

        //Open side menu
        productPage.clickOnSideMenu();
        wait.until(ExpectedConditions.elementToBeClickable(productPage.logoutButton));

        //Click logout
        productPage.clickOnLogoutButton();

        //Verify user is redirected to login page
        Assert.assertTrue(homePage.loginForm.isDisplayed());
        String expectedURL = "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @Test (priority = 10)
    public void userCanNavigateToAllItemsFromSideMenu() {
        //Open and verify that product detail page is opened
        productPage.clickOnAItem("Sauce Labs Bike Light");
        Assert.assertTrue(detailPage.itemDetails.isDisplayed());

        //Open side menu
        productPage.clickOnSideMenu();
        wait.until(ExpectedConditions.elementToBeClickable(detailPage.allItemsButton));
        //Click on All items
        detailPage.clickOnAllItemsButton();

        //Verify that user is redirected to products page
        String expectedURL= "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(),expectedURL);
        Assert.assertTrue(productPage.header.getText().contains("Products"));
    }

    @Test(priority = 20)
    public void resetAppStateButtonUpdateProductButtons() {

        //Add products and verify cart badge
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();
        Assert.assertEquals(productPage.cartIcon.getText(),"2");

        //Open side menu
        productPage.clickOnSideMenu();

        //Click reset app button
        wait.until(ExpectedConditions.elementToBeClickable(productPage.resetAppStateButton));
        productPage.clickOnResetAppStateButton();

        //Negative test, "Remove" buttons should be updated to "Add to cart" buttons
        Assert.assertTrue(productPage.addToCartButtonA.isDisplayed());
        Assert.assertTrue(productPage.addToCartButtonB.isDisplayed());

        //Bug report: Reset App State does not fully reset UI state on Product page
    }

    @Test (priority = 30)
    public void resetAppStateWorksAfterNavigationToCartAndBack() {
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();
        Assert.assertEquals(productPage.cartIcon.getText(),"2");

        //Open side menu
        productPage.clickOnSideMenu();

        //Click reset app button
        wait.until(ExpectedConditions.elementToBeClickable(productPage.resetAppStateButton));
        productPage.clickOnResetAppStateButton();

        //Go to cart
        productPage.clickOnCartIcon();
        //Cart is empty
        Assert.assertEquals(cartPage.cartList.size(), 0);

        //Go back to products page
        cartPage.clickOnContinueShoppingButton();

        //Buttons are reset correctly
        Assert.assertTrue(productPage.addToCartButtonA.isDisplayed());
        Assert.assertTrue(productPage.addToCartButtonB.isDisplayed());

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
