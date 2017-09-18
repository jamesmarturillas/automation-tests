package com.gengo.automation.pages;

import com.gengo.automation.fields.Variables;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.GoogleAppPermissionPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static org.testng.Assert.*;

/**
 * @class Object repository for Google permission manipulation page.
 * Contains element operations.
 */
public class GoogleAppPermissionPage extends GoogleAppPermissionPageFactory {

    public GoogleAppPermissionPage(WebDriver driver) throws IOException {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private Variables var = new Variables();

    public void removeGoogleAppInGmailAcct() {
        if (isGoogleAppAllowedAlready()) {
            wait.impWait(30, getGengoAppBody());
            getGengoAppBody().click();
            assertTrue(getGengoRemoveBtn().isDisplayed(),
                    var.getElementIsNotDisplayedErrMsg());
            getGengoRemoveBtn().click();
            assertTrue(getOkBtn().isDisplayed(),
                    var.getElementIsNotDisplayedErrMsg());
            getOkBtn().click();
            wait.impWait(20, getAppNoLongerExistsMsg());
        }
    }

    private boolean isGoogleAppAllowedAlready() {
        return driver.findElements(By.xpath("//h2[text()='Gengo']")).size() > 0;
    }
}
