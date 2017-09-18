package com.gengo.automation.pages;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.ElementActions;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.CustomerOrdersPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.*;

import static org.testng.Assert.assertTrue;

/**
 * @class Object repository for customer order form page.
 * Contains element operations.
 */
public class CustomerOrdersPage extends CustomerOrdersPageFactory {

    public CustomerOrdersPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    private CustomerOrderDetailsPage customerOrderDetailsPage = new CustomerOrderDetailsPage(driver);
    private PageActions page = new PageActions(driver);
    private CustomerDashboardPage customerDashboardPage = new CustomerDashboardPage(driver);
    private Variables var = new Variables();

    public void findOrder(String excerpt) {
        WebElement orderDetails = driver.findElement(By.xpath("//h3[contains(., '" + excerpt + "')]/parent::div/following-sibling::div/a"));
        wait.untilElementIsClickable(orderDetails);
        js.scrollIntoElement(orderDetails);
        orderDetails.click();
    }

    public void findOrder(WebElement orderDetails) {
        wait.untilElementIsClickable(orderDetails);
        js.scrollIntoElement(orderDetails);
        orderDetails.click();
    }

    public void findOrder(String excerpt, String langPair){
        WebElement orderDetails = driver.findElement(By.xpath("//h3[contains(., ' " + excerpt + " ')]/following-sibling::" +
                "p[contains(.,' " + langPair + " ')]/parent::div/following-sibling::div/a"));
        wait.untilElementVisible(orderDetails);
        js.scrollIntoElement(orderDetails);
        orderDetails.click();
    }

    public void findOrder(String excerpt, boolean withComment) {
        WebElement orderDetails = null;
        if (withComment) {
            orderDetails = driver.findElement(By.xpath("//h3[contains(., '" + excerpt + "')]/parent::div/following-sibling::" +
                    "div[contains(@class, 'comments-number')]/parent::div/div[3]/a"));
        }
        wait.untilElementVisible(orderDetails);
        js.scrollIntoElement(orderDetails);
        orderDetails.click();
    }

    public void findOrder(String excerpt, List<String> jobIds) {
        WebElement orderDetails = driver.findElement(By.xpath("//h3[contains(., '" + excerpt + "')]/parent::div/p[contains(.,'" + jobIds.get(0) + "')]/parent::div/following-sibling::div/a"));
        wait.untilElementVisible(orderDetails);
        js.scrollIntoElement(orderDetails);
        js.clickAtPointY(orderDetails);
    }

    public void findOrder(List<String> jobIds, int index) {
        WebElement orderDetails = driver.findElement(By.xpath("//p[contains(.,'" + jobIds.get(index) + "')]/parent::div/following-sibling::div/a"));
        wait.untilElementVisible(orderDetails);
        js.scrollIntoElement(orderDetails);
        js.clickAtPointY(orderDetails);
    }

    public void cancelOrders(String[] excerpts, List<String> jobIds) {
        for (int i = 0; i < jobIds.size() - 1; i++) {
            WebElement orderDetails = driver.findElement(By.xpath("//h3[contains(., '" + excerpts[i] + "')]/parent::div/p[contains(.,'" + jobIds.get(i) + "')]/parent::div/following-sibling::div/a"));
            wait.untilElementIsClickable(orderDetails);
            js.scrollIntoElement(orderDetails);
            orderDetails.click();
            wait.impWait(30, customerOrderDetailsPage.getCancelOrder());
            customerOrderDetailsPage.cancelOrder();
            assertTrue(page.getCurrentUrl().contains(customerDashboardPage.CUSTOMER_DASHBOARD_URL),
                    var.getWrongUrlErrMsg());
            driver.findElement(By.xpath("//ul/li/a[contains(@href, 'staging.gengo.com/c/order')]")).click();
            wait.impWait(30);
            clickPendingOption();
        }
    }

    public void rejectOrders(List<String> excerpts, List<String> jobIds) {
        for (int i = 0; i < jobIds.size() - 1; i++) {
            WebElement orderDetails = driver.findElement(By.xpath("//h3[contains(., '" + excerpts.get(i) + "')]/parent::div/p[contains(.,'" + jobIds.get(i) + "')]/parent::div/following-sibling::div/a"));
            wait.untilElementIsClickable(orderDetails);
            js.scrollIntoElement(orderDetails);
            orderDetails.click();
            wait.impWait(30, customerOrderDetailsPage.getRejectionFormLink());
            customerOrderDetailsPage.rejectTranslation(var.getCustomerComment(), true, Constants.REJECTION_REASON_QUALITY);
            driver.findElement(By.xpath("//ul/li/a[contains(@href, 'staging.gengo.com/c/order')]")).click();
            wait.impWait(30);
            clickReviewableOption();
        }
    }

