package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.global.GlobalMethods;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.TranslatorOnboardPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @class Object repository for translator onboard page.
 * Contains element operations.
 */
public class TranslatorOnboardPage extends TranslatorOnboardPageFactory {

    public TranslatorOnboardPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Variables var = new Variables();
    private Wait wait = new Wait(driver);
    private TranslatorExperiencePage translatorExperiencePage = new TranslatorExperiencePage(driver);
    private TranslatorTestsPage translatorTestsPage = new TranslatorTestsPage(driver);
    private GlobalPage global = new GlobalPage(driver);

    public void fillOutForm(boolean withZipCode) {
        getTxtBoxFullName().sendKeys(var.getFullName());
        getTxtBoxAddressLine1().sendKeys(var.getAddress1());
        getTxtBoxAddressLine2().sendKeys(var.getAddress2());
        getTxtBoxCity().sendKeys(var.getTown());
        getTxtBoxRegion().sendKeys(var.getState());
        if (withZipCode) getTxtBoxZipCode().sendKeys(var.getZipCode());
    }

    public void clearOutForm() {
        getTxtBoxFullName().clear();
        getTxtBoxAddressLine1().clear();
        getTxtBoxAddressLine2().clear();
        getTxtBoxCity().clear();
        getTxtBoxRegion().clear();
        getTxtBoxZipCode().clear();
    }

    public void clickContinue() {
        getBtnTranslatorOnboardContinue().click();
    }

    public void clickTaxDeclarationRadioButtonsNo() {
        getRadioLiveInUSNo().click();
        wait.impWait(5, getRadioIsUsCitizenNo());
        getRadioIsUsCitizenNo().click();
        wait.impWait(5);
    }

    public void clickTaxDeclarationRadioButtonsYesNo() {
        getRadioLiveInUSYes().click();
        wait.impWait(5, getRadioIsUsCitizenNo());
        getRadioIsUsCitizenNo().click();
        wait.impWait(5);
    }

    public void clickTaxDeclarationRadioButtonsNoYes() {
        getRadioLiveInUSNo().click();
        wait.impWait(5, getRadioIsUsCitizenYes());
        getRadioIsUsCitizenYes().click();
        wait.impWait(5);
    }

    public void clickTaxDeclarationRadioButtonsYes() {
        getRadioLiveInUSYes().click();
        wait.impWait(5, getRadioIsUsCitizenYes());
        getRadioIsUsCitizenYes().click();
        wait.impWait(5);
    }

    public boolean isContinueButtonEnabled() {
        return getBtnTranslatorOnboardContinue().isEnabled();
    }

    public boolean isTranslatorReset() {
        try {
            return getTranslatorOnboardText().isDisplayed();
        }
        catch(NoSuchElementException e) {
            return false;
        }
    }

    public void onboardTranslator() {
        if (!isTranslatorReset()) {
            return;
        }
        new Select(getSelectCountryDropDown()).selectByValue("IE"); // Test Ireland if it will pass without zip code.
        this.clickTaxDeclarationRadioButtonsNo();
        this.clickContinue();

        assertTrue(translatorExperiencePage.getTranslatorExperienceText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        translatorExperiencePage.selectTopics();
        translatorExperiencePage.selectFieldOfStudy();
        translatorExperiencePage.clickContinue();

        assertTrue(translatorTestsPage.getTranslatorTestsPageText().isDisplayed(),
                var.getElementIsNotDisplayedErrMsg());

        global.clickJobsTab();
    }
}
