package com.gengo.automation.tests.Admin;

import com.gengo.automation.global.AutomationBase;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @case Edit Job Title
 * Word to Character, Stripe Payment, Standard Tier, Single Job
 * @reference https://gengo.testrail.com/index.php?/cases/view/18297
 */
public class C18297_1_EditJobTitle extends AutomationBase {

    private String parentWindow, jobNo, excerpt, collectionId, jobURL, translatedItem, newExcerpt;
    private String[] itemToTranslate, unitCount ;

    @BeforeMethod
    public void initFields() throws IOException {
        excerpt = var.getExcerptEnglish(17);
        itemToTranslate = new String[] {
               var.getItemToTranslate(17)
           };
        unitCount = new String[] {
                var.getUnitCountSource(17)
        };
        translatedItem = var.getItemToTranslate(17);
        newExcerpt = "test " + globalMethods.uniqueIdentifier();
    }

    public C18297_1_EditJobTitle() throws IOException {}

    @AfterClass
    public void afterRun() {
        Reporter.log("Done running '" + this.getClass().getSimpleName() + "'", true);
    }

    /**
     * placeAnOrder --- a method for checking the logging in of a customer account,
     * placing an order, and payment process
     * */
    @Test
    public void placeAnOrder() {
        pluginPage.passThisPage();

        // Check if the prominent page elements can be seen on the Home Page
        assertTrue(homePage.checkHomePage());
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Logging in a customer account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());

        // Ensure that the account type is set to 'Customer'
        global.selectCustomer();

