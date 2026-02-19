package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver createDriver() {

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        ChromeOptions options = new ChromeOptions();

        if (headless) {
            // Headless requires explicit window size
            options.addArguments("--headless=new");
        }

        WebDriver driver = new ChromeDriver(options);

        // Maximize only works in non-headless mode
        if (!headless) {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        return driver;
    }
}
