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


public class SortingProductsTest extends BaseTest {

    //CREDENTIALS
    String validUsername = "standard_user";
    String validPassword = "secret_sauce";

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


    @Test(priority = 10)
    public void userCanSortProductByPriceLowToHigh() {
        productPage.clickOnSortDropdownMenu();
        productPage.clickOnLowToHighPrice();

        List<Double> actualPrices = new ArrayList<>();

        for (WebElement price : productPage.itemPrices) {
            String value = price.getText().replace("$", "");
            actualPrices.add(Double.parseDouble(value));
        }

        List<Double> expectedSorted = new ArrayList<>(actualPrices);
        Collections.sort(expectedSorted);

        Assert.assertEquals(actualPrices, expectedSorted);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

