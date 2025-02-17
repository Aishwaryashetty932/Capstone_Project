package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;

    // Using different types of locators
    private By nameField = By.id("name");  
    private By countryField = By.name("country");  
    private By cityField = By.className("city-field");  
    private By cardField = By.cssSelector("input[placeholder='Credit card']");  
    private By monthField = By.xpath("//input[@id='month']");  
    private By yearField = By.tagName("input");  
    private By purchaseButton = By.xpath("//button[text()='Purchase']");  
    private By confirmationMessage = By.xpath("/html/body/div[10]/p");  

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public String completePurchase(String name, String country, String city, String card, String month, String year) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Fill in form using different locator strategies
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);  // ID
        wait.until(ExpectedConditions.visibilityOfElementLocated(countryField)).sendKeys(country);  // Name
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityField)).sendKeys(city);  // Class Name
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardField)).sendKeys(card);  // CSS Selector
        wait.until(ExpectedConditions.visibilityOfElementLocated(monthField)).sendKeys(month);  // XPath
        wait.until(ExpectedConditions.visibilityOfElementLocated(yearField)).sendKeys(year);  // Tag Name

        // Click on Purchase Button
        wait.until(ExpectedConditions.elementToBeClickable(purchaseButton)).click();

        // Wait for Confirmation Message
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage));

        // Extract Confirmation Message
        String confirmationText = driver.findElement(confirmationMessage).getText();
        System.out.println("Confirmation Message: " + confirmationText);

        return extractPurchaseID(confirmationText);
    }

    // Extract Purchase ID from confirmation text
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
