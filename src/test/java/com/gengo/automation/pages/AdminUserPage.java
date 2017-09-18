package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.pages.PageFactories.AdminUserPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * @class Object repository for Admin user page.
 * Contains element operations.
 */
public class AdminUserPage extends AdminUserPageFactory {

    private String randomString;
    private Variables var = new Variables();
    public AdminUserPage(WebDriver driver) throws IOException {
        super(driver);
    }

    private GlobalPage globalPage = new GlobalPage(driver);

    public void editEmail() {
        if (isEmailNotUsed()) {
            randomString = globalPage.randomString(4);
            getUnlink().click();
            getEditableEmail().click();
            getEditEmailTxtBox().clear();
            getEditEmailTxtBox().sendKeys(var.getFbEmail(1).replace(".testcustomer", ".testcustomer+" + randomString));
            getSubmitEditedBtn().click();
            assertTrue(getSuccessEditMsg().isDisplayed(),
                    var.getElementIsNotDisplayedErrMsg());
        }
    }

    private boolean isEmailNotUsed() {
        return driver.findElements(By.xpath("//h1[@class='pull-left']")).size() > 0;
    }
}
