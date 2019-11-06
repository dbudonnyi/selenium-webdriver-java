package HW5.Pages;

import HW5.Data.ApplicationContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl;
    protected ApplicationContext appContext;

    public Page(ApplicationContext apContext) {
        appContext = apContext;

        this.driver = appContext.getDriver();
        PageFactory.initElements(this.driver, this);
        this.wait = new WebDriverWait(this.driver, 10);
        this.baseUrl = appContext.getBaseUrl();
    }

    public void waitUntilInvisibilityOf(WebElement elem) {
        this.wait.until(ExpectedConditions.invisibilityOf(elem));
    }
}

