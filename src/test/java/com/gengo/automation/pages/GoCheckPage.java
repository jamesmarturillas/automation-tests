package com.gengo.automation.pages;

import com.gengo.automation.helpers.ElementActions;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GoCheckPageFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @class Object repository for GoCheck page.
 * Contains element operations.
 */
public class GoCheckPage extends GoCheckPageFactory {

    public GoCheckPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    private ElementActions action = new ElementActions(driver);
    public String checkScore;

    public void clickSubmitGocheck() {
        getGocheckFirstSubmitBtn().click();
        wait.untilElementVisible(getSubmitConfirmationModal());
    }

    public void clickSubmitConfirmationModal(String action) {
        switch (action) {
            case "close":
                getCloseSubmitConfirmationModal().click();
                wait.impWait(30);
                break;
            case "cancel":
                getCancelSubmitConfirmationModal().click();
                wait.impWait(30);
                break;
            case "confirm":
                getConfirmSubmitConfirmationModal().click();
                wait.impWait(30);
                break;
        }
    }

    public void writeFeedbackToTranslator(String feedback) {
        getGocheckFeedbackTextArea().clear();
        getGocheckFeedbackTextArea().sendKeys(feedback);
        wait.impWait(30);
    }

    public void setCheckScore() {
        this.checkScore = getScoreDisplayConfirmationModal().getText();
        wait.impWait(30);
    }

    public String retrieveScore() {
        return this.checkScore;
    }

    public void addQualityIssueAnnotation(String issue) {
        getAnnotatorButton().click();
        getAnnotatorSelectQualityIssue().click();
        switch (issue) {
            case "wrongTermMajor":
                getAnnotatorQualityIssueWrongTermMaj().click();
                wait.impWait(30);
                break;
            case "wrongTermMinor":
                getAnnotatorQualityIssueWrongTermMin().click();
                wait.impWait(30);
                break;
            case "syntacticErrorMajor":
                getAnnotatorQualityIssueSyntacticErrorMaj().click();
                wait.impWait(30);
                break;
            case "syntacticErrorMinor":
                getAnnotatorQualityIssueSyntacticErrorMin().click();
                wait.impWait(30);
                break;
            case "omissionMajor":
                getAnnotatorQualityIssueOmissionMaj().click();
                wait.impWait(30);
                break;
            case "omissionMinor":
                getAnnotatorQualityIssueOmissionMin().click();
                wait.impWait(30);
                break;
            case "wordStructureMajor":
                getAnnotatorQualityIssueWordStructureMaj().click();
                wait.impWait(30);
                break;
            case "wordStructureMinor":
                getAnnotatorQualityIssueWordStructureMin().click();
                wait.impWait(30);
                break;
            case "misspellingMajor":
                getAnnotatorQualityIssueMisspellingMaj().click();
                wait.impWait(30);
                break;
            case "misspellingMinor":
                getAnnotatorQualityIssueMisspellingMin().click();
                wait.impWait(30);
                break;
            case "punctuationErrorMajor":
                getAnnotatorQualityIssuePunctuationErrorMaj().click();
                wait.impWait(30);
                break;
            case "punctuationErrorMinor":
                getAnnotatorQualityIssuePunctuationErrorMin().click();
                wait.impWait(30);
                break;
        }
    }

    public void addCommentAnnotation(String issueComment) {
        getAnnotatorCommentField().clear();
        getAnnotatorCommentField().sendKeys(issueComment);
        wait.impWait(30);
    }

    public void saveAnnotation() {
        getAnnotatorSaveBtn().click();
        wait.impWait(30);
    }

    public void cancelAnnotation() {
        getAnnotatorCancelBtn().click();
        wait.impWait(30);
    }

    public void highlightText(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        for (int iCnt = 0; iCnt < 3; iCnt++) {
            //Execute javascript
            js.executeScript("arguments[0].style.border='4px groove green'", element);
            wait.impWait(30);
            js.executeScript("arguments[0].style.border=''", element);
            action.doubleClick(element);
            wait.impWait(50, getAnnotatorButton());
        }
    }

    public void highlightTarget(WebElement element) {
        action.doubleClick(element);
        wait.impWait(30, getAnnotatorButton());
    }
}
