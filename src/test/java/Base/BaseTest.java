package Base;

import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public static WebDriver driver;
    public WebDriverWait wait;

    // Shared objects used across all tests
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
