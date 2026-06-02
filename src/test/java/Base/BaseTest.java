package Base;

import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    // WebDriver koristim da bih kontrolisala browser (Chrome)
    // static znači da ga dijele svi testovi
    public static WebDriver driver;

    // WebDriverWait koristim za čekanje elemenata da budu vidljivi/klikabilni
    public WebDriverWait wait;

    // Page objekti (Page Object Model)
    // svaki page predstavlja jednu stranicu na sajtu
    public LoginPage homePage;
    public ProductPage productPage;
    public CartPage cartPage;
    public DetailPage detailPage;
    public CheckoutPage checkoutPage;
    public PaymentPage paymentPage;
    public ThankYouPage thankYouPage;


    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

    }
}
