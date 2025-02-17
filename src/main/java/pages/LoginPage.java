package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ReportManager;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;

    private By loginButton = By.id("login2");
    private By usernameField = By.id("loginusername");
    private By passwordField = By.id("loginpassword");
    private By submitButton = By.xpath("//button[text()='Log in']");
    private By welcomeMessage = By.id("nameofuser"); 
    private By logoutButton = By.id("logout2");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean login(String username, String password) {
        driver.findElement(loginButton).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        ReportManager.logInfo("Entered the User Credentials");
        driver.findElement(submitButton).click();

        // Wait for welcome message
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage));
            return driver.findElement(welcomeMessage).getText().contains(username);
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        driver.findElement(logoutButton).click();
    }

    public boolean isLoginButtonVisible() {
        return driver.findElement(loginButton).isDisplayed();
    }
}
