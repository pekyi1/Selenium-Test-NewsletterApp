package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

import java.time.Duration;

public class NewsletterPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

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
        this.waitUtils = new WaitUtils(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public NewsletterPage open(String url) {
        driver.get(url); //opens a new browser with the url
        return this;
    }

    public NewsletterPage enterEmail(String email) {
        waitUtils.waitForVisibility(emailInput).clear();
        emailInput.sendKeys(email);
        return this;
    }

    public NewsletterPage clickSubscribeButton() {
        waitUtils.safeClick(subscribeButton);
        return this;
    }

    public NewsletterPage typeEmail(String text) {
        // for “error clears on input” test
        waitUtils.waitForVisibility(emailInput).sendKeys(text);
        return this;
    }

    public NewsletterPage clickDismissButton() {
        waitUtils.safeClick(dismissButton);
        return this;
    }

    // ---------- Assertions helpers ----------
    public boolean isEmailErrorDisplayed() {
        // error is controlled via style.display, so visibilityOf works
        return waitUtils.isElementVisible(emailError, Duration.ofSeconds(3));
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
        return waitUtils.waitForVisibility(userEmailSpan).getText();
    }
}
