package HW5.Pages;

import HW5.Data.ApplicationContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends Page {
    @FindBy(css="#box-campaign-products.box > .listing.products > article")
    private WebElement firstAvailableProduct;

    @FindBy(id="cart")
    private WebElement cartButton;

    public MainPage(ApplicationContext apContext) {
        super(apContext);
    }

    public MainPage open() {
        driver.get(baseUrl);
        return this;
    }

    public MainPage selectFirstAvailableProduct() {
        firstAvailableProduct.click();
        return this;
    }

    public MainPage clickOnCart() {
        cartButton.click();
        return this;
    }
}
