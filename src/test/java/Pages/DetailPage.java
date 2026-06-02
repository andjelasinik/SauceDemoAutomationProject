package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DetailPage extends BaseTest {

    //Initialize page elements
    public DetailPage() {
        PageFactory.initElements(driver, this);
    }


    @FindBy(className = "inventory_details")
    public WebElement itemDetails;

    @FindBy(id = "inventory_sidebar_link")
    public WebElement allItemsButton;

    //-----------------------

    public void clickOnAllItemsButton() {
        allItemsButton.click();
    }


}
