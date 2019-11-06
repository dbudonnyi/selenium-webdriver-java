package HW5.Pages;

import HW5.Data.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends Page {
    @FindBy(css="#box-product select[name=\"options[Size]\"]")
    private WebElement selectProductSize;

    @FindBy(css="#box-product button[name=add_cart_product]")
    private WebElement buttonAddProduct;

    private final String cartQuantitySelector = "#cart div.badge.quantity";
    @FindBy(css=cartQuantitySelector)
    private WebElement cartQuantity;

    public ProductPage(ApplicationContext apContext) {
        super(apContext);
    }

    public ProductPage addProductToCartWithSize(String productSize) {
        new Select(selectProductSize).selectByValue(productSize);
        int oldNumberOfProductsInCart = getNumberOfProductsInCart();
        buttonAddProduct.click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector(cartQuantitySelector), String.valueOf(oldNumberOfProductsInCart + 1)));
        return this;
    }

    public int getNumberOfProductsInCart() {
        String numOfProducts = cartQuantity.getText();
        return StringUtils.isNotBlank(numOfProducts) ? Integer.parseInt(numOfProducts) : 0;
    }
}
