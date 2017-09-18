package com.gengo.automation.pages;

import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GmailInboxPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @class Object repository for Gmail email inputting page.
 * Contains element operations.
 */
public class GmailInboxPage extends GmailInboxPageFactory {

    public GmailInboxPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    public void clickActivateGengoEmail() {
        wait.untilElementIsClickable(getActivateYourGengoEmail());
        getActivateYourGengoEmail().click();
        wait.impWait(20);
    }

    public void clickWelcomeToGengoEmail() {
        getWelcomeToGengoEmail().click();
        wait.impWait(20);
    }

    public void clickClosedAccountEmail() {
        getAcctClosedEmail().click();
        wait.impWait(20);
    }

    public void clickChangeEmail() {
        getChangeEmail().click();
        wait.impWait(20);
    }

    public void clickTopUpCompleteEmail() {
        wait.impWait(30, getTopUpCompleteEmail());
        getTopUpCompleteEmail().click();
        wait.impWait(20);
    }

    public void clickTranslatorComment(String customerNameToCheck) {
        wait.impWait(30);
        By translatorCommentWithName = By.xpath("//span[contains(.,'new comment')]/parent::div/span[2][contains(text(), '" + customerNameToCheck + "')]");
        WebElement translatorComment = driver.findElement(translatorCommentWithName);
        js.scrollIntoElement(translatorComment);
        translatorComment.click();
    }

    public boolean checkOrderReceived(String orderNumber) {
        boolean state;
        WebElement email = driver.findElement(By.xpath("//span[contains(., 'Gengo')]/parent::div/parent::td/following-sibling::td/following-sibling::td/div/div/div/span/b[contains(., 'Gengo Order #" + orderNumber + " received')]"));
        try {
            state = email.isDisplayed();
        }
        catch (NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkJobForReview(String jobNumber) {
        boolean state;
        WebElement email = driver.findElement(By.xpath("//span[contains(., 'Gengo')]/parent::div/parent::td/following-sibling::td/following-sibling::td/div/div/div/span/b[contains(., 'Gengo Job #" + jobNumber + " ready for review')]"));
        try {
            state = email.isDisplayed();
        }
        catch (NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkJobFlagged(String jobNumber) {
        boolean state;
        WebElement email = driver.findElement(By.xpath("//span[contains(., 'Gengo')]/parent::div/parent::td/following-sibling::td/following-sibling::td/div/div/div/span/b[contains(., 'Gengo Job #" + jobNumber + " flagged')]"));
        try {
            state = email.isDisplayed();
        }
        catch (NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkJobApproved(String jobNumber){
        boolean state;
        WebElement email = driver.findElement(By.xpath("//span[contains(., 'Gengo')]/parent::div/parent::td/following-sibling::td/following-sibling::td/div/div/div/span/b[contains(., 'Gengo Job #" + jobNumber + " approved')]"));
        try {
            state = email.isDisplayed();
        }
        catch (NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkJobReviewed(String jobNumber){
        boolean state;
        WebElement email = driver.findElement(By.xpath("//span[contains(., 'Gengo')]/parent::div/parent::td/following-sibling::td/following-sibling::td/div/div/div/span/b[contains(., 'Gengo Job #" + jobNumber + " reviewed')]"));
        try {
            state = email.isDisplayed();
        }
        catch (NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public void clickJobReviewed(String jobNumber) {
        wait.impWait(30);
        By jobReviewedMail = By.xpath("//span[contains(.,'reviewed')]/parent::div/span[2][contains(text(), '" + jobNumber + "')]");
        WebElement jobReview = driver.findElement(jobReviewedMail);
        js.scrollIntoElement(jobReview);
        jobReview.click();
    }

    public boolean checkNewComment(String jobNumber) {
        boolean state;
        WebElement email = driver.findElement(By.xpath("//span[contains(., 'Gengo')]/parent::div/parent::td/following-sibling::td/following-sibling::td/div/div/div/span/b[contains(., 'Gengo Job " + jobNumber + " new comment')]"));
        try {
            state = email.isDisplayed();
        }
        catch (NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public void clickNewComment(String jobNumber) {
        WebElement email = driver.findElement(By.xpath("//span[contains(., 'Gengo')]/parent::div/parent::td/following-sibling::td/following-sibling::td/div/div/div/span/b[contains(., 'Gengo Job " + jobNumber + " new comment')]"));
        email.click();
    }

    public boolean checkNewCommentVisible(String commentText){
        boolean state;
        WebElement comment = driver.findElement(By.xpath("//p[contains(.,'" + commentText + "')]"));
        try {
            state = comment.isDisplayed();
        }
        catch (NoSuchElementException e){
            state = false;
        }
        return state;
    }
}
