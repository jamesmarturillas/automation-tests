package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.AdminJobsPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.testng.AssertJUnit.assertTrue;

public class AdminJobsPage extends AdminJobsPageFactory {

    public AdminJobsPage(WebDriver driver) {
        super(driver);
    }
    private AdminChecksPage adminChecksPage = new AdminChecksPage(driver);
    private AdminPage adminPage = new AdminPage(driver);
    private Wait wait = new Wait(driver);

    // Removed "Job #" and retrieving only the ID
    public String jobIDNumber() {
        return getJobDetailsJobID().getText().replaceAll("[A-z# ]", "");
    }

    public void openJobIDDetailsPage(String jobID) {
        boolean isClick = true;
        while(isClick) {
            try {
                driver.findElement(By.xpath("//tbody[@id='table-items']/tr/td/a[contains(.,'" + jobID + "')]")).click();
                wait.untilElementVisible(driver.findElement(By.xpath("//h1[contains(.,'" + jobID + "')]")));
                isClick = false;
            } catch (NoSuchElementException e) {
                isClick = true;
            }
        }
    }

    public void clickJobActions(String action) {
        wait.untilElementIsClickable(adminPage.getActionBtn());
        adminPage.getActionBtn().click();
        switch (action) {
            case "forceApprove":
                getJobActionBtnForceApprove().click();
                wait.untilElementVisible(adminPage.getAnswerYes());
                adminPage.getAnswerYes().click();
                wait.impWait(30);
                break;
            case "requestCheck":
                getJobActionBtnRequestCheck().click();
                wait.untilElementVisible(adminPage.getAnswerYes());
                adminPage.getAnswerYes().click();
                wait.impWait(30, adminChecksPage.getCheckRequestSuccessMsg());
                break;
            case "requestCorrections":
                getJobActionBtnRequestCorrections().click();
                wait.untilElementVisible(adminPage.getAnswerYes());
                adminPage.getAnswerYes().click();
                wait.impWait(30);
                break;
            case "comment":
                getJobActionBtnComment().click();
                wait.impWait(30);
                break;
            case "edit":
                getJobActionBtnEdit().click();
                wait.impWait(30);
                break;
        }
    }

    public void ChooseActionsDropdown(String action) {
        wait.untilElementIsClickable(getActionsBtn());
        getActionsBtn().click();
        switch(action) {
            case "cancel":
                getCancelLink().click();
                wait.untilElementVisible(getCancelModalHeader());
                assertTrue(getCancelModalHeader().isDisplayed());
                assertTrue(getCancelModalText().isDisplayed());
                break;
        }
    }

    public void clickCancelYesBtnModal() {
        wait.untilElementIsClickable(getCancelYesBtn());
        getCancelYesBtn().click();
        wait.untilElementNotVisible(By.xpath("//div[@class='modal-body'][contains(.,'Are you sure you want to cancel')]/following-sibling::div/button[contains(.,'Yes')]"));
    }

    public void clickSingleCheckbox(String jobNo) {
        boolean isClick = true;
        jobNo = jobNo.substring(1);
        while(isClick)
        try{
            driver.findElement(By.xpath("//input[@name='item_cb_" + jobNo + "']")).isDisplayed();
            driver.findElement(By.xpath("//input[@name='item_cb_" + jobNo + "']")).click();
            if ( driver.findElement(By.xpath("//input[@name='item_cb_" + jobNo + "']")).isSelected())
                isClick = false;
        }
        catch (NoSuchElementException e){
            isClick = true;
            getNextSetTable().click();
        }
    }

    public void checkMultipleCheckbox(String[] jobNo) {
        boolean isClick = true;
        while(isClick)
            try{
                for(String job : jobNo) {
                    driver.findElement(By.xpath("//input[@name='item_cb_" + job + "']")).isDisplayed();
                    driver.findElement(By.xpath("//input[@name='item_cb_" + job + "']")).click();
                    if (driver.findElement(By.xpath("//input[@name='item_cb_" + job + "']")).isSelected())
                        isClick = false;
                }
            }
            catch (NoSuchElementException e){
                isClick = true;
                getNextSetTable().click();
            }
    }

    public boolean checkCancelConfirmation() {
        boolean isDisplayed = true;
        try {
            wait.untilElementVisible(getCancelConfirmationAlert());
        }
        catch (Exception e)
        {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean checkJobStatus(String[] jobID, String status) {
        boolean isDisplayed = true;
        WebElement element;
        for(int i = 0; i < jobID.length; i++) {
            boolean notFound = true;
            while (notFound) {
                try {
                 element = driver.findElement(By.xpath("//td[contains(.,'" + jobID[i].substring(1) + "')]/following-sibling::td[contains(.,'" + status + "')]"));
                    if(element.isDisplayed())
                        notFound = false;
                } catch (NoSuchElementException e) {
                    getNextSetTable().click();
                }
            }
        }
        return isDisplayed;
    }


    public boolean checkJobStatus(String jobID, String status) {
        boolean isDisplayed = true;
        WebElement element;
        boolean notFound = true;
            while (notFound) {
                try {
                    element = driver.findElement(By.xpath("//td[contains(.,'" + jobID.substring(1) + "')]/following-sibling::td[contains(.,'" + status + "')]"));
                    if(element.isDisplayed())
                        notFound = false;

                } catch (NoSuchElementException e) {
                    getNextSetTable().click();
                }
            }
        return isDisplayed;

    }

    public void chooseActionAndConfirm(String action, String alertStatus) {
        boolean toClick = true;
        while (toClick) {
            this.ChooseActionsDropdown(action);
            switch(action){
                case "cancel":
                    this.clickCancelYesBtnModal();
                    break;
            }
            switch(alertStatus) {
                case "success":
                    if(this.checkCancelConfirmation())
                        toClick = false;
                    break;
            }
        }
    }

    public void selectStatusFilter(String status){
        Select dropdown = new Select(getFilterStatus());
        dropdown.selectByVisibleText(status);
    }

    public void clickJob(String jobNumber) {
        WebElement element;
            boolean notFound = true;
            while (notFound) {
                try {
                   element = driver.findElement(By.xpath("//a[contains(.,'"+jobNumber.substring(1)+"')]"));
                    if(element.isDisplayed())
                        notFound = false;
                    element.click();

                } catch (NoSuchElementException e) {
                    getNextSetTable().click();
                }
            }
    }
}
