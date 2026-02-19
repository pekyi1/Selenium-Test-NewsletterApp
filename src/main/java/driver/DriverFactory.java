package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver createDriver() {
       // WebDriverManager.chromedriver().setup();

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        ChromeOptions options = new ChromeOptions();

//        if (headless) {
//            // Headless requires explicit window size
//            options.addArguments("--headless=new");
//            options.addArguments("--window-size=1920,1080");
//        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        // Maximize only works in non-headless mode
        if (!headless) {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        return driver;
    }
}
