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

        // login pre testova
        homePage.login(validUsername, validPassword);
    }


    @Test(priority = 10)
    public void userCanSortProductByPriceLowToHigh() {

        // biram sort LOW to HIGH
        productPage.clickOnSortDropdownMenu();
        productPage.clickOnLowToHighPrice();

        List<Double> actualPrices = new ArrayList<>();

        // uzimam cene sa ekrana i skidam $
        for (WebElement price : productPage.itemPrices) {
            String value = price.getText().replace("$", "");
            actualPrices.add(Double.parseDouble(value));
        }

        // kopiram listu da mogu da je sortiram
        List<Double> expectedSorted = new ArrayList<>(actualPrices);

        // sortiram od najmanje ka najvećoj
        Collections.sort(expectedSorted);

        // proveravam da li je UI isto tako sortirao
        Assert.assertEquals(actualPrices, expectedSorted);
    }

    @Test(priority = 20)
    public void userCanSortProductByPriceHighToLow() {

        // biram sort HIGH to LOW
        productPage.clickOnSortDropdownMenu();
        productPage.clickOnLHighToLowPrice();

        List<Double> actualPrices = new ArrayList<>();

        // uzimam cene sa ekrana
        for (WebElement price : productPage.itemPrices) {
            String value = price.getText().replace("$", "");
            actualPrices.add(Double.parseDouble(value));
        }

        // kopiram listu
        List<Double> expectedSorted = new ArrayList<>(actualPrices);

        // sortiram pa okrenem da bude od najveće ka najmanjoj
        Collections.sort(expectedSorted);
        Collections.reverse(expectedSorted);

        // proveravam da li je UI pravilno sortirao
        Assert.assertEquals(actualPrices, expectedSorted);
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

