package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Gmail email inputting page.
 * Contains PageFactory initialization.
 */
public class GmailSignInEmailPageFactory {

    protected WebDriver driver;

    public GmailSignInEmailPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//input[@id='Email' or @id='identifierId']")
    WebElement txtBoxEmail;

    @FindBy(xpath = "//*[@id='next']")
    WebElement nextBtn;


    /**
     * Getters
     */
    public WebElement getTxtBoxEmail() {
        return txtBoxEmail;
    }

    public WebElement getNextBtn() {
        return nextBtn;
    }

}
