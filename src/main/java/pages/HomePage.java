package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;

   
    @FindBy(xpath = "//a[text()='Phones']")
    private WebElement mobilesCategory;

    @FindBy(xpath = "//a[text()='Laptops']")
    private WebElement laptopsCategory;

    @FindBy(xpath = "//a[text()='Monitors']")
    private WebElement monitorsCategory;

    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  
    }

    public String selectCategory(String category) {
        String selectedCategory = "";

        switch (category) {
            case "Phones":
                mobilesCategory.click();
                selectedCategory = "Phones";
                break;
            case "Laptops":
                laptopsCategory.click();
                selectedCategory = "Laptops";
                break;
            case "Monitors":
                monitorsCategory.click();
                selectedCategory = "Monitors";
                break;
            default:
                System.out.println("Category not found!");
        }

        return selectedCategory;
    }
}
