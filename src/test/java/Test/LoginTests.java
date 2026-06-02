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

    // kredencijali za login testove (validni i nevalidni)
    String validUsername= "standard_user";
    String validPassword= "secret_sauce";
    String invalidUsername= "user";
    String invalidPassword= "not_secret_souce";

    @BeforeMethod
    public void pageSetUp() {
        ChromeOptions options = new ChromeOptions();

        // podešavam Chrome pre nego što se pokrene test
        // koristim ChromeOptions da bih kontrolisala ponašanje browsera

        // otvara Chrome u incognito modu
        // da nemam cookies, cache i prethodne sesije koje mogu da utiču na test
        options.addArguments("--incognito");

        // isključuje notifikacije u browseru
        // da popup "allow notifications" ne blokira elemente i klikove
        options.addArguments("--disable-notifications");

        // gasi blokiranje popup prozora
        // da Selenium može da vidi i radi sa popupovima ako se pojave
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        // otvaram sajt
        driver.navigate().to("https://www.saucedemo.com/");

        // pravim page objekte
        homePage = new LoginPage();
        productPage = new ProductPage();
    }

    @Test(priority = 10)
    public void userCanLoginWithValidCredentials() {

        // unos validnih kredencijala i login
        homePage.inputUsername(validUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();

        // proveravam da sam prebačena na products stranicu
        String expectedURL= "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

        // proveravam da se vidi products header
        Assert.assertTrue(productPage.header.isDisplayed());
        Assert.assertTrue(productPage.header.getText().contains("Products"));
    }

    @Test(priority = 20)
    public void userCannotLoginWithInvalidUsername() {

        // unos pogrešnog username-a
        homePage.inputUsername(invalidUsername);
        homePage.inputPassword(validPassword);
        homePage.clickOnSubmitButton();

        // proveravam da ostajem na login stranici
        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

        // proveravam da se prikazuje error poruka
        Assert.assertTrue(homePage.errorMessage.isDisplayed());

        // proveravam tačan tekst greške
        String expectedMessage= "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @Test(priority = 30)
    public void userCannotLoginWithInvalidPassword() {

        // unos pogrešnog password-a
        homePage.inputUsername(validUsername);
        homePage.inputPassword(invalidPassword);
        homePage.clickOnSubmitButton();

        // proveravam da login nije uspeo
        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

        Assert.assertTrue(homePage.errorMessage.isDisplayed());

        String expectedMessage= "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @Test(priority = 40)
    public void userCannotLoginWithEmptyPasswordField() {

        // unos samo username-a (password prazan)
        homePage.inputUsername(validUsername);
        homePage.clickOnSubmitButton();

        // proveravam da login nije uspeo
        String expectedURL= "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

        Assert.assertTrue(homePage.errorMessage.isDisplayed());

        // proveravam poruku za prazan password
        String expectedMessage= "Epic sadface: Password is required";
        Assert.assertEquals(homePage.errorMessage.getText(), expectedMessage);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
