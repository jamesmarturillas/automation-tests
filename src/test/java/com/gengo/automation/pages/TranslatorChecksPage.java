package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.TranslatorChecksPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for translator checker page.
 * Contains element operations.
 */
public class TranslatorChecksPage extends TranslatorChecksPageFactory {

    public TranslatorChecksPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    public void clickAllChecks(){
        getAllLeftPanel().click();
        wait.impWait(10);
    }

    public void clickPriorityChecks(){
        getPriorityLeftPanel().click();
        wait.impWait(10);
    }
}
