package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @class Page factory for translator experience page.
 * Contains PageFactory initialization.
 */
public class TranslatorExperiencePageFactory {

    protected WebDriver driver;
    public final String TRANSLATOR_EXPERIENCE_URL = "https://staging.gengo.com/t/onboard/experience";

    public TranslatorExperiencePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//h1[contains(text(), 'Your translation experience')]")
    WebElement translatorExperienceText;

    @FindBy(xpath = "//input[@name='translator_level' and @value='0']")
    WebElement radioNewExperience;

    @FindBy(xpath = "//input[@name='translator_level' and @value='1']")
    WebElement radioCasualExperience;

    @FindBy(xpath = "//input[@name='translator_level' and @value='2']")
    WebElement radioSpecialistExperience;

    @FindBy(xpath = "//input[@class='btn btn-primary pull-right btn-submit' and @value='Continue']")
    WebElement btnTranslatorExperienceContinue;

    @FindBy(xpath = "//p[contains(., 'Preferred translation topics is required.')]")
    WebElement noPreferredTranslationErrMsg;

    @FindBy(xpath = "//div[@class='form-group form-group-specialization']/div[@class='col-md-8 form-group-vertical-input']/div")
    WebElement specializationDropdown;

    @FindBy(xpath = "//div[@class='form-group form-group-specialization']/div[@class='col-md-8 form-group-vertical-input']/div/div/ul/li[contains(@class, 'active-result')]")
    WebElement chosenSpecialization;

    @FindBy(xpath = "//a[@class='search-choice-close']")
    WebElement closeChosenSpecialization;

    @FindBy(xpath = "//a[@class='btn btn-default pull-right']")
    WebElement backBtn;

    @FindBy(xpath = "//div[@class='form-group']/div[@class='col-md-8 form-group-vertical-input']/div")
    WebElement fieldOfStudyDropDown;

    @FindBy(xpath = "//div[@class='form-group']/div[@class='col-md-8 form-group-vertical-input']/div/div/ul/li[contains(@class, 'active-result')]")
    WebElement chosenFieldOfStudy;

    @FindBys({@FindBy(xpath = "//input[@name='translator_level' and @checked='checked']")})
    List<WebElement> checkedExperience;

    /**
     * Getters
     */
    public WebElement getTranslatorExperienceText() {
        return translatorExperienceText;
    }

    public WebElement getRadioNewExperience() {
        return radioNewExperience;
    }

    public WebElement getRadioCasualExperience() {
        return radioCasualExperience;
    }

    public WebElement getRadioSpecialistExperience() {
        return radioSpecialistExperience;
    }

    public WebElement getBtnTranslatorExperienceContinue() {
        return btnTranslatorExperienceContinue;
    }

    public WebElement getNoPreferredTranslationErrMsg() {
        return noPreferredTranslationErrMsg;
    }

    public WebElement getSpecializationDropdown() {
        return specializationDropdown;
    }

    public WebElement getChosenSpecialization() {
        return chosenSpecialization;
    }

    public WebElement getCloseChosenSpecialization() {
        return closeChosenSpecialization;
    }

    public WebElement getBackBtn() {
        return backBtn;
    }

    public WebElement getFieldOfStudyDropDown() {
        return fieldOfStudyDropDown;
    }

    public WebElement getChosenFieldOfStudy() {
        return chosenFieldOfStudy;
    }

    public List<WebElement> getCheckedExperience() {
        return checkedExperience;
    }
}
