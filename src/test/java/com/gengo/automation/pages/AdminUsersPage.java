package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.pages.PageFactories.AdminUsersPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

/**
 * @class Object repository for Admin Users page.
 * Contains element operations.
 */
public class AdminUsersPage extends AdminUsersPageFactory{
    private Variables var = new Variables();

    public AdminUsersPage(WebDriver driver) throws IOException {
        super(driver);
    }

    public void chooseRole(String role){
        Select dropdown = new Select(getRoleDropdown());
        dropdown.selectByVisibleText(role);
    }

    public void viewFirstTranslatorWithRewards() {
        boolean isFound = false;
        while(!isFound){
            try{
                if(getFirstTranslatorWithRewards().isDisplayed())
                    isFound = true;
                getFirstTranslatorWithRewards().click();
            }
            catch(NoSuchElementException e){
                getNextBtn().click();
            }
        }
    }

    public void viewFirstTranslatorZeroRewards() {
        boolean isFound = false;
        while(!isFound){
            try{
                if(getFirstTranslatorZeroRewards().isDisplayed())
                    isFound = true;
                getFirstTranslatorZeroRewards().click();
            }
            catch(NoSuchElementException e){
                getNextBtn().click();
            }
        }
    }
}
