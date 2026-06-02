package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.PageFactory.initElements;

public class ThankYouPage extends BaseTest {

    //Initialize page elements
    public ThankYouPage() {
        initElements(driver, this);
    }

    @FindBy(id = "checkout_complete_container")
    public WebElement thankYouMessage;

    @FindBy(className = "title")
    public WebElement completeMessage;

}
