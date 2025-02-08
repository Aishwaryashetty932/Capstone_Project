package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    WebDriver driver;

   
    private By addToCartButton = By.xpath("//a[text()='Add to cart']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public String addProductToCart(String productName) {
       
        By productLocator = By.xpath("//a[text()='" + productName + "']");
        driver.findElement(productLocator).click();
        
        driver.findElement(addToCartButton).click();
        
     
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(3)); 
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
       
        } catch (TimeoutException e) {
            
        }
        
        return productName;
    }
}
