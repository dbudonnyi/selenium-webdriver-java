package HW5.Data;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationContext {
    private WebDriver driver;
    private String baseUrl;
    private List<String> productSizes = new ArrayList<>();

    public ApplicationContext(WebDriver driver){
        this.driver = driver;
    }

    public ApplicationContext(){
    }

    public ApplicationContext setDriver(WebDriver driver){
        this.driver = driver;
        return this;
    }

    public WebDriver getDriver(){
        return this.driver;
    }

    public ApplicationContext setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        return this;
    }

    public String getBaseUrl(){
        return this.baseUrl;
    }

    public ApplicationContext addProductSize(String productSize){
        this.productSizes.add(productSize);
        return this;
    }

    public List<String> getProductSizes(){
        return this.productSizes;
    }
}
