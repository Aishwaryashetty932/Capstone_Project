package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    WebDriver driver;

    private By cartButton = By.id("cartur");
    private By placeOrderButton = By.xpath("//button[text()='Place Order']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToCartAndCheckout() {
        driver.findElement(cartButton).click();
        try {
            Thread.sleep(3000);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(placeOrderButton).click();
    }
}
