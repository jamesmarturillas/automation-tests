package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.PluginPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for password protection page.
 * Contains element operations.
 */
public class PluginPage extends PluginPageFactory {

    public PluginPage(WebDriver driver) {
        super(driver);
    }

    public void passThisPage() {
        if (check.isElementPresentBySource(driver, BANNER_MSG)) {
            getTxtPassword().sendKeys(PASSWORD);
            getSubmitBtn().click();
        }
    }
}
