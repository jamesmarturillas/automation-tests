package com.gengo.automation.global;

import com.gengo.automation.config.Invoker;
import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

import java.awt.*;
import java.io.IOException;

/**
 * @class A class to instantiate all PageObjects
 */
public class AutomationBase extends Invoker {

    /** PageObjects */
    protected static AccountSettingsPage accountSettingsPage;
    protected static AdminChecksPage adminChecksPage;
    protected static AdminJobCommentPage adminJobCommentPage;
    protected static AdminJobDetailsPage adminJobDetailsPage;
    protected static AdminJobEditPage adminJobEditPage;
    protected static AdminJobsPage adminJobsPage;
    protected static AdminOrderDetailsPage adminOrderDetailsPage;
    protected static AdminOrdersPage adminOrdersPage;
    protected static AdminPage adminPage;
    protected static AdminNewQualificationsPage adminNewQualificationsPage;
    protected static AdminUserPage adminUserPage;
    protected static AdminUsersPage adminUsersPage;
    protected static CustomerDashboardPage customerDashboardPage;
    protected static CustomerOrderCompletePage customerOrderCompletePage;
    protected static CustomerOrderDetailsPage customerOrderDetailsPage;
    protected static CustomerOrderFormPage customerOrderFormPage;
    protected static CustomerOrderLanguagesPage customerOrderLanguagesPage;
    protected static CustomerCheckoutPage customerCheckoutPage;
    protected static CustomerOrderPage customerOrderPage;
    protected static CustomerOrderQuotePage customerOrderQuotePage;
    protected static CustomerOrdersPage customerOrdersPage;
    protected static FacebookPopUpPage facebookPopUpPage;
    protected static GlobalPage globalPage;
    protected static GlossaryPage glossaryPage;
    protected static GmailActivateGengoPage gmailActivateGengoPage;
    protected static GmailAllowAppPage gmailAllowAppPage;
    protected static GmailChangeEmailPage gmailChangeEmailPage;
    protected static GmailClosedAccountPage gmailClosedAccountPage;
    protected static GmailInboxPage gmailInboxPage;
    protected static GmailJobReviewedPage gmailJobReviewedPage;
    protected static GmailSignInEmailPage gmailSignInEmailPage;
    protected static GmailSignInPasswordPage gmailSignInPasswordPage;
    protected static GmailUserCommentPage gmailUserCommentPage;
    protected static GmailWelcomeToGengoPage gmailWelcomeToGengoPage;
    protected static GoCheckPage goCheckPage;
    protected static GoogleAppPermissionPage googleAppPermissionPage;
    protected static HomePage homePage;
    protected static LoginPage loginPage;
    protected static PayPalPage payPalPage;
    protected static PluginPage pluginPage;
    protected static ReviewPage reviewPage;
    protected static SignUpPage signUpPage;
    protected static SuccessRegistrationPage successRegistrationPage;
    protected static SupportPage supportPage;
    protected static TopUpPage topUpPage;
    protected static TranslatorChecksPage translatorChecksPage;
    protected static TranslatorDashboardPage translatorDashboardPage;
    protected static TranslatorExperiencePage translatorExperiencePage;
    protected static TranslatorJobsPage translatorJobsPage;
    protected static TranslatorOnboardPage translatorOnboardPage;
    protected static TranslatorTestsPage translatorTestsPage;
    protected static WorkbenchFilePage workbenchFilePage;
    protected static WorkbenchPage workbenchPage;

    protected static GlobalMethods globalMethods;

    /** Helpers */
    protected static Switcher switcher;

    /** Other Fields */
    protected static Variables var;
    protected String newUser = null;

    /** Constructor to handle pages with possibilities to throw IOException */
    public AutomationBase(WebDriver driver) throws IOException {}

    /**
     * Empty default constructor
     *  >> Needed as some of test cases requires.
     */
    public AutomationBase() throws IOException {}

