package com.gengo.automation.pages;

import com.gengo.automation.global.GlobalMethods;
import com.gengo.automation.helpers.ElementState;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.TranslatorJobsPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * @class Object repository for translator tests page.
 * Contains element operations.
 */
public class TranslatorJobsPage extends TranslatorJobsPageFactory {

    public TranslatorJobsPage(WebDriver driver) throws IOException, AWTException {
        super(driver);
    }
    private JSExecutor js = new JSExecutor(driver);
    private Wait wait = new Wait(driver);
    private WorkbenchPage workbenchPage = new WorkbenchPage(driver);
    private PageActions page = new PageActions(driver);
    private ElementState elementState = new ElementState(driver);

    public void findJob(String jobExcerpt){
        String path = "//td/a[contains(., '" + jobExcerpt + "')][last()]";
        List<WebElement> jobDetails = driver.findElements(By.xpath(path));
        boolean elementFound = false;
        while(!elementFound){
            if(jobDetails.size() > 0){
                elementFound = true;
                jobDetails.get(0).click();
            }
            else{
                js.scrollIntoElement(getNextButton());
                getNextButton().click();
            }
            jobDetails = driver.findElements(By.xpath(path));
        }
    }

    public void findJob(String jobExcerpt, String langPair) {
        String path = "//td/a[contains(., '" + langPair + "')]/parent::td/following-sibling::td/a[contains(.,' " + jobExcerpt + " ')][last()]";
        List<WebElement> jobDetails = driver.findElements(By.xpath(path));
        boolean elementFound = false;
        while(!elementFound){
            if(jobDetails.size() > 0){
                elementFound = true;
                jobDetails.get(0).click();
            }
            else{
                js.scrollIntoElement(getNextButton());
                getNextButton().click();
            }
            jobDetails = driver.findElements(By.xpath(path));
        }
    }

