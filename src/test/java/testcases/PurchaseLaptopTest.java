package testcases;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.ExcelReader;
import utils.ScreenshotUtil;
import utils.ReportManager;

import java.io.IOException;

public class PurchaseLaptopTest extends BaseClass {

    @BeforeClass
    @Parameters("browser")//parameter browser
    public void setup(String browser) throws IOException {
        openBrowser(browser);  
        driver.get(prop.getProperty("url"));  
        ReportManager.startTest("Purchase Laptop Test");  
        ReportManager.logInfo("Browser launched successfully");
        ReportManager.logInfo("Navigated to the URL: " + prop.getProperty("url"));
    }

    @Test
    public void purchaseLaptopTest() throws Exception {
        String[] credentials = ExcelReader.readLoginDetails();
        String username = credentials[0];
        String password = credentials[1];

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        try {
            // Step 1: Login and Validate
            boolean loginSuccess = loginPage.login(username, password);
            ScreenshotUtil.takeScreenshot(driver, "Login_Success");
            Assert.assertTrue(loginSuccess, "Login Failed: 'Welcome username' not displayed.");
            ReportManager.logPass("Successfully logged in as: " + username);
        } catch (Exception e) {
            ReportManager.logFail("Login Failed: " + e.getMessage());
            throw e;
        }

        try {
            // Step 2: Select Category as Laptops
            String selectedCategory = homePage.selectCategory("Laptops");
            ScreenshotUtil.takeScreenshot(driver, "Category_Selected");
            ReportManager.logPass("Selected Category: " + selectedCategory);
        } catch (Exception e) {
            ReportManager.logFail("Failed to select category 'Laptops': " + e.getMessage());
            throw e;
        }

        try {
            // Step 3: Select Product MacBook Air
            String selectedProduct = productPage.addProductToCart("MacBook air");
            ScreenshotUtil.takeScreenshot(driver, "Product_Added");
            ReportManager.logInfo(selectedProduct + " added to cart");
            ReportManager.logPass("Successfully added the Product to Cart");
        } catch (Exception e) {
            ReportManager.logFail("Failed to add product to cart: " + e.getMessage());
            throw e;
        }

        try {
            // Step 4: Go to Cart and Checkout
            cartPage.goToCartAndCheckout();
            ScreenshotUtil.takeScreenshot(driver, "Cart_Opened");
            ReportManager.logPass("Proceeded to Cart and Checkout");
        } catch (Exception e) {
            ReportManager.logFail("Failed to open cart or proceed to checkout: " + e.getMessage());
            throw e;
        }

        try {
            // Step 5: Complete Purchase
            String purchaseID = checkoutPage.completePurchase("Aishwarya", "India", "Bangalore", "123456789", "05", "2026");
            ScreenshotUtil.takeScreenshot(driver, "Purchase_Completed");
            ReportManager.logPurchaseID(purchaseID);
            ReportManager.logPass("Successfully purchased the product");
        } catch (Exception e) {
            ReportManager.logFail("Failed to complete purchase: " + e.getMessage());
            throw e;
        }
    }

    @AfterClass
    public void tearDown() throws InterruptedException, IOException {
        ReportManager.endTest();  
        closeBrowser();  
    }
}
