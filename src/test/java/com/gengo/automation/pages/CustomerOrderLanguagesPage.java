package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.CustomerOrderLanguagesPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * @class Object repository for sample template of a PageObject class.
 * Contains element operations.
 */
public class CustomerOrderLanguagesPage extends CustomerOrderLanguagesPageFactory {

    public CustomerOrderLanguagesPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Variables var = new Variables();
    private Wait wait = new Wait(driver);

    /**
     * @return true if source language is English and auto detected, otherwise false.
     */
    public boolean isSourceAutoDetected() {
        try{
            wait.untilElementNotVisible(By.xpath("//*[contains(.,'Detecting language')]"));
        }
        catch (TimeoutException e){
        }
        return getSourceLanguageDropDown().getText().contains(var.getEnglishFrom() + " (auto detected)");
    }

    public boolean isSourceAutoDetected(String language) {
        try{
            wait.untilElementNotVisible(By.xpath("//*[contains(.,'Detecting language')]"));
        }
        catch (TimeoutException e){
        }
        return getSourceLanguageDropDown().getText().contains(language + " (auto detected)");
    }

    public void choooseLanguage(String language) {
        getTargetLanguageDropDown().click();
        getLanguageTo(language).click();
        wait.impWait(10, getAddLanguageBtn());
        getAddLanguageBtn().click();
    }

    public void chooseSourceLanguage(String language) {
        getSourceLanguageDropDown().click();
        getLanguageFrom(language).click();
        wait.impWait(10);
    }

    public void clickNextOptions() {
        getNextOptionsBtn().click();
        wait.impWait(20);
    }
}
