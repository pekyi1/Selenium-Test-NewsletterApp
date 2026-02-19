package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsletterSignupTest extends BaseTest {


    @Tag("smoke")
    @Tag("regression")
    @Order(1)
    @ParameterizedTest(name = "Valid Email: {0}")
    @MethodSource("utils.JsonDataUtils#getValidEmails")
    void validEmail_showsSuccessCard_andDisplaysTrimmedEmail(String validEmail) {
            newsletterPage
                .enterEmail(validEmail)
                .clickSubscribeButton();

        Assertions.assertAll("Valid Email Success",
                () -> Assertions.assertTrue(newsletterPage.isSuccessCardShown(), "Success card should be shown for valid email."),
                () -> Assertions.assertFalse(newsletterPage.isSignupCardShown(), "Signup card should be hidden after success."),
                () -> Assertions.assertEquals(validEmail.trim(), newsletterPage.getShownUserEmail(),
                        "Displayed email should match trimmed input."));
    }
}

//    @Tag("regression")
//    @Order(2)
//    @ParameterizedTest(name = "Invalid Email (JSON): {0}")
//    @MethodSource("utils.JsonDataUtils#getInvalidEmails")
//    void invalidEmails_showError_andInputGetsErrorClass(String invalidEmail) {
//        NewsletterPage page = new NewsletterPage(driver)
//                .open(url)
//                .enterEmail(invalidEmail)
//                .clickSubscribeButton();
//
//        Assertions.assertAll("Invalid Email Error Checks",
//                () -> Assertions.assertTrue(page.isEmailErrorDisplayed(),
//                        "Error message should display for invalid email: " + invalidEmail),
//                () -> Assertions.assertTrue(page.isEmailInputInErrorState(),
//                        "Email input should have 'error' class for: " + invalidEmail));
//
//        if (invalidEmail.isEmpty()) {
//            // Specific assertion for empty email case if needed
//            Assertions.assertAll("Empty Email Specific Checks",
//                    () -> Assertions.assertTrue(page.isSignupCardShown(), "Signup card should remain visible."),
//                    () -> Assertions.assertFalse(page.isSuccessCardShown(), "Success card should remain hidden."));
//        }
//    }
//
//    @Tag("regression")
//    @Order(3)
//    @Test
//    void errorClearsWhenUserTypesAfterInvalidSubmit() {
//        // Use a known invalid email from our data
//        String badEmail = utils.JsonDataUtils.getSingleInvalidEmail();
//
//        NewsletterPage page = new NewsletterPage(driver)
//                .open(url)
//                .enterEmail(badEmail)
//                .clickSubscribeButton();
//
//        Assertions.assertAll("Error Visible Before Typing",
//                () -> Assertions.assertTrue(page.isEmailErrorDisplayed(),
//                        "Error should be visible after invalid submit."),
//                () -> Assertions.assertTrue(page.isEmailInputInErrorState(), "Input should be in error state."));
//
//        // Your JS hides error on any input event when error class is present
//        page.typeEmail("a");
//
//        Assertions.assertAll("Error Hidden After Typing",
//                () -> Assertions.assertFalse(page.isEmailErrorDisplayed(),
//                        "Error should hide when user starts typing."),
//                () -> Assertions.assertFalse(page.isEmailInputInErrorState(),
//                        "Error class should be removed on input."));
//    }
//
//    @Tag("regression")
//    @Order(4)
//    @Test
//    void dismissResetsToSignup_andClearsErrorState() {
//        // use a valid email from our data
//        String validStub = utils.JsonDataUtils.getSingleValidEmail();
//        NewsletterPage page = new NewsletterPage(driver)
//                .open(url)
//                .enterEmail(validStub)
//                .clickSubscribeButton();
//
//        Assertions.assertTrue(page.isSuccessCardShown(), "Success should be visible before dismiss.");
//        page.clickDismissButton();
//
//        Assertions.assertAll("Dismiss State Reset",
//                () -> Assertions.assertTrue(page.isSignupCardShown(), "Signup should be visible after dismiss."),
//                () -> Assertions.assertFalse(page.isSuccessCardShown(), "Success should be hidden after dismiss."),
//                () -> Assertions.assertFalse(page.isEmailErrorDisplayed(),
//                        "Error should be hidden after dismiss/reset."),
//                () -> Assertions.assertFalse(page.isEmailInputInErrorState(),
//                        "Input should not have error class after dismiss."));
//    }
//}
