package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Object repository for translator dashboard page.
 * Contains PageFactory initialization.
 */
public class TranslatorDashboardPageFactory {

    protected WebDriver driver;
    public final String TRANSLATOR_DASHBOARD_URL = "https://staging.gengo.com/t/dashboard";

    public TranslatorDashboardPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(., 'Jobs')]")
    WebElement jobsTab;

    @FindBy(xpath = "//a[contains(., 'Checks')]")
    WebElement checkerTab;

    @FindBy(xpath = "//a[contains(., 'Tests')]")
    WebElement testsTab;

    @FindBy(xpath = "//a[contains(., 'Resources')]")
    WebElement resourcesTab;

    @FindBy(xpath = "//a[contains(., 'Community')]")
    WebElement communityTab;

    @FindBy(xpath = "//a[contains(., 'Support')]")
    WebElement supportTab;

    @FindBy(xpath = "//h1[@class=\"td-main-title\"][contains(.,'Work dashboard')]")
    WebElement workDashboardHeader;

    @FindBy(xpath = "//div[@class='box metrics-box'][contains(.,'Quality')]")
    WebElement qualitySection;

    @FindBy(xpath = "//div[@class='box metrics-box'][contains(.,'Activity')]")
    WebElement activitySection;

    @FindBy(tagName = "footer")
    WebElement footerSection;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']")
    WebElement tabNavBar;

    @FindBy(xpath = "//p[contains(.,'Sorry, the translation has been cancelled by the customer:')]")
    WebElement fileJobCancelled;

    /**
     * Getters
     */
    public WebElement getJobsTab() {
        return jobsTab;
    }

    public WebElement getCheckerTab() {
        return checkerTab;
    }

    public WebElement getTestsTab() {
        return testsTab;
    }

    public WebElement getResourcesTab() {
        return resourcesTab;
    }

    public WebElement getCommunityTab() {
        return communityTab;
    }

    public WebElement getSupportTab() {
        return supportTab;
    }

    public WebElement getWorkDashboardHeader() {
        return workDashboardHeader;
    }

    public WebElement getQualitySection() {
        return qualitySection;
    }

    public WebElement getActivitySection() {
        return activitySection;
    }

    public WebElement getFooterSection() {
        return footerSection;
    }

    public WebElement getTabNavBar() {
        return tabNavBar;
    }

    public WebElement getFileJobCancelled() {
        return fileJobCancelled;
    }
}