    public void findJob(String jobExcerpt, String[] jobId) throws IOException {
        By path = By.xpath("//td/a[contains(., '" + jobExcerpt + "')][last()]");
        List<WebElement> jobDetails = driver.findElements(path);
        GlobalMethods globalMethods = new GlobalMethods(driver);
        String currURL;
        boolean elementFound = false;
        int i = 0;
        while(!elementFound) {
            if(jobDetails.size() > 0) {
                elementFound = true;
                try {
                    while(!globalMethods.retryingFindClick(jobDetails.get(i))) {
                        driver.navigate().refresh();
                        wait.impWait(30);
                        jobDetails = driver.findElements(path);
                        wait.impWait(30);
                        continue;
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    currURL = page.getCurrentUrl();
                    js.scrollIntoElement(getNextButton());
                    getNextButton().click();
                    if (!page.getCurrentUrl().equals(currURL)) {
                        jobDetails = driver.findElements(path);
                        elementFound = false;
                        continue;
                    }
                    else {
                        break;
                    }

                }

                if (jobId != null) {
                    workbenchPage.closeWorkbenchModal();
                    if (!workbenchPage.isCorrectCollection(jobId[0])) {
                        elementFound = false;
                        i++;
                        continue;
                    }
                    else {
                        workbenchPage.closeWorkbenchModal();
                        workbenchPage.startTranslate();
                        workbenchPage.clickHighlightedWords();
                        workbenchPage.submitJob();
                        workbenchPage.submitModalOk();
                        page.goBack();
                        continue;
                    }
                }
            }
            else {
                js.scrollIntoElement(getNextButton());
                getNextButton().click();
            }
            jobDetails = driver.findElements(path);
        }
    }

    public void findJob(String jobExcerpt, List<String> jobIds) throws IOException {
        By path = By.xpath("//td/a[contains(., '" + jobExcerpt + "')][last()]");
        List<WebElement> jobDetails = driver.findElements(path);
        GlobalMethods globalMethods = new GlobalMethods(driver);
        String currURL;
        boolean elementFound = false;
        int i = 0;
        while(!elementFound) {
            if(jobDetails.size() > 0) {
                elementFound = true;
                try {
                    while(!globalMethods.retryingFindClick(jobDetails.get(i))) {
                        driver.navigate().refresh();
                        wait.impWait(30);
                        jobDetails = driver.findElements(path);
                        wait.impWait(30);
                        elementFound = false;
                        continue;
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    currURL = page.getCurrentUrl();
                    js.scrollIntoElement(getNextButton());
                    getNextButton().click();
                    if (!page.getCurrentUrl().equals(currURL)) {
                        jobDetails = driver.findElements(path);
                        elementFound = false;
                        i = 0;
                        continue;
                    }
                    else {
                        break;
                    }
                }

                if (jobIds.size() != 0) {
                    if (!workbenchPage.isCorrectCollection(jobIds)) {
                        elementFound = false;
                        i++;
                        continue;
                    }
                }
            }
            else {
                if (!page.getCurrentUrl().contains("t/workbench/")) {
                    js.scrollIntoElement(getNextButton());
                    getNextButton().click();
                    wait.impWait(30);
                    i = 0;
                }
                else {
                    break;
                }
            }
            jobDetails = driver.findElements(path);
        }
    }

    public void findJob(String jobExcerpt, List<String> jobIds, boolean isNonGrouped) throws IOException {
        By path = By.xpath("//td/a[contains(., '" + jobExcerpt + "')][last()]");
        List<WebElement> jobDetails = driver.findElements(path);
        GlobalMethods globalMethods = new GlobalMethods(driver);
        String currURL;
        boolean elementFound = false;
        int i = 0;
        while(!elementFound) {
            if(jobDetails.size() > 0) {
                elementFound = true;
                try {
                    while(!globalMethods.retryingFindClick(jobDetails.get(i))) {
                        driver.navigate().refresh();
                        wait.impWait(30);
                        jobDetails = driver.findElements(path);
                        wait.impWait(30);
                        elementFound = false;
                        continue;
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    currURL = page.getCurrentUrl();
                    js.scrollIntoElement(getNextButton());
                    getNextButton().click();
                    if (!page.getCurrentUrl().equals(currURL)) {
                        jobDetails = driver.findElements(path);
                        elementFound = false;
                        i = 0;
                        continue;
                    }
                    else {
                        break;
                    }
                }

                if (jobIds.size() != 0) {
                    if (!workbenchPage.isCorrectCollection(jobIds.get(0), isNonGrouped)) {
                        page.goBack();
                        elementFound = false;
                        i++;
                        continue;
                    } else {
                        jobIds.remove(0);
                    }
                }
            }
            else {
                if (!page.getCurrentUrl().contains("t/workbench/")) {
                    js.scrollIntoElement(getNextButton());
                    getNextButton().click();
                    wait.impWait(30);
                    i = 0;
                }
                else {
                    break;
                }
            }
            jobDetails = driver.findElements(path);
        }
    }

    public void findFileJob(String jobExcerpt, List<String> jobIds) throws IOException {
        By path = By.xpath("//td/a[contains(., '" + jobExcerpt + "')][last()]");
        List<WebElement> jobDetails = driver.findElements(path);
        GlobalMethods globalMethods = new GlobalMethods(driver);
        String currURL;
        boolean elementFound = false;
        int i = 0;
        while(!elementFound) {
            if(jobDetails.size() > 0) {
                elementFound = true;
                try {
                    while(!globalMethods.retryingFindClick(jobDetails.get(i))) {
                        driver.navigate().refresh();
                        wait.impWait(30);
                        jobDetails = driver.findElements(path);
                        wait.impWait(30);
                        elementFound = false;
                        continue;
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    currURL = page.getCurrentUrl();
                    js.scrollIntoElement(getNextButton());
                    getNextButton().click();
                    if (!page.getCurrentUrl().equals(currURL)) {
                        jobDetails = driver.findElements(path);
                        elementFound = false;
                        i = 0;
                        continue;
                    }
                    else {
                        break;
                    }
                }

                if (jobIds.size() != 0) {
                    if (!workbenchPage.isCorrectFileCollection(jobIds.get(0))) {
                        page.goBack();
                        elementFound = false;
                        i++;
                        continue;
                    }
                }
            }
            else {
                if (!page.getCurrentUrl().contains("t/jobs/details/")) {
                    js.scrollIntoElement(getNextButton());
                    getNextButton().click();
                    wait.impWait(30);
                    i = 0;
                }
                else {
                    break;
                }
            }
            jobDetails = driver.findElements(path);
        }
    }

    public void clickIncomplete() {
        getIncompleteTab().click();
        wait.untilElementVisible(getIncompleteHeader());
    }

    public void clickReviewable() {
        getReviewableTab().click();
        wait.untilElementVisible(getReviewableHeader());
    }

    public void clickHeld() {
        getHeldTab().click();
        wait.untilElementVisible(getHeldHeader());
    }

    public boolean isJobTermsAgreementVisible() {
        boolean isClick = true;
        try {
            getAgreeTermsCheck().isDisplayed();
        } catch (NoSuchElementException e) {
            isClick = false;
        }
        return isClick;
    }

    public boolean hasPreferredJobs(String excerpt) {
        try {
            return elementState.isElementPresentInSource(excerpt);
        }
        catch(Exception e) {}
        return false;
    }
}
