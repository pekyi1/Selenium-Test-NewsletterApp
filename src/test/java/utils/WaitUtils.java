package utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private final WebDriverWait wait;
    private final WebDriver driver;

    public WaitUtils(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    public WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public String waitForText(WebElement element) {
        return waitForVisibility(element).getText();
    }

    public boolean isElementVisible(WebElement element, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Safe Click with Retry Logic (Handles ElementClickInterceptedException)
     * Principle: Handle Exceptions
     */
    public void safeClick(WebElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForClickability(element).click();
                return;
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                // Wait a bit and retry
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                attempts++;
            }
        }
        throw new RuntimeException("Failed to click element after 3 attempts");
    }

    /**
     * Wait for dynamic element with specific text
     * Principle: Handle Dynamic Elements
     */
    public WebElement waitForElementWithText(org.openqa.selenium.By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text))
                ? driver.findElement(locator)
                : null;
    }
}
