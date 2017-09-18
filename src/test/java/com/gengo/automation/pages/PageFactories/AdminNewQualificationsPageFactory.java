package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Object repository for Admin New Qualifications page.
 * Contains PageFactory initialization.
 */
public class AdminNewQualificationsPageFactory {
    protected WebDriver driver;

    public AdminNewQualificationsPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//input[@name='translator_id']")
    WebElement translatorIdTextInput;

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    WebElement createBtn;

    @FindBy(xpath = "//div[@class='alert alert-dismissable alert-danger']")
    WebElement alertMsg;

    @FindBy(xpath = "//select[@name='lc_src']")
    WebElement sourceLanguageDropDown;

    @FindBy(xpath = "//select[@name='lc_tgt']")
    WebElement targetLanguageDropDown;

    @FindBy(xpath = "//select[@name='rank']")
    WebElement rankDropDown;

    /**
     * Getters
     */
    public WebElement getTranslatorIdTextInput() {
        return translatorIdTextInput;
    }

    public WebElement getCreateBtn() {
        return createBtn;
    }

    public WebElement getAlertMsg() {
        return alertMsg;
    }

    public WebElement getSourceLanguageDropDown() {
        return sourceLanguageDropDown;
    }

    public WebElement getTargetLanguageDropDown() {
        return targetLanguageDropDown;
    }

    public WebElement getRankDropDown() {
        return rankDropDown;
    }

}
