package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.NewsletterPage;

public class NewsletterSignupTest extends BaseTest {

    @Test
    void newsletterSignupShowsSuccessMessage() {
        String baseUrl = System.getProperty("baseUrl", "https://newsletter-sign-up-form-rust-eight.vercel.app/");

        NewsletterPage page = new NewsletterPage(driver)
                .open(baseUrl)
                .enterEmail("pekyi.fred@example.com")
                .submit();

        String msg = page.successMessageText();
        Assertions.assertTrue(msg != null && !msg.isBlank(), "Success message should be shown.");
    }
}
