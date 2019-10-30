import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;


public class HW4 {
    private static EventFiringWebDriver driver;
    private static Wait wait;

    @BeforeClass
    public static void setup() throws MalformedURLException {
        WebDriverManager.chromedriver().setup();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("chrome");
        driver = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps));
        driver.register(new HW4EventListener());
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass
    public static void quit(){
        driver.quit();
    }

    @Test
    public void verifyThatLinksAreOpenedInNewWindow() {
        driver.get("http://demo.litecart.net/admin/");
        driver.findElement(By.cssSelector("button[name=login]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.center-block.img-responsive")));

        driver.findElement(By.cssSelector("#box-apps-menu > li[data-code=countries]")).click();
        driver.findElement(By.cssSelector("#content")).findElement(By.linkText("Add New Country")).click();

        List<WebElement> icons = driver.findElements(By.cssSelector("#content form[name=country_form] label > a > i.fa.fa-external-link"));
        String originalWindow = driver.getWindowHandle();
        for(WebElement icon : icons) {
            icon.click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            Set<String> existWs = driver.getWindowHandles();
            Assert.assertEquals("New window is opened", 2, existWs.size());
            existWs.remove(originalWindow);
            driver.switchTo().window(existWs.iterator().next());
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }
}
