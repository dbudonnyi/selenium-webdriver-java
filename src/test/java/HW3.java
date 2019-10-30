import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HW3 {
    private static WebDriver driver;
    private static Wait wait;

    @BeforeClass
    public static void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass
    public static void quit(){
        driver.quit();
    }

    @Test
    public void createNewItem() {
        driver.get("http://demo.litecart.net/admin/");
        driver.findElement(By.cssSelector("button[name=login]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.center-block.img-responsive")));

        String uuid = UUID.randomUUID().toString();
        String carName = "Police Car " + uuid;

        driver.findElement(By.cssSelector("#box-apps-menu > li[data-code=catalog]")).click();
        driver.findElement(By.cssSelector("#box-apps-menu > li[data-code=catalog] li[data-code=catalog]")).click();
        driver.findElement(By.cssSelector("#content")).findElement(By.linkText("Add New Product")).click();

        driver.findElement(By.cssSelector("#content ul.nav.nav-tabs a[href*=\"#tab-general\"]")).click();
        driver.findElement(By.cssSelector("#tab-general input[type=radio][name=status][value=\"1\"]")).findElement(By.xpath("./..")).click();
        driver.findElement(By.cssSelector("#tab-general input[type=text][name=\"name[en]\"]")).sendKeys(carName);
        driver.findElement(By.cssSelector("#tab-general input[type=text][name=code]")).sendKeys(uuid);
        Select manufacturers = new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]")));
        manufacturers.selectByVisibleText("ACME Corp.");
        driver.findElement(By.cssSelector("#tab-general input[type=date][name=date_valid_from]")).sendKeys(Keys.HOME + "20102019");
        driver.findElement(By.cssSelector("#tab-general input[type=date][name=date_valid_to]")).sendKeys(Keys.HOME + "20102021");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("car.jpg").getFile());
        driver.findElement(By.cssSelector("#tab-general input[type=file][name=\"new_images[]\"]")).sendKeys(file.getAbsolutePath());

        driver.findElement(By.cssSelector("#content ul.nav.nav-tabs a[href*=\"#tab-information\"]")).click();
        driver.findElement(By.cssSelector("#tab-information a[href*=\"#en\"][data-toggle=tab]")).click();
        driver.findElement(By.cssSelector("#en input[type=text][name=\"short_description[en]\"]")).sendKeys("Police car");
        driver.findElement(By.cssSelector("#en textarea[name=\"description[en]\"]")).sendKeys("Great Police car");
        driver.findElement(By.cssSelector("#en textarea[name=\"technical_data[en]\"]")).sendKeys("Technical data");
        driver.findElement(By.cssSelector("#en input[type=text][name=\"head_title[en]\"]")).sendKeys("POLICE CAR");
        driver.findElement(By.cssSelector("#en input[type=text][name=\"meta_description[en]\"]")).sendKeys("New police car");

        driver.findElement(By.cssSelector("#content ul.nav.nav-tabs a[href*=\"#tab-prices\"]")).click();
        driver.findElement(By.cssSelector("#prices input[type=number][name=purchase_price]")).clear();
        driver.findElement(By.cssSelector("#prices input[type=number][name=purchase_price]")).sendKeys("10");
        Select currency = new Select(driver.findElement(By.cssSelector("#prices select[name=purchase_price_currency_code]")));
        currency.selectByValue("USD");
        driver.findElement(By.cssSelector("#prices input[type=number][name=\"prices[USD]\"]")).sendKeys("15");
        driver.findElement(By.cssSelector("#content button[type=submit][name=save]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content table.table.table-striped.table-hover.data-table > tbody > :last-child > :nth-child(3) > a")));
        Assert.assertEquals("Item was created", carName, driver.findElement(By.cssSelector("#content table.table.table-striped.table-hover.data-table > tbody > :last-child > :nth-child(3) > a")).getText());
    }

    @Test
    public void addRemoveFromTheCart() {
        List<String> sizes = Arrays.asList("Small", "Medium", "Large");
        for (int i = 0; i < 3; i++) {
            driver.get("http://demo.litecart.net/");
            driver.findElement(By.cssSelector("#box-campaign-products.box > .listing.products > article")).click();
            Select sizeSelect = new Select(driver.findElement(By.cssSelector("#box-product select[name=\"options[Size]\"]")));
            sizeSelect.selectByValue(sizes.get(i));
            driver.findElement(By.cssSelector("#box-product button[name=add_cart_product]")).click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector("#cart div.badge.quantity"), String.valueOf(i + 1)));
        }

        driver.findElement(By.id("cart")).click();

        for (int i = 0; i < 3; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#box-checkout-cart tbody > tr:first-child > td:last-child > button")));
            WebElement removeButton = driver.findElement(By.cssSelector("#box-checkout-cart tbody > tr:last-child > td:last-child > button"));
            removeButton.click();
            if (i != 2) {
                wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.id("box-checkout-summary"))));
            }
            wait.until(ExpectedConditions.invisibilityOf(removeButton));
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-checkout > div.cart.wrapper em")));
        Assert.assertEquals("'There are no items in your cart.' message appear", "There are no items in your cart.", driver.findElement(By.cssSelector("#box-checkout > div.cart.wrapper em")).getText());

        driver.findElement(By.cssSelector("#box-checkout > div.cart.wrapper a")).click();
        Assert.assertEquals("Cart is empty", "", driver.findElement(By.cssSelector("#cart div.badge.quantity")).getText());
    }
}
