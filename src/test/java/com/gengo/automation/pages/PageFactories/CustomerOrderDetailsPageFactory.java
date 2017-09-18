package com.gengo.automation.pages.PageFactories;

import com.gengo.automation.helpers.Wait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @class Object repository for customer order details page.
 * Contains PageFactory initialization.
 */
public class CustomerOrderDetailsPageFactory {

    protected WebDriver driver;
    protected Wait wait;

    public CustomerOrderDetailsPageFactory(WebDriver driver) {
        this.driver = driver;
        wait = new Wait(this.driver);
        PageFactory.initElements(driver,this);
    }


    /**
     * Element initialization
     */

    /**
     * Common Details (Available, Revising, Reviewable, On Hold, Approved)
     * */
    @FindBy(xpath = "//h2[contains(text(), 'Translation approved!')]")
    WebElement translationApprovedTxt;

    @FindBy(xpath = "//h1[contains(.,'Job #')]")
    WebElement jobNumberHeader;

    @FindBy(xpath = "//a[contains(.,'Order ID: ')]")
    WebElement orderIDLine;

    @FindBy(xpath = "//div[@class='job-status-icon']")
    WebElement jobStatusIcon;

    @FindBy(xpath = "//span[@class='price line1']")
    WebElement orderPrice;

    @FindBy(xpath = "//span[@class='date line2']")
    WebElement orderDate;

    @FindBy(xpath = "//p[@class='line2']")
    WebElement langPairAndWordCount;

    @FindBy(xpath = "//p[@class=''original-text-header]")
    WebElement sourceLanguage;

    @FindBy(xpath = "(//pre)[1]")
    WebElement sourceText;

    @FindBy(xpath = "//a[contains(.,'Add/Read comments')]")
    WebElement addReadCommentsLink;

    @FindBy(xpath = "//a[contains(.,'Hide comments')]")
    WebElement hideComments;

    @FindBy(xpath = "//textarea[@name='body']")
    WebElement commentTextArea;

    @FindBy(xpath = "//a[contains(.,'Submit comment')]")
    WebElement submitComment;

    @FindBy(id = "feedback-form-submit")
    WebElement submitFeedBackBtn;


    /**
     * Available Order Details
     * */
    @FindBy(id = "confirm_order_cancel")
    WebElement cancelOrder;

    /**
     * Pending Order Details
     * */
    @FindBy(xpath = "//div[contains(@class, 'flagged')]")
    WebElement flaggedSection;

    @FindBy(xpath = "//a[@class='resolve-flag orange-link']")
    WebElement removeFlagLink;

    @FindBy(xpath = "//div[@class='flagged resolved']")
    WebElement resolvedFlagSection;

    /**
     * Reviewable Order Details
     * */
    @FindBy(xpath = "//a[@class='confirmapprove ui_btn primary_btn']")
    WebElement approveLink;

    @FindBy(id = "confirm-approve-approve")
    WebElement confirmApprove;

    @FindBy(id = "confirm-approve-back")
    WebElement backApprove;

    @FindBy(xpath = "//a[@class='gengo-modal-close']")
    WebElement closeModal;

    @FindBy(id = "request-corrections-link")
    WebElement requestCorrections;

    @FindBy(xpath = "//input[@id='incomplete']")
    WebElement incompleteCheckBox;

    @FindBy(xpath = "//input[@id='errors']")
    WebElement errorsCheckBox;

    @FindBy(xpath = "//input[@id='meaning']")
    WebElement missedMeaningCheckBox;

    @FindBy(id = "request-corrections-textarea")
    WebElement requestCorrectionComment;

    @FindBy(xpath = "//a[@id='request-corrections-submit']")
    WebElement submitRequestCorrections;

    @FindBy(id = "rejection-form-link")
    WebElement rejectionFormLink;

    @FindBy(id = "rejection-form-inner-link")
    WebElement confirmRejectionLink;

    @FindBy(xpath = "//input[@id='cancel']")
    WebElement cancelAndRefundRadioBtn;

    @FindBy(xpath = "//input[@id='reopen']")
    WebElement otherTranslatorRadioBtn;

    @FindBy(xpath = "//input[@id='rejection_reason_quality']")
    WebElement poorQualityRadioBtn;

    @FindBy(xpath = "//input[@id='rejection_reason_incomplete']")
    WebElement incompleteRadioBtn;

    @FindBy(xpath = "//input[@id='rejection_reason_other']")
    WebElement otherRadioBtn;

    @FindBy(xpath = "//textarea[@name='rejection_comments']")
    WebElement rejectionCommentTextArea;

    @FindBy(xpath = "//a[@id='reject-submit']")
    WebElement submitRejection;

    @FindBy(xpath = "//div[@id='flashdata'][contains(.,'Translation approved!')]")
    WebElement inPageAlertApprove;

