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

    // kredencijali za login (koristim validnog usera sa sajta)
    String validUsername = "standard_user";
    String validPassword = "secret_sauce";

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

        // login pre testova
        homePage.login(validUsername, validPassword);
    }

    @Test
    public void userCanLogout() throws InterruptedException {

        // otvaram meni sa leve strane
        productPage.clickOnSideMenu();

        // čekam da logout dugme bude klikabilno
        wait.until(ExpectedConditions.elementToBeClickable(productPage.logoutButton));

        // klik na logout
        productPage.clickOnLogoutButton();

        // proveravam da sam se vratila na login stranicu
        Assert.assertTrue(homePage.loginForm.isDisplayed());

        // proveravam URL posle logout-a
        String expectedURL = "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
