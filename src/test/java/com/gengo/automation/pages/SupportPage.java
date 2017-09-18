package com.gengo.automation.pages;

import com.gengo.automation.fields.Constants;
import com.gengo.automation.helpers.ElementState;
import com.gengo.automation.helpers.JSExecutor;
import com.gengo.automation.helpers.PageActions;
import com.gengo.automation.helpers.Wait;
import com.gengo.automation.pages.PageFactories.SupportPageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @class Object repository for Support page.
 * Contains element operations.
 */
public class SupportPage extends SupportPageFactory {

    public SupportPage(WebDriver driver) {
        super(driver);
    }
    private Wait wait = new Wait(driver);
    private JSExecutor js = new JSExecutor(driver);
    private PageActions page = new PageActions(driver);

    public void verifyLinks(String pagePart, String locale) {
        List<WebElement> elements = new ArrayList<>();
        String url = "";
        switch (pagePart) {
            case Constants.SUPPORT_HEADER :
                elements = getHeaderLinks();
                break;
            case Constants.SUPPORT_BODY :
                elements = getBodyLinks();
                break;
            case Constants.SUPPORT_FOOTER :
                elements = getFooterLinks();
                break;
        }

        switch (locale) {
            case Constants.HP_EN_LOCALE :
                url = Constants.LNK_SUPPORT_EN_LOCALE;
                break;
            case Constants.HP_ZH_LOCALE :
                url = Constants.LNK_SUPPORT_EN_LOCALE;
                break;
            case Constants.HP_JA_LOCALE :
                url = Constants.LNK_SUPPORT_JA_LOCALE;
                break;
            case Constants.HP_ES_LOCALE :
                url = Constants.LNK_SUPPORT_EN_LOCALE;
        }

        for (int i = 0; i < elements.size(); i++) {
            try {
                System.out.println(elements.get(i).getText());
                elements.get(i).click();
                wait.impWait(30);
                js.hasPageTransition(); // Checks if there was reloading of page performed.
                wait.impWait(30);
                page.goBack();
                // Since there are only 2 urls for SUPPORT page, the criteria for case clause is just 2.
                switch (url) {
                    case Constants.LNK_SUPPORT_EN_LOCALE :
                        page.launchUrl(Constants.LNK_SUPPORT_EN_LOCALE);
                        break;
                    case Constants.LNK_SUPPORT_JA_LOCALE :
                        page.launchUrl(Constants.LNK_SUPPORT_JA_LOCALE);
                        break;
                }
                wait.impWait(30);
            }
            catch (StaleElementReferenceException e) {
                switch(pagePart) {
                    case Constants.SUPPORT_HEADER :
                        elements = getHeaderLinks();
                        break;
                    case Constants.SUPPORT_BODY :
                        elements = getBodyLinks();
                        break;
                    case Constants.SUPPORT_FOOTER :
                        elements = getFooterLinks();
                        break;
                }
                continue;
            }
        }
    }
}
