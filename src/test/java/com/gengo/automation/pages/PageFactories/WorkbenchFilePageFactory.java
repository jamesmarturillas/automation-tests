package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @class Page factory for workbench performing file jobs.
 * Contains PageFactory initialization.
 */
public class WorkbenchFilePageFactory {

    protected WebDriver driver;

    public WorkbenchFilePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    /**
     * Element initialization
     */
    @FindBy(id = "start_job_button")
    WebElement downdloadFileBtn;

    @FindBy(xpath = "//p[contains(text(), 'You are now assigned to this job.')]")
    WebElement assignedJobTxtCriteria;

    @FindBy(xpath = "//input[@name='file']")
    WebElement uploadTranslatedFileBtn;

    @FindBy(xpath = "//a[@class='confirm_cancel remove-link']")
    WebElement cancelFileJobLInk;

    @FindBy(id = "meaning")
    WebElement meaningChkbox;

    @FindBy(id = "punctuation")
    WebElement punctuationChkbox;

    @FindBy(id = "spelling")
    WebElement spellingChkbox;

    @FindBy(id = "grammar")
    WebElement grammarChkbox;

    @FindBy(id = "styleguide")
    WebElement styleguideChkbox;

    @FindBy(id = "glossary")
    WebElement glossaryChkbox;

    @FindBy(xpath = "//h1[contains(text(), 'Pending Customer Review')]")
    WebElement finishFileJobTxtCriteria;

    @FindBy(xpath = "//div[@class='hint']/h4")
    WebElement glossaryHintHeader;

    @FindBy(xpath = "//div[@class='hint']/p[contains(.,'The customer has suggested using these highlighted terms in your translations. Please override these suggestions in situations where the terms do not make sense in context.')]")
    WebElement glossaryHintDescription;

    @FindBy(xpath = "//a[@class='string-job-glossary-ok-button primary_btn button_medium'][contains(.,'OK, Got it')]")
    WebElement glossaryHintOkBtn;

    @FindBy(xpath = "//a[@class='ui_btn'][contains(.,'View Glossary')]")
    WebElement viewGlossaryBtn;

    @FindBy(xpath = "//p[contains(.,'You may only work on 1 job at a time.')]")
    WebElement unableToPickAnotherJobErrMsg;

    @FindBy(id = "gengo-confirm-decline")
    WebElement modalDecline;

    @FindBy(xpath = "//input[@class='ui_btn core_btn confirm-decline-decline']")
    WebElement declineJobBtn;

    @FindBys({@FindBy(xpath = "//form[@id='form-decline-job']/label")})
    List<WebElement> fileJobDeclineReasons;

    @FindBy(xpath = "//i[@class='icon-close']")
    WebElement messageCloseIcon;

    /** View Glossary Elements */
    @FindBy(xpath = "//h2[contains(.,'Term Glossary')]")
    WebElement termGlossaryHeader;

    @FindBy(xpath = "//p[contains(.,'The customer wants you to use these highlighted terms in your translation. Please think carefully before overriding this suggestion.')]")
    WebElement termGlossaryDescription;

    @FindBy(xpath = "//div[@class='alert alert-overlay alert-success' and contains(., 'You have cancelled')]")
    WebElement fileJobCancelledMsg;

    /**
     * Getters
     */
    public WebElement getDowndloadFileBtn() {
        return downdloadFileBtn;
    }

    public WebElement getAssignedJobTxtCriteria() {
        return assignedJobTxtCriteria;
    }

    public WebElement getUploadTranslatedFileBtn() {
        return uploadTranslatedFileBtn;
    }

    public WebElement getCancelFileJobLInk() {
        return cancelFileJobLInk;
    }

    public WebElement getMeaningChkbox() {
        return meaningChkbox;
    }

    public WebElement getPunctuationChkbox() {
        return punctuationChkbox;
    }

    public WebElement getSpellingChkbox() {
        return spellingChkbox;
    }

    public WebElement getGrammarChkbox() {
        return grammarChkbox;
    }

    public WebElement getStyleguideChkbox() {
        return styleguideChkbox;
    }

    public WebElement getGlossaryChkbox() {
        return glossaryChkbox;
    }

    public WebElement getFinishFileJobTxtCriteria() {
        return finishFileJobTxtCriteria;
    }

    public WebElement getGlossaryHintHeader() {
        return glossaryHintHeader;
    }

    public WebElement getGlossaryHintDescription() {
        return glossaryHintDescription;
    }

    public WebElement getGlossaryHintOkBtn() {
        return glossaryHintOkBtn;
    }

    public WebElement getViewGlossaryBtn() {
        return viewGlossaryBtn;
    }

    public WebElement getTermGlossaryHeader() {
        return termGlossaryHeader;
    }

    public WebElement getModalDecline() {
        return modalDecline;
    }

    public WebElement getDeclineJobBtn() {
        return declineJobBtn;
    }

    public List<WebElement> getFileJobDeclineReasons() {
        return fileJobDeclineReasons;
    }

    public WebElement getMessageCloseIcon() {
        return messageCloseIcon;
    }

    public WebElement getTermGlossaryDescription() {
        return termGlossaryDescription;
    }

    public WebElement getUnableToPickAnotherJobErrMsg() {
        return unableToPickAnotherJobErrMsg;
    }

    public WebElement getFileJobCancelledMsg() {
        return fileJobCancelledMsg;
    }
}
