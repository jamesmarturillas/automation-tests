package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.ElementActions;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GlobalPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

/**
 * @class Object repository for global page.
 * Contains element operations for elements that can be seen in multiple pages.
 */
public class GlobalPage extends GlobalPageFactory {

    private final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private SecureRandom rnd = new SecureRandom();

    public GlobalPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private CustomerOrdersPage customerOrdersPage = new CustomerOrdersPage(driver);
    private Variables var = new Variables();
    private ElementActions elementActions = new ElementActions(driver);
    private JSExecutor js = new JSExecutor(driver);

    public void clickTranslatorLogo() {
        wait.impWait(30);
        getGengoTranslatorLogoInWorkbench().click();
        wait.impWait(30);
    }

    public void clickName() {
        elementActions.moveToElement(getSignOutParentDropDown());
        try {
            if (!getAccountBtn().isDisplayed()) {
                getSignOutParentDropDown().click();
            }
        }
        catch (NoSuchElementException e) {}
        wait.impWait(30);
    }

    public void hideAdminRightPanel() {
        getRightPanelHide().click();
        wait.impWait(5);
    }

    public void adminSignOut() {
        this.hideAdminRightPanel();
        wait.untilElementIsClickable(getSignOutParentDropDown());
        getAdminSignOutParentDropDown().click();
        getAdminSignOutBtn().click();
        wait.impWait(10);
    }

    public void adminSignOut(boolean hasToHidePanel) {
        if (hasToHidePanel)
            this.hideAdminRightPanel();

        wait.untilElementIsClickable(getSignOutParentDropDown());
        js.scrollIntoElement(getSignOutParentDropDown());
        getAdminSignOutParentDropDown().click();
        getAdminSignOutBtn().click();
        wait.impWait(10);
    }

    public void nonAdminSignOut() {
        this.clickName();
        String signOutUrl = getNonAdminSignOutBtn().getAttribute("href");
        new PageActions(driver).launchUrl(signOutUrl);
        wait.impWait(10);
    }

    public void goToAcctSettings() {
        this.clickName();
        getAccountBtn().click();
        wait.impWait(10);
        getLeftMenuAccountSettings().click();
        wait.impWait(10);
    }

    public void goToTopUp() {
        this.clickName();
        getAccountBtn().click();
        wait.impWait(10);
        getLeftMenuTopUp().click();
        wait.impWait(10);
    }

    public void selectCustomer() {
        this.clickName();
        getCustomerBtn().click();
        wait.impWait(10);
    }

    public void selectTranslator() {
        this.clickName();
        getTranslatorBtn().click();
        wait.impWait(10);
    }

    public void selectAdmin() {
        this.clickName();
        getAdminBtn().click();
        wait.impWait(10);
    }

    public void goToAdminPage() {
        clickName();
        getAdminBtn().click();
        wait.impWait(10);
    }

    public void goToOrdersPage(){
        wait.impWait(30, getOrdersTab());
        getOrdersTab().click();
        wait.impWait(10);
    }

    public void clickOrderTranslationIcon() {
        wait.untilElementIsClickable(getOrderTranslationIcon());
        getOrderTranslationIcon().click();
        wait.impWait(10);
    }

    public String randomString(int len) {
        StringBuilder sb = new StringBuilder( len );
        for(int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public void goToPendingJob(String excerpt) {
        this.goToOrdersPage();
        wait.impWait(10);
        customerOrdersPage.findOrder(excerpt, true);
    }

    public void goToPendingJob(String excerpt, List<String> jobIds) {
        this.goToOrdersPage();
        wait.impWait(10);
        customerOrdersPage.findOrder(excerpt, jobIds);
    }

    public void goToCustomerReviewableJob(String excerpt) {
        this.goToOrdersPage();
        wait.impWait(10);
        customerOrdersPage.findOrder(excerpt);
    }

    public void goToCustomerReviewableJob(String excerpt, List<String> jobIds) {
        this.goToOrdersPage();
        wait.impWait(10);
        customerOrdersPage.findOrder(excerpt, jobIds);
    }

    public void goToCustomerReviewableJob(List<String> jobIds, int index) {
        this.goToOrdersPage();
        wait.impWait(10);
        customerOrdersPage.findOrder(jobIds, index);
    }

    public double getCurrentCredits() {
        return Double.parseDouble(getCreditsAmt().getText().replaceAll("[^0-9.,]+",""));
    }

    public void goToTranslatorCompletedJobs() {
        getTranslatorCompletedBtn().click();
        wait.impWait(30);

    }

    public void goToTranslatorDashboardPage() {
        getTranslatorDashboardBtn().click();
        wait.impWait(5);
    }

    public void goToTranslatorAllJobs() {
        this.clickJobsTab();
        wait.impWait(10);
        getTranslatorAllJobsBtn().click();
        wait.impWait(30);
    }

    public void goToTranslatorReviewableJobs() {
        this.clickJobsTab();
        wait.impWait(10);
        getTranslatorReviewableBtn().click();
        wait.impWait(30);
    }

    public void goToTranslatorPreferredJobs() {
        this.clickJobsTab();
        wait.impWait(10);
        getTranslatorPreferredBtn().click();
        wait.impWait(30);
    }

    public void clickJobsTab() {
        getTranslatorJobsTab().click();
        wait.impWait(10);
    }

    public void clickTestsTab() {
        getTranslatorTestsTab().click();
        wait.impWait(10);
    }

    public void clickResourcesTab() {
        getTranslatorResourcesTab().click();
        wait.impWait(10);
    }

    public void clickCommunityTab() {
        getTranslatorCommunityTab().click();
        wait.impWait(10);
    }

    public void clickSupportTab() {
        getTranslatorSupportTab().click();
        wait.impWait(10);
    }

    public void clickOrdersTab() {
        getOrdersTab().click();
        wait.impWait(10);
    }

    /**
     * @param languge
     *  Holds the string argument such as :
     *  => "Deutsch"
     *  => "Español"
     *  => "中文"
     *  => "日本語"
     *  => "English"
     */
    public void chooseLanguage(String languge) {
        By selectedLanguage = By.xpath("//a[contains(text(), '" + languge + "')]");
        wait.impWait(10, getAccountLanguageSelectionDropDown());
        getAccountLanguageSelectionDropDown().click();
        getParentLanguageSelection().findElement(selectedLanguage).click();
        wait.impWait(60);
    }

    public void restoreLocaleToEnglish() {
        if (!getAccountLanguageSelectionDropDown().getText().contains(var.getLangEN())) {
            this.chooseLanguage(var.getLangEN());
            wait.impWait(30);
        }
    }

    public void clickTranslatorRevisingBtn() {
        wait.impWait(30);
        getTranslatorRevisingBtn().click();
        wait.impWait(30);
    }

    public void clickTranslatorReviewableBtn() {
        wait.impWait(30);
        getTranslatorReviewableBtn().click();
        wait.impWait(30);
    }

    public void clickCustomerApprovedBtn() {
        wait.impWait(30);
        getCustomerApprovedBtn().click();
        wait.impWait(30);
    }

    public void clickGlossariesTab() {
        wait.impWait(30);
        getGlossariesTab().click();
        wait.impWait(30);
    }
}
