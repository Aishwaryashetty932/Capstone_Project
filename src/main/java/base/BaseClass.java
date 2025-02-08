package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
    public static WebDriver driver;
    public static Properties prop;

    // Load configuration file
    public static void loadConfig() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("config.properties");
        prop.load(fis);
    }

    @Parameters("browser")
    public void openBrowser(String browser) throws IOException {
        loadConfig();
        //String browser = prop.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            System.out.println("Invalid browser name!");
            return;
        }

        int waitTime = Integer.parseInt(prop.getProperty("implicitWait", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
        driver.manage().window().maximize();
    }

    // Close browser
    public static void closeBrowser() throws InterruptedException {
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
        }
    }
}
