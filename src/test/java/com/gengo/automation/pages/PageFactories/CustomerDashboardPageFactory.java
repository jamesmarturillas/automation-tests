package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for customer dashboard page.
 * Contains PageFactory initialization.
 */
public class CustomerDashboardPageFactory {

    protected WebDriver driver;
    public final String CUSTOMER_DASHBOARD_URL = "https://staging.gengo.com/c/dashboard/";

    public CustomerDashboardPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//div[@id='in-line-title-box']")
    WebElement customerDashboardtext;

    @FindBy(xpath = "//a[@class='ui_btn core_btn' and @href='https://staging.gengo.com/order/?from=dashboard']")
    WebElement orderTranslationBtn;

    @FindBy(xpath = "//a[@class='tooltip-target' and @href='https://staging.gengo.com/order/?from=dashboard']")
    WebElement orderTranslationLink;

    @FindBy(xpath = "//div[@class='container-fluid navbar-personal']")
    WebElement navBar;

    @FindBy(id = "translate")
    WebElement dashboardArea;

    @FindBy(tagName = "footer")
    WebElement footerSection;

    @FindBy(className = "nav-account-credits")
    WebElement credits;

    /**
     * Getters
     */
    public WebElement getCustomerDashboardText() {
        return customerDashboardtext;
    }

    public WebElement getOrderTranslationBtn() {
        return orderTranslationBtn;
    }

    public WebElement getNavBar() {
        return navBar;
    }

    public WebElement getDashboardArea() {
        return dashboardArea;
    }

    public WebElement getFooterSection() {
        return footerSection;
    }

    public WebElement getOrderTranslationLink() {
        return orderTranslationLink;
    }

    public WebElement getCredits() {
        return credits;
    }

}
