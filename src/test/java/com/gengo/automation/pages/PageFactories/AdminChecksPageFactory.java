package com.gengo.automation.pages.PageFactories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @class Page factory for Admin-Checks page.
 */
public class AdminChecksPageFactory {

    protected WebDriver driver;

    public AdminChecksPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** Success and Error Messages */
    @FindBy(xpath = "//p[contains(text(), 'Check requested successfully')]")
    WebElement checkRequestSuccessMsg;

    /** Lists locators */
    @FindBy(xpath = "//select[@name='trigger']")
    WebElement filterCheckTrigger;

    @FindBy(xpath = "//select[@name='source']")
    WebElement filterCheckSource;

    @FindBy(xpath = "//select[@name='target']")
    WebElement filterCheckTarget;

    @FindBy(xpath = "//select[@name='tier']")
    WebElement filterCheckLevel;

    @FindBy(xpath = "//select[@name='status']")
    WebElement filterCheckStatus;

    /** 'Checks filter' locators */
    @FindBy(xpath = "//input[@name='job_id']")
    WebElement filterCheckJobID;

    @FindBy(xpath = "//input[@name='translator_id']")
    WebElement filterCheckTranslatorID;

    @FindBy(xpath = "//input[@name='reviewer_id']")
    WebElement filterCheckReviewerID;

