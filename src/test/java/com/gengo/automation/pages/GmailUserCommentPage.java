package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.GmailUserCommentPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Gmail user comment page.
 * Contains element operations.
 */
public class GmailUserCommentPage extends GmailUserCommentPageFactory{

    public GmailUserCommentPage(WebDriver driver) {
        super(driver);
    }
}
