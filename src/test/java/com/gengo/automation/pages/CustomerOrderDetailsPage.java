package com.gengo.automation.pages;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.helpers.ElementState;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.CustomerOrderDetailsPageFactory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;

import java.io.IOException;
import java.util.List;

/**
 * @class Object repository for customer order details page.
 * Contains element operations.
 */
public class CustomerOrderDetailsPage extends CustomerOrderDetailsPageFactory {

    public CustomerOrderDetailsPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    private PageActions pageActions = new PageActions(driver);
    private ElementState elementState = new ElementState(driver);

    public void approveJob() {
        wait.untilElementIsClickable(getApproveLink());
        getApproveLink().click();
        wait.untilElementVisible(getConfirmApprove());
        getConfirmApprove().click();
        wait.untilElementVisible(getInPageAlertApprove());
    }

    public void approveJob(boolean hasToClickCancelOrCloseButton) {
        wait.untilElementIsClickable(getApproveLink());
        getApproveLink().click();
        wait.untilElementVisible(getConfirmApprove());
        if (hasToClickCancelOrCloseButton) {
            getBackApprove().click();
            elementState.assureElementNotDisplayed(getGengoModal());
            pageActions.refresh();
            wait.impWait(30, getApproveLink());
            getApproveLink().click();
            getCloseModal().click();
            elementState.assureElementNotDisplayed(getGengoModal());
            pageActions.refresh();
            wait.impWait(30, getApproveLink());
            getApproveLink().click();
            wait.impWait(30, getConfirmApprove());
        }
        getConfirmApprove().click();
        wait.untilElementVisible(getInPageAlertApprove());
    }

    public void addComment(String comment) {
        wait.impWait(30);
        if (!getCommentsList().isDisplayed()) getAddReadCommentsLink().click();
        js.scrollIntoElement(getCommentTextArea());
        getCommentTextArea().sendKeys(comment);
        wait.untilElementIsClickable(getSubmitComment());
        js.scrollIntoElement(getSubmitComment());
        getSubmitComment().click();
        wait.untilElementVisible(driver.findElement(By.xpath("//p[contains(.,'" + comment + "')]")));
    }

    public boolean checkCommentVisible(String comment) {
        wait.untilElementVisible(getAddReadCommentsLink());
        js.click(getAddReadCommentsLink());
        Boolean isDisplayed = false;
        WebElement commentLine;
        try {
            commentLine = driver.findElement(By.xpath("//p[contains(.,'" + comment + "')]"));
            wait.untilElementVisible(commentLine);
            isDisplayed = commentLine.isDisplayed();
        }
        catch(NoSuchElementException e){}
        return isDisplayed;
    }

