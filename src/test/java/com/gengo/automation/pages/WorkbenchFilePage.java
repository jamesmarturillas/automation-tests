package com.gengo.automation.pages;

import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.Switcher;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.WorkbenchFilePageFactory;
import org.openqa.selenium.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.AssertJUnit.assertTrue;

/**
 * @class Object repository for for workbench performing file jobs.
 * Contains element operations.
 */
public class WorkbenchFilePage extends WorkbenchFilePageFactory {

    public WorkbenchFilePage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private Switcher switcher = new Switcher(driver);
    private JSExecutor js = new JSExecutor(driver);

    public void downloadFile() {
        getDowndloadFileBtn().click();
        wait.impWait(30, getAssignedJobTxtCriteria());
    }

    public void checkEnsures() {
        js.scrollIntoElement(getMeaningChkbox());
        wait.untilElementIsClickable(getMeaningChkbox());
        getMeaningChkbox().click();
        js.scrollIntoElement(getPunctuationChkbox());
        wait.untilElementIsClickable(getPunctuationChkbox());
        getPunctuationChkbox().click();
        js.scrollIntoElement(getSpellingChkbox());
        wait.untilElementIsClickable(getSpellingChkbox());
        getSpellingChkbox().click();
        js.scrollIntoElement(getGrammarChkbox());
        wait.untilElementIsClickable(getGrammarChkbox());
        getGrammarChkbox().click();
        js.scrollIntoElement(getStyleguideChkbox());
        wait.untilElementIsClickable(getStyleguideChkbox());
        getStyleguideChkbox().click();
    }

    public void uploadTranslatedFile(String file) {
        getUploadTranslatedFileBtn().sendKeys(file);
        switcher.detectUnexpectedAlertAndAccept();
    }

    public void translateFileJob(String file) {
        this.downloadFile();
        this.checkEnsures();
        this.uploadTranslatedFile(file);
        switcher.switchToUnexpectedAlertAndAccept();
    }

    public boolean checkGlossary() {
        boolean state = true;
        try{
            getGlossaryHintHeader().isDisplayed();
            getGlossaryHintDescription().isDisplayed();
            getGlossaryHintOkBtn().isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkGlossaryList(HashMap<String, String> map) {
        boolean state = true;
        try{
            for (Map.Entry<String,String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                driver.findElement(By.xpath("//tr/td[contains(.,'" + key + "')]/following-sibling::td[contains(.,'" + value + "')]")).isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public void checkGlossaryHint() {
        assertTrue(this.checkGlossary());
        getGlossaryHintOkBtn().click();
    }

    public boolean checkGlossaryPage(String jobNo, HashMap<String, String> map, String sourceLang, String targetLang) {
        boolean state = true;
        getViewGlossaryBtn().click();
        switcher.switchToPopUp();
        try {
            wait.untilElementVisible(getTermGlossaryHeader());
            getTermGlossaryDescription().isDisplayed();
            String count = String.valueOf(map.size());
            driver.findElement(By.xpath("//h1[contains(.,'Job " + jobNo + "')]/span[contains(.,'> Term Glossary ')]/span[contains(.,'" + count + "')]")).isDisplayed();
            driver.findElement(By.xpath("//th/h2[contains(.,'Source:')]/em[contains(.,'" + sourceLang + "')]")).isDisplayed();
            driver.findElement(By.xpath("//th/h2[contains(.,'Target:')]/em[contains(.,'" + targetLang + "')]")).isDisplayed();
            assertTrue(this.checkGlossaryList(map));
        }
        catch(NoSuchElementException | TimeoutException e) {
            state = false;
        }
        return state;
    }

    public void translateFileJobWithGlossary(String file) {
        this.checkEnsures();
        getGlossaryChkbox().click();
        this.uploadTranslatedFile(file);
        switcher.detectUnexpectedAlertAndAccept();
    }

    public boolean checkComment(String instruction) {
        boolean state = true;
        try {
            driver.findElement(By.xpath("//p[contains(.,'" + instruction + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public void cancelJob(String reason) {
        wait.impWait(30, getCancelFileJobLInk());
        getCancelFileJobLInk().click();
        wait.untilElementVisible(getModalDecline());
        this.selectDeclineReason(reason);
        getDeclineJobBtn().click();
        wait.impWait(30, getFileJobCancelledMsg());
    }

    public void clickMessageIconClose() {
        wait.impWait(30);
        getMessageCloseIcon().click();
        wait.impWait(30);
    }

    private void selectDeclineReason(String reason) {
        List<WebElement> elements = getFileJobDeclineReasons();
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().contains(reason)) {
                elements.get(i).click();
                wait.impWait(30);
            }
        }
    }
}
