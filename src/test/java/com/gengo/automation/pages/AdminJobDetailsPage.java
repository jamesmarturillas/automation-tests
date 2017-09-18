package com.gengo.automation.pages;

import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.AdminJobDetailsPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Admin Job Details Page.
 * Contains element operations.
 */
public class AdminJobDetailsPage extends AdminJobDetailsPageFactory {

    public AdminJobDetailsPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);

    public String jobNumber() {
        return getJobIDNo().getText().replaceAll("[A-z# ]","");
    }

    public void chooseActions(String action) {
        getActionsBtn().click();
        switch (action){
            case "edit":
                getEditBtn().click();
                break;
            case "comment":
                getCommentBtn().click();
                break;
            case "requestcheck" :
                getRequestCheckBtn().click();
                break;
        }
    }
}
