package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class NewsletterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit'].btn")
    private WebElement subscribeButton;

    @FindBy(id = "email-error")
    private WebElement emailError;

    @FindBy(id = "signup-card")
    private WebElement signupCard;

    @FindBy(id = "success-card")
    private WebElement successCard;

    @FindBy(id = "user-email")
    private WebElement userEmailSpan;

    @FindBy(id = "dismiss-btn")
    private WebElement dismissButton;

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public NewsletterPage open(String url) {
        driver.get(url);
        return this;
    }

    public NewsletterPage setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput)).clear();
        emailInput.sendKeys(email);
        return this;
    }

    public NewsletterPage submit() {
        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton)).click();
        return this;
    }

    public NewsletterPage typeEmail(String text) {
        // for “error clears on input” test
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(text);
        return this;
    }

    public NewsletterPage dismiss() {
        wait.until(ExpectedConditions.elementToBeClickable(dismissButton)).click();
        return this;
    }

    // ---------- Assertions helpers ----------
    public boolean isEmailErrorDisplayed() {
        // error is controlled via style.display, so visibilityOf works
        return waitVisible(emailError, Duration.ofSeconds(3));
    }

    public boolean isEmailInputInErrorState() {
        return emailInput.getAttribute("class").contains("error");
    }

    public boolean isSuccessCardShown() {
        return !successCard.getAttribute("class").contains("hidden");
    }

    public boolean isSignupCardShown() {
        return !signupCard.getAttribute("class").contains("hidden");
    }

    public String getShownUserEmail() {
        return wait.until(ExpectedConditions.visibilityOf(userEmailSpan)).getText();
    }

    private boolean waitVisible(WebElement el, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(el));
            return el.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
