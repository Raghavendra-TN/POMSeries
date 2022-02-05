package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountsPage {

    private WebDriver driver;
    private ElementUtil eleUtil;

    public AccountsPage(WebDriver driver){
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    private By header = By.cssSelector("div#logo a");
    private By accountSections = By.cssSelector("div#content h2");
    private By searchField = By.name("search");
    private By searchButton = By.cssSelector("div#search button");
    private By logoutLink = By.linkText("Logout");

    public String getAccountPageTitle(){
            return eleUtil.doGetTitle(Constants.ACCOUNTS_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
    }
    public String getAccountsPageHeader(){
        return eleUtil.doGetText(header);
    }

    public boolean isLogoutLinkExist(){
        return eleUtil.doIsDisplayed(logoutLink);
    }

    public void logout(){
        if(isLogoutLinkExist()){
            eleUtil.doClick(logoutLink);
        }
    }

    public List<String> getAccountSecList(){
        List<WebElement> accSecList = eleUtil.waitForElementsToBeVisible(accountSections, 10);
        List<String> accSecValList = new ArrayList<String>();
        for (WebElement e : accSecList){
            String text = e.getText();
            accSecValList.add(text);
        }
        return accSecValList;
    }

    public boolean isSearchExist(){
        return eleUtil.doIsDisplayed(searchField);
    }

    public SearchResultsPage doSearch(String productName){
        System.out.println("searching the product: "+ productName);
        eleUtil.doSendKeys(searchField, productName);
        eleUtil.doClick(searchButton, 10);
        return new SearchResultsPage(driver);

    }
}

