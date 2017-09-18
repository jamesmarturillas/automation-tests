package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.TranslatorDashboardPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for translator dashboard page.
 * Contains element operations.
 */
public class TranslatorDashboardPage extends TranslatorDashboardPageFactory {

    public TranslatorDashboardPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    public void clickJobsTab(){
        wait.impWait(30, getJobsTab());
        getJobsTab().click();
        wait.impWait(10);
    }

    public void clickCheckerTab(){
        getCheckerTab().click();
        wait.impWait(10);
    }

    public boolean checkTranslatorDashboard(){
        Boolean state = true;
        try{
            getWorkDashboardHeader().isDisplayed();
            getQualitySection().isDisplayed();
            getActivitySection().isDisplayed();
            getFooterSection().isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean fileJobCancelled() {
        Boolean state = true;
        try{
            getFileJobCancelled().isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }
}
