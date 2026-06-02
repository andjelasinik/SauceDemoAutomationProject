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

    //Credentials
    String validUsername= "standard_user";
    String validPassword= "secret_sauce";
    String invalidUsername= "user";
    String invalidPassword= "not_secret_souce";

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
    }

    @Test(priority = 10)
    public void userCanLoginWithValidCredentials() {

        //Enter valid credentials
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();

        String expectedURL= "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

        //Verify successful login
        Assert.assertTrue(productPage.header.isDisplayed());
        Assert.assertTrue(productPage.header.getText().contains("Products"));
    }

    @Test(priority = 20)
    public void userCannotLoginWithInvalidUsername() {

        //Enter invalid username
        homePage.inputUsername(invalidUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();

        //Verify user stay on login page and error message is displayed
        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertTrue(homePage.errorMessage.isDisplayed());
        String expectedMessage= "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @Test(priority = 30)
    public void userCannotLoginWithInvalidPassword() {

        //Input invalid password
        homePage.inputUsername(validUsername);
        homePage.inputPassword(invalidPassword);
        homePage.clickOnSubmitButton();

        //Verify user stay on login page and error message is displayed
        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertTrue(homePage.errorMessage.isDisplayed());
        String expectedMessage= "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @Test(priority = 40)
    public void userCannotLoginWithEmptyUsernameField() {

        //Leave username field empty
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();

        //Verify user stay on login page and error message is displayed
        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertTrue(homePage.errorMessage.isDisplayed());
        String expectedMessage= "Epic sadface: Username is required";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @Test(priority = 45)
    public void userCannotLoginWithEmptyPasswordField() {

        //Leave password field empty
        homePage.inputUsername(validUsername);
        homePage.clickOnSubmitButton();

        //Verify user stay on login page and error message is displayed
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
