package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for GoCheck page.
 */
public class GoCheckPageFactory {

    protected WebDriver driver;

    public GoCheckPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** 'GoChecks' locators */
    @FindBy(xpath = "//h1[@id='gengo-navigation']/a[1]//a[contains(.,'Translator')]")
    WebElement navAdminTranslatorLink;

    @FindBy(xpath = "//h1[@id='gengo-navigation']/a[2]//a[contains(.,'Job')]")
    WebElement navAdminJobLink;

    @FindBy(xpath = "//h1[@id='gengo-navigation']/a[3]//a[contains(.,'comments')]")
    WebElement navAdminJobCommentsLink;

    @FindBy(xpath = "//h1[@id='gengo-navigation']/a[4]//a[contains(.,'Check')]")
    WebElement navAdminCheckLink;

    @FindBy(xpath = "//h1[@id='gengo-navigation']/a[5]//a[contains(.,'comments')]")
    WebElement navAdminCheckCommentsLink;

    @FindBy(xpath = ".//*[@id='content']/div/p")
    WebElement gocheckTargetContent;

    @FindBy(xpath = "//textarea[@id='id_fb']")
    WebElement gocheckFeedbackTextArea;

    // Submit or Update button
    @FindBy(xpath = "//button[@class='btn btn-primary']")
    WebElement gocheckFirstSubmitBtn;

    @FindBy(xpath = "//div[@class='navbar-inner']")
    WebElement gocheckNavBar;

    /** Annotator pop-up locators for adding markings */
    @FindBy(xpath = "//button[contains(.,'Annotate')]")
    WebElement annotatorButton;

    @FindBy(xpath = "//form[@class='annotator-widget']")
    WebElement annotatorWidget;

    @FindBy(xpath = "//form[@id='annotator-field-0']")
    WebElement annotatorSelectQualityIssue;

    @FindBy(xpath = "//option[@value='WT_Maj']")
    WebElement annotatorQualityIssueWrongTermMaj;

    @FindBy(xpath = "//option[@value='WT_Min']")
    WebElement annotatorQualityIssueWrongTermMin;

    @FindBy(xpath = "//option[@value='SE_Maj']")
    WebElement annotatorQualityIssueSyntacticErrorMaj;

    @FindBy(xpath = "//option[@value='SE_Min']")
    WebElement annotatorQualityIssueSyntacticErrorMin;

    @FindBy(xpath = "//option[@value='Om_Maj']")
    WebElement annotatorQualityIssueOmissionMaj;

    @FindBy(xpath = "//option[@value='Om_Min']")
    WebElement annotatorQualityIssueOmissionMin;

    @FindBy(xpath = "//option[@value='SA_Maj']")
    WebElement annotatorQualityIssueWordStructureMaj;

    @FindBy(xpath = "//option[@value='SA_Min']")
    WebElement annotatorQualityIssueWordStructureMin;

    @FindBy(xpath = "//option[@value='SP_Maj']")
    WebElement annotatorQualityIssueMisspellingMaj;

    @FindBy(xpath = "//option[@value='SP_Min']")
    WebElement annotatorQualityIssueMisspellingMin;

    @FindBy(xpath = "//option[@value='PE_Maj']")
    WebElement annotatorQualityIssuePunctuationErrorMaj;

    @FindBy(xpath = "//option[@value='PE_Min']")
    WebElement annotatorQualityIssuePunctuationErrorMin;

    @FindBy(xpath = "//textarea[@id='annotator-field-1']")
    WebElement annotatorCommentField;

    @FindBy(xpath = "//a[@class='annotator-cancel']")
    WebElement annotatorCancelBtn;

    @FindBy(xpath = "//a[@class='annotator-save']")
    WebElement annotatorSaveBtn;

    /** Confirmation modal locators for submission */
    @FindBy(xpath = "//div[@id='confirmationModal']")
    WebElement submitConfirmationModal;

    @FindBy(xpath = "//button[@id='closeButton'][contains(.,'×')]")
    WebElement closeSubmitConfirmationModal;

    @FindBy(xpath = "//button[@id='cancelConfirmBtn'][contains(.,'Cancel')]")
    WebElement cancelSubmitConfirmationModal;

