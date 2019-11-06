package HW5;

import org.junit.Assert;
import org.junit.Test;

public class HW5 extends TestBase {

    @Test
    public void addRemoveFromTheCart() {
        app.addNewProductsToCartWithSizes(app.getProductSizes());
        Assert.assertEquals("Add products to cart", app.getProductSizes().size(), app.getNumberOfProductsInCart());

        app.clickOnCart()
                .removeAllProductfromCart();
        Assert.assertEquals("'There are no items in your cart.' message appear", "There are no items in your cart.", app.getEmptyCartMessage());

        app.clickBackButton();
        Assert.assertEquals("Cart size is 0", 0, app.getNumberOfProductsInCart());
    }

}
