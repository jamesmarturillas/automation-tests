package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail inbox change Gengo email account page.
 * Contains PageFactory initialization.
 */
public class GmailChangeEmailPageFactory {

    protected WebDriver driver;

    public GmailChangeEmailPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(text(), 'Change Email Address')]")
    WebElement changeEmailAddressBtn;

    /**
     * Getters
     */
    public WebElement getChangeEmailAddressBtn() {
        return changeEmailAddressBtn;
    }
}
