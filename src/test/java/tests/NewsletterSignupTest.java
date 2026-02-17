package tests;

import org.junit.jupiter.api.*;
import pages.NewsletterPage;

public class NewsletterSignupTest extends BaseTest {

    private String url;

    @BeforeEach
    void getUrl() {
        url = System.getProperty("baseUrl");
        Assertions.assertNotNull(url, "Set -DbaseUrl=<hosted url> in IntelliJ/CI.");
    }

    @Test
    void validEmail_showsSuccessCard_andDisplaysTrimmedEmail() {
        NewsletterPage page = new NewsletterPage(driver)
                .open(url)
                .setEmail("  fred.pekyi@example.com  ")
                .submit();

        Assertions.assertTrue(page.isSuccessCardShown(), "Success card should be shown for valid email.");
        Assertions.assertFalse(page.isSignupCardShown(), "Signup card should be hidden after success.");
        Assertions.assertEquals("fred.pekyi@example.com", page.getShownUserEmail(),
                "Displayed email should match trimmed input.");
    }

    @Test
    void emptyEmail_showsError_andEmailInputGetsErrorClass() {
        NewsletterPage page = new NewsletterPage(driver)
                .open(url)
                .setEmail("")
                .submit();

        Assertions.assertTrue(page.isEmailErrorDisplayed(), "Error message should display for empty email.");
        Assertions.assertTrue(page.isEmailInputInErrorState(), "Email input should have 'error' class.");
        Assertions.assertTrue(page.isSignupCardShown(), "Signup card should remain visible on error.");
        Assertions.assertFalse(page.isSuccessCardShown(), "Success card should remain hidden on error.");
    }

    @Test
    void invalidEmailFormat_showsError() {
        NewsletterPage page = new NewsletterPage(driver)
                .open(url)
                .setEmail("fred.pekyi.example.com") // missing @
                .submit();

        Assertions.assertTrue(page.isEmailErrorDisplayed(), "Error message should display for invalid email.");
        Assertions.assertTrue(page.isEmailInputInErrorState(), "Email input should have 'error' class.");
    }

    @Test
    void emailWithoutDotDomain_showsError() {
        NewsletterPage page = new NewsletterPage(driver)
                .open(url)
                .setEmail("fred@company") // no .tld
                .submit();

        Assertions.assertTrue(page.isEmailErrorDisplayed(), "Error message should display for missing TLD.");
        Assertions.assertTrue(page.isEmailInputInErrorState(), "Email input should have 'error' class.");
    }

    @Test
    void errorClearsWhenUserTypesAfterInvalidSubmit() {
        NewsletterPage page = new NewsletterPage(driver)
                .open(url)
                .setEmail("bademail")
                .submit();

        Assertions.assertTrue(page.isEmailErrorDisplayed(), "Error should be visible after invalid submit.");
        Assertions.assertTrue(page.isEmailInputInErrorState(), "Input should be in error state.");

        // Your JS hides error on any input event when error class is present
        page.typeEmail("a");

        Assertions.assertFalse(page.isEmailErrorDisplayed(), "Error should hide when user starts typing.");
        Assertions.assertFalse(page.isEmailInputInErrorState(), "Error class should be removed on input.");
    }

    @Test
    void dismissResetsToSignup_andClearsErrorState() {
        NewsletterPage page = new NewsletterPage(driver)
                .open(url)
                .setEmail("fred.pekyi@example") //intentionally failed to test logs
                .submit();

        Assertions.assertTrue(page.isSuccessCardShown(), "Success should be visible before dismiss.");
        page.dismiss();

        Assertions.assertTrue(page.isSignupCardShown(), "Signup should be visible after dismiss.");
        Assertions.assertFalse(page.isSuccessCardShown(), "Success should be hidden after dismiss.");

        // After dismiss, error must be hidden and input not in error state
        Assertions.assertFalse(page.isEmailErrorDisplayed(), "Error should be hidden after dismiss/reset.");
        Assertions.assertFalse(page.isEmailInputInErrorState(), "Input should not have error class after dismiss.");
    }
}
