package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {

    private ElementUtil eleUtil;

    private By firstName = By.id("input-firstname");
    private By lastName = By.id("input-lastname");
    private By email = By.id("input-email");
    private By telephone = By.id("input-telephone");
    private By password = By.id("input-password");
    private By confirmpassword = By.id("input-confirm");

    private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input");
    private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input");

    private By agreeCheckBox = By.name("agree");
    private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
    private By sucessMessg = By.cssSelector("div#content h1");
    private By logoutLink = By.linkText("Logout");
    private By registerLink = By.linkText("Register");

    public RegistrationPage(WebDriver driver){
        eleUtil = new ElementUtil(driver);
    }

    public boolean accountRegistration(String firstname, String lastName, String email,
                                    String telephone, String password, String subscribe){
        eleUtil.doSendKeys(this.firstName, firstname);
        eleUtil.doSendKeys(this.lastName, lastName);
        eleUtil.doSendKeys(this.email, email);
        eleUtil.doSendKeys(this.telephone, telephone);
        eleUtil.doSendKeys(this.password, password);
        eleUtil.doSendKeys(this.confirmpassword, password);

        if (subscribe.equals("yes")){
            eleUtil.doClick(subscribeYes);
        }else{
            eleUtil.doClick(subscribeNo);
        }
        eleUtil.doClick(agreeCheckBox);
        eleUtil.doClick(continueButton);
        String mesg = eleUtil.waitForElementToBeVisible(sucessMessg, 10, 1000).getText();

        if(mesg.contains(Constants.REGISTER_SUCCESS_MESSG)){
            eleUtil.doClick(logoutLink);
            eleUtil.doClick(registerLink);
            return true;

        }
            return false;
    }
}
