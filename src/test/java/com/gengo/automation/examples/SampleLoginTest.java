package com.gengo.automation.examples;

/**
 * No imported packages should stay unused.
 * Otherwise, it is best to delete/remove them.
 */

import com.gengo.automation.global.AutomationBase;
import com.gengo.automation.helpers.Wait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @class A class sample creation of test case. Still subject for
 * observation whether it's more optimized and easier to read.
 */

public class SampleLoginTest extends AutomationBase {

    /**
     * Initiation of fields should always
     * be preceded by a newline after the declaration of class
     */
    private final String EXPECTED_PAGE_TITLE = "Customer area | Dashboard | Gengo";
    private final String LOGIN_EMAIL = "gengo.automationtest+c17@gmail.com";
    private Wait wait = new Wait(getDriver());

    public SampleLoginTest() throws IOException {}

    @AfterMethod
    public void afterRun() {

        // This method should always be placed after the `@BeforeMethod` annotated method.
        // It is best to put the longest method {@Test} at the last row.

        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    @Test(description = "Sample test case for logging in to staging.")
    public void loginToStaging() {

        // Case note :
        // If the account is not active, this sample test case will fail.

        pluginPage.passThisPage();
        homePage.clickSignInButton();
        wait.impWait(10);
        loginPage.loginAccount(LOGIN_EMAIL, var.getDefaultPassword());

        // When using the static `assertion` (assertTrue, assertFalse etc.) methods of TestNG Assert class,
        // It is best to put a second argument {@param message} in it to describe what went wrong upon failure.

        assertTrue(page.isTitle(EXPECTED_PAGE_TITLE), "The page title is wrong.");
    }
}
