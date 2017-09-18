package com.gengo.automation.pages;

import com.gengo.automation.helpers.*;
import com.gengo.automation.pages.PageFactories.GlossaryPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Glossary page.
 * Contains element operations.
 */
public class GlossaryPage extends GlossaryPageFactory {

    public GlossaryPage(WebDriver driver) {
        super(driver);
    }
    private final String GLOSSARY_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/files/";
    private PageActions page = new PageActions(driver);
    private Wait wait = new Wait(driver);
    private ElementActions elementActions = new ElementActions(driver);
    private ElementState elementState = new ElementState(driver);

    public void gotoUploadGlossaryPage() {
        page.launchUrl(GLOSSARY_PAGE_URL + "upload");
        wait.impWait(30);
    }

    public void uploadGlossary(String fileName) {
        this.gotoUploadGlossaryPage();
        String wholeFileName = GLOSSARY_FILE_PATH + fileName;
        if (elementState.isElementPresentInSource(fileName)) return;
        getUploadGlossaryBtn().click();
        elementActions.moveToElement(getChooseGlossaryFileBtn());
        getChooseGlossaryFileBtn().sendKeys(wholeFileName);
        wait.impWait(30, getDownloadGlossaryTxt());
    }

    public void uploadInvalidGlossary(String fileName) {
        this.gotoUploadGlossaryPage();
        String wholeFileName = GLOSSARY_FILE_PATH + fileName;
        elementActions.moveToElement(getChooseGlossaryFileBtn());
        getChooseGlossaryFileBtn().sendKeys(wholeFileName);
        wait.impWait(30, getErrorMessage());
    }

    public boolean isBracketedErrorDisplayed() {
        boolean state;
        try{
            state = getBracketedTermErrorMsg().isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }
}
