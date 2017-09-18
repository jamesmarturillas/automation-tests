package com.gengo.automation.pages;

import com.gengo.automation.pages.PageFactories.ReviewPageFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * @class Object repository for Review page.
 * Contains element operations.
 */
public class ReviewPage extends ReviewPageFactory {

    public ReviewPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkFeedbackArea(){
        try{
            return getFeedbackArea().isDisplayed();
        }
        catch(NoSuchElementException e){
            return false;
        }
    }
}
