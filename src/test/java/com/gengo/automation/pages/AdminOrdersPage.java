package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.AdminOrdersPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.testng.AssertJUnit.assertTrue;

public class AdminOrdersPage extends AdminOrdersPageFactory {

    public AdminOrdersPage(WebDriver driver) {
        super(driver);
    }
    private AdminChecksPage adminChecksPage = new AdminChecksPage(driver);
    private AdminPage adminPage = new AdminPage(driver);
    private Wait wait = new Wait(driver);

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

    public void clickSingleCheckbox(String orderId) {
        boolean isClick = true;
        while(isClick)
            try{
                driver.findElement(By.xpath("//input[@name='item_cb_" + orderId + "']")).isDisplayed();
                driver.findElement(By.xpath("//input[@name='item_cb_" + orderId + "']")).click();
                if ( driver.findElement(By.xpath("//input[@name='item_cb_" + orderId + "']")).isSelected())
                    isClick = false;
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

    public boolean checkCancelFail() {
        boolean isDisplayed = true;
        try {
            wait.untilElementVisible(getCancelFailAlert());
        }
        catch (Exception e)
        {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public void clickJob(String orderId) {
        WebElement element;
        boolean notFound = true;
        while (notFound) {
            try {
                element = driver.findElement(By.xpath("//a[contains(.,'"+orderId+"')]"));
                if(element.isDisplayed())
                    notFound = false;
                element.click();

            } catch (NoSuchElementException e) {
                getNextSetTable().click();
            }
        }
    }
}