    public void approveJobs(List<String> excerpts, List<String> jobIds) {
        for (int i = 0; i < jobIds.size(); i++) {
            WebElement orderDetails = driver.findElement(By.xpath("//h3[contains(., '" + excerpts.get(i) + "')]/parent::div/p[contains(.,'" + jobIds.get(i) + "')]/parent::div/following-sibling::div/a"));
            wait.untilElementIsClickable(orderDetails);
            js.scrollIntoElement(orderDetails);
            orderDetails.click();
            wait.impWait(30, customerOrderDetailsPage.getApproveLink());
            customerOrderDetailsPage.approveJob(true);
            assertTrue(customerOrderDetailsPage
                    .getTranslationApprovedTxt().isDisplayed(), var.getElementIsNotEnabledErrMsg());
            driver.findElement(By.xpath("//ul/li/a[contains(@href, 'staging.gengo.com/c/order')]")).click();
            wait.impWait(30);
            clickReviewableOption();
        }
    }

    public void clickPendingOption(){
        wait.impWait(30, getPendingOption());
        getPendingOption().click();
        wait.impWait(30, getPendingHeader());
        wait.impWait(30, getPendingOptionActive());
    }

    public void clickRevisingOption(){
        wait.untilElementVisible(getRevisingOption());
        getRevisingOption().click();
        wait.untilElementVisible(getRevisingHeader());
        wait.untilElementVisible(getRevisingOptionActive());
    }

    public void clickReviewableOption(){
        wait.untilElementVisible(getReviewableOption());
        getReviewableOption().click();
        wait.untilElementVisible(getReviewableHeader());
        wait.untilElementVisible(getReviewableOptionActive());
    }

    public void clickHeldOption(){
        wait.untilElementVisible(getHeldOption());
        getHeldOption().click();
        wait.untilElementVisible(getHeldHeader());
        wait.untilElementVisible(getHeldOptionActive());
    }

    public void clickApprovedOption(){
        wait.untilElementVisible(getApprovedOption());
        getApprovedOption().click();
        wait.untilElementVisible(getApprovedHeader());
        wait.untilElementVisible(getApprovedOptionActive());
    }

    public void clickAllOption(){
        wait.untilElementVisible(getAllOption());
        getAllOption().click();
    }

    public List<String> extractJobIdFromPendingJobs(String[] excerpts) {
        List<String> collections = new ArrayList<>();
        for (String excerpt : excerpts) {
            String collectionInfo = getCollectionInfoHolder().findElement(By.xpath("//*[contains(., '" + excerpt +"')]/p")).getText();
            String jobId = collectionInfo.substring(collectionInfo.indexOf("#") + 1);
            collections.add(jobId);
        }
        return collections;
    }

    public List<String> extractJobIdFromPendingJobs(List<String> excerpts) {
        List<String> collections = new ArrayList<>();
        for (String excerpt : excerpts) {
            String collectionInfo = getCollectionInfoHolder().findElement(By.xpath("//*[contains(., '" + excerpt +"')]/p")).getText();
            String jobId = collectionInfo.substring(collectionInfo.indexOf("#") + 1);
            collections.add(jobId);
        }
        return collections;
    }

    public String extractJobIdFromPendingJobs(String excerpt) {
        String collection;
        String collectionInfo = getCollectionInfoHolder().findElement(By.xpath("//*[contains(., '" + excerpt +"')]/p")).getText();
        collection = collectionInfo.substring(collectionInfo.indexOf("#") + 1);
        return collection;
    }

    public void openJob(String jobId) {
        WebElement jobDetails = getCollectionInfoHolder().findElement(By.xpath("//p[contains(., '" + jobId + "')]/parent::div/following-sibling::div/a[contains(text(), 'Details')]"));
        jobDetails.click();
        wait.impWait(30);
    }

    public void clickPreferredIcon() {
        getPreferredJobIcon().click();
        wait.untilElementVisible(getPreferredJobIconMsg());
    }
}
