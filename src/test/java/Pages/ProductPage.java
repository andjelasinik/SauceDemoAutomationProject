package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.openqa.selenium.support.PageFactory.initElements;

public class ProductPage extends BaseTest {

    //Initialize page elements
    public ProductPage() {
        initElements(driver, this);
    }

    @FindBy(id = "header_container")
    public WebElement header;

    @FindBy(className = "shopping_cart_link")
    public WebElement cartIcon;

    @FindBy(id = "react-burger-menu-btn")
    public WebElement sideMenu;

    @FindBy(id = "logout_sidebar_link")
    public WebElement logoutButton;

    @FindBy(id = "item_0_title_link")
    public List<WebElement> item;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    public WebElement addToCartButtonA;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    public WebElement addToCartButtonB;

    @FindBy(id = "remove-sauce-labs-backpack")
    public WebElement removeButtonA;

    @FindBy(id = "remove-sauce-labs-bike-light")
    public WebElement removeButtonB;

    @FindBy(className = "product_sort_container")
    public WebElement sortDropdownMenu;

    @FindBy(css = "option[value='lohi']")
    public WebElement lowToHighPrice;

    @FindBy(css = "option[value='hilo']")
    public WebElement highToLowPrice;

    @FindBy(className = "inventory_item_price")
    public List<WebElement> itemPrices;

    @FindBy(id = "inventory_sidebar_link")
    public WebElement allItemsButton;

    @FindBy(id = "reset_sidebar_link")
    public WebElement resetAppStateButton;


    //----------------------------------------------

    public void clickOnSideMenu() {
        sideMenu.click();
    }

    public void clickOnLogoutButton() {
        logoutButton.click();
    }

    public void clickOnAItem(String itemName) {
        for (int i = 0; i < item.size(); i++) {
            if (item.get(i).getText().equals(itemName)) {
                item.get(i).click();
                break;
            }
        }
    }

    public void clickOnAddToCartA() {
        addToCartButtonA.click();
    }

    public void clickOnAddToCartB() {
        addToCartButtonB.click();
    }

    public void clickOnCartIcon() {
        cartIcon.click();
    }

    public void clickOnRemoveButtonA(){
        removeButtonA.click();
    }

    public void clickOnRemoveButtonB(){
        removeButtonB.click();
    }

    public void clickOnSortDropdownMenu(){
        sortDropdownMenu.click();
    }

    public void clickOnLowToHighPrice(){
        lowToHighPrice.click();
    }

    public void clickOnLHighToLowPrice(){
        highToLowPrice.click();
    }


    public void clickOnAllItemsButton() {
        allItemsButton.click();
    }

    public void clickOnResetAppStateButton(){
        resetAppStateButton.click();
    }
}
