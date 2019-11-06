package HW5.Application;

import HW5.Data.ApplicationContext;
import HW5.Pages.CheckoutPage;
import HW5.Pages.MainPage;
import HW5.Pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Application {
    protected WebDriver driver;
    protected CheckoutPage checkoutPage;
    protected MainPage mainPage;
    protected ProductPage productPage;
    protected ApplicationContext applicationContext;

    public Application(){
        applicationContext = new ApplicationContext()
                .setDriver(new ChromeDriver())
                .setBaseUrl("http://demo.litecart.net")
                .addProductSize("Small")
                .addProductSize("Medium")
                .addProductSize("Large");
        checkoutPage = new CheckoutPage(applicationContext);
        mainPage = new MainPage(applicationContext);
        productPage = new ProductPage(applicationContext);
    }

    public void quit(){
        applicationContext.getDriver().quit();
    }

    public Application addNewProductToCart(String productSize) {
        mainPage.open().selectFirstAvailableProduct();
        productPage.addProductToCartWithSize(productSize);
        return this;
    }

    public Application clickOnCart() {
        mainPage.clickOnCart();
        return this;
    }

    public Application addNewProductsToCartWithSizes(List<String> sizes) {
        for (String size : sizes) {
            addNewProductToCart(size);
        }
        return this;
    }

    public int getNumberOfProductsInCart() {
        return productPage.getNumberOfProductsInCart();
    }

    public Application removeAllProductfromCart() {
        int numberOfProductsInCart = checkoutPage.getNumberOfProductsInCart();

        for (int i = 0; i < numberOfProductsInCart; i++) {
            WebElement removeButton = checkoutPage.getRemoveButtonOfFirstElement();
            checkoutPage.removeFirstProductFromCart();

            if (i < numberOfProductsInCart - 1) {
                checkoutPage.waitRefreshingTableWithProducts();
            } else {
                checkoutPage.waitUntilTableWithProductsDisappeared();
            }
            checkoutPage.waitUntilInvisibilityOf(removeButton);
        }
        return this;
    }

    public String getEmptyCartMessage() {
        return checkoutPage.getEmptyCartMessage().getText();
    }

    public Application clickBackButton() {
        checkoutPage.clickBackButton();
        return this;
    }

    public List<String> getProductSizes() {
        return applicationContext.getProductSizes();
    }
}
