package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.AdminJobCommentPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * Created by rgonzaga on 7/19/17.
 */
public class AdminJobCommentPage extends AdminJobCommentPageFactory{

    public AdminJobCommentPage(WebDriver driver) {
        super(driver);
    }

    public void addComment(String comment) {
        getCommentBodyTextArea().clear();
        getCommentBodyTextArea().sendKeys(comment);
    }
}