    @FindBy(xpath = "//button[@id='completedConfirmBtn'][contains(.,'Submit feedback')]")
    WebElement confirmSubmitConfirmationModal;

    @FindBy(xpath = "//div[@id='confirmationModal']/div[3]/p[@class='contact']/a")
    WebElement emailLinkConfirmationModal;

    @FindBy(xpath = "//span[@id='score']")
    WebElement scoreDisplayConfirmationModal;

    /**
     * Getters
     */
    public WebElement getNavAdminTranslatorLink() {
        return navAdminTranslatorLink;
    }

    public WebElement getNavAdminJobLink() {
        return navAdminJobLink;
    }

    public WebElement getNavAdminJobCommentsLink() {
        return navAdminJobCommentsLink;
    }

    public WebElement getNavAdminCheckLink() {
        return navAdminCheckLink;
    }

    public WebElement getNavAdminCheckCommentsLink() {
        return navAdminCheckCommentsLink;
    }

    public WebElement getGocheckTargetContent() {
        return gocheckTargetContent;
    }

    public WebElement getGocheckFeedbackTextArea() {
        return gocheckFeedbackTextArea;
    }

    public WebElement getGocheckFirstSubmitBtn() {
        return gocheckFirstSubmitBtn;
    }

    public WebElement getGocheckNavBar() {
        return gocheckNavBar;
    }

    public WebElement getAnnotatorButton() {
        return annotatorButton;
    }

    public WebElement getAnnotatorWidget() {
        return annotatorWidget;
    }

    public WebElement getAnnotatorSelectQualityIssue() {
        return annotatorSelectQualityIssue;
    }

    public WebElement getAnnotatorQualityIssueWrongTermMaj() {
        return annotatorQualityIssueWrongTermMaj;
    }

    public WebElement getAnnotatorQualityIssueWrongTermMin() {
        return annotatorQualityIssueWrongTermMin;
    }

    public WebElement getAnnotatorQualityIssueSyntacticErrorMaj() {
        return annotatorQualityIssueSyntacticErrorMaj;
    }

    public WebElement getAnnotatorQualityIssueSyntacticErrorMin() {
        return annotatorQualityIssueSyntacticErrorMin;
    }

    public WebElement getAnnotatorQualityIssueOmissionMaj() {
        return annotatorQualityIssueOmissionMaj;
    }

    public WebElement getAnnotatorQualityIssueOmissionMin() {
        return annotatorQualityIssueOmissionMin;
    }

    public WebElement getAnnotatorQualityIssueWordStructureMaj() {
        return annotatorQualityIssueWordStructureMaj;
    }

    public WebElement getAnnotatorQualityIssueWordStructureMin() {
        return annotatorQualityIssueWordStructureMin;
    }

    public WebElement getAnnotatorQualityIssueMisspellingMaj() {
        return annotatorQualityIssueMisspellingMaj;
    }

    public WebElement getAnnotatorQualityIssueMisspellingMin() {
        return annotatorQualityIssueMisspellingMin;
    }

    public WebElement getAnnotatorQualityIssuePunctuationErrorMaj() {
        return annotatorQualityIssuePunctuationErrorMaj;
    }

    public WebElement getAnnotatorQualityIssuePunctuationErrorMin() {
        return annotatorQualityIssuePunctuationErrorMin;
    }

    public WebElement getAnnotatorCommentField() {
        return annotatorCommentField;
    }

    public WebElement getAnnotatorCancelBtn() {
        return annotatorCancelBtn;
    }

    public WebElement getAnnotatorSaveBtn() {
        return annotatorSaveBtn;
    }

    public WebElement getSubmitConfirmationModal() {
        return submitConfirmationModal;
    }

    public WebElement getCloseSubmitConfirmationModal() {
        return closeSubmitConfirmationModal;
    }

    public WebElement getCancelSubmitConfirmationModal() {
        return cancelSubmitConfirmationModal;
    }

    public WebElement getConfirmSubmitConfirmationModal() {
        return confirmSubmitConfirmationModal;
    }

    public WebElement getEmailLinkConfirmationModal() {
        return emailLinkConfirmationModal;
    }

    public WebElement getScoreDisplayConfirmationModal() {
        return scoreDisplayConfirmationModal;
    }
}
