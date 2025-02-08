package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;

    private By nameField = By.id("name");
    private By countryField = By.id("country");
    private By cityField = By.id("city");
    private By cardField = By.id("card");
    private By monthField = By.id("month");
    private By yearField = By.id("year");
    private By purchaseButton = By.xpath("//button[text()='Purchase']");
    private By confirmationMessage = By.xpath("/html/body/div[10]/p"); 

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public String completePurchase(String name, String country, String city, String card, String month, String year) throws InterruptedException {
    	Thread.sleep(1000);        
    	driver.findElement(nameField).sendKeys(name);
    	Thread.sleep(500);  
        driver.findElement(countryField).sendKeys(country);
        Thread.sleep(500);  
        driver.findElement(cityField).sendKeys(city);
        Thread.sleep(500);  
        driver.findElement(cardField).sendKeys(card);
        Thread.sleep(500);  
        driver.findElement(monthField).sendKeys(month);
        Thread.sleep(500);  
        driver.findElement(yearField).sendKeys(year);
        Thread.sleep(500);  
        driver.findElement(purchaseButton).click();

        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage));

        
        String confirmationText = driver.findElement(confirmationMessage).getText();
        System.out.println("Confirmation Message: " + confirmationText);

        
        return extractPurchaseID(confirmationText);
    }

    // Method to extract Purchase ID from confirmation text
    private String extractPurchaseID(String message) {
        if (message.contains("Id:")) {
            String[] lines = message.split("\n"); 
            for (String line : lines) {
                if (line.startsWith("Id:")) {
                    return line.split(":")[1].trim();
                }
            }
        }
        return "UNKNOWN"; 
    }
}
