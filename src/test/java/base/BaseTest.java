package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import driver.DriverFactory;
import pages.NewsletterPage;

public abstract class BaseTest {

    protected WebDriver driver;
    private String url;

    protected NewsletterPage newsletterPage;

    protected String getUrl() {
        url = System.getProperty("baseUrl",
                System.getenv().getOrDefault("APP_BASE_URL",
                        "https://newsletter-sign-up-form-rust-eight.vercel.app/index.html"));
        Assertions.assertNotNull(url);

        return url;
    }

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(getUrl());
        newsletterPage = new NewsletterPage(driver);
    }


    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