    // Trigger
    @FindBy(xpath = "//select[@name='trigger']/option[@value='any']")
    WebElement filterCheckTriggerAny;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='1']")
    WebElement filterCheckTriggerFirstJob;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='2']")
    WebElement filterCheckTriggerRandomJob;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='3']")
    WebElement filterCheckTriggerRejectedJob;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='4']")
    WebElement filterCheckTriggerLowCustomerScore;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='5']")
    WebElement filterCheckTriggerAdminRequest;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='6']")
    WebElement filterCheckTriggerLowCheckScore;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='7']")
    WebElement filterCheckTriggerProjectRequest;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='8']")
    WebElement filterCheckTriggerRevisionService;

    @FindBy(xpath = "//select[@name='trigger']/option[@value='9']")
    WebElement filterCheckTriggerRandomSampling;

    // Source languages
    @FindBy(xpath = "//select[@name='source']/option[@value='any']")
    WebElement filterCheckSourceAny;

    @FindBy(xpath = "//select[@name='source']/option[@value='ar']")
    WebElement filterCheckSourceAR;

    @FindBy(xpath = "//select[@name='source']/option[@value='zh']")
    WebElement filterCheckSourceZH;

    @FindBy(xpath = "//select[@name='source']/option[@value='zh-tw']")
    WebElement filterCheckSourceZHTW;

    @FindBy(xpath = "//select[@name='source']/option[@value='da']")
    WebElement filterCheckSourceDA;

    @FindBy(xpath = "//select[@name='source']/option[@value='nl']")
    WebElement filterCheckSourceNL;

    @FindBy(xpath = "//select[@name='source']/option[@value='en']")
    WebElement filterCheckSourceEN;

    @FindBy(xpath = "//select[@name='source']/option[@value='fr']")
    WebElement filterCheckSourceFR;

    @FindBy(xpath = "//select[@name='source']/option[@value='frca']")
    WebElement filterCheckSourceFRCA;

    @FindBy(xpath = "//select[@name='source']/option[@value='de']")
    WebElement filterCheckSourceDE;

    @FindBy(xpath = "//select[@name='source']/option[@value='el']")
    WebElement filterCheckSourceEL;

    @FindBy(xpath = "//select[@name='source']/option[@value='id']")
    WebElement filterCheckSourceID;

    @FindBy(xpath = "//select[@name='source']/option[@value='it']")
    WebElement filterCheckSourceIT;

    @FindBy(xpath = "//select[@name='source']/option[@value='ja']")
    WebElement filterCheckSourceJA;

    @FindBy(xpath = "//select[@name='source']/option[@value='ko']")
    WebElement filterCheckSourceKO;

    @FindBy(xpath = "//select[@name='source']/option[@value='no']")
    WebElement filterCheckSourceNO;

    @FindBy(xpath = "//select[@name='source']/option[@value='pl']")
    WebElement filterCheckSourcePL;

    @FindBy(xpath = "//select[@name='source']/option[@value='pt-br']")
    WebElement filterCheckSourcePTBR;

    @FindBy(xpath = "//select[@name='source']/option[@value='pt']")
    WebElement filterCheckSourcePT;

    @FindBy(xpath = "//select[@name='source']/option[@value='ru']")
    WebElement filterCheckSourceRU;

    @FindBy(xpath = "//select[@name='source']/option[@value='es-la']")
    WebElement filterCheckSourceESLA;

    @FindBy(xpath = "//select[@name='source']/option[@value='es']")
    WebElement filterCheckSourceES;

    @FindBy(xpath = "//select[@name='source']/option[@value='sv']")
    WebElement filterCheckSourceSV;

    @FindBy(xpath = "//select[@name='source']/option[@value='th']")
    WebElement filterCheckSourceTH;

    // Target languages
    @FindBy(xpath = "//select[@name='target']/option[@value='any']")
    WebElement filterCheckTargetAny;

    @FindBy(xpath = "//select[@name='target']/option[@value='ar']")
    WebElement filterCheckTargetAR;

    @FindBy(xpath = "//select[@name='target']/option[@value='bg']")
    WebElement filterCheckTargetBG;

    @FindBy(xpath = "//select[@name='target']/option[@value='zh']")
    WebElement filterCheckTargetZH;

    @FindBy(xpath = "//select[@name='target']/option[@value='zh-tw']")
    WebElement filterCheckTargetZHTW;

    @FindBy(xpath = "//select[@name='target']/option[@value='cs']")
    WebElement filterCheckTargetCS;

    @FindBy(xpath = "//select[@name='target']/option[@value='da']")
    WebElement filterCheckTargetDA;

    @FindBy(xpath = "//select[@name='target']/option[@value='nl']")
    WebElement filterCheckTargetNL;

    @FindBy(xpath = "//select[@name='target']/option[@value='en']")
    WebElement filterCheckTargetEN;

    @FindBy(xpath = "//select[@name='target']/option[@value='en-gb']")
    WebElement filterCheckTargetENGB;

    @FindBy(xpath = "//select[@name='target']/option[@value='fi']")
    WebElement filterCheckTargetFI;

    @FindBy(xpath = "//select[@name='target']/option[@value='fr']")
    WebElement filterCheckTargetFR;

    @FindBy(xpath = "//select[@name='target']/option[@value='fr-ca']")
    WebElement filterCheckTargetFRCA;

    @FindBy(xpath = "//select[@name='target']/option[@value='de']")
    WebElement filterCheckTargetDE;

    @FindBy(xpath = "//select[@name='target']/option[@value='el']")
    WebElement filterCheckTargetEL;

    @FindBy(xpath = "//select[@name='target']/option[@value='he']")
    WebElement filterCheckTargetHE;

    @FindBy(xpath = "//select[@name='target']/option[@value='hu']")
    WebElement filterCheckTargetHU;

    @FindBy(xpath = "//select[@name='target']/option[@value='id']")
    WebElement filterCheckTargetID;

    @FindBy(xpath = "//select[@name='target']/option[@value='it']")
    WebElement filterCheckTargetIT;

    @FindBy(xpath = "//select[@name='target']/option[@value='ja']")
    WebElement filterCheckTargetJA;

    @FindBy(xpath = "//select[@name='target']/option[@value='ko']")
    WebElement filterCheckTargetKO;

    @FindBy(xpath = "//select[@name='target']/option[@value='ms']")
    WebElement filterCheckTargetMS;

    @FindBy(xpath = "//select[@name='target']/option[@value='no']")
    WebElement filterCheckTargetNO;

    @FindBy(xpath = "//select[@name='target']/option[@value='pl']")
    WebElement filterCheckTargetPL;

    @FindBy(xpath = "//select[@name='target']/option[@value='pt-br']")
    WebElement filterCheckTargetPTBR;

    @FindBy(xpath = "//select[@name='target']/option[@value='pt']")
    WebElement filterCheckTargetPT;

    @FindBy(xpath = "//select[@name='target']/option[@value='ro']")
    WebElement filterCheckTargetRO;

    @FindBy(xpath = "//select[@name='target']/option[@value='ru']")
    WebElement filterCheckTargetRU;

    @FindBy(xpath = "//select[@name='target']/option[@value='sr']")
    WebElement filterCheckTargetSR;

    @FindBy(xpath = "//select[@name='target']/option[@value='sk']")
    WebElement filterCheckTargetSK;

    @FindBy(xpath = "//select[@name='target']/option[@value='es-la']")
    WebElement filterCheckTargetESLA;

    @FindBy(xpath = "//select[@name='target']/option[@value='es']")
    WebElement filterCheckTargetES;

    @FindBy(xpath = "//select[@name='target']/option[@value='sv']")
    WebElement filterCheckTargetSV;

    @FindBy(xpath = "//select[@name='target']/option[@value='tl']")
    WebElement filterCheckTargetTL;

    @FindBy(xpath = "//select[@name='target']/option[@value='th']")
    WebElement filterCheckTargetTH;

    @FindBy(xpath = "//select[@name='target']/option[@value='tr']")
    WebElement filterCheckTargetTR;

    @FindBy(xpath = "//select[@name='target']/option[@value='uk']")
    WebElement filterCheckTargetUK;

    @FindBy(xpath = "//select[@name='target']/option[@value='vi']")
    WebElement filterCheckTargetVI;

    // Job level
    @FindBy(xpath = "//select[@name='tier']/option[@value='any']")
    WebElement filterCheckLevelAny;

    @FindBy(xpath = "//select[@name='tier']/option[@value='standard']")
    WebElement filterCheckLevelStandard;

    @FindBy(xpath = "//select[@name='tier']/option[@value='pro']")
    WebElement filterCheckLevelPro;

    @FindBy(xpath = "//select[@name='tier']/option[@value='ultra_proofread']")
    WebElement filterCheckLevelUltraProofread;

    // Check status
    @FindBy(xpath = "//select[@name='status']/option[@value='any']")
    WebElement filterCheckStatusAny;

    @FindBy(xpath = "//select[@name='status']/option[@value='pending']")
    WebElement filterCheckStatusPending;

    @FindBy(xpath = "//select[@name='status']/option[@value='overdue']")
    WebElement filterCheckStatusOverdue;

    @FindBy(xpath = "//select[@name='status']/option[@value='complete']")
    WebElement filterCheckStatusComplete;

    /** 'Checks list' locators */
    @FindBy(xpath = "//span[@class='label label-default'][contains(.,'Overdue')]")
    WebElement checkStatusOverdue;

    @FindBy(xpath = "//table[@id='table-items']/tbody/tr[1]/td[2]/a")
    WebElement selectFirstCheck;

    @FindBy(xpath = "//table[@id='table-items']/tbody/tr[1]/td[9]/span")
    WebElement checkStatusOnList;

    @FindBy(xpath = ".//*[@id='table-items']/tbody/tr[1]/td[8]")
    WebElement checkScoreOnList;

    /** 'Check details' locators */
    @FindBy(xpath = "//h3[contains(text(), 'Details')]")
    WebElement checkDetailsPage;

    @FindBy(xpath = ".//*[@id='content']/div/div[2]/div[2]/div[3]/div[1]/div/table/tbody/tr[4]/td[2]/span")
    WebElement checkDetailsTrigger;

    @FindBy(xpath = ".//*[@id='content']/div/div[2]/div[2]/div[3]/div[2]/div/table/tbody/tr[3]/td[2]/a")
    WebElement checkDetailsJobID;

    @FindBy(xpath = ".//*[@id='content']/div/div[2]/div[2]/div[3]/div[1]/div/table/tbody/tr[6]/td[2]/span")
    WebElement checkDetailsStatus;

    @FindBy(xpath = "//a[@id='action-check']")
    WebElement checkActionBtnCheckLink;

    @FindBy(xpath = "//a[@id='action-edit']")
    WebElement checkActionBtnEditLink;

    @FindBy(xpath = "//a[@id='action-delete']")
    WebElement checkActionBtnDeleteLink;

    /**
     * Getters
     */
    public WebElement getCheckRequestSuccessMsg() {
        return checkRequestSuccessMsg;
    }

    public WebElement getFilterCheckJobID() {
        return filterCheckJobID;
    }

    public WebElement getFilterCheckTranslatorID() {
        return filterCheckTranslatorID;
    }

    public WebElement getFilterCheckReviewerID() {
        return filterCheckReviewerID;
    }

    public WebElement getFilterCheckTrigger() {
        return filterCheckTrigger;
    }

    public WebElement getFilterCheckTriggerAny() {
        return filterCheckTriggerAny;
    }

    public WebElement getFilterCheckTriggerFirstJob() {
        return filterCheckTriggerFirstJob;
    }

    public WebElement getFilterCheckTriggerRandomJob() {
        return filterCheckTriggerRandomJob;
    }

    public WebElement getFilterCheckTriggerRejectedJob() {
        return filterCheckTriggerRejectedJob;
    }

    public WebElement getFilterCheckTriggerLowCustomerScore() {
        return filterCheckTriggerLowCustomerScore;
    }

    public WebElement getFilterCheckTriggerAdminRequest() {
        return filterCheckTriggerAdminRequest;
    }

    public WebElement getFilterCheckTriggerLowCheckScore() {
        return filterCheckTriggerLowCheckScore;
    }

    public WebElement getFilterCheckTriggerProjectRequest() {
        return filterCheckTriggerProjectRequest;
    }

    public WebElement getFilterCheckTriggerRevisionService() {
        return filterCheckTriggerRevisionService;
    }

    public WebElement getFilterCheckTriggerRandomSampling() {
        return filterCheckTriggerRandomSampling;
    }

    public WebElement getFilterCheckSource() {
        return filterCheckSource;
    }

    public WebElement getFilterCheckSourceAny() {
        return filterCheckSourceAny;
    }

    public WebElement getFilterCheckSourceAR() {
        return filterCheckSourceAR;
    }

    public WebElement getFilterCheckSourceZH() {
        return filterCheckSourceZH;
    }

    public WebElement getFilterCheckSourceZHTW() {
        return filterCheckSourceZHTW;
    }

    public WebElement getFilterCheckSourceDA() {
        return filterCheckSourceDA;
    }

    public WebElement getFilterCheckSourceNL() {
        return filterCheckSourceNL;
    }

    public WebElement getFilterCheckSourceEN() {
        return filterCheckSourceEN;
    }

    public WebElement getFilterCheckSourceFR() {
        return filterCheckSourceFR;
    }

    public WebElement getFilterCheckSourceFRCA() {
        return filterCheckSourceFRCA;
    }

    public WebElement getFilterCheckSourceDE() {
        return filterCheckSourceDE;
    }

    public WebElement getFilterCheckSourceEL() {
        return filterCheckSourceEL;
    }

    public WebElement getFilterCheckSourceID() {
        return filterCheckSourceID;
    }

    public WebElement getFilterCheckSourceIT() {
        return filterCheckSourceIT;
    }

    public WebElement getFilterCheckSourceJA() {
        return filterCheckSourceJA;
    }

    public WebElement getFilterCheckSourceKO() {
        return filterCheckSourceKO;
    }

    public WebElement getFilterCheckSourceNO() {
        return filterCheckSourceNO;
    }

    public WebElement getFilterCheckSourcePL() {
        return filterCheckSourcePL;
    }

    public WebElement getFilterCheckSourcePTBR() {
        return filterCheckSourcePTBR;
    }

    public WebElement getFilterCheckSourcePT() {
        return filterCheckSourcePT;
    }

    public WebElement getFilterCheckSourceRU() {
        return filterCheckSourceRU;
    }

    public WebElement getFilterCheckSourceESLA() {
        return filterCheckSourceESLA;
    }

    public WebElement getFilterCheckSourceES() {
        return filterCheckSourceES;
    }

    public WebElement getFilterCheckSourceSV() {
        return filterCheckSourceSV;
    }

    public WebElement getFilterCheckSourceTH() {
        return filterCheckSourceTH;
    }

    public WebElement getFilterCheckTarget() {
        return filterCheckTarget;
    }

    public WebElement getFilterCheckTargetAny() {
        return filterCheckTargetAny;
    }

    public WebElement getFilterCheckTargetAR() {
        return filterCheckTargetAR;
    }

    public WebElement getFilterCheckTargetBG() {
        return filterCheckTargetBG;
    }

    public WebElement getFilterCheckTargetZH() {
        return filterCheckTargetZH;
    }

    public WebElement getFilterCheckTargetZHTW() {
        return filterCheckTargetZHTW;
    }

    public WebElement getFilterCheckTargetCS() {
        return filterCheckTargetCS;
    }

    public WebElement getFilterCheckTargetDA() {
        return filterCheckTargetDA;
    }

    public WebElement getFilterCheckTargetNL() {
        return filterCheckTargetNL;
    }

    public WebElement getFilterCheckTargetEN() {
        return filterCheckTargetEN;
    }

    public WebElement getFilterCheckTargetENGB() {
        return filterCheckTargetENGB;
    }

    public WebElement getFilterCheckTargetFI() {
        return filterCheckTargetFI;
    }

    public WebElement getFilterCheckTargetFR() {
        return filterCheckTargetFR;
    }

    public WebElement getFilterCheckTargetFRCA() {
        return filterCheckTargetFRCA;
    }

    public WebElement getFilterCheckTargetDE() {
        return filterCheckTargetDE;
    }

    public WebElement getFilterCheckTargetEL() {
        return filterCheckTargetEL;
    }

    public WebElement getFilterCheckTargetHE() {
        return filterCheckTargetHE;
    }

    public WebElement getFilterCheckTargetHU() {
        return filterCheckTargetHU;
    }

    public WebElement getFilterCheckTargetID() {
        return filterCheckTargetID;
    }

    public WebElement getFilterCheckTargetIT() {
        return filterCheckTargetIT;
    }

    public WebElement getFilterCheckTargetJA() {
        return filterCheckTargetJA;
    }

    public WebElement getFilterCheckTargetKO() {
        return filterCheckTargetKO;
    }

    public WebElement getFilterCheckTargetMS() {
        return filterCheckTargetMS;
    }

    public WebElement getFilterCheckTargetNO() {
        return filterCheckTargetNO;
    }

    public WebElement getFilterCheckTargetPL() {
        return filterCheckTargetPL;
    }

    public WebElement getFilterCheckTargetPTBR() {
        return filterCheckTargetPTBR;
    }

    public WebElement getFilterCheckTargetPT() {
        return filterCheckTargetPT;
    }

    public WebElement getFilterCheckTargetRO() {
        return filterCheckTargetRO;
    }

    public WebElement getFilterCheckTargetRU() {
        return filterCheckTargetRU;
    }

    public WebElement getFilterCheckTargetSR() {
        return filterCheckTargetSR;
    }

    public WebElement getFilterCheckTargetSK() {
        return filterCheckTargetSK;
    }

    public WebElement getFilterCheckTargetESLA() {
        return filterCheckTargetESLA;
    }

    public WebElement getFilterCheckTargetES() {
        return filterCheckTargetES;
    }

    public WebElement getFilterCheckTargetSV() {
        return filterCheckTargetSV;
    }

    public WebElement getFilterCheckTargetTL() {
        return filterCheckTargetTL;
    }

    public WebElement getFilterCheckTargetTH() {
        return filterCheckTargetTH;
    }

    public WebElement getFilterCheckTargetTR() {
        return filterCheckTargetTR;
    }

    public  WebElement getFilterCheckTargetUK() {
        return filterCheckTargetUK;
    }

    public WebElement getFilterCheckTargetVI() {
        return filterCheckTargetVI;
    }

    public WebElement getFilterCheckLevel() {
        return filterCheckLevel;
    }

    public WebElement getFilterCheckLevelAny() {
        return filterCheckLevelAny;
    }

    public WebElement getFilterCheckLevelStandard() {
        return filterCheckLevelStandard;
    }

    public WebElement getFilterCheckLevelPro() {
        return filterCheckLevelPro;
    }

    public WebElement getFilterCheckLevelUltraProofread() {
        return filterCheckLevelUltraProofread;
    }

    public WebElement getFilterCheckStatus() {
        return filterCheckStatus;
    }

    public WebElement getFilterCheckStatusAny() {
        return getFilterCheckStatusAny();
    }

    public WebElement getFilterCheckStatusPending() {
        return filterCheckStatusPending;
    }

    public WebElement getFilterCheckStatusOverdue() {
        return filterCheckStatusOverdue;
    }

    public WebElement getFilterCheckStatusComplete() {
        return filterCheckStatusComplete;
    }

    public WebElement getSelectFirstCheck() {
        return selectFirstCheck;
    }

    public WebElement getCheckStatus() {
        return checkStatusOverdue;
    }

    public WebElement getCheckDetailsPage() {
        return checkDetailsPage;
    }

    public WebElement getCheckDetailsTrigger() {
        return checkDetailsTrigger;
    }

    public WebElement getCheckDetailsJobID() {
        return checkDetailsJobID;
    }

    public WebElement getCheckDetailsStatus() {
        return checkDetailsStatus;
    }

    public WebElement getCheckActionBtnCheckLink() {
        return checkActionBtnCheckLink;
    }

    public WebElement getCheckActionBtnEditLink() {
        return checkActionBtnEditLink;
    }

    public WebElement getCheckActionBtnDeleteLink() {
        return checkActionBtnDeleteLink;
    }

    public WebElement getCheckStatusOnList() {
        return checkStatusOnList;
    }

    public WebElement getCheckScoreOnList() {
        return checkScoreOnList;
    }
}