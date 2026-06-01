package Test;

import Base.BaseTest;
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

public class LogoutTest extends BaseTest {

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
    }

    @Test
    public void userCanLogout() throws InterruptedException {
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();
        productPage.clickOnSideMenu();
        wait.until(ExpectedConditions.elementToBeClickable(productPage.logoutButton));
        productPage.clickOnLogoutButton();

        Assert.assertTrue(homePage.loginForm.isDisplayed());
        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
