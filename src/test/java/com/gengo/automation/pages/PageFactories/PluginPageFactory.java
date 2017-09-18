package com.gengo.automation.pages.PageFactories;

import com.gengo.automation.helpers.CheckElementPresence;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for password protection page.
 * Contains PageFactory initialization.
 */
public class PluginPageFactory {

    protected WebDriver driver;
    protected final String BANNER_MSG = "This site is a private site. You must enter the access password to proceed.";
    protected final String PASSWORD = "Gengo2017!";
    protected CheckElementPresence check = new CheckElementPresence(driver);

    public PluginPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(id = "custom_messaging_banner")
    WebElement pageBanner;

    @FindBy(name = "hwsp_motech")
    WebElement txtPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitBtn;

    /**
     * Getters
     */
    public WebElement getPageBanner() {
        return pageBanner;
    }

    public WebElement getTxtPassword() {
        return txtPassword;
    }

    public WebElement getSubmitBtn() {
        return submitBtn;
    }
}
