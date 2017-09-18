package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.CustomerOrderPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * TODO : to be replaced by CustomerOrderDetailsPage PageObject
 *
 * @class Object repository for Page factory for customer order (not list of jobs) page.
 * Contains element operations.
 */
public class CustomerOrderPage extends CustomerOrderPageFactory {

    public CustomerOrderPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    public void clickAddReadCommentsLink() {
        getAddReadCommentsLink().click();
        wait.impWait(10, getHideCommentsLink());
    }

    public void addCommentAsCustomer(String comment) {
        this.clickAddReadCommentsLink();
        getAddCommentTxtArea().sendKeys(comment);
        wait.untilElementIsClickable(getSubmitCommentBtn());
        getSubmitCommentBtn().click();
    }

}
