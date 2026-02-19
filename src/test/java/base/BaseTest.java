package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import driver.DriverFactory;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
