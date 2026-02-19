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
                                () -> Assertions.assertTrue(newsletterPage.isSuccessCardShown(),
                                                "Success card should be shown for valid email."),
                                () -> Assertions.assertFalse(newsletterPage.isSignupCardShown(),
                                                "Signup card should be hidden after success."),
                                () -> Assertions.assertEquals(validEmail.trim(), newsletterPage.getShownUserEmail(),
                                                "Displayed email should match trimmed input."));
        }

        @Tag("regression")
        @Order(2)
        @ParameterizedTest(name = "Invalid Email (JSON): {0}")
        @MethodSource("utils.JsonDataUtils#getInvalidEmails")
        void invalidEmails_showError_andInputGetsErrorClass(String invalidEmail) {

                newsletterPage.enterEmail(invalidEmail)
                                .clickSubscribeButton();

                Assertions.assertAll("Invalid Email Error Checks",
                                () -> Assertions.assertTrue(newsletterPage.isEmailErrorDisplayed(),
                                                "Error message should display for invalid email: " + invalidEmail),
                                () -> Assertions.assertTrue(newsletterPage.isEmailInputInErrorState(),
                                                "Email input should have 'error' class for: " + invalidEmail));

                if (invalidEmail.isEmpty()) {
                        // Specific assertion for empty email case if needed
                        Assertions.assertAll("Empty Email Specific Checks",
                                        () -> Assertions.assertTrue(newsletterPage.isSignupCardShown(),
                                                        "Signup card should remain visible."),
                                        () -> Assertions.assertFalse(newsletterPage.isSuccessCardShown(),
                                                        "Success card should remain hidden."));
                }
        }

        @Tag("regression")
        @Order(3)
        @Test
        void errorClearsWhenUserTypesAfterInvalidSubmit() {
                // Use a known invalid email from our data
                String badEmail = utils.JsonDataUtils.getSingleInvalidEmail();

                newsletterPage
                                .enterEmail(badEmail)
                                .clickSubscribeButton();

                Assertions.assertAll("Error Visible Before Typing",
                                () -> Assertions.assertTrue(newsletterPage.isEmailErrorDisplayed(),
                                                "Error should be visible after invalid submit."),
                                () -> Assertions.assertTrue(newsletterPage.isEmailInputInErrorState(),
                                                "Input should be in error state."));

                // Your JS hides error on any input event when error class is present
                newsletterPage.typeEmail("a");

                Assertions.assertAll("Error Hidden After Typing",
                                () -> Assertions.assertFalse(newsletterPage.isEmailErrorDisplayed(),
                                                "Error should hide when user starts typing."),
                                () -> Assertions.assertFalse(newsletterPage.isEmailInputInErrorState(),
                                                "Error class should be removed on input."));
        }

        @Tag("regression")
        @Order(4)
        @Test
        void dismissResetsToSignup_andClearsErrorState() {
                // use a valid email from our data
                String validStub = utils.JsonDataUtils.getSingleValidEmail();

                newsletterPage
                                .enterEmail(validStub)
                                .clickSubscribeButton();

                Assertions.assertTrue(newsletterPage.isSuccessCardShown(), "Success should be visible before dismiss.");
                newsletterPage.clickDismissButton();

                Assertions.assertAll("Dismiss State Reset",
                                () -> Assertions.assertTrue(newsletterPage.isSignupCardShown(),
                                                "Signup should be visible after dismiss."),
                                () -> Assertions.assertFalse(newsletterPage.isSuccessCardShown(),
                                                "Success should be hidden after dismiss."),
                                () -> Assertions.assertFalse(newsletterPage.isEmailErrorDisplayed(),
                                                "Error should be hidden after dismiss/reset."),
                                () -> Assertions.assertFalse(newsletterPage.isEmailInputInErrorState(),
                                                "Input should not have error class after dismiss."));
        }
}
