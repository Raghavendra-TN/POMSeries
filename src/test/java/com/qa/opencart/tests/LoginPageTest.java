package com.qa.opencart.tests;

import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.utils.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Epic 100: Design Open Cart - Login Page")
@Story("US 101: Open Cart Login Design with multiple features")
@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest {

    @Description("login Page Title Test")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 1)
    public void loginPageTitleTest(){
       String actTitle = loginPage.getLoginPageTitle();
       System.out.println("page title: "+ actTitle);
       Assert.assertEquals(actTitle, Constants.LOGIN_PAGE_TITLE, "......login page title is not correct......");
    }

    @Description("login Page Url Test")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2)
    public void loginPageUrlTest(){
        Assert.assertTrue( loginPage.getLoginPageUrl());
    }

    @Description("forgot pwd link Test")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void forgotPwdLinkTest(){
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Description("registration link Test")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 4, enabled = false)
    public void registerLinkTest(){
        Assert.assertTrue(loginPage.isRegisterLinkExist());
    }

    @Description("login test with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 5)
    public void loginTest(){
        accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
        Assert.assertEquals(accountsPage.getAccountPageTitle(), Constants.ACCOUNTS_PAGE_TITLE);
    }
}
