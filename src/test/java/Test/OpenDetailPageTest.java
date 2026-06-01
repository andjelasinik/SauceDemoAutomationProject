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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class OpenDetailPageTest extends BaseTest {
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
        detailPage = new DetailPage();

        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();
    }


    @Test
    public void userCanOpenProductDetailPage() {
        productPage.clickOnAItem("Sauce Labs Bike Light");

        Assert.assertTrue(detailPage.itemDetails.isDisplayed());
        String expectedURL="https://www.saucedemo.com/inventory-item.html?id=0";
        Assert.assertEquals(driver.getCurrentUrl(),expectedURL);

    }
}
