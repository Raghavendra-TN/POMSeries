package com.qa.opencart.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    public WebDriver driver;
    public Properties prop;
    public static String highlight;
    public OptionsManager optionsManager;
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
    FileInputStream ip = null;

    /**
     * This method is used to initialize the webdriver
     * @param prop
     * @return this will return driver
     */
    public WebDriver init_driver(Properties prop){
        String browserName = prop.getProperty("browser").trim();
        System.out.println("browser name is : "+ browserName);
        highlight = prop.getProperty("highlight").trim();
        optionsManager = new OptionsManager(prop);

        if(browserName.equals("chrome")){
            WebDriverManager.chromedriver().setup();

            if (Boolean.parseBoolean(prop.getProperty("remote"))){
                //remote code
                init_remoteDriver("chrome");
            }else {
                //local
                //driver = new ChromeDriver(optionsManager.getChromeOptions());
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
            }
        } else if(browserName.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            //driver = new FirefoxDriver(optionsManager.getFirefoOptions());
            if (Boolean.parseBoolean(prop.getProperty("remote"))){
                //remote code
                init_remoteDriver("firefox");
            } else {
                tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
            }
        } else if(browserName.equals("safari")){
            //driver = new SafariDriver();
            tlDriver.set(new SafariDriver());
        } else {
            System.out.println("please pass the right browser: "+ browserName);
        }
//        driver.manage().window().fullscreen();
//        driver.manage().deleteAllCookies();
//        driver.get(prop.getProperty("url"));

        getDriver().manage().window().fullscreen();
        getDriver().manage().deleteAllCookies();
        //getDriver().get(prop.getProperty("url"));
        //openUrl(prop.getProperty("url"));
        try {
            URL url = new URL(prop.getProperty("url"));
            openUrl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return getDriver();
    }

    private void init_remoteDriver(String browser) {
        System.out.println("Running test on remote grid server: "+ browser);
        if(browser.equalsIgnoreCase("chrome")){
            DesiredCapabilities cap = new DesiredCapabilities();
           // cap.setBrowserName("chrome");
            cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
            try {
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        else if(browser.equalsIgnoreCase("firefox")){
                DesiredCapabilities cap = new DesiredCapabilities();
                cap.setBrowserName("firefox");
               // cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
                try {
                    tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
        }

    }

    /**
     * getDriver(): it will return a thread local copy of the webdriver
     */
    public static synchronized WebDriver getDriver(){

        return tlDriver.get();
    }

    /**
     * this method is used to initialize  the properties
     * @return this will return prop reference
     */
    public Properties init_prop(){
        prop = new Properties();

        String envName = System.getProperty("env"); //qa/dev/stage/uat

        if(envName == null){
            System.out.println("Running on PROD env: ");
            try {
                ip = new FileInputStream("./src/test/resources/config/config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Running on environment: " + envName);
            try {
                switch (envName.toLowerCase()) {
                    case "qa":
                        ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                        break;
                    case "dev":
                        ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
                        break;
                    case "stage":
                        ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
                        break;
                    case "uat":
                        ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
                        break;
                    default:
                        System.out.println("please pass the right environment.......");
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }

    /**
     * take screenshot
     */

    public String getScreenshot(){

        File src = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")+"/screenshots/"+ System.currentTimeMillis()+".png";
        File destination = new File(path);
        try {
            FileUtils.copyFile(src, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * launch url
     * @param url
     */
    public void openUrl(String url)  {
            try {
                if (url == null) {
                    throw new Exception("url is null");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        getDriver().get(url);
    }

    public void openUrl(URL url)  {
        try {
            if (url == null) {
                throw new Exception("url is null");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        getDriver().navigate().to(url);
    }

    public void openUrl(String baseUrl, String path)  {
        try {
            if (baseUrl == null) {
                throw new Exception("url is null");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        getDriver().get(baseUrl+"/"+path);
    }

    public void openUrl(String baseUrl, String path, String queryParam)  {
        try {
            if (baseUrl == null) {
                throw new Exception("url is null");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        getDriver().get(baseUrl+"/"+path+"?"+queryParam);
    }
}
