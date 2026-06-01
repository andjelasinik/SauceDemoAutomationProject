package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BaseTest {

    public CartPage() {
        PageFactory.initElements(driver, this);
    }


    @FindBy(id = "item_4_title_link")
    public WebElement itemInCartA;

    @FindBy(id = "item_0_title_link")
    public WebElement itemInCartB;

    @FindBy(id = "cart_list")
    public List<WebElement> cartList;

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(id = "remove-sauce-labs-backpack")
    public WebElement removeButtonA;

    @FindBy(id = "remove-sauce-labs-bike-light")
    public WebElement removeButtonB;


    //-----------------------------------

    public void clickOnContinueShoppingButton() {
        continueShoppingButton.click();
    }

    public void clickOnRemoveButtonA() {
        removeButtonA.click();
    }

    public void clickOnRemoveButtonB() {
        removeButtonB.click();
    }

    @FindBy(className = "shopping_cart_link")
    public WebElement cartIcon;
}