    @BeforeClass
    public void initObjects() throws IOException, AWTException {

        // Instantiation of PageObjects
        accountSettingsPage = new AccountSettingsPage(getDriver());
        adminChecksPage = new AdminChecksPage(getDriver());
        adminJobCommentPage = new AdminJobCommentPage(getDriver());
        adminJobDetailsPage = new AdminJobDetailsPage(getDriver());
        adminJobEditPage = new AdminJobEditPage(getDriver());
        adminJobsPage = new AdminJobsPage(getDriver());
        adminOrderDetailsPage = new AdminOrderDetailsPage(getDriver());
        adminOrdersPage = new AdminOrdersPage(getDriver());
        adminPage = new AdminPage(getDriver());
        adminNewQualificationsPage = new AdminNewQualificationsPage(getDriver());
        adminUserPage = new AdminUserPage(getDriver());
        adminUsersPage = new AdminUsersPage(getDriver());
        customerDashboardPage = new CustomerDashboardPage(getDriver());
        customerOrderCompletePage = new CustomerOrderCompletePage(getDriver());
        customerOrderDetailsPage = new CustomerOrderDetailsPage(getDriver());
        customerOrderFormPage = new CustomerOrderFormPage(getDriver());
        customerOrderLanguagesPage = new CustomerOrderLanguagesPage(getDriver());
        customerCheckoutPage = new CustomerCheckoutPage(getDriver());
        customerOrderPage = new CustomerOrderPage(getDriver());
        customerOrderQuotePage = new CustomerOrderQuotePage(getDriver());
        customerOrdersPage = new CustomerOrdersPage(getDriver());
        facebookPopUpPage = new FacebookPopUpPage(getDriver());
        globalPage = new GlobalPage(getDriver());
        glossaryPage = new GlossaryPage(getDriver());
        gmailActivateGengoPage = new GmailActivateGengoPage(getDriver());
        gmailAllowAppPage = new GmailAllowAppPage(getDriver());
        gmailChangeEmailPage = new GmailChangeEmailPage(getDriver());
        gmailClosedAccountPage = new GmailClosedAccountPage(getDriver());
        gmailInboxPage = new GmailInboxPage(getDriver());
        gmailJobReviewedPage = new GmailJobReviewedPage(getDriver());
        gmailSignInEmailPage = new GmailSignInEmailPage(getDriver());
        gmailSignInPasswordPage = new GmailSignInPasswordPage(getDriver());
        gmailUserCommentPage = new GmailUserCommentPage(getDriver());
        gmailWelcomeToGengoPage = new GmailWelcomeToGengoPage(getDriver());
        goCheckPage = new GoCheckPage(getDriver());
        googleAppPermissionPage = new GoogleAppPermissionPage(getDriver());
        homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());
        payPalPage = new PayPalPage(getDriver());
        pluginPage = new PluginPage(getDriver());
        reviewPage = new ReviewPage(getDriver());
        signUpPage = new SignUpPage(getDriver());
        successRegistrationPage = new SuccessRegistrationPage(getDriver());
        supportPage = new SupportPage(getDriver());
        topUpPage = new TopUpPage(getDriver());
        translatorChecksPage = new TranslatorChecksPage(getDriver());
        translatorDashboardPage = new TranslatorDashboardPage(getDriver());
        translatorExperiencePage = new TranslatorExperiencePage(getDriver());
        translatorJobsPage = new TranslatorJobsPage(getDriver());
        translatorOnboardPage = new TranslatorOnboardPage(getDriver());
        translatorTestsPage = new TranslatorTestsPage(getDriver());
        workbenchFilePage = new WorkbenchFilePage(getDriver());
        workbenchPage = new WorkbenchPage((getDriver()));

        globalMethods = new GlobalMethods(getDriver());

        // Helpers block
        switcher = new Switcher(getDriver());

        // Other fields
        var = new Variables();
    }
}