    @FindBy(xpath = "//div[@id='flashdata'][contains(.,'Corrections sent to translator')]")
    WebElement inPageAlertCorrection;

    @FindBy(xpath = "//div[@id='flashdata'][contains(.,'Translation to be rejected and resubmitted to another translator - held for review')]")
    WebElement inPageAlertRejection;

    @FindBy(xpath = "//div[@id='flashdata'][contains(.,'Translation rejected - held for review')]")
    WebElement inPageAlertRejectionRefund;

    @FindBy(xpath = "//ol[contains(@class, 'comments-list')]")
    WebElement commentsList;

    @FindBy(xpath = "//ol[contains(@class, 'comments-list')][2]")
    WebElement firstComment;

    @FindBys({@FindBy(xpath = "//ol[contains(@class, 'comments-list')]/li")})
    List<WebElement> commentsAsList;

    /**
     * Approved Order Details
     * */
    @FindBy(xpath = "//a[contains(.,'Printable receipt')]")
    WebElement printableReceipt;

    @FindBy(xpath = "remove-link")
    WebElement archiveJob;

    @FindBy(id = "feedback-toggle-link")
    WebElement feedbackToggle;

    @FindBy(className = "add-preferred")
    WebElement addToPreferredTranslator;

    @FindBy(className = "enable-rating")
    WebElement translationQualityToggle;

    @FindBy(id = "rating_quality_1")
    WebElement rateQuality1RadioBtn;

    @FindBy(id = "rating_quality_2")
    WebElement rateQuality2RadioBtn;

    @FindBy(id = "rating_quality_3")
    WebElement rateQuality3RadioBtn;

    @FindBy(id = "rating_quality_4")
    WebElement rateQuality4RadioBtn;

    @FindBy(id = "rating_quality_5")
    WebElement rateQuality5RadioBtn;

    @FindBy(id = "rating_time_1")
    WebElement rateTime1RadioBtn;

    @FindBy(id = "rating_time_22")
    WebElement rateTime2RadioBtn;

    @FindBy(id = "rating_time_3")
    WebElement rateTime3RadioBtn;

    @FindBy(id = "rating_time_4")
    WebElement rateTime4RadioBtn;

    @FindBy(id = "rating_time_5")
    WebElement rateTime5RadioBtn;

    @FindBy(id = "feedback_translator")
    WebElement feedbackTranslatorComment;

    @FindBy(id = "feedback_mygengo")
    WebElement feedbackGengoComment;

    @FindBy(id = "feedback-form-update")
    WebElement submitFeedback;

    @FindBy(xpath = "//div[@class='gengo-modal']")
    WebElement gengoModal;

    /**
     * Available and Approved Order Details
     * */
    @FindBy(xpath = "//p[class='target-lang']")
    WebElement targetLanguage;

    @FindBy(xpath = "(//pre)[2]")
    WebElement targetText;

    /**
     * Reviewable and Approved Order Details
     * */
    @FindBy(xpath = "//div[@class='translated-by']")
    WebElement translatesByLine;

    /**
     * Getters
     */

    /**
     * Common Details (Available, Revising, Reviewable, On Hold, Approved)
     * */
    public WebElement getTranslationApprovedTxt() {
        return translationApprovedTxt;
    }
    public WebElement getJobNumberHeader() {
        return jobNumberHeader;
    }
    public WebElement getOrderIDLine() {
        return orderIDLine;
    }
    public WebElement getJobStatusIcon() {
        return jobStatusIcon;
    }
    public WebElement getOrderPrice() {
        return orderPrice;
    }
    public WebElement getOrderDate() {
        return orderDate;
    }
    public WebElement getLangPairAndWordCount() {
        return langPairAndWordCount;
    }
    public WebElement getSourceLanguage() {
        return sourceLanguage;
    }
    public WebElement getSourceText() {
        return sourceText;
    }
    public WebElement getAddReadCommentsLink() {
        return addReadCommentsLink;
    }
    public WebElement getHideComments() {
        return hideComments;
    }
    public WebElement getCommentTextArea() {
        return commentTextArea;
    }
    public WebElement getSubmitComment() {
        return submitComment;
    }


    /**
     * Available Order Details
     * */
    public WebElement getCancelOrder() {
        return cancelOrder;
    }

    /**
     * Pending Order Details
     * */
    public WebElement getFlaggedSection(){
        return flaggedSection;
    }

    public WebElement getRemoveFlagLink(){
        return removeFlagLink;
    }

    public WebElement getResolvedFlagSection() {
        return resolvedFlagSection;
    }

