package HW5.Pages;

import HW5.Data.ApplicationContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CheckoutPage extends Page {
    private final String productsTableInCartSelector = "#box-checkout-cart tbody > tr";
    @FindBys( {
            @FindBy(css=productsTableInCartSelector)
    } )
    private List<WebElement> productsTableInCart;

    private By deleteFirstElementButtonBy = By.cssSelector("#box-checkout-cart tbody > tr:first-child > td:last-child > button");
    private By summaryTableBy = By.id("box-checkout-summary");

    private final String summaryTableID = "box-checkout-summary";
    @FindBy(id=summaryTableID)
    private WebElement summaryTable;

    @FindBy(css="#box-checkout > div.cart.wrapper em")
    private WebElement emptyCartText;

    @FindBy(css="#box-checkout > div.cart.wrapper a")
    private WebElement backButton;


    public CheckoutPage(ApplicationContext apContext){
        super(apContext);
    }

    public CheckoutPage open() {
        driver.get(baseUrl + "/checkout");
        return this;
    }

    public int getNumberOfProductsInCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(productsTableInCartSelector)));
        return productsTableInCart.size();
    }

    public WebElement getRemoveButtonOfFirstElement() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteFirstElementButtonBy));
        return driver.findElement(deleteFirstElementButtonBy);
    }

    public CheckoutPage removeFirstProductFromCart() {
        getRemoveButtonOfFirstElement().click();
        return this;
    }

    public CheckoutPage waitRefreshingTableWithProducts() {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(summaryTableBy)));
        return this;
    }

    public CheckoutPage waitUntilTableWithProductsDisappeared() {
        super.waitUntilInvisibilityOf(driver.findElement(summaryTableBy));
        return this;
    }

    public WebElement getEmptyCartMessage() {
        return wait.until(ExpectedConditions.elementToBeClickable(emptyCartText));
    }

    public CheckoutPage clickBackButton() {
        backButton.click();
        return this;
    }
}
