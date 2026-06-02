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

    @FindBy(id = "add-to-cart")
    public WebElement addToCartButton;

    @FindBy(id = "remove")
    public WebElement removeButton;

    @FindBy(id = "react-burger-menu-btn")
    public WebElement sideMenu;

    @FindBy(id = "inventory_sidebar_link")
    public WebElement allItemsButton;

    //----------------------------------------

    public void clickOnAddToCartButton() {
        addToCartButton.click();
    }

    public void clickOnSideMenu() {
        sideMenu.click();
    }

    public void clickOnAllItemsButton() {
        allItemsButton.click();
    }


}
