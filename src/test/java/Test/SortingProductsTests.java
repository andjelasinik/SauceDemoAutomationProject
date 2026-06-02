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

        //Open sorting dropdown and select low to high sorting
        productPage.clickOnSortDropdownMenu();
        productPage.clickOnLowToHighPrice();

        //Create a list where I will store all displayed product prices
        List<Double> actualPrices = new ArrayList<>();

        //Get prices from the page and convert them from String to Double
        for (WebElement price : productPage.itemPrices) {
            String value = price.getText().replace("$", "");
            actualPrices.add(Double.parseDouble(value));
        }
        //Create a copy of the original list
        List<Double> expectedSorted = new ArrayList<>(actualPrices);

        //Sort copied list in ascending order
        Collections.sort(expectedSorted);

        //Verify products are sorted correctly
        Assert.assertEquals(actualPrices, expectedSorted);
    }

    @Test(priority = 20)
    public void userCanSortProductByPriceHighToLow() {

        //Open sorting dropdown and select high to low sorting
        productPage.clickOnSortDropdownMenu();
        productPage.clickOnLHighToLowPrice();

        //Create a list where I will store all displayed product prices
        List<Double> actualPrices = new ArrayList<>();

        //Get prices from the page and convert them from String to Double
        for (WebElement price : productPage.itemPrices) {
            String value = price.getText().replace("$", "");
            actualPrices.add(Double.parseDouble(value));
        }
        //Create a copy of the original list
        List<Double> expectedSorted = new ArrayList<>(actualPrices);

        //Sort copied list in descending order
        Collections.sort(expectedSorted);
        Collections.reverse(expectedSorted);

        //Verify products are sorted correctly
        Assert.assertEquals(actualPrices, expectedSorted);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

