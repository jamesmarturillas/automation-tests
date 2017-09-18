package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.AdminChecksPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class AdminChecksPage extends AdminChecksPageFactory {

    public AdminChecksPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private AdminPage adminPage = new AdminPage(driver);
    public String checkID, checkTrigger, checkJobID, checkStatus;

    public void filterChecksJobID(String jobId) {
        getFilterCheckJobID().sendKeys(jobId);
        wait.impWait(30);
    }

    public void filterChecksTrigger(String trigger) {
        getFilterCheckTrigger().click();
        switch (trigger) {
            case "firstJob":
                getFilterCheckTriggerRejectedJob().click();
                wait.impWait(30);
                break;
            case "randomJob":
                getFilterCheckTriggerRejectedJob().click();
                wait.impWait(30);
                break;
            case "rejectedJob":
                getFilterCheckTriggerRejectedJob().click();
                wait.impWait(30);
                break;
            case "lowCustomerScore":
                getFilterCheckTriggerLowCustomerScore().click();
                wait.impWait(30);
                break;
            case "adminRequest":
                getFilterCheckTriggerAdminRequest().click();
                wait.impWait(30);
                break;
            case "lowCheckScore":
                getFilterCheckTriggerLowCheckScore().click();
                wait.impWait(30);
                break;
            case "projectRequest":
                getFilterCheckTriggerProjectRequest().click();
                wait.impWait(30);
                break;
            case "revisionService":
                getFilterCheckTriggerRevisionService().click();
                wait.impWait(30);
                break;
            case "randomSampling":
                getFilterCheckTriggerRandomSampling().click();
                wait.impWait(30);
                break;
            case "any":
                getFilterCheckTriggerAny().click();
                wait.impWait(30);
                break;
        }
    }

    public void filterChecksSource(String source) {
        getFilterCheckSource().click();
        switch (source) {
            case "arabic":
                getFilterCheckSourceAR().click();
                wait.impWait(30);
                break;
            case "chineseSimplified":
                getFilterCheckSourceZH().click();
                wait.impWait(30);
                break;
            case "chineseTraditional":
                getFilterCheckSourceZHTW().click();
                wait.impWait(30);
                break;
            case "danish":
                getFilterCheckSourceDA().click();
                wait.impWait(30);
                break;
            case "dutch":
                getFilterCheckSourceNL().click();
                wait.impWait(30);
                break;
            case "english":
                getFilterCheckSourceEN().click();
                wait.impWait(30);
                break;
            case "french":
                getFilterCheckSourceFR().click();
                wait.impWait(30);
                break;
            case "frenchCanada":
                getFilterCheckSourceFRCA().click();
                wait.impWait(30);
                break;
            case "german":
                getFilterCheckSourceDE().click();
                wait.impWait(30);
                break;
            case "greek":
                getFilterCheckSourceEL().click();
                wait.impWait(30);
                break;
            case "indonesian":
                getFilterCheckSourceID().click();
                wait.impWait(30);
                break;
            case "italian":
                getFilterCheckSourceIT().click();
                wait.impWait(30);
                break;
            case "japanese":
                getFilterCheckSourceJA().click();
                wait.impWait(30);
                break;
            case "korean":
                getFilterCheckSourceKO().click();
                wait.impWait(30);
                break;
            case "norwegian":
                getFilterCheckSourceNO().click();
                wait.impWait(30);
                break;
            case "polish":
                getFilterCheckSourcePL().click();
                wait.impWait(30);
                break;
            case "portugueseBrazil":
                getFilterCheckSourcePTBR().click();
                wait.impWait(30);
                break;
            case "portugueseEurope":
                getFilterCheckSourcePT().click();
                wait.impWait(30);
                break;
            case "russian":
                getFilterCheckSourceRU().click();
                wait.impWait(30);
                break;
            case "spanishLatinAmerica":
                getFilterCheckSourceESLA().click();
                wait.impWait(30);
                break;
            case "spanishSpain":
                getFilterCheckSourceES().click();
                wait.impWait(30);
                break;
            case "swedish":
                getFilterCheckSourceSV().click();
                wait.impWait(30);
                break;
            case "thai":
                getFilterCheckSourceTH().click();
                wait.impWait(30);
                break;
            case "any":
                getFilterCheckSourceAny().click();
                wait.impWait(30);
                break;
        }
    }

    public void filterChecksTarget(String target) {
        getFilterCheckTarget().click();
        switch (target) {
            case "arabic":
                getFilterCheckTargetAR().click();
                wait.impWait(30);
                break;
            case "bulgarian":
                getFilterCheckTargetBG().click();
                wait.impWait(30);
                break;
            case "chineseSimplified":
                getFilterCheckTargetZH().click();
                wait.impWait(30);
                break;
            case "chineseTraditional":
                getFilterCheckTargetZHTW().click();
                wait.impWait(30);
                break;
            case "czech":
                getFilterCheckTargetCS().click();
                wait.impWait(30);
                break;
            case "danish":
                getFilterCheckTargetDA().click();
                wait.impWait(30);
                break;
            case "dutch":
                getFilterCheckTargetNL().click();
                wait.impWait(30);
                break;
            case "english":
                getFilterCheckTargetEN().click();
                wait.impWait(30);
                break;
            case "englishBritish":
                getFilterCheckTargetENGB().click();
                wait.impWait(30);
                break;
            case "finnish":
                getFilterCheckTargetFI().click();
                wait.impWait(30);
                break;
            case "french":
                getFilterCheckTargetFR().click();
                wait.impWait(30);
                break;
            case "frenchCanada":
                getFilterCheckTargetFRCA().click();
                wait.impWait(30);
                break;
            case "german":
                getFilterCheckTargetDE().click();
                wait.impWait(30);
                break;
            case "greek":
                getFilterCheckTargetEL().click();
                wait.impWait(30);
                break;
            case "hebrew":
                getFilterCheckTargetHE().click();
                wait.impWait(30);
                break;
            case "hungarian":
                getFilterCheckTargetHU().click();
                wait.impWait(30);
                break;
            case "indonesian":
                getFilterCheckTargetID().click();
                wait.impWait(30);
                break;
            case "italian":
                getFilterCheckTargetIT().click();
                wait.impWait(30);
                break;
            case "japanese":
                getFilterCheckTargetJA().click();
                wait.impWait(30);
                break;
            case "korean":
                getFilterCheckTargetKO().click();
                wait.impWait(30);
                break;
            case "malay":
                getFilterCheckTargetMS().click();
                wait.impWait(30);
                break;
            case "norwegian":
                getFilterCheckTargetNO().click();
                wait.impWait(30);
                break;
            case "polish":
                getFilterCheckTargetPL().click();
                wait.impWait(30);
                break;
            case "portuguesezBrazil":
                getFilterCheckTargetPTBR().click();
                wait.impWait(30);
                break;
            case "portugueseEurope":
                getFilterCheckTargetPT().click();
                wait.impWait(30);
                break;
            case "romanian":
                getFilterCheckTargetRO().click();
                wait.impWait(30);
                break;
            case "russian":
                getFilterCheckTargetRU().click();
                wait.impWait(30);
                break;
            case "serbian":
                getFilterCheckTargetSR().click();
                wait.impWait(30);
                break;
            case "slovak":
                getFilterCheckTargetSK().click();
                wait.impWait(30);
                break;
            case "spanishLatinAmerica":
                getFilterCheckTargetESLA().click();
                wait.impWait(30);
                break;
            case "spanishSpain":
                getFilterCheckTargetES().click();
                wait.impWait(30);
                break;
            case "swedish":
                getFilterCheckTargetSV().click();
                wait.impWait(30);
                break;
            case "tagalog":
                getFilterCheckTargetTL().click();
                wait.impWait(30);
                break;
            case "thai":
                getFilterCheckTargetTH().click();
                wait.impWait(30);
                break;
            case "turkish":
                getFilterCheckTargetTR().click();
                wait.impWait(30);
                break;
            case "ukrainian":
                getFilterCheckTargetUK().click();
                wait.impWait(30);
                break;
            case "vietnamese":
                getFilterCheckTargetVI().click();
                wait.impWait(30);
                break;
            case "any":
                getFilterCheckTargetAny().click();
                wait.impWait(30);
                break;
        }
    }

    public void filterChecksLevel(String level) {
        getFilterCheckLevel().click();
        switch (level) {
            case "standard":
                getFilterCheckLevelStandard().click();
                wait.impWait(30);
                break;
            case "pro":
                getFilterCheckLevelPro().click();
                wait.impWait(30);
                break;
            case "ultraProofread":
                getFilterCheckLevelUltraProofread().click();
                wait.impWait(30);
                break;
            case "any":
                getFilterCheckLevelAny().click();
                wait.impWait(30);
                break;
        }
    }

    public void filterChecksStatus(String status) {
        getFilterCheckStatus().click();
        switch (status) {
            case "overdue":
                getFilterCheckStatusOverdue().click();
                wait.impWait(30);
                break;
            case "pending":
                getFilterCheckStatusPending().click();
                wait.impWait(30);
                break;
            case "complete":
                getFilterCheckStatusComplete().click();
                wait.impWait(30);
                break;
            case "any":
                getFilterCheckStatusAny().click();
                wait.impWait(30);
                break;
        }
    }

    // Check Details page
    public void setCheckID() {
        this.checkID = getSelectFirstCheck().getText();
        wait.impWait(30);
    }

    public void setTrigger() {
        this.checkTrigger = getCheckDetailsTrigger().getText();
        wait.impWait(30);
    }

    public void setJobID() {
        this.checkJobID = getCheckDetailsJobID().getText();
        wait.impWait(30);
    }

    public void setCheckStatus() {
        this.checkStatus = getCheckDetailsStatus().getText();
        wait.impWait(30);
    }

    public String retrieveCheckID() {
        return this.checkID;
    }

    public String retrieveTrigger() {
        return this.checkTrigger;
    }

    public String retrieveJobID() {
        return this.checkJobID;
    }

    public String retrieveCheckStatus() {
        return this.checkStatus;
    }

    public void accessFirstCheckDetails() {
        getSelectFirstCheck().click();
        wait.impWait(30);
    }

    public void goToGoCheckPage() {
        adminPage.getActionBtn().click();
        getCheckActionBtnCheckLink().click();
        wait.impWait(30);
    }

    public void editGoCheckPage() {
        adminPage.getActionBtn().click();
        getCheckActionBtnEditLink().click();
        wait.impWait(30);
    }

    public void checkDetailsdeleteGocheck() {
        adminPage.getActionBtn().click();
        getCheckActionBtnDeleteLink().click();
        wait.impWait(30);
    }

    public void navigateGoCheckPageviaStatus(String status) {
        switch (status) {
            case "Pending":
            case "Overdue":
                this.goToGoCheckPage();
                wait.impWait(30);
                break;
            case "Complete":
                this.editGoCheckPage();
                wait.impWait(30);
                break;
        }
    }

}