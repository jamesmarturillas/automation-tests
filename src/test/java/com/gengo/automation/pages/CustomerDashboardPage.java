package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.CustomerDashboardPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for customer order form page.
 * Contains element operations.
 */
public class CustomerDashboardPage extends CustomerDashboardPageFactory {

    public CustomerDashboardPage(WebDriver driver) {
        super(driver);
    }

    public Boolean checkCustomerDashboard(){
        Boolean state = true;
        try{
            getCustomerDashboardText().isDisplayed();
            getOrderTranslationLink().isDisplayed();
            getNavBar().isDisplayed();
            getDashboardArea().isDisplayed();
            getFooterSection().isDisplayed();
        }
        catch(NoSuchElementException e){
            state = false;
        }
        return state;
    }
}
