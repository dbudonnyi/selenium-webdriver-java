package HW5;

import HW5.Application.Application;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestBase {
    static Application app;

    @BeforeClass
    public static void setup(){
        WebDriverManager.chromedriver().setup();
        app = new Application();
    }

    @AfterClass
    public static void quit(){
        app.quit();
    }
}
