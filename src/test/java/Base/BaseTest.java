package Base;

import Pages.CartPage;
import Pages.DetailPage;
import Pages.LoginPage;
import Pages.ProductPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public static WebDriver driver;
    public WebDriverWait wait;
    public LoginPage homePage;
    public ProductPage productPage;
    public CartPage cartPage;
    public DetailPage detailPage;


    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

    }
}
