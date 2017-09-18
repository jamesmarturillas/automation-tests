package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.*;
import com.gengo.automation.pages.PageFactories.WorkbenchPageFactory;
import org.openqa.selenium.*;

import org.openqa.selenium.NoSuchElementException;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

/**
 * @class Object repository for translator tests page.
 * Contains element operations.
 */
public class WorkbenchPage extends WorkbenchPageFactory {

    public WorkbenchPage(WebDriver driver) throws IOException, AWTException {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    private Variables var = new Variables();
    private PageRobot robot = new PageRobot(driver);
    private ElementState elementState = new ElementState(driver);
    private PageActions page = new PageActions(driver);

    public void closeWorkbenchModal() {
        if (elementState.isElementPresentInSource("modal-content")) {
            getCloseWorkbenchModal().click();
            wait.impWait(30);
        }
    }

    public boolean isWorkbenchModalVisible() {
        boolean state;
        try {
            state = getCloseWorkbenchModal().isDisplayed();
        }
        catch (NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public void startTranslate() {
        wait.untilElementIsClickable(getStartTranslateMainButton());
        getStartTranslateMainButton().click();
        wait.untilTextIsPresent(getStartTranslateParentButton(), "No jobs to submit");
        wait.impWait(10);
    }

    public void startTranslate(boolean hasToWaitForStartTranslateButton) {
        if (hasToWaitForStartTranslateButton) {
            wait.untilElementIsClickable(getStartTranslateMainButton());
            getStartTranslateMainButton().click();
        }
        wait.untilTextIsPresent(getStartTranslateParentButton(), "No jobs to submit");
        wait.impWait(10);
    }

    public void startTranslate(boolean... willAssertForUnableToPickUp) {
        for (boolean assertion : willAssertForUnableToPickUp) {
            if (!assertion) {
                break;
            }
        }
        wait.untilElementIsClickable(getStartTranslateMainButton());
        getStartTranslateMainButton().click();
        wait.impWait(10);
    }

    public void clickInactiveText() {
        try{
            wait.untilElementIsClickable(getInactiveSourceText());
            getInactiveSourceText().click();
        }
        catch(NoSuchElementException e){}
    }

    public void translateTextArea(String translatedText) {
        wait.untilElementVisible(getTranslationTextArea());
        js.scrollIntoElement(getTranslationTextArea());
        getTranslationTextArea().clear();
        getTranslationTextArea().sendKeys(translatedText);
        wait.untilElementIsClickable(getSubmitButton());
    }

    public void translateTextArea(String translatedText, boolean waitSubmitBtn) {
        wait.untilElementVisible(getTranslationTextArea());
        js.scrollIntoElement(getTranslationTextArea());
        getTranslationTextArea().clear();
        getTranslationTextArea().sendKeys(translatedText);
        if(waitSubmitBtn)
            wait.untilElementIsClickable(getSubmitButton());
    }

    public void translateTextAreaInactive(String translatedText) {
        wait.untilElementVisible(getTranslationTextArea2());
        js.scrollIntoElement(getTranslationTextArea2());
        getTranslationTextArea2().clear();
        getTranslationTextArea2().sendKeys(translatedText);
        wait.untilElementIsClickable(getSubmitButton());
    }

    public void translateTextAreaError(String translatedText) {
        wait.untilElementVisible(getTranslationTextArea());
        js.scrollIntoElement(getTranslationTextArea());
        getTranslationTextArea().clear();
        getTranslationTextArea().sendKeys(translatedText);
        wait.untilElementVisible(getFixErrorsToSubmitBtn());
    }

    public void translateTextAreaAddItem(String translatedText) {
        wait.untilElementVisible(getTranslationTextArea());
        js.scrollIntoElement(getTranslationTextArea());
        getTranslationTextArea().sendKeys(translatedText);
        wait.untilElementIsClickable(getSubmitButton());
    }

    public void clearTranslateTextArea() {
        getTranslationTextArea().clear();
        wait.impWait(30, getSubmitButtonParent());
    }

    public void addTextSpecificArea(String text, String excerpt){
        WebElement textarea = driver.findElement(By.xpath("//div/pre[contains(.,'" + excerpt + "')]/parent::div/following-sibling::div/div/textarea"));
        textarea.sendKeys(text);
    }

    public void translateTextArea(String[] translatedText, String[] excerpt, int amountOfJob) {
        for (int count = 0; count < amountOfJob; count++) {
            if (count != 0) {
                driver.findElement(By.xpath("//div/pre[contains(., '" + excerpt[count] + "')]/" +
                        "parent::div/following-sibling::div/div")).click();
            }
            driver.findElement(By.xpath("//div/pre[contains(., '" + excerpt[count] + "')]/" +
                    "parent::div/following-sibling::div/div/textarea")).clear();
            driver.findElement(By.xpath("//div/pre[contains(., '" + excerpt[count] + "')]/" +
                    "parent::div/following-sibling::div/div/textarea")).sendKeys(translatedText[count]);
            wait.impWait(30);
        }
        while (!getSubmitButton().getText().contains("Submit " + amountOfJob + " job")) {
            wait.impWait(30);
        }
    }

    public void translateTextArea(String[] translatedTexts, List<String> excerpts, int amountOfJob) {
        for (int count = 0; count < amountOfJob; count++) {
            if (count != 0) {
                driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                        "parent::div/following-sibling::div/div")).click();
            }
            driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                    "parent::div/following-sibling::div/div/textarea")).clear();
            driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                    "parent::div/following-sibling::div/div/textarea")).sendKeys(translatedTexts[count]);
            wait.impWait(30);
        }
        while (!getSubmitButton().getText().contains("Submit " + amountOfJob + " job")) {
            wait.impWait(30);
        }
    }

    public void translateTextArea(String[] translatedTexts, List<String> excerpts, int amountOfJob, int startingIndex) {
        int tmpAmountOfJob = amountOfJob;
        if (startingIndex > 0) tmpAmountOfJob++;
        try {
            for (int count = startingIndex; count < tmpAmountOfJob; count++) {
                if (count != 0) {
                    driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                            "parent::div/following-sibling::div/div")).click();
                }
                driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                        "parent::div/following-sibling::div/div/textarea")).clear();
                driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                        "parent::div/following-sibling::div/div/textarea")).sendKeys(translatedTexts[count]);
                wait.impWait(30);
            }
        }
        catch (IndexOutOfBoundsException e) {}
        while (!getSubmitButton().getText().contains("Submit " + amountOfJob + " job")) {
            wait.impWait(30);
        }
    }

    public void translateTextArea(String[] translatedTexts, List<String> excerpts, int amountOfJob, boolean withComment) {
        for (int count = 0; count < amountOfJob; count++) {
            if (count != 0) {
                driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                        "parent::div/following-sibling::div/div")).click();
            }
            driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                    "parent::div/following-sibling::div/div/textarea")).clear();
            driver.findElement(By.xpath("//div/pre[contains(., '" + excerpts.get(count) + "')]/" +
                    "parent::div/following-sibling::div/div/textarea")).sendKeys(translatedTexts[count]);
            wait.impWait(30);
        }
        while (!getSubmitButton().getText().contains("Submit " + amountOfJob + " job")) {
            wait.impWait(30);
        }
    }

    public void submitJob() {
        while (!getSubmitButton().getText().contains("Submit ")) {
            wait.impWait(30);
        }
        getSubmitButton().click();
        wait.impWait(30);
    }

    public void submitJobWithoutWait() {
        wait.impWait(30, getSubmitButtonParent());
        getSubmitButtonParent().click();
    }

    public void submitModalOk() {
        wait.untilElementVisible(getSubmitJobModalOkButton());
        getSubmitJobModalOkButton().click();
        wait.untilElementVisible(getDashboardHeader());
    }

    public void submitModalOk(boolean hasToWaitForDashboardHeader) {
        wait.untilElementVisible(getSubmitJobModalOkButton());
        getSubmitJobModalOkButton().click();
        if (hasToWaitForDashboardHeader) {
            wait.untilElementVisible(getDashboardHeader());
        }
        else {
            wait.untilTextIsPresent(getStartTranslateParentButton(), "No jobs to submit");
        }
        wait.impWait(30);
    }

    public void translateJob(String translatedText) {
        this.closeWorkbenchModal();
        this.startTranslate(false);
        this.translateTextArea(translatedText);
        this.submitJob();
        this.submitModalOk();
    }

    public void translateJob(String translatedText, boolean withWorkbenchModal) {
        if (withWorkbenchModal) this.closeWorkbenchModal();
        this.startTranslate();
        this.translateTextArea(translatedText);
        this.checkSavingStatus();
        this.submitJob();
        this.submitModalOk();
    }

    public void translateJobMultiple(String[] translatedTexts, String[] excerpts, int amountOfJob) {
        this.closeWorkbenchModal();
        this.startTranslate();
        this.translateTextArea(translatedTexts, excerpts, amountOfJob);
        this.checkSavingStatus();
        this.submitJob();
        this.submitModalOk();
    }

    public void translateJobMultiple(String[] translatedTexts, List<String> excerpts, int amountOfJob) {
        this.closeWorkbenchModal();
        this.startTranslate();
        this.translateTextArea(translatedTexts, excerpts, amountOfJob);
        this.checkSavingStatus();
        this.submitJob();
        this.submitModalOk();
    }

    public void translateJobMultiple(String[] translatedTexts, List<String> excerpts, boolean hasToWaitForStartTranslateButton, int amountOfJob) {
        this.closeWorkbenchModal();
        this.startTranslate(hasToWaitForStartTranslateButton);
        this.translateTextArea(translatedTexts, excerpts, amountOfJob);
        this.checkSavingStatus();
        this.submitJob();
        this.submitModalOk();
    }

    public void translateJobMultiple(String[] translatedTexts, List<String> excerpts, boolean hasToWaitForStartTranslateButton, boolean hasToWaitForTranslatorDashboard, int amountOfJob, int startingIndex) {
        this.closeWorkbenchModal();
        this.startTranslate(hasToWaitForStartTranslateButton);
        this.translateTextArea(translatedTexts, excerpts, amountOfJob, startingIndex);
        this.checkSavingStatus();
        this.submitJob();
        this.submitModalOk(hasToWaitForTranslatorDashboard);
    }

    public void translateJobMultiple(boolean hasToClickStartTranslateButton, String[] translatedTexts, List<String> excerpts, boolean hasToWaitForTranslatorDashboard, int amountOfJob, int startingIndex) {
        this.closeWorkbenchModal();
        if (hasToClickStartTranslateButton) this.startTranslate();
        this.translateTextArea(translatedTexts, excerpts, amountOfJob, startingIndex);
        this.checkSavingStatus();
        this.submitJob();
        this.submitModalOk(hasToWaitForTranslatorDashboard);
    }

    public void translateJobMultiple(String[] translatedTexts, List<String> excerpts, int amountOfJob, boolean withComment) {
        this.closeWorkbenchModal();
        if (withComment) addTranslatorsComment(var.getTranslatorComment(), false);
        this.startTranslate();
        this.translateTextArea(translatedTexts, excerpts, amountOfJob, withComment);
        this.checkSavingStatus();
        this.submitJob();
        this.submitModalOk();
    }

    public void checkJobInfoAndTranslate(String[] translatedTexts, List<String> excerpts, List<String> strToInspect, List<String> jobIds, boolean hasToWaitForTranslatorDashboard, int amtTimesToLoop, int amountOfJob, int startingIndex) throws InterruptedException {
        this.closeWorkbenchModal();
        this.translateTextArea(translatedTexts, excerpts, amountOfJob, startingIndex);
        this.checkSavingStatus();
        assertTrue(this.isStatusInfoCorrect(strToInspect, jobIds, amtTimesToLoop),
                var.getTextNotEqualErrMsg());
        this.submitJob();
        this.submitModalOk(hasToWaitForTranslatorDashboard);
    }

    public void checkJobInfo(List<String> strToInspect, List<String> jobIds, int amtTimesToLoop) throws InterruptedException {
        this.closeWorkbenchModal();
        assertTrue(this.isStatusInfoCorrect(strToInspect, jobIds,amtTimesToLoop),
                var.getTextNotEqualErrMsg());
    }

    public boolean isStatusInfoCorrect(List<String> strToInspect, List<String> jobIds, int amtTimesToLoop) throws InterruptedException {
        int i = 0;
        for (int y = 0; y <= amtTimesToLoop; y++) {
            try {
                // Loop for value(s) that needs to be found more than once.
                if (amtTimesToLoop > 1) {
                    // Loop for multiple values and need to check multiple times.
                    if (strToInspect.size() > 1) {
                        if (getJobStatus(y, jobIds.size()).equals(strToInspect.get(y))) {
                            i++;
                            if (i < amtTimesToLoop) {
                                return true;
                            }
                        }
                    }
                    // Loop for single value but needs to check multiple times.
                    else {
                        if (getJobStatus(y, jobIds.size()).equals(strToInspect.get(0))) {
                            i++;
                            if (i < amtTimesToLoop) {
                                return true;
                            }
                        }
                    }
                }
                // Loop 1 time.
                else {
                    if (getJobStatus(y, jobIds.size()).equals(strToInspect.get(0))) {
                        return true;
                    }
                }
            }
            catch (StaleElementReferenceException e) {
                y = 0;
            }
            catch (IndexOutOfBoundsException e) {
                if (y <= amtTimesToLoop) {
                    y--;
                }
                else if (y < 0) {
                    y = 0;
                }
            }
        }
        return false;
    }

    public boolean isTranslationTextAreaNotEditable() {
        return getTranslationTextArea().getAttribute("ng-disabled").contains("!isEditable");
    }

    public void checkSavingStatus() {
        assertTrue(elementState.isElementPresentInSource("Saving…"));
        assertTrue(elementState.isElementPresentInSource("Saved"));
    }

    public void addMultipleTranslatedText(String[] translatedText, String[] excerpt, int amountOfJob) {
        this.translateTextArea(translatedText, excerpt, amountOfJob);
    }

    public void translateAndSubmitJob(String translatedText) {
        this.translateTextArea(translatedText);
        this.submitJob();
        this.submitModalOk();
    }

    public void startTranslateJob() {
        this.closeWorkbenchModal();
        this.startTranslate();
    }

    public void clickJobCommentButton(boolean hasToWaitForCommentTextArea) {
        getJobCommentButton().click();
        if (hasToWaitForCommentTextArea) {
            wait.impWait(10, getWorkbenchCommentsTextArea());
        }
        wait.impWait(10);
    }

    public void typeComment() {
        getWorkbenchCommentsTextArea().sendKeys(var.getTranslatorComment());
    }

    public void typeComment(String comment) {
        getWorkbenchCommentsTextArea().sendKeys(comment);
    }

    public void sendTranslatorsComment() {
        By submitCommentBtn = By.xpath("//button[@class='btn btn-sm btn-primary submit']");
        this.clickJobCommentButton(true);
        this.typeComment();
        getWorkbenchSubmitCommentsButton().click();
        wait.untilElementNotVisible(submitCommentBtn);
        this.clickJobCommentButton(false);
    }

    public void addTranslatorsComment(String comment) {
        By submitCommentBtn = By.xpath("//button[@class='btn btn-sm btn-primary submit']");
        this.clickJobCommentButton(true);
        this.typeComment(comment);
        getWorkbenchSubmitCommentsButton().click();
        wait.untilElementNotVisible(submitCommentBtn);
        this.clickJobCommentButton(false);
    }

    public void addTranslatorsComment(String comment, String excerpt) {
        By submitCommentBtn = By.xpath("//button[@class='btn btn-sm btn-primary submit']");
        WebElement commentBtn = driver.findElement(By.xpath("//div/div[contains(.,'" + excerpt + "')][@class='source-wrap']/parent::div/parent::div/preceding-sibling::div//*[@class='icon-comment']"));
        commentBtn.click();
        this.typeComment(comment);
        getWorkbenchSubmitCommentsButton().click();
        wait.untilElementNotVisible(submitCommentBtn);
        commentBtn.click();
    }

    public void addTranslatorsComment(String comment, boolean hasToOpenLeftPanel) {
        if (hasToOpenLeftPanel) this.clickJobCommentButton(true);
        this.typeComment(comment);
        getWorkbenchCommentsTextArea().submit();
        wait.impWait(10);
        if (hasToOpenLeftPanel) this.clickJobCommentButton(false);
    }

    public void addTranslatorsComment(String comment, boolean hasToOpenLeftPanel, boolean hasToCloseLeftPanel) {
        if (hasToOpenLeftPanel) this.clickJobCommentButton(true);
        this.typeComment(comment);
        getWorkbenchCommentsTextArea().submit();

        if (!hasToCloseLeftPanel) {
            wait.impWait(30, getFirstComment(true));
        }
        else {
            wait.impWait(30);
        }

        if (hasToOpenLeftPanel && hasToCloseLeftPanel) this.clickJobCommentButton(false);
    }

    public boolean commentHasBracketAndHtmlTags(boolean isTranslatorsCommentCheck) {
        Pattern p = Pattern.compile("[^[]<>]]");
        return p.matcher(getFirstComment(isTranslatorsCommentCheck).getText()).find();
    }

    public Boolean isCommentsSectionVisible() {
        Boolean isDisplayed = false;
        try {
            isDisplayed = getCommentsSection().isDisplayed();
        }
        catch(NoSuchElementException e){}
        return isDisplayed;
    }

    public boolean isCommentPlain(String comment, boolean commentFromTranslator) {
        while (!getFirstComment(commentFromTranslator).getText().contains(comment)) {
            try {
                wait.impWait(30);
            } catch (StaleElementReferenceException e) {}
        }
        return getFirstComment(commentFromTranslator).getText().contains(comment);
    }

    public Boolean checkCommentVisible(String comment) {
        Boolean isDisplayed = false;
        WebElement commentLine;
        try {
            commentLine = driver.findElement(By.xpath("//div[@class='text ng-binding'][contains(.,'" + comment + "')]"));
            isDisplayed = commentLine.isDisplayed();
        }
        catch(NoSuchElementException e){}
        return isDisplayed;
    }

    public Boolean checkCommentVisible(List<String> comments) {
        Boolean isDisplayed = false;
        WebElement commentLine;
        try {
            for(String comment : comments) {
                commentLine = driver.findElement(By.xpath("//div[@class='text ng-binding'][contains(.,'" + comment + "')]"));
                isDisplayed = commentLine.isDisplayed();
            }
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public void toggleCommentsSection() {
        getJobCommentButton().click();
        wait.impWait(3);
    }

    public void declineJob() {
        this.clickDeclineJobDropDown();
        js.scrollIntoElement(getDeclineButton());
        getDeclineButton().click();
        wait.untilElementVisible(getDeclineCollection());
        getDeclineCollection().click();
        wait.untilElementVisible(getDashboardHeader());
    }

    public void clickDeclineJobDropDown() {
        wait.impWait(30);
        getSubmitDeclineJobDropDownButton().click();
        wait.impWait(20);
    }

    public void declineJob(String reason, String comment){
        this.clickDeclineJobDropDown();
        js.scrollIntoElement(getDeclineCollection());
        getDeclineCollection().click();
        wait.untilElementVisible(getDeclineButton());
        switch(reason) {
            case "unable":
                getDeclineUnableToFinish().click();
                break;
            case "not":
                getDeclineNotInLanguage().click();
                break;
            case "unsuitable":
                getDeclineUnsuitableContent().click();
                break;
            case "context":
                getDeclineNotEnoughContext().click();
                break;
            case "unreasonable":
                getDeclineUnreasonableRequests().click();
                break;
            case "count":
                getDeclineWrongWordCount().click();
                break;
            case "issue":
                getDeclineTechnicalIssue().click();
                break;
            }
        getDeclineCommentTextArea().sendKeys(comment);
        getDeclineButton().click();
        wait.untilElementVisible(getDashboardHeader());
    }

    public void flagJob(String reason, String comment) {
        getFlagIssueButton().click();
        wait.untilElementIsClickable(getFlagUnsuitableContentRadioButton());
        switch(reason) {
            case "not":
                while(!getFlagNotLanguageRadioButton().isSelected())
                    getFlagNotLanguageRadioButton().click();
                break;
            case "unsuitable":
                while(!getFlagUnsuitableContentRadioButton().isSelected())
                    getFlagUnsuitableContentRadioButton().click();
                break;
            case "context":
                while(!getFlagNotEnoughContextRadioButton().isSelected())
                    getFlagNotEnoughContextRadioButton().click();
                break;
            case "unreasonable":
                while(!getFlagUnreasonableRequestRadioButton().isSelected())
                    getFlagUnreasonableRequestRadioButton().click();
                break;
            case "wordCount":
                while(!getFlagWrongWordCountRadionButton().isSelected())
                    getFlagWrongWordCountRadionButton().click();
                break;
            case "issue":
                while(!getFlagTechnicalIssueRadionButton().isSelected())
                    getFlagTechnicalIssueRadionButton().click();
                break;
        }
        getFlagCommentTextArea().sendKeys(comment);
        getFlagJobButton().click();
        wait.untilElementVisible(getFlagResolveIssueButton());
    }

    public Boolean isFlagResolved() {
        Boolean isDisplayed;
        try {
            wait.untilElementVisible(getFlagIssueButton());
            isDisplayed = getFlagIssueButton().isDisplayed();
        }
        catch(NoSuchElementException e){
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isDeclineButtonVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getDeclineCollection().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isGlossaryVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getGlossarySection().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isIssuesVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getIssuesSection().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isDeclineModalVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getDeclineCollectionModalButton().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isHorizontalSelected() {
        boolean isSelected;
        try {
            isSelected = getHorizontalSelected().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isSelected = false;
        }
        return isSelected;
    }

    public boolean isVerticalSelected() {
        boolean isSelected;
        try {
            isSelected = getVerticalSelected().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isSelected = false;
        }
        return isSelected;
    }

    public boolean isTextAreaVisible(boolean isPickedUp) {
        boolean isDisplayed;
        WebElement textArea;
        try {
            if(isPickedUp)
                textArea = getTranslationTextArea();
            else
                textArea = getClickTranslationTextArea();
            isDisplayed = textArea.isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isJobFilteredVisible(String excerpt) {

        boolean isDisplayed;
        WebElement text;
        try {
            text = driver.findElement(By.xpath("//pre[@class='source ng-isolate-scope'][contains(.,'" + excerpt + "')]"));
            isDisplayed = text.isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public void openIssuesSection() {
        WebElement validationButton = getValidationButton();
        boolean clickBtn = true;
        while(clickBtn){
            try{
                validationButton.click();
                if(getIssuesSection().isDisplayed()) {
                    clickBtn = false;
                    wait.untilElementVisible(getIssuesSection());
                }
            }
            catch(NoSuchElementException e){
                validationButton = getValidationButtonWithWarning();
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public void openErrorIssuesSection() {
        boolean clickBtn = true;
        while(clickBtn){
            try{
                getValidationButtonWithWarning().click();
                if(getIssuesSection().isDisplayed()) {
                    clickBtn = false;
                    wait.untilElementVisible(getIssuesSection());
                }
            }
            catch(NoSuchElementException e){
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public void openWarningErrorIssuesSection() {
        boolean clickBtn = true;
        while(clickBtn){
            try{
                getValidationButtonWithWarningError().click();
                if(getIssuesSection().isDisplayed()) {
                    clickBtn = false;
                    wait.untilElementVisible(getIssuesSection());
                }
            }
            catch(NoSuchElementException e){
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public void openGlossarySection() {
        wait.untilElementIsClickable(getGlossaryBtn());
        boolean clickBtn = true;
        while(clickBtn){
            try{
                getGlossaryBtn().click();
                if(getGlossarySection().isDisplayed()) {
                    clickBtn = false;
                    wait.untilElementVisible(getGlossarySection());
                }
            }
            catch(NoSuchElementException e){
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public void openCommentSection() {
        wait.untilElementIsClickable(getJobCommentButton());
        boolean clickBtn = true;
        while(clickBtn){
            try{
                getJobCommentButton().click();
                if(getCommentsSection().isDisplayed()) {
                    clickBtn = false;
                    wait.untilElementVisible(getCommentsSection());
                }
            }
            catch(NoSuchElementException e){
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public boolean checkGlossarySection() {
        boolean state = true;
        try{
            getGotItButton().isDisplayed();
            getFindOutMoreLink().isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkIssueSection(boolean hasIssue) {
        boolean state = true;
        try{
            if(hasIssue){
                getGotItButton().isDisplayed();
                getFindOutMoreLink().isDisplayed();
                getSendFeedbackBtn().isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public void closeIssuesSection() {
        WebElement validationButton = getValidationButton();
        boolean clickBtn = true;
        while(clickBtn){
            try{
                validationButton.click();
                if(!getIssuesSection().isDisplayed()) {
                    clickBtn = false;
                }
            }
            catch(NoSuchElementException e){
                validationButton = getValidationButtonWithWarning();
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public void closeGlossarySection() {
        wait.untilElementIsClickable(getGlossaryBtn());
        boolean clickBtn = true;
        while(clickBtn){
            try{
                getGlossaryBtn().click();
                if(!getGlossarySection().isDisplayed()) {
                    clickBtn = false;
                }
            }
            catch(NoSuchElementException e){
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public void closeCommentSection() {
        wait.untilElementIsClickable(getJobCommentButton());
        boolean clickBtn = true;
        while(clickBtn){
            try{
                getJobCommentButton().click();
                if(!getCommentsSection().isDisplayed()) {
                    clickBtn = false;
                }
            }
            catch(NoSuchElementException e){
                clickBtn = true;
            }
            wait.impWait(5);
        }
    }

    public void changeLayoutToVertical() {
        wait.untilElementIsClickable(getVerticalBtn());
        boolean clickBtn = true;
        while(clickBtn){
            try{
                wait.impWait(3);
                getVerticalBtn().click();
                if(getVerticalSelected().isDisplayed()) {
                    clickBtn = false;
                    wait.untilElementVisible(getVerticalSelected());
                }

            }
            catch(NoSuchElementException e){
                clickBtn = true;
            }
        }
    }

    public void changeLayoutToHorizontal() {
        wait.untilElementIsClickable(getHorizontalBtn());
        boolean clickBtn = true;
        while(clickBtn){
            try{
                getHorizontalBtn().click();
                if(getHorizontalSelected().isDisplayed()) {
                    clickBtn = false;
                    wait.untilElementVisible(getHorizontalSelected());
                }
            }
            catch(NoSuchElementException e){
                clickBtn = true;
            }
        }
    }

    public void openFilterDropDown() {
        WebElement filterBtn = getFilterBtn();
        boolean clickBtn = true;
        while(clickBtn){
            try{
                filterBtn.click();
                clickBtn = false;
            }
            catch(NoSuchElementException e){
                filterBtn = getFilterSelectedBtn();
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public void closeFilterDropDown() {
        WebElement filterBtn = getFilterBtn();
        boolean clickBtn = true;
        while(clickBtn){
            try{
                filterBtn.click();
                clickBtn = false;
            }
            catch(NoSuchElementException e){
                filterBtn = getFilterSelectedBtn();
                clickBtn = true;
            }
            wait.impWait(10);
        }
    }

    public int countAll() {
        return Integer.parseInt(getFilterAllCount().getText());
    }

    public int countAvailable() {
        return Integer.parseInt(getFilterAvailableCount().getText());
    }

    public int countEmpty() {
        return Integer.parseInt(getFilterEmptyCount().getText());
    }

    public int countUnsubmitted() {
        return Integer.parseInt(getFilterUnsubmittedCount().getText());
    }

    public int countSubmitted() {
        return Integer.parseInt(getFilterSubmittedCount().getText());
    }

    public int countRevising() {
        return Integer.parseInt(getFilterRevisingCount().getText());
    }

    public int countError() {
        return Integer.parseInt(getFilterErrorCount().getText());
    }

    public boolean isFilterSelected() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterSelectedBtn().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isAllVisible() {
        boolean isDisplayed;
        try {
                isDisplayed = getFilterAll().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isAvailableVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterAvailable().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isEmptyVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterEmpty().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isEmptyDisabledVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterEmptyDisabled().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isUnsubmittedVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterUnsubmitted().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isUnsubmittedDisabledVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterUnsubmittedDisabled().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isSubmittedVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterSubmitted().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isSubmittedDisabledVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterSubmittedDisabled().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isRevisingDisabledVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterRevisingDisabled().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isErrorVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterError().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isErrorDisabledVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getFilterErrorDisabled().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isNoJobToSubmitVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getNoJobToSubmitBtn().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isStartTranslatingVisible() {
        boolean isDisplayed;
        try {
            isDisplayed = getStartTranslateMainButton().isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public void filterAll() {
        getFilterAll().click();
        wait.untilElementVisible(getFilterBtn());
    }

    public void filterAvailable() {
        getFilterAvailable().click();
        wait.untilElementVisible(getFilterSelectedBtn());
    }

    public void filterEmpty() {
        getFilterEmpty().click();
        wait.untilElementVisible(getFilterSelectedBtn());
    }

    public void filterEmptyDisabled() {
        getFilterEmptyDisabled().click();
        wait.untilElementVisible(getFilterBtn());
    }

    public void filterUnsubmittedDisabled() {
        getFilterUnsubmittedDisabled().click();
        wait.untilElementVisible(getFilterBtn());
    }

    public void filterUnsubmitted() {
        getFilterUnsubmitted().click();
        wait.untilElementVisible(getFilterSelectedBtn());
    }

    public void filterSubmitted() {
        getFilterSubmitted().click();
        wait.untilElementVisible(getFilterSelectedBtn());
    }

    public void filterSubmittedDisabled() {
        getFilterSubmittedDisabled().click();
        wait.untilElementVisible(getFilterBtn());
    }

    public void filterRevising() {
        getFilterRevising().click();
        wait.untilElementVisible(getFilterSelectedBtn());
    }

    public void filterRevisingDisabled() {
        getFilterRevisingDisabled().click();
        wait.untilElementVisible(getFilterBtn());
    }

    public void filterError() {
        getFilterError().click();
        wait.untilElementVisible(getFilterSelectedBtn());
    }

    public void filterErrorDisabled() {
        getFilterErrorDisabled().click();
        wait.untilElementVisible(getFilterBtn());
    }

    public void clickMore() {
        getMoreButton().click();
        wait.impWait(3);
    }

    public boolean checkMoreDropDown() {
        boolean state = true;
        try{
            getMoreHowToUse().isDisplayed();
            getMoreKeyboardShortcuts().isDisplayed();
            getMoreSupport().isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean checkMoreHowToUse() {
       boolean state;
       try {
           getMoreHowToUse().click();
           state = this.checkFirstTimeHelpFlow();
       }
       catch(NoSuchElementException e){
           state = false;
       }
       return state;
    }

    public boolean checkFirstTimeHelpFlow () {
        boolean state = true;
        try {
            wait.untilElementVisible(getWelcomeModalHeaderText1());
            getWelcomeModalHeaderText1().isDisplayed();
            getWelcomeModalMedia1().isDisplayed();
            getWelcomeModalDescription1().isDisplayed();
            getWelcomeModalStep1().isDisplayed();
            wait.untilElementIsClickable(getModalNextBtn());
            getModalNextBtn().click();
            getWelcomeModalHeaderText2().isDisplayed();
            getWelcomeModalMedia2().isDisplayed();
            getWelcomeModalDescription2().isDisplayed();
            getWelcomeModalStep2().isDisplayed();
            getModalPreviousBtn().isDisplayed();
            wait.untilElementIsClickable(getModalNextBtn());
            getModalNextBtn().click();
            getWelcomeModalHeaderText3().isDisplayed();
            getWelcomeModalMedia3().isDisplayed();
            getWelcomeModalDescription3().isDisplayed();
            getWelcomeModalStep3().isDisplayed();
            getModalPreviousBtn().isDisplayed();
            wait.untilElementIsClickable(getModalNextBtn());
            getModalNextBtn().click();
            getWelcomeModalHeaderText4().isDisplayed();
            getWelcomeModalMedia4().isDisplayed();
            getWelcomeModalDescription4().isDisplayed();
            getWelcomeModalStep4().isDisplayed();
            getModalPreviousBtn().isDisplayed();
            wait.untilElementIsClickable(getModalNextBtn());
            getModalNextBtn().click();
            getWelcomeModalHeaderText5().isDisplayed();
            getWelcomeModalMedia5().isDisplayed();
            getWelcomeModalDescription5().isDisplayed();
            getWelcomeModalStep5().isDisplayed();
            getModalPreviousBtn().isDisplayed();
            wait.untilElementIsClickable(getModalNextBtn());
            getModalNextBtn().click();
        }
        catch (NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean checkMoreKeyboardShortcuts() {
        boolean state = true;
        try {
            getMoreKeyboardShortcuts().click();
            driver.findElement(By.xpath("//h2[@id='shortcutModalLabel']")).isDisplayed();
            getCloseBtn().click();
            wait.impWait(5);
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean isKeyboardShortcutVisible() {
        boolean state = true;
        try {
            wait.impWait(5);
            driver.findElement(By.xpath("//h2[@id='shortcutModalLabel']")).isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkJobInfo() {
        boolean state = true;
        try{
            getJobInfo().click();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean checkSaving() {
        boolean state = true;
        try {
            driver.findElement(By.xpath("//span[@class='navbar-text'][contains(.,'Saving')]")).isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean checkSaved() {
        boolean state = true;
        try {
            driver.findElement(By.xpath("//span[@class='navbar-text'][contains(.,'Saved')]")).isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean isErrorBtnVisible() {
        boolean state;
        try {
           state = getFixErrorsToSubmitBtn().isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean collectionUnavailableCheck() {
        boolean state = true;
        try {
            getCollectionUnavailableText().isDisplayed();
            getCollectionUnavailableOKBtn().click();
            wait.untilElementVisible(getDashboardHeader());
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean isSubmitDisabled() {
        boolean state = true;
        try{
            getDisabledSubmitBtn().isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public void clickUncountedSymbols() {
        this.closeWorkbenchModal();
        wait.impWait(30, getHighlightedTexts().get(0));
        this.startTranslate();
        while (!getSubmitButton().getText().contains("No jobs to submit")) {
            continue;
        }
        try {
            for (WebElement symbol : getHighlightedTexts()) {
                symbol.click();
            }
        }
        catch(ElementNotVisibleException e) {
            return;
        }

    }

    public boolean checkGlossaryMatches(String[] glossaryTexts, String[] glossaryMatches) {
        boolean state = true;
        int ctr = 0;
        try{
            for(String source : glossaryTexts) {
                driver.findElement(By.xpath("//tr[@class='ng-scope']/td[@class='ng-binding'][contains(.,'" + source + "')]/following-sibling::td[@class='ng-binding'][contains(.,'" + glossaryMatches[ctr] + "')]")).isDisplayed();
                ctr++;
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkHighlightGlossary(String[] glossarySource) {
        boolean state = true;
        try{
            for(String text : glossarySource)
                driver.findElement(By.xpath("//span[@class='highlight glossary'][contains(.,'" + text + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkHighlightGlossaryWarning(String[] glossarySource) {
        boolean state = true;
        try{
            for(String text : glossarySource){
                driver.findElement(By.xpath("//span[@class='highlight warning glossary'][contains(.,'" + text + "')]")).isDisplayed();}
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkHighlightWarning(String[] warnings) {
        boolean state = true;
        try{
            for(String text : warnings)
                driver.findElement(By.xpath("//span[@class='highlight warning'][contains(.,'" + text + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkHighlightTag(String[] tags) {
        boolean state = true;
        try{
            for(String text : tags)
                driver.findElement(By.xpath("//span[@class='highlight tags'][contains(.,'" + text + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkHighlightTagError(String[] tags) {
        boolean state = true;
        try{
            for(String text : tags){
                driver.findElement(By.xpath("//span[@class='highlight error tags'][contains(.,'" + text + "')]")).isDisplayed();}
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkHighlightComment(String[] comments) {
        boolean state = true;
        try{
            for(String text : comments)
                driver.findElement(By.xpath("//span[@class='highlight brackets'][contains(.,'" + text + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkHighlightCommentError(String[] comments) {
        boolean state = true;
        try{
            for(String text : comments)
                driver.findElement(By.xpath("//span[@class='highlight error brackets'][contains(.,'" + text + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public String getSourceActive() {
        String text;
        try {
            text = getActiveSourceText().getText();
        }
        catch(NoSuchElementException e){
            text = getActiveSourceWarningText().getText();
        }
        return text;
    }

    public String getTargetActive() {
        String text;
        try {
            text = getWarningTargetText().getText();
        }
        catch(NoSuchElementException e){
            text = getTargetText().getText();
        }
        return text;
    }

    public String getSource() {
        return getInactiveSourceText().getText();
    }

    public String getTarget() {
        return getInactiveTextArea().getText();
    }

    public boolean addGlossaryToText(String[] highlightGlossary, String[] glossaryTexts) {
        boolean state = true;
        try {
            for (String text : highlightGlossary) {
                driver.findElement(By.xpath("//span[@class='highlight warning glossary'][contains(.,'" + text + "')]")).click();
                getTranslationTextArea().sendKeys(" ");
                getTranslationTextArea().getText().contains(text);
                driver.findElement(By.xpath("//span[@class='highlight glossary'][contains(.,'" + text + "')]")).isDisplayed();
            }
            for (String match : glossaryTexts)
                driver.findElement(By.xpath("//td[@class='ng-binding'][contains(.,'" + match + "')]/preceding-sibling::td/i[@class='icon icon-check used']")).isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean addGlossaryToText(String[] highlightGlossary, String[] glossaryTexts, String excerpt) {
        boolean state = true;
        WebElement textarea;
        try {
            textarea = driver.findElement(By.xpath("//div/pre[contains(.,'" + excerpt + "')]/parent::div/following-sibling::div/div/textarea"));
            for (String text : highlightGlossary) {
                driver.findElement(By.xpath("//span[@class='highlight warning glossary'][contains(.,'" + text + "')]")).click();
                textarea.sendKeys(" ");
                textarea.getText().contains(text);
                driver.findElement(By.xpath("//span[@class='highlight glossary'][contains(.,'" + text + "')]")).isDisplayed();
            }
            for (String match : glossaryTexts)
                driver.findElement(By.xpath("//td[@class='ng-binding'][contains(.,'" + match + "')]/preceding-sibling::td/i[@class='icon icon-check used']")).isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean addCommentsToText(String[] comments) {
        boolean state = true;
        try {
            for (String text : comments) {
                driver.findElement(By.xpath("//span[@class='highlight error brackets'][contains(.,'" + text + "')]")).click();
                getTranslationTextArea().sendKeys(" ");
                getTranslationTextArea().getText().contains(text);
                driver.findElement(By.xpath("//pre[@class='source ng-isolate-scope']/span[@class='highlight brackets'][contains(.,'" + text + "')]")).isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean addCommentsToText(String[] comments, String excerpt) {
        boolean state = true;
        WebElement textarea;
        try {
            textarea = driver.findElement(By.xpath("//div/pre[contains(.,'" + excerpt + "')]/parent::div/following-sibling::div/div/textarea"));
            for (String text : comments) {
                driver.findElement(By.xpath("//span[@class='highlight error brackets'][contains(.,'" + text + "')]")).click();
                textarea.sendKeys(" ");
                textarea.getText().contains(text);
                driver.findElement(By.xpath("//pre[@class='source ng-isolate-scope']/span[@class='highlight brackets'][contains(.,'" + text + "')]")).isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean addTagsToText(String[] tags) {
        boolean state = true;
        try {
            for (String text : tags) {
                driver.findElement(By.xpath("//span[@class='highlight error tags'][contains(.,'" + text + "')]")).click();
                getTranslationTextArea().sendKeys(" ");
                getTranslationTextArea().getText().contains(text);
                driver.findElement(By.xpath("//pre[@class='source ng-isolate-scope']/span[@class='highlight tags'][contains(.,'" + text + "')]")).isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean addTagsToText(String[] tags, String excerpt) {
        boolean state = true;
        WebElement textarea;
        try {
            textarea = driver.findElement(By.xpath("//div/pre[contains(.,'" + excerpt + "')]/parent::div/following-sibling::div/div/textarea"));
            for (String text : tags) {
                driver.findElement(By.xpath("//span[@class='highlight error tags'][contains(.,'" + text + "')]")).click();
                textarea.sendKeys(" ");
                textarea.getText().contains(text);
                driver.findElement(By.xpath("//pre[@class='source ng-isolate-scope']/span[@class='highlight tags'][contains(.,'" + text + "')]")).isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean addRemainingCautionIssues(String[] resolveCaution) {
        boolean state = true;
        try {
            for (String text : resolveCaution) {
                getTranslationTextArea().sendKeys(text);
                getTranslationTextArea().sendKeys(" ");
                getTranslationTextArea().getText().contains(text);
                driver.findElement(By.xpath("//h4[contains(.,'no issues')]")).isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkTagsIssue(String[] tags) {
        boolean state = true;
        try {
            for (String text : tags)
                driver.findElement(By.xpath("//p[@class='ng-scope'][contains(.,'Missing tag')]/following-sibling::p[contains(.,'" + text + "')]")).isDisplayed();
            }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkCommentIssue(String[] comments) {
        boolean state = true;
        try {
            for (String text : comments) {
                driver.findElement(By.xpath("//p[@class='ng-scope'][contains(.,'Missing triple bracket')]/following-sibling::p[contains(.,'" + text + "')]")).isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public boolean checkCautionIssue(String[] warnings) {
        boolean state = true;
        try {
            for (String text : warnings) {
                driver.findElement(By.xpath("//p[@class='ng-scope'][contains(.,'Missing number')]/following-sibling::p[contains(.,'" + text + "')]")).isDisplayed();
            }
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }

    public String getJobId() {
        wait.untilElementIsClickable(getJobInfo());
        getJobInfo().click();
        return getJobIDText().getText().replaceAll("[^-?0-9]+","");
    }

    public boolean isCorrectCollection(List<String> jobIds) {
        for (String jobId : jobIds) {
            if (getSegmentHolder().getAttribute("data-id").equals(jobId)) {
                return true;
            }
        }
        page.goBack();
        return false;
    }

    public boolean isCorrectCollection(String jobId, boolean isNonGrouped) {
        if (getSegmentHolder().getAttribute("data-id").equals(jobId)) {
            page.goBack();
            return true;
        }
        return false;
    }

    public boolean isCorrectCollection(String jobId) {
        if (getSegmentHolder().getAttribute("data-id").equals(jobId)) {
            return true;
        }
        page.goBack();
        return false;
    }

    public boolean isCorrectFileCollection(String jobId) {
        try {
            return driver.findElement(By.xpath("//input[@name='job_id']")).getAttribute("value").equals(jobId);
        }
        catch(NoSuchElementException ignored) {}
        page.goBack();
        return false;
    }

    public String getJobStatus() {
        getJobInfo().click();
        return getJobStatusText().getText().substring(8, getJobStatusText().getText().length());
    }

    public String getJobStatus(int index, int size) throws InterruptedException {
        Thread.sleep(2500);
        if (index < size) index++;
        driver.findElement(By.xpath("//div[@ng-controller='JobCtrl'][" + index + "]/div/ul/li/a[@class='icon-info'][1]")).click();
        wait.untilElementVisible(getJobStatusText());
        String statsText = getJobStatusText().getText();
        statsText = statsText.substring(8, statsText.length());
        driver.findElement(By.xpath("//div[@ng-controller='CollectionMetaCtrl']")).click();
        return statsText;
    }

    public void keyboardShortcutOpenKey(boolean toOpen) throws AWTException {
        boolean click = true;
        while(click){
            try{
                int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SLASH};
                robot.pressKeys(keys);
                wait.untilElementVisible(driver.findElement(By.xpath("//h2[@id='shortcutModalLabel']")));
                click = !toOpen;
            }
            catch (TimeoutException e){
                click = !toOpen;
            }
            catch (NoSuchElementException e){
                click = toOpen;
            }
        }
    }

    public void toggleIssuesKey(boolean toOpen) throws AWTException {
        boolean click = true;
        while(click){
            try{
                int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_I};
                robot.pressKeys(keys);
                wait.untilElementVisible(getIssuesSection());
                click = !toOpen;
            }
            catch (TimeoutException e){
                click = !toOpen;
            }
            catch (NoSuchElementException e){
                click = toOpen;
            }
        }
    }

    public void toggleCommentsKey(boolean toOpen) throws AWTException {
        boolean click = true;
        while(click){
            try{
                int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_J};
                robot.pressKeys(keys);
                wait.untilElementVisible(getCommentsSection());
                click = !toOpen;
            }
            catch (TimeoutException e){
                click = !toOpen;
            }
            catch (NoSuchElementException e){
                click = toOpen;
            }
        }
    }

    public void toggleStartSubmitKey(boolean isStart) throws AWTException {
        int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_S};
        robot.pressKeys(keys);
        if (isStart)
            wait.untilElementVisible(getNoJobToSubmitBtn());
        else
            wait.untilElementIsClickable(getSubmitJobModalOkButton());
    }

    public void copySourceToTargetKey() throws AWTException {
        int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_V};
        robot.pressKeys(keys);
        wait.impWait(10);
        wait.untilElementVisible(driver.findElement(By.xpath("//button[@class='btn navbar-btn btn-primary']" +
                "[contains(.,'Submit 1 job')]")));
    }

    public void copySourceToTargetClickedKey() throws AWTException {
        int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_V};
        robot.pressKeys(keys);
        wait.impWait(10);
        wait.untilElementVisible(driver.findElement(By.xpath("//button[@class='btn navbar-btn btn-primary']" +
                "[contains(.,'Submit')]")));
    }

    public void copySourceToTargetClickedKey(String numberOfJobs) throws AWTException {
        int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_V};
        robot.pressKeys(keys);
        wait.impWait(10);
        wait.untilElementVisible(driver.findElement(By.xpath("//button[@class='btn navbar-btn btn-primary']" +
                "[contains(.,'Submit " + numberOfJobs + " job')]")));
    }

    public void filterUnsubmittedKey() throws AWTException {
        int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_U};
        robot.pressKeys(keys);
        wait.impWait(10);
        wait.untilElementVisible(getFilterSelectedBtn());
    }

    public void filterSubmittedKey() throws AWTException {
        int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_C};
        robot.pressKeys(keys);
        wait.impWait(10);
    }

    public void toggleGlossaryKey(boolean toOpen) throws AWTException {
                int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_M};
                robot.pressKeys(keys);
    }

    public void closeCollectionKey() throws AWTException {
        int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_E};
        robot.pressKeys(keys);
        wait.impWait(10);
        wait.untilElementVisible(getDashboardHeader());
    }

    public void declineCollectionKey() throws AWTException {
        wait.untilElementIsClickable(getSubmitButton());
        int[] keys = {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_D};
        robot.pressKeys(keys);
        wait.impWait(10);
        wait.untilElementVisible(getDeclineCollectionModalButton());
    }

    public void moveToNextJobKey() throws AWTException {
        int[] keys = {KeyEvent.VK_TAB};
        robot.pressKeys(keys);
        wait.impWait(10);
    }

    public void moveToPreviousJobKey() throws AWTException {
        int[] keys = {KeyEvent.VK_SHIFT, KeyEvent.VK_TAB};
        robot.pressKeys(keys);
        wait.impWait(10);
    }

    public void cancelDecline() {
        wait.untilElementIsClickable(getCloseBtn());
        getCloseBtn().click();
    }

    public boolean isSourceTextVisible(String excerpt){
        Boolean isDisplayed = true;
        try {
            isDisplayed = driver.findElement(By.xpath("//div/pre[contains(.,'" + excerpt + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e) {
            isDisplayed = false;
        }
        return isDisplayed;}

    public void setToHorizontalOrientation() {
        wait.impWait(30);
        getHorizontalBtn().click();
        wait.impWait(10, getHorizontalSelected());
    }

    public void setToVerticalOrientation() {
        wait.impWait(30);
        getVerticalBtn().click();
        wait.impWait(10, getVerticalSelected());
    }

    public void clickValidationBtn(boolean hasToWaitForValidationText) {
        wait.impWait(30);
        getValidationButton().click();
        if (hasToWaitForValidationText) wait.impWait(10, getValidationText());
        wait.impWait(10);
    }

    public void clickHighlightedWords() {
        wait.impWait(20);
        if (elementState.isElementPresentInSource("highlight tags")) {
            for (WebElement text : getHighlightedTexts()) {
                text.click();
            }
        }
        wait.impWait(20);
    }

    public void clickTranslationArea(String excerpt){
        WebElement textarea = driver.findElement(By.xpath("//div/pre[contains(.,'" + excerpt + "')]/parent::div/following-sibling::div/div"));
        wait.untilElementIsClickable(textarea);
        textarea.click();
    }

    public boolean checkUnavailableCollection() {
        boolean state = true;
        try{
            getCollectionUnavailableText().isDisplayed();
            getCollectionUnavailableDescription().isDisplayed();
            getCollectionUnavailableOKBtn().isDisplayed();
            wait.untilElementIsClickable(getCollectionUnavailableOKBtn());
            getCollectionUnavailableOKBtn().click();
            wait.untilElementVisible(getDashboardHeader());
        }
        catch (NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean checkInstructions(String instruction) {
        boolean state = true;
        try{
            driver.findElement(By.xpath("//div[@class='text ng-binding'][contains(.,'" + instruction + "')]")).isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }
}
