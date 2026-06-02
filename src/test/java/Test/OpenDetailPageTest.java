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
        cartPage = new CartPage();
        detailPage = new DetailPage();

        // login pre testova
        homePage.login(validUsername, validPassword);
    }


    @Test
    public void userCanOpenProductDetailPage() {
        // klik na jedan proizvod da otvorim detail page
        productPage.clickOnAItem("Sauce Labs Bike Light");

        // proveravam da li se otvorio detail page (da li je element vidljiv)
        Assert.assertTrue(detailPage.itemDetails.isDisplayed());

        // proveravam da li sam na tačnom URL-u za taj proizvod
        String expectedURL = "https://www.saucedemo.com/inventory-item.html?id=0";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

    }
}
