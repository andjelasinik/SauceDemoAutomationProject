package Test;

import Base.BaseTest;
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


public class LoginTests extends BaseTest {

    //CREDENTIALS
    String validUsername= "standard_user";
    String validPassword= "secret_sauce";
    String invalidUsername= "user";
    String invalidPassword= "not_secret_souce";

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

    @Test(priority = 10)
    public void userCanLoginWithValidCredentials() throws InterruptedException {
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();

        String expectedURL= "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertTrue(productPage.header.isDisplayed());
        Assert.assertTrue(productPage.header.getText().contains("Products"));
    }

    @Test(priority = 20)
    public void userCannotLoginWithInvalidUsername() {
        homePage.inputUsername(invalidUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();

        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertTrue(homePage.errorMessage.isDisplayed());
        String expectedMessage= "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @Test(priority = 30)
    public void userCannotLoginWithInvalidPassword() {
        homePage.inputUsername(validUsername);
        homePage.inputPassword(invalidPassword);
        homePage.clickOnSubmitButton();

        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertTrue(homePage.errorMessage.isDisplayed());
        String expectedMessage= "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @Test(priority = 40)
    public void userCannotLoginWithEmptyPasswordField() {
        homePage.inputUsername(validUsername);
        homePage.clickOnSubmitButton();

        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertTrue(homePage.errorMessage.isDisplayed());
        String expectedMessage= "Epic sadface: Password is required";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
