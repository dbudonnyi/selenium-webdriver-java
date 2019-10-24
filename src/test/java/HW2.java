import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import java.util.Dictionary;
import java.util.Hashtable;

public class HW2 {
    private static WebDriver chDriver;
    private static WebDriver ffDriver;
    private static WebDriver oDriver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.operadriver().setup();
    }

    @AfterClass
    public static void quit(){
        if (chDriver != null){
            chDriver.quit();
        }
        if (ffDriver != null){
            ffDriver.quit();
        }
        if (oDriver != null){
            oDriver.quit();
        }
    }

    @Test
    public void chromeTest() {
        chDriver = new ChromeDriver();
        Dictionary expectedValues = new Hashtable() {};
        expectedValues.put("campaigns_color_main_page", "rgba(204, 0, 0, 1)");
        expectedValues.put("campaigns_color_item_page", "rgba(204, 0, 0, 1)");
        expectedValues.put("campaigns_font-weight", "700");
        expectedValues.put("regular_color_main_page", "rgba(51, 51, 51, 1)");
        expectedValues.put("regular_color_item_page", "rgba(51, 51, 51, 1)");
        expectedValues.put("regular_text-decoration", "line-through solid rgb(51, 51, 51)");
        mainTest(chDriver, expectedValues);
        chDriver.quit();
    }

    @Test
    public void firefoxTest() {
        ffDriver = new FirefoxDriver();
        Dictionary expectedValues = new Hashtable() {};
        expectedValues.put("campaigns_color_main_page", "rgb(204, 0, 0)");
        expectedValues.put("campaigns_color_item_page", "rgb(204, 0, 0)");
        expectedValues.put("campaigns_font-weight", "700");
        expectedValues.put("regular_color_main_page", "rgb(51, 51, 51)");
        expectedValues.put("regular_color_item_page", "rgb(51, 51, 51)");
        expectedValues.put("regular_text-decoration", "line-through");
        mainTest(ffDriver, expectedValues);
        ffDriver.quit();
    }

    @Test
    public void operaTest() {
        OperaOptions operaOptions = new OperaOptions();
        operaOptions.setBinary("C:\\Users\\Dbudonny\\AppData\\Local\\Programs\\Opera\\64.0.3417.73\\opera.exe");
        oDriver = new OperaDriver(operaOptions);

        Dictionary expectedValues = new Hashtable() {};
        expectedValues.put("campaigns_color_main_page", "rgba(204, 0, 0, 1)");
        expectedValues.put("campaigns_color_item_page", "rgba(204, 0, 0, 1)");
        expectedValues.put("campaigns_font-weight", "700");
        expectedValues.put("regular_color_main_page", "rgba(51, 51, 51, 1)");
        expectedValues.put("regular_color_item_page", "rgba(51, 51, 51, 1)");
        expectedValues.put("regular_text-decoration", "line-through solid rgb(51, 51, 51)");
        mainTest(oDriver, expectedValues);
        oDriver.quit();
    }

    private void mainTest(WebDriver driver, Dictionary expectedValues){
        driver.get("http://demo.litecart.net/");

        WebElement article_main_page = driver.findElement(By.cssSelector("#box-campaign-products.box > .listing.products > article"));
        String mainPagePriceCampaignsSelector = ".info > .price-wrapper > .campaign-price";
        String mainPagePriceRegularSelector = ".info > .price-wrapper > .regular-price";

        String mainPageName = article_main_page.findElement(By.cssSelector(".info > .name")).getText();
        String mainPagePriceCampaigns = article_main_page.findElement(By.cssSelector(mainPagePriceCampaignsSelector)).getText();
        String mainPagePriceRegular = article_main_page.findElement(By.cssSelector(mainPagePriceRegularSelector)).getText();

//        System.out.println("[Main Page] Campaigns price color: \t" + article_main_page.findElement(By.cssSelector(mainPagePriceCampaignsSelector)).getCssValue("color"));
//        System.out.println("[Main Page] Campaigns price font-weight: \t"+ article_main_page.findElement(By.cssSelector(mainPagePriceCampaignsSelector)).getCssValue("font-weight"));
//        System.out.println("[Main Page] Regular price color: \t" + article_main_page.findElement(By.cssSelector(mainPagePriceRegularSelector)).getCssValue("color"));
//        System.out.println("[Main Page] Regular price text-decoration: \t" + article_main_page.findElement(By.cssSelector(mainPagePriceRegularSelector)).getCssValue("text-decoration"));

        Assert.assertEquals("[Main Page] Campaigns price color", expectedValues.get("campaigns_color_main_page"), article_main_page.findElement(By.cssSelector(mainPagePriceCampaignsSelector)).getCssValue("color"));
        Assert.assertEquals("[Main Page] Campaigns price font-weight", expectedValues.get("campaigns_font-weight"), article_main_page.findElement(By.cssSelector(mainPagePriceCampaignsSelector)).getCssValue("font-weight"));
        Assert.assertEquals("[Main Page] Regular price color", expectedValues.get("regular_color_main_page"), article_main_page.findElement(By.cssSelector(mainPagePriceRegularSelector)).getCssValue("color"));
        Assert.assertEquals("[Main Page] Regular price text-decoration", expectedValues.get("regular_text-decoration"), article_main_page.findElement(By.cssSelector(mainPagePriceRegularSelector)).getCssValue("text-decoration"));

        article_main_page.click();

        WebElement article_item_page = driver.findElement(By.cssSelector("#box-product"));
        String itemPagePriceCampaignsSelector = ".buy_now .price-wrapper > .campaign-price";
        String itemPagePriceRegularSelector = ".buy_now .price-wrapper > .regular-price";

        Assert.assertEquals("Compare Product Name on both pages", mainPageName, article_item_page.findElement(By.cssSelector("div.col-sm-8.col-md-6 > h1")).getText());
        Assert.assertEquals("Compare Campaigns price on both pages", mainPagePriceCampaigns, article_item_page.findElement(By.cssSelector(itemPagePriceCampaignsSelector)).getText());
        Assert.assertEquals("Compare Regular price on both pages", mainPagePriceRegular, article_item_page.findElement(By.cssSelector(itemPagePriceRegularSelector)).getText());

//        System.out.println("[Item Page] Campaigns price color: \t" + article_item_page.findElement(By.cssSelector(itemPagePriceCampaignsSelector)).getCssValue("color"));
//        System.out.println("[Item Page] Campaigns price font-weight: \t"+ article_item_page.findElement(By.cssSelector(itemPagePriceCampaignsSelector)).getCssValue("font-weight"));
//        System.out.println("[Item Page] Regular price color: \t" + article_item_page.findElement(By.cssSelector(itemPagePriceRegularSelector)).getCssValue("color"));
//        System.out.println("[Item Page] Regular price text-decoration: \t" + article_item_page.findElement(By.cssSelector(itemPagePriceRegularSelector)).getCssValue("text-decoration"));

        Assert.assertEquals("[Item Page] Campaigns price color", expectedValues.get("campaigns_color_item_page"), article_item_page.findElement(By.cssSelector(itemPagePriceCampaignsSelector)).getCssValue("color"));
        Assert.assertEquals("[Item Page] Campaigns price font-weight", expectedValues.get("campaigns_font-weight"), article_item_page.findElement(By.cssSelector(itemPagePriceCampaignsSelector)).getCssValue("font-weight"));
        Assert.assertEquals("[Item Page] Regular price color", expectedValues.get("regular_color_item_page"), article_item_page.findElement(By.cssSelector(itemPagePriceRegularSelector)).getCssValue("color"));
        Assert.assertEquals("[Item Page] Regular price text-decoration", expectedValues.get("regular_text-decoration"), article_item_page.findElement(By.cssSelector(itemPagePriceRegularSelector)).getCssValue("text-decoration"));
    }
}
