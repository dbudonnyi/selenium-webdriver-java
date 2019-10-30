import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class HW4EventListener extends AbstractWebDriverEventListener {

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        System.out.println("[Before Find] locator: " + by.toString());
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        System.out.println("[After Find] locator: " + by.toString());
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        System.out.println("[Exception]: " + throwable.getMessage().split(":")[0]);

        File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = Paths.get(System.getProperty("user.dir"), "screenshots", System.currentTimeMillis() + "_screen.png").toString();
        try {
            File screenshotFile = new File(screenshotPath);
            screenshotFile.getParentFile().mkdirs();
            Files.copy(tempFile, screenshotFile);
            System.out.println("Screenshot captured to: " + screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
