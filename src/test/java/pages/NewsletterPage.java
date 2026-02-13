package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewsletterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

//    @FindBy(id = "name")
//    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitBtn;

    @FindBy(css = ".success-card")
    private WebElement successMsg;

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public NewsletterPage open(String baseUrl) {
        driver.get(baseUrl);
        return this;
    }

//    public NewsletterPage enterName(String name) {
//        wait.until(ExpectedConditions.visibilityOf(nameInput)).clear();
//        nameInput.sendKeys(name);
//        return this;
//    }

    public NewsletterPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput)).clear();
        emailInput.sendKeys(email);
        return this;
    }

    public NewsletterPage submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        return this;
    }

    public String successMessageText() {
        return wait.until(ExpectedConditions.visibilityOf(successMsg)).getText();
    }
}
