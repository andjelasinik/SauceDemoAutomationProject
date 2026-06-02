package Test;

import Base.BaseTest;
import Pages.CartPage;
import Pages.LoginPage;
import Pages.ProductPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;


public class SortingProductsTests extends BaseTest {

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

        //Login before each test
        homePage.login(validUsername, validPassword);
    }


    @Test(priority = 10)
    public void userCanSortProductByPriceLowToHigh() {

        // select sorting option: Price (low to high)
        productPage.clickOnSortDropdownMenu();
        productPage.clickOnLowToHighPrice();

        // create a list where I will store all displayed product prices
        List<Double> actualPrices = new ArrayList<>();

        // get prices from the page and convert them from String to Double
        for (WebElement price : productPage.itemPrices) {
            String value = price.getText().replace("$", "");
            actualPrices.add(Double.parseDouble(value));
        }

        // create a copy of the original list
        List<Double> expectedSorted = new ArrayList<>(actualPrices);

        // sort copied list in ascending order
        Collections.sort(expectedSorted);

        // compare displayed prices with sorted prices
        Assert.assertEquals(actualPrices, expectedSorted);
    }

    @Test(priority = 20)
    public void userCanSortProductByPriceHighToLow() {

        // select sorting option: Price (high to low)
        productPage.clickOnSortDropdownMenu();
        productPage.clickOnLHighToLowPrice();

        // create a list where I will store all displayed product prices
        List<Double> actualPrices = new ArrayList<>();

        // get prices from the page and convert them from String to Double
        for (WebElement price : productPage.itemPrices) {
            String value = price.getText().replace("$", "");
            actualPrices.add(Double.parseDouble(value));
        }

        // create a copy of the original list
        List<Double> expectedSorted = new ArrayList<>(actualPrices);

        // sort list in descending order
        Collections.sort(expectedSorted);
        Collections.reverse(expectedSorted);

        // compare displayed prices with expected descending order
        Assert.assertEquals(actualPrices, expectedSorted);
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

