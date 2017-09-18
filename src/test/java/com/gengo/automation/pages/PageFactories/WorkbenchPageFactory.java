package com.gengo.automation.pages.PageFactories;

import com.gengo.automation.helpers.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @class Object repository for workbench page.
 * Contains PageFactory initialization.
 */
public class WorkbenchPageFactory {

    protected WebDriver driver;
    private Wait wait;

    public WorkbenchPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new Wait(this.driver);
    }

    /**
     * Element initialization
     */
    @FindBy(xpath = "//a[contains(.,'More')]")
    WebElement moreOptionsBtn;

    @FindBy(xpath = "//button[@class='close hidden-xs']")
    WebElement closeWorkbenchModal;

    @FindBy(xpath = "//a[@class='btn btn-link']/i[contains(@class, 'icon-comment')]")
    WebElement jobCommentButton;

    @FindBy(xpath = "//li[contains(@class, 'button-wrap')]")
    WebElement startTranslateParentButton;

    @FindBy(xpath = "//button[contains(., 'Start Translating')]")
    WebElement startTranslateMainButton;

    @FindBy(xpath = "//a[@class='btn btn-xs btn-primary btn-got-it']")
    WebElement gotItButton;

    @FindBy(xpath = "//*[@id='jobs']/div/div/div[2]/div/div[3]/div/textarea")
    WebElement translationTextArea;

    @FindBy(xpath = "//div[@class='segment ng-scope']/div[@class='target-wrap ng-scope disabled']/div")
    WebElement clickTranslationText2;

    @FindBy(xpath = "(//div[@class='segment ng-scope']/div[@class='target-wrap ng-scope disabled']/div)[1]")
    WebElement clickTranslationText2New;

    @FindBy(xpath = "//div[@class='segment ng-scope active']/div/div/textarea")
    WebElement translationTextArea2;

    @FindBy(xpath = "//*[@class='target placeholder ng-isolate-scope']")
    WebElement clickTranslationTextArea;

    @FindBy(xpath = "//div[@class='segment ng-scope warning']/div[@class='target-wrap ng-scope disabled']/div")
    WebElement clickTranslationTextWarning;

    @FindBy(xpath = "//div[@class='segment ng-scope warning active']/div/div/textarea")
    WebElement translationTextAreaWarning;

    @FindBy(xpath = "//button[@class='btn navbar-btn btn-primary']")
    WebElement submitButton;

    @FindBy(xpath = "//div[@class='btn-group clearfix ng-scope'][contains(., 'No jobs to submit')]")
    WebElement submitButtonParent;

    @FindBy(xpath = "//*[@class='modal-title']")
    WebElement submitJobModal;

    @FindBy(xpath = "//*[@class='btn btn-primary ng-isolate-scope']")
    WebElement submitJobModalOkButton;

    @FindBy(xpath = "//textarea[@placeholder='Write a comment in English']")
    WebElement workbenchCommentsTextArea;

    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary submit']")
    WebElement workbenchSubmitCommentsButton;

    @FindBy(xpath = "//*[@class='name']")
    WebElement workbenchAccountNavigationDropDown;

    @FindBy(xpath = "//*[@class='icon-flag']")
    WebElement flagIssueButton;

    @FindBy(xpath = "//*[@class='icon-flag flagged']")
    WebElement flagResolveIssueButton;

    @FindBy(xpath = "//label[contains(., 'Not in ')]/input")
    WebElement flagNotLanguageRadioButton;

    @FindBy(xpath = "//label[contains(., 'Unsuitable content')]/input")
    WebElement flagUnsuitableContentRadioButton;

    @FindBy(xpath = "//label[contains(., 'Not enough context')]/input")
    WebElement flagNotEnoughContextRadioButton;

    @FindBy(xpath = "//label[contains(., 'Unreasonable requests')]/input")
    WebElement flagUnreasonableRequestRadioButton;

    @FindBy(xpath = "//label[contains(., 'Wrong word count')]/input")
    WebElement flagWrongWordCountRadionButton;

    @FindBy(xpath = "//label[contains(., 'Technical issue')]/input")
    WebElement flagTechnicalIssueRadionButton;

    @FindBy(xpath = "//textarea[@class='form-control input-sm ng-pristine ng-untouched ng-valid']")
    WebElement flagCommentTextArea;

    @FindBy(xpath = "//*[@class='btn btn-danger']")
    WebElement flagJobButton;

    @FindBy(xpath = "//*[@class='btn btn-default']")
    WebElement flagCancelButton;

    @FindBy(xpath = "//*[@class='navbar-brand']")
    WebElement translatorLogo;

    @FindBy(xpath = "//button[contains(., 'OK')]")
    WebElement collectionLimitModalOkButton;

    @FindBy(xpath = "//button[contains(., 'OK')]")
    WebElement modalOkButton;

    @FindBy(xpath = "//*[@class='btn dropdown-toggle btn-primary']")
    WebElement submitDeclineJobDropDownButton;

    @FindBy(xpath = "//ul/li/a[contains(text(), 'Decline Collection')]")
    WebElement declineCollection;

    @FindBy(xpath = "//button[@class='btn btn-danger' and contains(., 'Decline Collection')]")
    WebElement declineButton;

    @FindBy(xpath = "//form[@class='ng-valid ng-dirty ng-valid-parse']/div[3]/label/input")
    WebElement declineReasonUnsuitableContent;

    @FindBy(xpath = "//div[@class='modal-dialog']/div/div/form/textarea")
    WebElement declineCollectionModalTextArea;

    @FindBy(xpath = "//button[contains(., 'Decline Collection')]")
    WebElement declineCollectionModalButton;

    @FindBy(xpath = "//button[@class='btn btn-default'][contains(., 'Close')]")
    WebElement shortcutListModalCloseButton;

    @FindBy(xpath = "//i[@class='icon-horizontal']/parent::a")
    WebElement horizontalViewButton;

    @FindBy(xpath = "//i[@class='icon-vertical']/parent::a")
    WebElement verticalViewButton;

    @FindBy(xpath = "//i[@class='icon-warning']/parent::a[@popover='Validation']")
    WebElement validationButton;

    @FindBy(xpath = "//i[@class='icon-warning warning']/parent::a[@popover='Validation']")
    WebElement validationButtonWithWarning;

    @FindBy(xpath = "//i[@class='icon-warning error warning']/parent::a[@popover='Validation']")
    WebElement validationButtonWithWarningError;

    @FindBy(xpath = "//i[@class='icon-memory']/parent::a")
    WebElement toolsButton;

    @FindBy(xpath = "//i[@class='icon-memory selected']/parent::a")
    WebElement toolsButtonSelected;

    @FindBy(xpath = "//i[@class='icon-funnel']/parent::a[@class='dropdown-toggle']")
    WebElement filterDropdownButton;

    @FindBy(xpath = "//i[@class='icon-more']/parent::a[@class='dropdown-toggle']")
    WebElement moreButton;

    @FindBy(xpath = "//h1[contains(., 'Work dashboard')]")
    WebElement dashboardHeader;

    @FindBy(xpath = "//h2[contains(., 'Reviewable jobs')]")
    WebElement reviewableHeader;

    @FindBy(id = "context")
    WebElement commentsSection;

    @FindBy(xpath = "//a[@class='btn btn-xs btn-link btn-fint-out-more']")
    WebElement findOutMoreLink;

    @FindBy(xpath = "//a[contains(.,'Horizontal')]")
    WebElement horizontalBtn;

    @FindBy(xpath = "//a[@class='selected'][contains(.,'Horizontal')]")
    WebElement horizontalSelected;

    @FindBy(xpath = "//a[contains(.,'Vertical')]")
    WebElement verticalBtn;

    @FindBy(xpath = "//a[@class='selected'][contains(.,'Vertical')]")
    WebElement verticalSelected;

    @FindBy(xpath = "//div[@class='ng-scope'][contains(.,'Glossaries')]")
    WebElement glossarySection;

    @FindBy(xpath = "//div[@class='ng-scope'][contains(.,'issues')]")
    WebElement issuesSection;

    @FindBy(xpath = "//a[@class='btn btn-link memory']")
    WebElement glossaryBtn;

    @FindBy(xpath = "//a[@class='btn btn-xs btn-primary btn-send-feedback']")
    WebElement sendFeedbackBtn;

    @FindBy(xpath = "//a[@class='dropdown-toggle'][contains(.,'Filter')]")
    WebElement filterBtn;

    @FindBy(xpath = "//a[@class='dropdown-toggle selected'][contains(.,'Filter')]")
    WebElement filterSelectedBtn;

    @FindBy(xpath = "//span[@class='count approved-count ng-binding']")
    WebElement filterAvailableCount;

    @FindBy(xpath = "//a[@class='filter-change'][contains(.,'Available')]")
    WebElement filterAvailable;

    @FindBy(xpath = "//span[@class='count no-translation-count ng-binding']")
    WebElement filterAllCount;

    @FindBy(xpath = "//a[@class='filter-change'][contains(.,'All')]")
    WebElement filterAll;

    @FindBy(xpath = "//a[@class='filter-change'][contains(.,'Empty')]")
    WebElement filterEmpty;

    @FindBy(xpath = "//a[@class='filter-change disabled'][contains(.,'Empty')]")
    WebElement filterEmptyDisabled;

    @FindBy(xpath = "//span[contains(.,'Empty')]/following-sibling::span[@class='count no-translation-count ng-binding']")
    WebElement filterEmptyCount;

    @FindBy(xpath = "//a[@class='filter-change'][contains(.,'Unsubmitted')]")
    WebElement filterUnsubmitted;

    @FindBy(xpath = "//a[@class='filter-change disabled'][contains(.,'Unsubmitted')]")
    WebElement filterUnsubmittedDisabled;

    @FindBy(xpath = "//span[@class='count translation-count ng-binding']")
    WebElement filterUnsubmittedCount;

    @FindBy(xpath = "//a[@class='filter-change'][contains(.,'Submitted')]")
    WebElement filterSubmitted;

    @FindBy(xpath = "//a[@class='filter-change disabled'][contains(.,'Submitted')]")
    WebElement filterSubmittedDisabled;

    @FindBy(xpath = "//span[@class='count submitted-count ng-binding']")
    WebElement filterSubmittedCount;

    @FindBy(xpath = "//a[@class='filter-change'][contains(.,'Revising')]")
    WebElement filterRevising;

    @FindBy(xpath = "//a[@class='filter-change disabled'][contains(.,'Revising')]")
    WebElement filterRevisingDisabled;

    @FindBy(xpath = "//span[@class='count returned-count ng-binding']")
    WebElement filterRevisingCount;

    @FindBy(xpath = "//a[@class='filter-change'][contains(.,'Cautions & Errors')]")
    WebElement filterError;

    @FindBy(xpath = "//a[@class='filter-change disabled'][contains(.,'Cautions & Errors')]")
    WebElement filterErrorDisabled;

    @FindBy(xpath = "//span[@class='count approved-count ng-binding']")
    WebElement filterErrorCount;

    @FindBy(xpath = "//button[@class='btn navbar-btn btn-primary'][contains(.,'No jobs to submit')]")
    WebElement noJobToSubmitBtn;

    @FindBy(xpath = "//ul[@class='dropdown-menu']/li/a[contains(.,'How to use')]")
    WebElement moreHowToUse;

    @FindBy(xpath = "//ul[@class='dropdown-menu']/li/a[contains(.,'Keyboard shortcuts')]")
    WebElement moreKeyboardShortcuts;

    @FindBy(xpath = "//ul[@class='dropdown-menu']/li/a[contains(.,'Support')]")
    WebElement moreSupport;

    @FindBy(xpath = "//button[@class='btn btn-primary ng-scope']")
    WebElement modalNextBtn;

    @FindBy(xpath = "//button[contains(.,'Previous')]")
    WebElement modalPreviousBtn;

    @FindBy(xpath = "//button[@class='btn btn-default']")
    WebElement closeBtn;

    @FindBy(xpath = "//a[@class='icon-info']")
    WebElement jobInfo;

    @FindBys({@FindBy(xpath = "//div[@ng-controller='JobCtrl']/div/ul/li/a[@class='icon-info'][1]")})
    List<WebElement> jobInfos;

    @FindBy(xpath = "//pre[@class='source ng-isolate-scope']")
    WebElement inactiveSourceText;

    @FindBy(xpath = "//div[@class='box job translated focused']/div/div/div/pre")
    WebElement activeSourceText;

    @FindBy(xpath = "//div[@class='box job warnings focused']/div/div/div/pre")
    WebElement activeSourceWarningText;

    @FindBy(xpath = "//button[@class='btn navbar-btn btn-danger']")
    WebElement fixErrorsToSubmitBtn;

    @FindBy(xpath = "//label[contains(.,'unable to finish')]")
    WebElement declineUnableToFinish;

    @FindBy(xpath = "//label[contains(.,'Not in ')]")
    WebElement declineNotInLanguage;

    @FindBy(xpath = "//label[contains(.,'Unsuitable content')]")
    WebElement declineUnsuitableContent;

    @FindBy(xpath = "//label[contains(.,'Not enough context')]")
    WebElement declineNotEnoughContext;

    @FindBy(xpath = "//label[contains(.,'Unreasonable requests')]")
    WebElement declineUnreasonableRequests;

    @FindBy(xpath = "//label[contains(.,'Wrong word count')]")
    WebElement declineWrongWordCount;

    @FindBy(xpath = "//label[contains(.,'Technical issue')]")
    WebElement declineTechnicalIssue;

    @FindBy(xpath = "//textarea[@class='form-control input-sm ng-pristine ng-untouched ng-valid']")
    WebElement declineCommentTextArea;

    @FindBy(xpath = "//h2[contains(.,'Collection unavailable')]")
    WebElement collectionUnavailableText;

    @FindBy(xpath = "//button[@class='btn btn-primary ng-isolate-scope'][contains(.,'OK')]")
    WebElement collectionUnavailableOKBtn;

    @FindBy(xpath = "")
    WebElement disabledSubmitBtn;

    @FindBy(xpath = "//a[@class='btn btn-link validation']/i[@class='icon-warning error warning']")
    WebElement errorValidationBtn;

    @FindBy(xpath = "//b[contains(text(), '$') and contains(text(), '.')]" )
    WebElement rewardTxt;

    @FindBy(xpath = "//div[@class='time-countdown ng-scope']")
    WebElement allottedTimeTxt;

    @FindBy(xpath = "//li[contains(text(), 'approval')]")
    WebElement approvalTimeTxt;

    @FindBy(xpath = "//li[contains(text(), 'unit') or contains(text(), 'unit')]")
    WebElement totalUnitsTxt;

    @FindBy(xpath = "//li[contains(text(), '/unit')]")
    WebElement rewardPerUnitTxt;

    @FindBy(xpath = "//li[contains(., 'job') or contains(., 'jobs')]")
    WebElement jobCountTxt;

    @FindBy(xpath = "//ul[@class='list-inline list-meta']/li[contains(., '#')]")
    WebElement collectionIdTxt;

    @FindBy(xpath = "//li[@class='tag tier ng-binding']")
    WebElement tierTxt;

    @FindBy(xpath = "//li[@ng-if='summary.has_tm']")
    WebElement tmTxt;

    @FindBy(xpath = "//li[@ng-if='summary.purpose']")
    WebElement purposeTxt;

    @FindBy(xpath = "//li[@ng-if='summary.tone']")
    WebElement toneTxt;

    @FindBy(xpath = "//li[@class='tag language ng-binding']")
    WebElement languagePairTxt;

    @FindBy(xpath = "//span")
    WebElement textSaveAndSaving;

    @FindBy(xpath = "//div[@class='box job submitted focused']")
    WebElement jobIdHolder;

    @FindBy(xpath = "//p[contains(.,'Status:')]")
    WebElement jobStatusText;

    @FindBy(xpath = "//p[contains(.,'ID:')]")
    WebElement jobIDText;

    @FindBy(xpath = "//div[@class='background-div target ng-pristine ng-untouched ng-valid']")
    WebElement inactiveTextArea;

    @FindBy(xpath = "//div[@class='box job warnings focused']/div/div/div/div")
    WebElement warningTargetText;

    @FindBy(xpath = "//div[@class='box job translated focused']/div/div/div/div")
    WebElement targetText;

    @FindBy(xpath = "//p[contains(text(), 'Potential grammatical issues are displayed here when present.')]")
    WebElement validationText;

    @FindBy(xpath = "//div[@ng-repeat-start='job in jobCollection.jobList track by job.id']")
    WebElement segmentHolder;

    @FindBys({@FindBy(xpath = "//span[contains(@class, 'highlight')]")})
    List<WebElement> highlightedTexts;

    @FindBy(xpath = "//h2[@class='modal-title ng-scope'][contains(.,'Welcome to the workbench')]")
    WebElement welcomeModalHeaderText1;

    @FindBy(xpath = "//div[@class='image walkthrough01']")
    WebElement welcomeModalMedia1;

    @FindBy(xpath = "//p[@class='discription'][contains(.,'Before clicking “Start Translating”, check the allotted time and reward. Enter your translation in the target field—always follow the Gengo Style Guide!')]")
    WebElement welcomeModalDescription1;

    @FindBy(xpath = "//span[@class='pull-left ng-binding'][contains(.,'Step 1 of 5')]")
    WebElement welcomeModalStep1;

    @FindBy(xpath = "//h2[@class='modal-title ng-scope'][contains(.,'Translation tools')]")
    WebElement welcomeModalHeaderText2;

    @FindBy(xpath = "//div[@class='image walkthrough02']")
    WebElement welcomeModalMedia2;

    @FindBy(xpath = "//p[@class='discription'][contains(.,'Content which shouldn’t be translated and glossary terms are highlighted in the source text. Click on them to copy to the target.')]")
    WebElement welcomeModalDescription2;

    @FindBy(xpath = "//span[@class='pull-left ng-binding'][contains(.,'Step 2 of 5')]")
    WebElement welcomeModalStep2;

    @FindBy(xpath = "//h2[@class='modal-title ng-scope'][contains(.,'Communication')]")
    WebElement welcomeModalHeaderText3;

    @FindBy(xpath = "//div[@class='image walkthrough03']")
    WebElement welcomeModalMedia3;

    @FindBy(xpath = "//p[@class='discription'][contains(.,'Click on the comment bubble to view job information and communicate with the customer. Flag issues here, too.')]")
    WebElement welcomeModalDescription3;

    @FindBy(xpath = "//span[@class='pull-left ng-binding'][contains(.,'Step 3 of 5')]")
    WebElement welcomeModalStep3;

    @FindBy(xpath = "//h2[@class='modal-title ng-scope'][contains(.,'Submitting your work')]")
    WebElement welcomeModalHeaderText4;

    @FindBy(xpath = "//div[@class='image walkthrough04']")
    WebElement welcomeModalMedia4;

    @FindBy(xpath = "//p[@class='discription'][contains(.,'Your work is autosaved as you go. Once validation errors—shown in red—are resolved, you can submit your translation.')]")
    WebElement welcomeModalDescription4;

    @FindBy(xpath = "//span[@class='pull-left ng-binding'][contains(.,'Step 4 of 5')]")
    WebElement welcomeModalStep4;

    @FindBy(xpath = "//h2[@class='modal-title ng-scope'][contains(.,'Ready to go?')]")
    WebElement welcomeModalHeaderText5;

    @FindBy(xpath = "//div[@class='video-container']/iframe']")
    WebElement welcomeModalMedia5;

    @FindBy(xpath = "//p[@class='discription'][contains(.,'Watch the video to learn more or read our How-to guide')]")
    WebElement welcomeModalDescription5;

    @FindBy(xpath = "//span[@class='pull-left ng-binding'][contains(.,'Step 5 of 5')]")
    WebElement welcomeModalStep5;

    @FindBy(xpath = "//h2[@id='errorModalLabel'][contains(.,'Collection unavailable')]")
    WebElement collectionUnavailableHeader;

    @FindBy(xpath = "//div[@class='modal-body ng-binding ng-scope'][contains(.,'Sorry, this collection is no longer available for you to work on.')]")
    WebElement collectionUnavailableDescription;

    @FindBy(xpath = "//ul[@class='list-unstyled']/li[2]")
    WebElement firstComment;

    /**
     * Getters
     */
    public WebElement getMoreOptionsBtn() {
        return moreOptionsBtn;
    }

    public WebElement getCloseWorkbenchModal() {
        return closeWorkbenchModal;
    }

    public WebElement getJobCommentButton() {
        return jobCommentButton;
    }

    public WebElement getStartTranslateParentButton() {
        return startTranslateParentButton;
    }

    public WebElement getStartTranslateMainButton() {
        return startTranslateMainButton;
    }

    public WebElement getGotItButton() {
        return gotItButton;
    }

    public WebElement getTranslationTextArea() {
        return translationTextArea;
    }

    public WebElement getClickTranslationText2() {
        return clickTranslationText2;
    }

    public WebElement getClickTranslationText2New() {
        return clickTranslationText2New;
    }

    public WebElement getTranslationTextArea2() {
        return translationTextArea2;
    }

    public WebElement getClickTranslationTextArea() {
        return clickTranslationTextArea;
    }

    public WebElement getClickTranslationTextWarning() {
        return clickTranslationTextWarning;
    }

    public WebElement getTranslationTextAreaWarning() {
        return translationTextAreaWarning;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public WebElement getSubmitButtonParent() {
        return submitButtonParent;
    }

    public WebElement getSubmitJobModal() {
        return submitJobModal;
    }

    public WebElement getSubmitJobModalOkButton() {
        return submitJobModalOkButton;
    }

    public WebElement getWorkbenchCommentsTextArea() {
        return workbenchCommentsTextArea;
    }

    public WebElement getWorkbenchSubmitCommentsButton() {
        return workbenchSubmitCommentsButton;
    }

    public WebElement getWorkbenchAccountNavigationDropDown() {
        return workbenchAccountNavigationDropDown;
    }

    public WebElement getFlagIssueButton() {
        return flagIssueButton;
    }

    public WebElement getFlagResolveIssueButton() {
        return flagResolveIssueButton;
    }

    public WebElement getFlagNotLanguageRadioButton() {
        return flagNotLanguageRadioButton;
    }

    public WebElement getFlagUnsuitableContentRadioButton() {
        return flagUnsuitableContentRadioButton;
    }

    public WebElement getFlagNotEnoughContextRadioButton() {
        return flagNotEnoughContextRadioButton;
    }

    public WebElement getFlagUnreasonableRequestRadioButton() {
        return flagUnreasonableRequestRadioButton;
    }

    public WebElement getFlagWrongWordCountRadionButton() {
        return flagWrongWordCountRadionButton;
    }

    public WebElement getFlagTechnicalIssueRadionButton() {
        return flagTechnicalIssueRadionButton;
    }

    public WebElement getFlagCommentTextArea() {
        return flagCommentTextArea;
    }

    public WebElement getFlagJobButton() {
        return flagJobButton;
    }

    public WebElement getFlagCancelButton() {
        return flagCancelButton;
    }

    public WebElement getTranslatorLogo() {
        return translatorLogo;
    }

    public WebElement getCollectionLimitModalOkButton() {
        return collectionLimitModalOkButton;
    }

    public WebElement getModalOkButton() {
        return modalOkButton;
    }

    public WebElement getSubmitDeclineJobDropDownButton() {
        return submitDeclineJobDropDownButton;
    }

    public WebElement getDeclineCollection() {
        return declineCollection;
    }

    public WebElement getDeclineButton() {
        return declineButton;
    }

    public WebElement getDeclineReasonUnsuitableContent() {
        return declineReasonUnsuitableContent;
    }

    public WebElement getDeclineCollectionModalTextArea() {
        return declineCollectionModalTextArea;
    }

    public WebElement getDeclineCollectionModalButton() {
        return declineCollectionModalButton;
    }

    public WebElement getShortcutListModalCloseButton() {
        return shortcutListModalCloseButton;
    }

    public WebElement getHorizontalViewButton() {
        return horizontalViewButton;
    }

    public WebElement getVerticalViewButton() {
        return verticalViewButton;
    }

    public WebElement getValidationButton() {
        return validationButton;
    }

    public WebElement getValidationButtonWithWarning() {
        return validationButtonWithWarning;
    }

    public WebElement getValidationButtonWithWarningError() {
        return validationButtonWithWarningError;
    }

    public WebElement getToolsButton() {
        return toolsButton;
    }

    public WebElement getToolsButtonSelected() {
        return toolsButtonSelected;
    }

    public WebElement getFilterDropdownButton() {
        return filterDropdownButton;
    }

    public WebElement getMoreButton() {
        return moreButton;
    }

    public WebElement getDashboardHeader(){
        return dashboardHeader;
    }

    public WebElement getReviewableHeader(){
       return reviewableHeader;
    }

    public WebElement getCommentsSection(){
        return commentsSection;
    }

    public WebElement getFindOutMoreLink() {
        return findOutMoreLink;
    }

    public WebElement getHorizontalBtn() {
        return horizontalBtn;
    }

    public WebElement getHorizontalSelected() {
        return horizontalSelected;
    }

    public WebElement getVerticalBtn() {
        return verticalBtn;
    }

    public WebElement getVerticalSelected() {
        return verticalSelected;
    }

    public WebElement getGlossarySection() {
        return glossarySection;
    }

    public WebElement getIssuesSection() {
        return issuesSection;
    }

    public WebElement getGlossaryBtn() {
        return glossaryBtn;
    }

    public WebElement getSendFeedbackBtn() {
        return sendFeedbackBtn;
    }

    public WebElement getFilterBtn() {
        return filterBtn;
    }

    public WebElement getFilterSelectedBtn() {
        return filterSelectedBtn;
    }

    public WebElement getFilterAll() {
        return filterAll;
    }

    public WebElement getFilterAllCount() {
        return filterAllCount;
    }

    public WebElement getFilterAvailable() {
        return filterAvailable;
    }

    public WebElement getFilterAvailableCount() {
        return filterAvailableCount;
    }

    public WebElement getFilterEmpty() {
        return filterEmpty;
    }

    public WebElement getFilterUnsubmittedDisabled() {
        return filterUnsubmittedDisabled;
    }

    public WebElement getFilterSubmittedDisabled() {
        return filterSubmittedDisabled;
    }

    public WebElement getFilterRevising() {
        return filterRevising;
    }

    public WebElement getFilterRevisingDisabled() {
        return filterRevisingDisabled;
    }

    public WebElement getFilterErrorDisabled() {
        return filterErrorDisabled;
    }

    public WebElement getFilterEmptyCount() {
        return filterEmptyCount;
    }

    public WebElement getFilterUnsubmittedCount() {
        return filterUnsubmittedCount;
    }

    public WebElement getFilterSubmittedCount() {
        return filterSubmittedCount;
    }

    public WebElement getFilterRevisingCount() {
        return filterRevisingCount;
    }

    public WebElement getFilterErrorCount() {
        return filterErrorCount;
    }

    public WebElement getFilterEmptyDisabled() {
        return filterEmptyDisabled;
    }

    public WebElement getFilterUnsubmitted() {
        return filterUnsubmitted;
    }

    public WebElement getFilterError() {
        return filterError;
    }

    public WebElement getFilterSubmitted() {
        return filterSubmitted;
    }

    public WebElement getNoJobToSubmitBtn() {
        return noJobToSubmitBtn;
    }

    public WebElement getMoreHowToUse() {
        return moreHowToUse;
    }

    public WebElement getMoreKeyboardShortcuts() {
        return  moreKeyboardShortcuts;
    }

    public WebElement getMoreSupport() {
        return moreSupport;
    }

    public WebElement getModalNextBtn() {
        return modalNextBtn;
    }

    public WebElement getModalPreviousBtn() {
        return modalPreviousBtn;
    }

    public WebElement getCloseBtn() {
        return closeBtn;
    }

    public WebElement getJobInfo() {
        return jobInfo;
    }

    public List<WebElement> getJobInfos() {
        return jobInfos;
    }

    public WebElement getInactiveSourceText() {
        return inactiveSourceText;
    }

    public WebElement getActiveSourceText() {
        return activeSourceText;
    }

    public WebElement getActiveSourceWarningText() {
        return activeSourceWarningText;
    }

    public WebElement getFixErrorsToSubmitBtn() {
        return fixErrorsToSubmitBtn;
    }

    public WebElement getDeclineUnableToFinish() {
        return declineUnableToFinish;
    }

    public WebElement getDeclineNotInLanguage() {
        return declineNotInLanguage;
    }

    public WebElement getDeclineUnsuitableContent() {
        return declineUnsuitableContent;
    }

    public WebElement getDeclineNotEnoughContext() {
        return declineNotEnoughContext;
    }

    public WebElement getDeclineUnreasonableRequests() {
        return declineUnreasonableRequests;
    }

    public WebElement getDeclineWrongWordCount() {
        return declineWrongWordCount;
    }

    public WebElement getDeclineTechnicalIssue() {
        return declineTechnicalIssue;
    }

    public WebElement getDeclineCommentTextArea() {
        return declineCommentTextArea;
    }

    public WebElement getCollectionUnavailableText() {
        return collectionUnavailableText;
    }

    public WebElement getCollectionUnavailableOKBtn() {
        return collectionUnavailableOKBtn;
    }

    public WebElement getDisabledSubmitBtn() {
        return disabledSubmitBtn;
    }

    public WebElement getErrorValidationBtn() {
        return errorValidationBtn;
    }

    public WebElement getRewardTxt() {
        return rewardTxt;
    }

    public WebElement getAllottedTimeTxt() {
        return allottedTimeTxt;
    }

    public WebElement getApprovalTimeTxt() {
        return approvalTimeTxt;
    }

    public WebElement getTotalUnitsTxt() {
        return totalUnitsTxt;
    }

    public WebElement getRewardPerUnitTxt() {
        return rewardPerUnitTxt;
    }

    public WebElement getJobCountTxt() {
        return jobCountTxt;
    }

    public WebElement getCollectionIdTxt() {
        return collectionIdTxt;
    }

    public WebElement getTierTxt() {
        return tierTxt;
    }

    public WebElement getTmTxt() {
        return tmTxt;
    }

    public WebElement getPurposeTxt() {
        return purposeTxt;
    }

    public WebElement getToneTxt() {
        return toneTxt;
    }

    public WebElement getLanguagePairTxt() {
        return languagePairTxt;
    }

    public WebElement getTextSaveAndSaving() {
        return textSaveAndSaving;
    }

    public WebElement getJobIdHolder() {
        return jobIdHolder;
    }

    public WebElement getJobStatusText() {
        return jobStatusText;
    }

    public WebElement getJobIDText() {
        return jobIDText;
    }

    public WebElement getInactiveTextArea() {
        return inactiveTextArea;
    }

    public WebElement getValidationText() {
        return validationText;
    }

    public WebElement getSegmentHolder() {
        return segmentHolder;
    }

    public List<WebElement> getHighlightedTexts() {
        return highlightedTexts;
    }

    public WebElement getWarningTargetText() {
        return warningTargetText;
    }

    public WebElement getTargetText()  {
        return targetText;
    }

    public WebElement getWelcomeModalHeaderText1() {
        return welcomeModalHeaderText1;
    }

    public WebElement getWelcomeModalMedia1() {
        return welcomeModalMedia1;
    }

    public WebElement getWelcomeModalDescription1() {
        return welcomeModalDescription1;
    }

    public WebElement getWelcomeModalStep1() {
        return welcomeModalStep1;
    }

    public WebElement getWelcomeModalHeaderText2() {
        return welcomeModalHeaderText2;
    }

    public WebElement getWelcomeModalMedia2() {
        return welcomeModalMedia2;
    }

    public WebElement getWelcomeModalDescription2() {
        return welcomeModalDescription2;
    }

    public WebElement getWelcomeModalStep2() {
        return welcomeModalStep2;
    }

    public WebElement getWelcomeModalHeaderText3() {
        return welcomeModalHeaderText3;
    }

    public WebElement getWelcomeModalMedia3() {
        return welcomeModalMedia3;
    }

    public WebElement getWelcomeModalDescription3() {
        return welcomeModalDescription3;
    }

    public WebElement getWelcomeModalStep3() {
        return welcomeModalStep3;
    }

    public WebElement getWelcomeModalHeaderText4() {
        return welcomeModalHeaderText4;
    }

    public WebElement getWelcomeModalMedia4() {
        return welcomeModalMedia4;
    }

    public WebElement getWelcomeModalDescription4() {
        return welcomeModalDescription4;
    }

    public WebElement getWelcomeModalStep4() {
        return welcomeModalStep4;
    }

    public WebElement getWelcomeModalHeaderText5() {
        return welcomeModalHeaderText5;
    }

    public WebElement getWelcomeModalMedia5() {
        return welcomeModalMedia5;
    }

    public WebElement getWelcomeModalDescription5() {
        return welcomeModalDescription5;
    }

    public WebElement getWelcomeModalStep5() {
        return welcomeModalStep5;
    }

    public WebElement getCollectionUnavailableDescription() {
        return collectionUnavailableDescription;
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
