package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for success registration page.
 * Contains PageFactory initialization.
 */
public class SuccessRegistrationPageFactory {

    protected WebDriver driver;
    public final String SUCCESS_PAGE_URL = "https://staging.gengo.com/auth/signup/";

    public SuccessRegistrationPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[@class='brand-logo' and @href='https://staging.gengo.com/']")
    WebElement successPageGengoLogo;

    @FindBy(xpath = "//section[@class='connect-area larger']")
    WebElement successPageBody;


    /**
     * Getters
     */
    public WebElement getSuccessPageGengoLogo() {
        return successPageGengoLogo;
    }

    public WebElement getSuccessPageBody() {
        return successPageBody;
    }

}
