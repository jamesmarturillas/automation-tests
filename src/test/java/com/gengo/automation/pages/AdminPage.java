package com.gengo.automation.pages;

import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.AdminPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

/**
 * @class Object repository for Admin page.
 * Contains element operations.
 */
public class AdminPage extends AdminPageFactory {

    public AdminPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver);
    private JSExecutor js = new JSExecutor(driver);

    public void searchAccount(String account) {
        getTxtBoxSearch().sendKeys(account);
        getTxtBoxSearch().submit();
        wait.impWait(30);
    }

    public void hidePanel() {
        getRightPanelHide().click();
        wait.impWait(5);
    }

    public void clickActivityTab() {
        wait.impWait(30);
        getActivityTab().click();
        wait.impWait(30);
    }

    public void clickProjectsTab() {
        wait.impWait(30);
        getProjectsTab().click();
        wait.impWait(30);
    }

    public void clickLanguagesTab() {
        wait.impWait(30);
        getLanguagesTab().click();
        wait.impWait(30);
    }

    public void clickFinanceTab() {
        wait.impWait(30);
        getFinanceTab().click();
        wait.impWait(30);
    }

    public void clickManageTab() {
        wait.impWait(30);
        getManageTab().click();
        wait.impWait(30);
    }

    public void goToOrders() {
        this.clickActivityTab();
        getActivityTab().findElement(getByOrders()).click();
        wait.impWait(30);
    }

    public void goToCollections() {
        this.clickActivityTab();
        getActivityTab().findElement(getByCollections()).click();
        wait.impWait(30);
    }

    public void goToJobs() {
        this.clickActivityTab();
        getActivityTab().findElement(getByJobs()).click();
        wait.impWait(30);
    }

    public void goToComments() {
        this.clickActivityTab();
        getActivityTab().findElement(getByComments()).click();
        wait.impWait(30);
    }

    public void goToProjects() {
        this.clickProjectsTab();
        getProjectsTab().findElement(getByProjects()).click();
        wait.impWait(30);
    }

    public void goToFiles() {
        this.clickProjectsTab();
        getProjectsTab().findElement(getByFiles()).click();
        wait.impWait(30);
    }

    public void goToLanguages() {
        this.clickLanguagesTab();
        getLanguagesTab().findElement(getByLanguages()).click();
        wait.impWait(30);
    }

    public void goToLanguagePairs() {
        this.clickLanguagesTab();
        getLanguagesTab().findElement(getByLanguagePairs()).click();
        wait.impWait(30);
    }

    public void goToQualifications() {
        this.clickLanguagesTab();
        getLanguagesTab().findElement(getByQualifications()).click();
        wait.impWait(30);
    }

    public void goToChecks() {
        this.clickLanguagesTab();
        getLanguagesTab().findElement(getByChecks()).click();
        wait.impWait(30);
    }

    public void goToFinance() {
        this.clickFinanceTab();
        getFinanceTab().findElement(getByTransactions()).click();
        wait.impWait(30);
    }

    public void goToPurchases() {
        this.clickFinanceTab();
        getFinanceTab().findElement(getByPurchases()).click();
        wait.impWait(30);
    }

    public void goToPayouts() {
        this.clickFinanceTab();
        getFinanceTab().findElement(getByPayouts()).click();
        wait.impWait(30);
    }

    public void goToAccounts() {
        this.clickManageTab();
        getManageTab().findElement(getByAccounts()).click();
        wait.impWait(30);
    }

    public void goToUsers() {
        this.clickManageTab();
        getManageTab().findElement(getByUsers()).click();
        wait.impWait(30);
    }

    public void goToNews() {
        this.clickManageTab();
        getManageTab().findElement(getByNews()).click();
        wait.impWait(30);
    }

    public void searchJobId(String jobId) {
        getTxtBoxJobId().clear();
        getTxtBoxJobId().sendKeys(jobId);
        getTxtBoxJobId().submit();
        wait.impWait(30);
    }

    public void searchAndOpenCollectionID(String collectionID) {
        boolean isClick = true;
        getTxtBoxSearch().clear();
        getTxtBoxSearch().sendKeys(collectionID);
        getTxtBoxSearch().submit();
        wait.impWait(30);
        driver.navigate().refresh();
        while(isClick) {
            try {
                driver.findElement(By.xpath("//td[contains(.,'Collection')]/preceding-sibling::td/a[contains(.,'" + collectionID + "')]")).click();
                wait.untilElementVisible(driver.findElement(By.xpath("//h1[contains(.,'" + collectionID + "')]")));
                isClick = false;
            } catch (NoSuchElementException e) {
                isClick = true;
            }
        }
    }

    public void openHeldJob() {
        getResultTable().findElement(getByResultId()).click();
    }

    public void searchJobIdAndOpen(String jobId) {
        this.searchJobId(jobId);
        this.openHeldJob();
    }

    public boolean checkAssignTranslatorModal() {
        boolean state = true;
        try {
            getAssignTranslatorModalHeader().isDisplayed();
            getAssignTranslatorModalText().isDisplayed();
            getAnswerYes().isDisplayed();
            getAnswerNo().isDisplayed();
            getCloseBtn().isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public boolean checkAddTranslatorModal() {
        boolean state = true;
        try {
            getAddTranslatorModalHeader().isDisplayed();
            getAddTranslatorModalText().isDisplayed();
            getAddTranslatorInput().isDisplayed();
            getAddTranslatorCancelBtn().isDisplayed();
            getAddTranslatorUpdateBtn().isDisplayed();
            getCloseBtn().isDisplayed();
        }
        catch(NoSuchElementException e) {
            state = false;
        }
        return state;
    }

    public void clickCollectionActions(String action) {
        getActionBtn().click();
        switch (action) {
            case "comment":
                getCollectionActionBtnCommentLink().click();
                wait.impWait(30);
                break;
            case "hold":
                getCollectionActionBtnHoldLink().click();
                wait.impWait(30);
                break;
            case "unhold":
                getCollectionActionBtnUnholdLink().click();
                wait.untilElementVisible(getAnswerYes());
                getAnswerYes().click();
                wait.impWait(30);
                break;
            case "makePreferred":
                getCollectionActionBtnMakePreferredLink().click();
                wait.impWait(30);
                break;
            case "assignTranslator":
                getCollectionActionBtnAssignTranslatorLink().click();
                wait.impWait(30);
                break;
            case "editReward":
                getCollectionActionBtnEditRewardLink().click();
                wait.impWait(30);
                break;
        }
    }

    public void clickUserActions(String action) {
        wait.untilElementIsClickable(getActionBtn());
        getActionBtn().click();
        switch (action) {
            case "hijackUser":
                getUserActionBtnHijackUserLink().click();
                wait.untilElementVisible(getAnswerYes());
                getAnswerYes().click();
                wait.impWait(30, customerDashboardPage.getCustomerDashboardText());
                break;
            case "addPreferred":
                getUserActionBtnAddPreferredTranslatorsLink().click();
                wait.impWait(30, getPreferredTranslatorModalTxt());
                break;
            case "bulkAddPreferred":
                getUserActionBtnBulkAddPreferredTranslatorsLink().click();
                wait.impWait(30);
                break;
            case "creditRewardAdjustment":
                getUserActionBtnCreditOrRewardAdjustmentLink().click();
                wait.impWait(30);
                break;
            case "associateAccount":
                getUserActionBtnAssociateAccountLink().click();
                wait.impWait(30);
                break;
            case "suspendUser":
                getUserActionBtnSuspendUserLink().click();
                wait.impWait(30);
                break;
            case "comment":
                getUserActionBtnCommentLink().click();
                wait.impWait(30);
                break;
            case "editCustomPrices":
                getUserActionBtnEditCustomPricesLink().click();
                wait.impWait(30);
                break;
            case "exportSpend":
                getUserActionBtnExportSpendLink().click();
                wait.impWait(30);
                break;
            case "exportTmReport":
                getUserActionBtnExportTmReportLink().click();
                wait.impWait(30);
                break;
        }
    }

    public void addTranslator(String identifier) {
        assertTrue(this.checkAssignTranslatorModal());
        wait.untilElementIsClickable(getAnswerYes());
        getAnswerYes().click();
        wait.untilElementVisible(getAddTranslatorModalHeader());
        assertTrue(this.checkAddTranslatorModal());
        getAddTranslatorInput().clear();
        getAddTranslatorInput().sendKeys(identifier);
        wait.untilElementVisible(driver.findElement(By.xpath("//li[contains(.,'" + identifier + "')]")));
        WebElement result = driver.findElement(By.xpath("//li[contains(.,'" + identifier + "')]"));
        result.click();
        assertTrue(getAddTranslatorInput().getAttribute("value").contains(identifier));
        getAddTranslatorUpdateBtn().click();
    }

    public void clickCreateNewQualificationsBtn() {
        wait.impWait(30);
        getCreateNewQualificationsBtn().click();
        wait.impWait(30);
    }

    public void createQualification(String idNumber, String source, String target, String rank) {
        Select srcLanguage = new Select(getSelectQualificationsSource());
        Select tgtLanguage = new Select(getSelectQualificationsTarget());
        Select rnkLevel = new Select(getSelectQualificationsRank());

        getTxtTranslatorId().clear();
        getTxtTranslatorId().sendKeys(idNumber);
        srcLanguage.selectByVisibleText(source);
        tgtLanguage.selectByVisibleText(target);
        rnkLevel.selectByVisibleText(rank);
        getSelectQualificationsRank().submit();
    }

    public String getUserIDViaEmail(String email) {
        getTxtBoxSearch().clear();
        getTxtBoxSearch().sendKeys(email);
        getTxtBoxSearch().submit();
        wait.untilElementVisible(driver.findElement(By.xpath("//a[@id='detail-email'][contains(.,'" + email + "')]")));
        WebElement textElement = driver.findElement(By.xpath("//h1[@class='pull-left']"));
        return textElement.getText().replaceAll("[A-z@ ]","");
    }

    public void clickFilterButton() {
        getFilterCheckButton().click();
        wait.impWait(30);
    }

    public void clickClearButton() {
        getClearFilterCheckButton().click();
        wait.impWait(30);
    }
}