    public Boolean checkCommentVisible(List<String> comments) {
        wait.untilElementVisible(getAddReadCommentsLink());
        getAddReadCommentsLink().click();
        Boolean isDisplayed = false;
        WebElement commentLine;
        try {
            for(String comment : comments) {
                commentLine = driver.findElement(By.xpath("//p[contains(.,'" + comment + "')]"));
                isDisplayed = commentLine.isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean lastCommentHasBracketAndHtml() {
        int commentsTreeAmount = getCommentsAsList().size();
        if (getCommentsAsList().get(commentsTreeAmount - 2).isDisplayed()) { // 2 is the value since `commentsTreeAmount` will contain absolute amount of <li> in the parent... while the `get()` method asks for index number and the last added comment is in the second-to-the-last location of the tree.
            if (getCommentsAsList().get(commentsTreeAmount - 2).findElement(By.xpath("//p[2]")).getText().contains("[[[") && getCommentsAsList().get(commentsTreeAmount - 2).findElement(By.xpath("//p[2]")).getText().contains("]]]")
                    && getCommentsAsList().get(commentsTreeAmount - 2).findElement(By.xpath("//p[2]")).getText().contains("<") && getCommentsAsList().get(commentsTreeAmount - 2).findElement(By.xpath("//p[2]")).getText().contains(">")) {
                return true;
            }
        }
        return false;
    }

    public void requestCorrection(String comment, boolean incomplete, boolean grammar, boolean meaning) {
        wait.untilElementVisible(getRequestCorrections());
        getRequestCorrections().click();
        wait.untilElementVisible(getRequestCorrectionComment());
        if(incomplete)
            getIncompleteCheckBox().click();
        if(grammar)
            getErrorsCheckBox().click();
        if(meaning)
            getMissedMeaningCheckBox().click();
        getRequestCorrectionComment().sendKeys(comment);
        getSubmitRequestCorrections().click();
        wait.untilElementVisible(getInPageAlertCorrection());
    }

    public void rejectTranslation(String feedback, boolean cancel, String reason) {
        wait.untilElementVisible(getRejectionFormLink());
        getRejectionFormLink().click();
        wait.untilElementIsClickable(getConfirmRejectionLink());
        getConfirmRejectionLink().click();
        if(cancel){
            wait.untilElementVisible(getCancelAndRefundRadioBtn());
            getCancelAndRefundRadioBtn().click();
        }
        else {
            wait.untilElementVisible(getOtherTranslatorRadioBtn());
            getOtherTranslatorRadioBtn().click();
        }
        switch(reason) {
            case Constants.REJECTION_REASON_QUALITY:
                wait.untilElementIsClickable(getPoorQualityRadioBtn());
                getPoorQualityRadioBtn().click();
                break;
            case Constants.REJECTION_REASON_INCOMPLETE:
                wait.untilElementIsClickable(getIncompleteRadioBtn());
                getIncompleteRadioBtn().click();
                break;
            case Constants.REJECTION_REASON_OTHER:
                wait.untilElementIsClickable(getOtherRadioBtn());
                getOtherRadioBtn().click();
                break;
        }
        js.scrollIntoElement(getRejectionCommentTextArea());
        getRejectionCommentTextArea().sendKeys(feedback);
        getRejectionCommentTextArea().submit();
        if (cancel) {
            wait.untilElementVisible(getInPageAlertRejectionRefund());
        }
        else {
            wait.untilElementVisible(getInPageAlertRejection());
        }
    }

    public void hideComments() {
        wait.untilElementIsClickable(getHideComments());
        getHideComments().click();
    }

    public Boolean isFlagVisible() {
        try {
            return getFlaggedSection().isDisplayed();
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    public void resolveFlag() {
        wait.untilElementVisible(getRemoveFlagLink());
        getRemoveFlagLink().click();
        pageActions.refresh();
        wait.untilElementIsClickable(getAddReadCommentsLink());
        js.scrollIntoElement(getResolvedFlagSection());
        getAddReadCommentsLink().click();
    }

    public String getJobNumberAvailableJob() {
        String jobNo = StringUtils.substringBetween(driver
                .findElement(By.xpath("//h1[contains(.,' - Available')]")).getText(), "Job ", " - Available");
        return jobNo;
    }

    public String getJobNumberReviewableJob() {
        String jobNo = StringUtils.substringBetween(driver
                .findElement(By.xpath("//h1[contains(.,' - Reviewable')]")).getText(), "Job ", " - Reviewable");
        return jobNo;
    }

    public void cancelOrder(){
        js.scrollIntoElement(getCancelOrder());
        getCancelOrder().click();
        wait.untilElementVisible(driver.findElement(By.xpath("//div[@id='flashdata']/h2[contains(.,'Translation cancelled!')]")));
    }

    public boolean cancelOrderNotFound() {
        boolean state = false;
        try{
            getCancelOrder().isDisplayed();
        }
        catch(NoSuchElementException e){
            state = true;
        }
        return state;
    }

    public void clickAddReadComments() {
        wait.impWait(10);
        getAddReadCommentsLink().click();
        wait.impWait(10);
    }

    public boolean checkInstruction(String instruction) {
        boolean state = true;
        try {
            driver.findElement(By.xpath("//*[contains(.,'" + instruction + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean isCommentPlain(String comment, boolean commentFromTranslator) {
        while (!getCommentsAsList().get(getCommentsAsList().size() - 2).getText().contains(comment)) {
            try {
                wait.impWait(30);
            } catch (StaleElementReferenceException e) {}
        }
        return getCommentsAsList().get(getCommentsAsList().size() - 2).getText().contains(comment);
    }
}
