package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.TranslatorExperiencePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

/**
 * @class Object repository for translator experience page.
 * Contains element operations.
 */
public class TranslatorExperiencePage extends TranslatorExperiencePageFactory {

    public TranslatorExperiencePage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    public void clickNewExperienceRadioBtn() {
        getRadioNewExperience().click();
        wait.impWait(5);
    }

    public void clickCasualExperienceRadioBtn() {
        getRadioCasualExperience().click();
        wait.impWait(5);
    }

    public void clickSpecialistExperienceRadioBtn() {
        getRadioSpecialistExperience().click();
        wait.impWait(5);
    }

    public void clickContinue() {
        getBtnTranslatorExperienceContinue().click();
        wait.impWait(10);
    }

    public void selectTopics() {
        int maxSpecialization = 5;
        for (int i = 0; i < maxSpecialization; i++) {
            getSpecializationDropdown().click();
            wait.impWait(30);
            getChosenSpecialization().click();
        }
    }

    public void selectFieldOfStudy() {
        int maxFieldOfStudy = 2;
        for (int i = 0; i < maxFieldOfStudy; i++) {
            getFieldOfStudyDropDown().click();
            wait.impWait(30);
            getChosenFieldOfStudy().click();
        }
    }

    public void removeATopic() {
        getCloseChosenSpecialization().click();
        wait.impWait(5);
    }

    public int getRemainingSpecializationAmount() {
        wait.impWait(30);
        return driver.findElements(By.xpath("//a[@class='search-choice-close']")).size();
    }

    public boolean isSpecializationAmountRight(int toCompare) {
        return getRemainingSpecializationAmount() == toCompare;
    }

    public void returnRemovedSpecialization() {
        getSpecializationDropdown().click();
        wait.impWait(30);
        getChosenSpecialization().click();
    }

    public void clickBackBtn() {
        getBackBtn().click();
    }

    public boolean isOnlyOneExperienceSelected() {
        return getCheckedExperience().size() == 1;
    }
}
