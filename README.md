# The automation-tests
## A Java for Selenium API powered Framework that uses Page Object Model 

This contains all scripts for automating Gengo systems regression tests using Java for Selenium.


## What's inside?

* A Data-driven Framework.
* Page Object Pattern
* The PageFactory
* WebDriverManager

### A Data-driven Framework

Since this is a `Data-driven Framework` it means all raw data used in execution such as _user accounts, translation data, pricing data and etc._ are stored in a data source. With that being said, all data are stored in several `.csv` files.

The files' path is : 
```
automation-tests/src/test/resources/testData/FileName.csv
```

### Page Object Pattern
Is a design pattern that...
> "reduces the amount of duplicated code and means that if the UI changes, the fix need only be applied in one place."

— Selenium PageObject's [wiki](https://github.com/SeleniumHQ/selenium/wiki/PageObjects#page-objects)

It is duly implemented in this framework in order to avoid boiler-plate code from the entire test case and enforce ease of code modification once there are minor or even major changes from the app-to-test environment. More from the wiki linked above.

### The PageFactory
It is important to note that a `WebElement` class of Selenium will never have a pointer if it's not found in a certain page being examined. This is why some of automation testers encounter `NullPointerException`s in their test cases out of nowhere though they know that the element exists. This mostly happpens in an AJAX-heavy applications.

The **PageFactory** is a support class of Selenium for *Page Objects* which is implemented in this framework as stated above.

Primarily, this class is used with `@FindBy` annotation to prevent difficulties of finding an element in a page. It creates a dynamic proxy for a set `WebElement` so even when it's *not yet* found, it won't return a `NullPointerException` because these proxies serve as a preparation. In other words, it initializes the `WebElement` and instantiate it once found. Because of this, there will also never have a `StaleElementException` which is mostly encountered when looping a page navigations. Perhaps, this class also prevents verbosity of code appearance.

Instead of :

```java
driver.findElement(By.xpath("//your/xpath")).click();
```
that returns hazardous instances during runtime,

we will prepare the `WebElement` using `@FindBy` by the following methodology :

```java
@FindBy(xpath = "//your/xpath")
private WebElement nameOfTheElement;

public WebElement getNameOfTheElement() {
	return nameOfTheElement;
}

// This usually resides in a PageObject class.
```
and use it in action in this way :

```java
getNameOfTheElement().click();
```
isn't it cool that you can just modify the `xpath` or locator once and won't need to look for those
```java
driver.findElement(By.xpath("//verbosed/syntax/without/pagefactory"))
``` 
in each test case (imagine if you have 100+ test cases) anymore?

Indeed, **PageFactory** is very useful to avoid boiler-plate code and implement smart element finding methodology.

## WebDriverManager
This is a dependency made by [Boni Garcia](http://bonigarcia.github.io/).

Well, what’s up with it? It is actually the most magical part of this framework.

With it...
* You won’t need to download required browser drivers that you will need in your test scripts such as _ChromeDriver_, _GeckoDriver_ or _PhantomJSDriver_ in order to make your automation work with your desired browser.
* You won’t need to bring the the **System.setProperty(“key”, “value”)** along the browser set-up to point your script to the path of your browser binary.
* No more hassle of copying and pasting the browser binary if you need to run your script in other machine. Just hit **run** and you’re set.

## System Requirements
**Dependencies**
* Selenium 3.4.0 (recommended)
* Maven 3 or later
* Surefire Plugin 2 or later
* Google Guava 21.0 (recommended)
* WebDriverManager 1.7.1
* org.apache.commons — commons-exec 1.3 or later
* org.apache.commons — commons-lang3 3.5 or later

**Environment**
* Java - JDK 8 (1.8.0_121)
* IDE - IntelliJ IDEA (Community or Ultimate)

**NOTE :**

For browser binaries, the WebDriverManager keeps downloading the latest version of binary of the chosen browser so it is better to make sure or re-assure from time to time that the Selenium API version works fine with the browser. Otherwise, an upgrade is necessary. Reason is, we need to keep to our browsers updated to latest so a latest version of Selenium API is a must (if it's proven stable).

