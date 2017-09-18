package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.SuccessRegistrationPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for registration success page.
 * Contains element operations.
 */
public class SuccessRegistrationPage extends SuccessRegistrationPageFactory {

    public SuccessRegistrationPage(WebDriver driver) {
        super(driver);
    }
}
