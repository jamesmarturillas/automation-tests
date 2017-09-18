package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class A class for storing all PageFactory locators that can be seen in multiple pages.
 * Purpose : To avoid rewriting the same locator(s) in different web page(s).
 */
public class GlobalPageFactory {

    protected WebDriver driver;
    public final String ADMIN_URL = "https://admin.staging.gengo.com/";
    public final String TRANSLATOR_DASHBOARD_URL = "https://staging.gengo.com/t/dashboard";
    public final String TRANSLATOR_JOBS_PAGE_URL = "https://staging.gengo.com/t/jobs/status/available";
    public final String TRANSLATOR_TESTS_PAGE_URL = "https://staging.gengo.com/t/tests/dashboard/";
    public final String TRANSLATOR_RESOURCES_PAGE_URL = "https://staging.gengo.com/translators/resources/";
    public final String COMMUNITY_PAGE_URL = "https://support.gengo.com/hc/en-us/community/topics";
    public final String SUPPORT_PAGE_URL = "https://support.gengo.com/hc/en-us/categories/204289667";

    public GlobalPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(@class, 'brand')]")
    WebElement gengoTranslatorLogoInWorkbench;

    @FindBy(xpath = "//h1")
    WebElement pageTitle;

    @FindBy(xpath = "//a[@class='brand']")
    WebElement gengoLogoInOrderForm;

    @FindBy(id = "pHideToolBarButton")
    WebElement rightPanelHide;

    @FindBy(xpath = "//span[@class='glyphicon glyphicon-cog']")
    WebElement adminSignOutParentDropDown;

    @FindBy(xpath = "//a[contains(@href,'https://admin.staging.gengo.com/logout')]")
    WebElement admninSignOutBtn;

    @FindBy(xpath = "//button[@class='close']")
    WebElement successMsgCloseBtn;

    @FindBy(xpath = "//a[@class='dropdown-toggle']")
    WebElement signOutParentDropDown;

    @FindBy(xpath = "//span[@class='nav-account-display-name']")
    WebElement userNameInDropDown;

    @FindBy(xpath = "//a[contains(@href, 'staging.gengo.com/logout')]")
    WebElement nonAdminSignOutBtn;

    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/account/')]")
    WebElement accountBtn;

    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/account/profile')]")
    WebElement settingsBtn;

    @FindBy(xpath = "//a[@class='active'][contains(.,'Customer')]")
    WebElement customerBtn;

    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/t/dashboard')]")
    WebElement translatorBtn;

    @FindBy(xpath = "//li/a[@href='https://admin.staging.gengo.com/']")
    WebElement adminBtn;

    @FindBy(xpath = "//a[contains(@href, 'staging.gengo.com/account/dashboard')]")
    WebElement leftMenuAll;

    @FindBy(xpath = "//ul[@class='nav nav-list sidebar']/li/a[contains(@href, 'staging.gengo.com/account/profile/')]")
    WebElement leftMenuAccountSettings;

    @FindBy(xpath = "//ul[@class='nav nav-list sidebar']/li/a[contains(@href, 'staging.gengo.com/account/top_up/')]")
    WebElement leftMenuTopUp;

    @FindBy(xpath = "//ul[@class='nav nav-list sidebar']/li/a[contains(@href, 'staging.gengo.com/account/api_settings/)']")
    WebElement leftMenuApiSettings;

    /** Customer account tabs */

    @FindBy(xpath = "//a[contains(@href, 'staging.gengo.com/order/?from=dashboard')]")
    WebElement orderTranslationIcon;

    @FindBy(xpath = "//a[contains(@href, 'staging.gengo.com/c/order/')]")
    WebElement ordersTab;

    @FindBy(xpath = "//a[contains(@href, 'staging.gengo.com/c/glossaries/')]")
    WebElement glossariesTab;

    @FindBy(xpath = "//a[contains(@href, 'staging.gengo.com/c/preferred-translators/')]")
    WebElement preferredTranslatorsTab;

    @FindBy(xpath = "//a[contains(@href, 'https://support.gengo.com/hc/en-us/categories')]")
    WebElement supportTab;

    @FindBy(xpath = "//span[@class='nav-account-credits']")
    WebElement creditsAmt;

    /** Customer left side panel */
    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/c/dashboard/jobs/reviewable')]")
    WebElement customerReviewableBtn;

    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/c/dashboard/jobs/approved')]")
    WebElement customerApprovedBtn;

    /** Translator Navigation Bar*/

    @FindBy(xpath = "//a[@class='navbar-brand']")
    WebElement translatorDashboardBtn;

    @FindBy(xpath = "//li/a[contains(.,'Jobs')]")
    WebElement translatorJobsTab;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li/a[contains(.,'Tests')]")
    WebElement translatorTestsTab;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li/a[contains(.,'Resources')]")
    WebElement translatorResourcesTab;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li/a[contains(.,'Community')]")
    WebElement translatorCommunityTab;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li/a[contains(.,'Support')]")
    WebElement translatorSupportTab;

    /** Translator left side panel */
    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/t/jobs/status/available/') and contains(., 'All')]")
    WebElement translatorAllJobsBtn;

    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/t/jobs/status/revising')]")
    WebElement translatorRevisingBtn;

    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/t/jobs/status/reviewable')]")
    WebElement translatorReviewableBtn;

    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/t/jobs/status/completed')]")
    WebElement translatorCompletedBtn;

    @FindBy(xpath = "//li/a[contains(@href, 'staging.gengo.com/t/jobs/status/available/preferred')]")
    WebElement translatorPreferredBtn;

    /** Footer */
    @FindBy(xpath = "//footer/div/button[@class='language-btn btn dropdown-toggle']")
    WebElement accountLanguageSelectionDropDown;

    @FindBy(xpath = "//footer/div/ul[@class='dropdown-menu']/li")
    WebElement parentLanguageSelection;


    /**
     * Getters
     */

    public WebElement getGengoTranslatorLogoInWorkbench() {
        return gengoTranslatorLogoInWorkbench;
    }

    public WebElement getPageTitle() {
        return pageTitle;
    }

    public WebElement getGengoLogoInOrderForm() {
        return gengoLogoInOrderForm;
    }

    /** Top dropdown */
    public WebElement getRightPanelHide() {
        return rightPanelHide;
    }

    public WebElement getAdminSignOutParentDropDown() {
        return adminSignOutParentDropDown;
    }

    public WebElement getAdminSignOutBtn() {
        return admninSignOutBtn;
    }

    public WebElement getSuccessMsgCloseBtn() {
        return successMsgCloseBtn;
    }

    public WebElement getSignOutParentDropDown() {
        return signOutParentDropDown;
    }

    public WebElement getUserNameInDropDown() {
        return userNameInDropDown;
    }

    public WebElement getNonAdminSignOutBtn() {
        return nonAdminSignOutBtn;
    }

    public WebElement getAccountBtn() {
        return accountBtn;
    }

    public WebElement getSettingsBtn() {
        return settingsBtn;
    }

    public WebElement getCustomerBtn() {
        return customerBtn;
    }

    public WebElement getTranslatorBtn() {
        return translatorBtn;
    }

    public WebElement getAdminBtn() {
        return adminBtn;
    }

    /** Account */
    public WebElement getLeftMenuAll() {
        return leftMenuAll;
    }

    public WebElement getLeftMenuAccountSettings() {
        return leftMenuAccountSettings;
    }

    public WebElement getLeftMenuTopUp() {
        return leftMenuTopUp;
    }

    public WebElement getLeftMenuApiSettings() {
        return leftMenuApiSettings;
    }

    public WebElement getOrderTranslationIcon() {
        return orderTranslationIcon;
    }

    public WebElement getOrdersTab() {
        return ordersTab;
    }

    public WebElement getGlossariesTab() {
        return glossariesTab;
    }

    public WebElement getTranslatorAllJobsBtn() {
        return translatorAllJobsBtn;
    }

    public WebElement getPreferredTranslatorsTab() {
        return preferredTranslatorsTab;
    }

    public WebElement getSupportTab() {
        return supportTab;
    }

    public WebElement getCustomerReviewableBtn() {
        return customerReviewableBtn;
    }

    public WebElement getCustomerApprovedBtn() {
        return customerApprovedBtn;
    }

    public WebElement getCreditsAmt() {
        return creditsAmt;
    }


    /** Translator Navigation Bar*/

    public WebElement getTranslatorDashboardBtn() {
        return translatorDashboardBtn;
    }

    public WebElement getTranslatorJobsTab() {
        return translatorJobsTab;
    }

    public WebElement getTranslatorTestsTab() {
        return translatorTestsTab;
    }

    public WebElement getTranslatorResourcesTab() {
        return translatorResourcesTab;
    }

    public WebElement getTranslatorCommunityTab() {
        return translatorCommunityTab;
    }

    public WebElement getTranslatorSupportTab() {
        return translatorSupportTab;
    }

    public WebElement getAccountLanguageSelectionDropDown() {
        return accountLanguageSelectionDropDown;
    }

    public WebElement getParentLanguageSelection() {
        return parentLanguageSelection;
    }

    public WebElement getTranslatorRevisingBtn() {
        return translatorRevisingBtn;
    }

    public WebElement getTranslatorReviewableBtn() {
        return translatorReviewableBtn;
    }

    public WebElement getTranslatorCompletedBtn() {
        return translatorCompletedBtn;
    }

    public WebElement getTranslatorPreferredBtn() {
        return translatorPreferredBtn;
    }
}
