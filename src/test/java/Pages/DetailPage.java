package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DetailPage extends BaseTest {

    public DetailPage() {
        PageFactory.initElements(driver, this);
    }


    @FindBy(className = "inventory_details")
    public WebElement itemDetails;


}
