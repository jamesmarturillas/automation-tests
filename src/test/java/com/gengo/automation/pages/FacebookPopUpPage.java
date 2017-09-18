package com.gengo.automation.pages;
import com.gengo.automation.pages.PageFactories.FacebookPopUpPageFactory;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * @class Object repository for homepage.
 * Contains element operations.
 */
public class FacebookPopUpPage extends FacebookPopUpPageFactory {

    public FacebookPopUpPage(WebDriver driver) throws IOException {
        super(driver);
    }

    public void login(String username, String password) {
        getTxtBoxEmail().sendKeys(username);
        getTxtBoxPassword().sendKeys(password);
        getLoginBtn().click();
    }
}