    /**
     * Reviewable Order Details
     * */
    public WebElement getApproveLink() {
        return approveLink;
    }
    public WebElement getConfirmApprove() {
        return confirmApprove;
    }
    public WebElement getBackApprove() {
        return backApprove;
    }
    public WebElement getCloseModal() {
        return closeModal;
    }
    public WebElement getRequestCorrections() {
        return requestCorrections;
    }
    public WebElement getIncompleteCheckBox() {
        return incompleteCheckBox;
    }
    public WebElement getErrorsCheckBox() {
        return errorsCheckBox;
    }
    public WebElement getMissedMeaningCheckBox() {
        return missedMeaningCheckBox;
    }
    public WebElement getRequestCorrectionComment() {
        return requestCorrectionComment;
    }
    public WebElement getSubmitRequestCorrections() {
        return submitRequestCorrections;
    }
    public WebElement getRejectionFormLink() {
        return rejectionFormLink;
    }
    public WebElement getConfirmRejectionLink() {
        return confirmRejectionLink;
    }
    public WebElement getCancelAndRefundRadioBtn() {
        return cancelAndRefundRadioBtn;
    }
    public WebElement getOtherTranslatorRadioBtn() {
        return otherTranslatorRadioBtn;
    }
    public WebElement getPoorQualityRadioBtn() {
        return poorQualityRadioBtn;
    }
    public WebElement getIncompleteRadioBtn() {
        return incompleteRadioBtn;
    }
    public WebElement getOtherRadioBtn() {
        return otherRadioBtn;
    }
    public WebElement getRejectionCommentTextArea() {
        return rejectionCommentTextArea;
    }
    public WebElement getSubmitRejection() {
        return submitRejection;
    }
    public WebElement getInPageAlertApprove(){
        return inPageAlertApprove;
    }
    public WebElement getInPageAlertCorrection(){
        return inPageAlertCorrection;
    }
    public WebElement getInPageAlertRejection(){
        return inPageAlertRejection;
    }
    public WebElement getInPageAlertRejectionRefund(){
        return inPageAlertRejectionRefund;
    }
    public WebElement getCommentsList() {
        return commentsList;
    }

    /**
     * Approved Order Details
     * */
    public WebElement getPrintableReceipt() {
        return printableReceipt;
    }
    public WebElement getArchiveJob() {
        return archiveJob;
    }
    public WebElement getFeedbackToggle() {
        return feedbackToggle;
    }
    public WebElement getAddToPreferredTranslator() {
        return addToPreferredTranslator;
    }
    public WebElement getTranslationQualityToggle() {
        return translationQualityToggle;
    }
    public WebElement getRateQuality1RadioBtn() {
        return rateQuality1RadioBtn;
    }
    public WebElement getRateQuality2RadioBtn() {
        return rateQuality2RadioBtn;
    }
    public WebElement getRateQuality3RadioBtn() {
        return rateQuality3RadioBtn;
    }
    public WebElement getRateQuality4RadioBtn() {
        return rateQuality4RadioBtn;
    }
    public WebElement getRateQuality5RadioBtn() {
        return rateQuality5RadioBtn;
    }
    public WebElement getRateTime1RadioBtn() {
        return rateTime1RadioBtn;
    }
    public WebElement getRateTime2RadioBtn() {
        return rateTime2RadioBtn;
    }
    public WebElement getRateTime3RadioBtn() {
        return rateTime3RadioBtn;
    }
    public WebElement getRateTime4RadioBtn() {
        return rateTime4RadioBtn;
    }
    public WebElement getRateTime5RadioBtn() {
        return rateTime5RadioBtn;
    }
    public WebElement getFeedbackTranslatorComment() {
        return feedbackTranslatorComment;
    }
    public WebElement getFeedbackGengoComment() {
        return feedbackGengoComment;
    }
    public WebElement getSubmitFeedback() {
        return submitFeedback;
    }
    public WebElement getSubmitFeedBackBtn() {
        return submitFeedBackBtn;
    }
    public WebElement getGengoModal() {
        return gengoModal;
    }
    public List<WebElement> getCommentsAsList() {
        return commentsAsList;
    }

    /**
     * Available and Approved Order Details
     * */
    public WebElement getTargetLanguage() {
        return targetLanguage;
    }
    public WebElement getTargetText() {
        return targetText;
    }

    /**
     * Reviewable and Approved Order Details
     * */
    public WebElement getTranslatesByLine() {
        return translatesByLine;
    }
    public WebElement getFirstComment(boolean isTranslatorsCommentCheck) {
        if (isTranslatorsCommentCheck) {
            while (!firstComment.getText().contains("You")) {
                try {
                    wait.impWait(30);
                }
                catch (StaleElementReferenceException e) {
                    firstComment = driver.findElement(By.xpath("//ul[@class='list-unstyled']/li[2]"));
                    continue;
                }
            }
        }
        return firstComment;
    }
}
