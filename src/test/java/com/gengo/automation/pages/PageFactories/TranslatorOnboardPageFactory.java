package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for translator onboard page.
 * Contains PageFactory initialization.
 */
public class TranslatorOnboardPageFactory {

    protected WebDriver driver;
    public final String TRANSLATOR_ONBOARD_URL = "https://staging.gengo.com/t/onboard/basic";

    public TranslatorOnboardPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[contains(text(), 'Start with the basics')]")
    WebElement translatorOnboardText;

    @FindBy(name = "full_name")
    WebElement txtBoxFullName;

    @FindBy(name = "address_line_1")
    WebElement txtBoxAddressLine1;

    @FindBy(name = "address_line_2")
    WebElement txtBoxAddressLine2;

    @FindBy(name = "city")
    WebElement txtBoxCity;

    @FindBy(name = "region")
    WebElement txtBoxRegion;

    @FindBy(name = "zip_code")
    WebElement txtBoxZipCode;

    @FindBy(xpath = "//select[@name='country']")
    WebElement selectCountryDropDown;

    @FindBy(xpath = "//input[@name='lives_in_us' and @value='yes']")
    WebElement radioLiveInUSYes;

    @FindBy(xpath = "//input[@name='lives_in_us' and @value='no']")
    WebElement radioLiveInUSNo;

    @FindBy(xpath = "//input[@name='us_citizen_or_resident_alien' and @value='yes']")
    WebElement radioIsUsCitizenYes;

    @FindBy(xpath = "//input[@name='us_citizen_or_resident_alien' and @value='no']")
    WebElement radioIsUsCitizenNo;

    @FindBy(xpath = "//input[@class='btn btn-primary pull-right btn-submit' and @value='Continue']")
    WebElement btnTranslatorOnboardContinue;

    @FindBy(xpath = "//p[contains(.,'Full name is required.')]")
    WebElement emptyFieldsOnboardFail;

    @FindBy(xpath = "//p[contains(text(), 'We’re sorry but you’re not eligible to work for Gengo.')]")
    WebElement cannotWorkOnboardFail;

    @FindBy(xpath = "//p[contains(text(), 'Zip / postal code is required.')]")
    WebElement noZipCodeOnboardFail;

    /**
     * Getters
     */
    public WebElement getTranslatorOnboardText() {
        return translatorOnboardText;
    }

    public WebElement getTxtBoxFullName() {
        return txtBoxFullName;
    }

    public WebElement getTxtBoxAddressLine1() {
        return txtBoxAddressLine1;
    }

    public WebElement getTxtBoxAddressLine2() {
        return txtBoxAddressLine2;
    }

    public WebElement getTxtBoxCity() {
        return txtBoxCity;
    }

    public WebElement getTxtBoxRegion() {
        return txtBoxRegion;
    }

    public WebElement getTxtBoxZipCode() {
        return txtBoxZipCode;
    }

    public WebElement getRadioLiveInUSYes() {
        return radioLiveInUSYes;
    }

    public WebElement getRadioLiveInUSNo() {
        return radioLiveInUSNo;
    }

    public WebElement getRadioIsUsCitizenYes() {
        return radioIsUsCitizenYes;
    }

    public WebElement getRadioIsUsCitizenNo() {
        return radioIsUsCitizenNo;
    }

    public WebElement getBtnTranslatorOnboardContinue() {
        return btnTranslatorOnboardContinue;
    }

    public WebElement getEmptyFieldsOnboardFail() {
        return emptyFieldsOnboardFail;
    }

    public WebElement getCannotWorkOnboardFail() {
        return cannotWorkOnboardFail;
    }

    public WebElement getNoZipCodeOnboardFail() {
        return noZipCodeOnboardFail;
    }

    public WebElement getSelectCountryDropDown() {
        return selectCountryDropDown;
    }
}
