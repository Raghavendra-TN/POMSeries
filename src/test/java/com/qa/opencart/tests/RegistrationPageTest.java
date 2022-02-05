package com.qa.opencart.tests;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

public class RegistrationPageTest extends BaseTest {

    @BeforeClass
    public void setupRegistration(){
        registrationPage = loginPage.goToRegisterPage();
    }

    public String getRandomEmail(){
        Random randomGenerator = new Random();
        String email = "septautomation"+randomGenerator.nextInt(10000)+"@gmail.com";
        return email;
    }

    @DataProvider
    public Object[][] getRegisterData(){
        return ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
    }

    @Test(dataProvider = "getRegisterData")
    public void userRegistrationTest(String firstname, String lastName,
                                     String telephone, String password, String subscribe) {

       Assert.assertTrue(registrationPage.accountRegistration(firstname, lastName, getRandomEmail(),
                telephone, password, subscribe));


    }

}