        // Check if the prominent page elements can be seen on the Customer Dashboard Page
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerDashboardPage.checkCustomerDashboard());

        // Click the icon for placing an order
        global.clickOrderTranslationIcon();

        // Place the text to be translated on the order form and check for the word/character count
        customerOrderFormPage.inputItemToTranslate(itemToTranslate, unitCount, itemToTranslate.length, false);

        // Check if source language is auto detected by the system
        assertTrue(page.getCurrentUrl().equals(customerOrderLanguagesPage.ORDERLANGUAGES_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerOrderLanguagesPage.isSourceAutoDetected(),
                var.getTextNotEqualErrMsg());

        // Set the target language to Japanese
        customerOrderLanguagesPage.choooseLanguage(var.getJapaneseTo());
        customerOrderLanguagesPage.clickNextOptions();

        // Check the 'View Full Quote' page and the generated pdf File
        customerCheckoutPage.clickViewFullQuote();
        parentWindow = switcher.getWindowHandle();
        switcher.switchToPopUp();
        wait.impWait(3);
        assertTrue(page.getCurrentUrl().equals(customerOrderQuotePage.CUSTOMERORDERQUOTE_URL),
                var.getWrongUrlErrMsg());
        customerOrderQuotePage.typeAdress();
        assertTrue(customerOrderQuotePage.getAddressEmbedded().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        customerOrderQuotePage.downloadQuote();
        switcher.switchToParentWindow(parentWindow);

        // Place payment via credits
        customerCheckoutPage
                .payWithCredits();

        // Return to dashboard page
        customerOrderCompletePage.clickGoToDashboard();

        global.goToOrdersPage();
        customerOrdersPage.clickPendingOption();
        customerOrdersPage.findOrder(excerpt);

        jobNo = customerOrderDetailsPage.getJobNumberAvailableJob();

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * addCommentAsTranslator --- a method for picking up a job
     * */
    @Test(dependsOnMethods = "placeAnOrder")
    public void pickUpByTranslator() throws IOException {
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Log in a translator account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getTranslator(29), var.getDefaultPassword());

        // Check if the page redirected to contains the elements that should be seen in a Translator Dashboard
        assertTrue(translatorDashboardPage.checkTranslatorDashboard());

        // Navigate to the Jobs Page and look for the recently created job
        translatorDashboardPage.clickJobsTab();
        translatorJobsPage.findJob(excerpt);

        // The collection ID is retrieved for use in finding the job in Admin
        collectionId = workbenchPage.getCollectionIdTxt().getText().replaceAll("[A-z#]", "");

        // Retrieve page URL for checking by the translator later
        jobURL = page.getCurrentUrl();

        // Translate and submit
        workbenchPage.translateJob(translatedItem);

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * approveByCustomer --- have the customer approve the translated job
     * */
    @Test(dependsOnMethods = "pickUpByTranslator")
    public void approveByCustomer() {
        pluginPage.passThisPage();

        // Check if the prominent page elements can be seen on the Home Page
        assertTrue(homePage.checkHomePage());
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Logging in a customer account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());

        // Ensure that the account type is set to 'Customer'
        global.selectCustomer();

        // Check if the prominent page elements can be seen on the Customer Dashboard Page
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerDashboardPage.checkCustomerDashboard());

        // Find the reviewable job and approve it
        global.goToOrdersPage();
        customerOrdersPage.clickReviewableOption();
        customerOrdersPage.findOrder(excerpt);
        customerOrderDetailsPage.approveJob();

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }

    /**
     * adminEditTitle --- this method logs in an Admin account,
     * looks for the job and edits its title
     * */
    @Test(dependsOnMethods = "approveByCustomer")
    public void adminEditTitle(){
        pluginPage.passThisPage();
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());
        homePage.clickSignInButton();
        assertTrue(page.getCurrentUrl().equals(loginPage.LOGIN_PAGE_URL),
                var.getWrongUrlErrMsg());
        assertTrue(loginPage.getSignUpBtn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        loginPage.loginAccount(var.getTestAdmin(), var.getDefaultPassword());

        // Assert that page title is present in the DOM.
        assertTrue(global.getPageTitle().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        // Select Admin user level.
        global.selectAdmin();

        // Assert that we are redirected to the right page.
        assertTrue(page.getCurrentUrl().contains("admin."),
                var.getWrongUrlErrMsg());

        // Hide the right panel.
        adminPage.hidePanel();

        // Go to Jobs Page
        adminPage.goToJobs();

        // Go to Jobs Details Page and edit Title
        adminJobsPage.clickJob(jobNo);
        adminJobDetailsPage.chooseActions("edit");
        adminJobEditPage.editTitle(newExcerpt);
        adminJobEditPage.getUpdateBtn().click();

        // Check if title edit is submitted
        assertTrue(adminJobDetailsPage.getSuccessUpdateText().isDisplayed());

        global.nonAdminSignOut();
        assertTrue(homePage.checkHomePage());
    }

    /**
     * checkByCustomer --- this method has the user check if the title update is applied
     * */
    @Test(dependsOnMethods = "adminEditTitle")
    public void checkByCustomer(){
        pluginPage.passThisPage();

        // Check if the prominent page elements can be seen on the Home Page
        assertTrue(homePage.checkHomePage());
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementNotFoundErrMsg());

        // Logging in a customer account
        homePage.clickSignInButton();
        loginPage.loginAccount(var.getCustomer(27), var.getDefaultPassword());

        // Ensure that the account type is set to 'Customer'
        global.selectCustomer();

        // Check if the prominent page elements can be seen on the Customer Dashboard Page
        assertTrue(page.getCurrentUrl().equals(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                var.getWrongUrlErrMsg());
        assertTrue(customerDashboardPage.checkCustomerDashboard());

        global.goToOrdersPage();
        customerOrdersPage.clickApprovedOption();
        assertTrue(driver.findElement(By.xpath("//h3[contains(., '" + newExcerpt + "')]/parent::div/following-sibling::div/a")).isDisplayed());

        // Customer sign out
        global.nonAdminSignOut();

        // Check if the redirected page contains the prominent Home Page elements
        assertTrue(homePage.getSignIn().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());
        assertTrue(homePage.checkHomePage());
    }
}
