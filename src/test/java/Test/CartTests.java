package Test;

import Base.BaseTest;
import Pages.CartPage;
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

public class CartTests extends BaseTest {

    // kredencijali za login (koristim validnog usera sa sajta)
    String validUsername= "standard_user";
    String validPassword= "secret_sauce";

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

    @Test (priority = 10)
    public void userCanAddProductToCart(){

        // otvaram cart da proverim da li je prazan na početku
        productPage.clickOnCartIcon();

        // proveravam da nema proizvoda u cart-u
        Assert.assertEquals(cartPage.cartList.size(), 0);

        // vraćam se nazad na products
        cartPage.clickOnContinueShoppingButton();

        // dodajem prvi proizvod u cart
        productPage.clickOnAddToCartA();

        // proveravam da se cart broj povećao na 1
        Assert.assertTrue(productPage.cartIcon.getText().contains("1"));

        // dodajem drugi proizvod
        productPage.clickOnAddToCartB();

        // proveravam da se cart broj povećao na 2
        Assert.assertTrue(productPage.cartIcon.getText().contains("2"));

        // proveravam da su se dugmad promenila u Remove
        Assert.assertTrue(productPage.removeButtonA.getText().contains("Remove"));
        Assert.assertTrue(productPage.removeButtonB.getText().contains("Remove"));

        // otvaram cart i proveravam da su proizvodi dodati
        productPage.clickOnCartIcon();

        Assert.assertEquals(cartPage.itemInCartA.getText(), "Sauce Labs Backpack");
        Assert.assertEquals(cartPage.itemInCartB.getText(), "Sauce Labs Bike Light");
    }

    @Test(priority = 20)
    public void userCanRemoveProductsFromProductPage(){

        // dodajem dva proizvoda u cart
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();

        // brišem prvi proizvod direktno sa products stranice
        productPage.clickOnRemoveButtonA();

        // proveravam da se broj u cart-u smanjio
        Assert.assertTrue(productPage.cartIcon.getText().contains("1"));

        // brišem drugi proizvod
        productPage.clickOnRemoveButtonB();

        // proveravam da je cart prazan
        Assert.assertFalse(productPage.cartIcon.getText().contains("1"));

        // otvaram cart i proveravam da nema proizvoda
        productPage.clickOnCartIcon();
        Assert.assertEquals(cartPage.cartList.size(), 0);
    }

    @Test(priority = 30)
    public void userCanRemoveProductsFromCart(){

        // dodajem proizvode
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();

        // ulazim u cart
        productPage.clickOnCartIcon();

        // proveravam da su oba proizvoda u cart-u
        Assert.assertTrue(cartPage.cartIcon.getText().contains("2"));

        // brišem prvi proizvod iz cart-a
        cartPage.clickOnRemoveButtonA();

        // proveravam da je ostao 1 proizvod
        Assert.assertTrue(cartPage.cartIcon.getText().contains("1"));

        // brišem drugi proizvod
        cartPage.clickOnRemoveButtonB();

        // proveravam da je cart prazan
        Assert.assertFalse(cartPage.cartIcon.getText().contains("1"));
        Assert.assertEquals(cartPage.cartList.size(), 0);
    }

    @Test(priority = 40)
    public void userCanContinueShoppingFromCart(){

        // dodajem proizvode u cart
        productPage.clickOnAddToCartA();
        productPage.clickOnAddToCartB();

        // ulazim u cart
        productPage.clickOnCartIcon();

        // klik na continue shopping
        cartPage.clickOnContinueShoppingButton();

        // proveravam da sam vraćena na products stranicu
        String expectedURL="https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(),expectedURL);

        // proveravam da je products header vidljiv
        Assert.assertTrue(productPage.header.getText().contains("Products"));
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}


