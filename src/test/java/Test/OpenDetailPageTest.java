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

public class OpenDetailPageTest extends BaseTest {
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
        cartPage = new CartPage();
        detailPage = new DetailPage();

        //Login before each test
        homePage.login(validUsername, validPassword);
    }


    @Test
    public void userCanOpenProductDetailPage() {
        // Open product details page
        productPage.clickOnAItem("Sauce Labs Bike Light");

        // Verify details page is displayed
        Assert.assertTrue(detailPage.itemDetails.isDisplayed());

        // Verify correct product URL
        String expectedURL = "https://www.saucedemo.com/inventory-item.html?id=0";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

