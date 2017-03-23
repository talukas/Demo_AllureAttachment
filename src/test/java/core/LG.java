package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;



@Title("One test")
@Description("Use as a Demo")
//@Test() 
public class LG {
     
	static WebDriver driver;
	Properties properties;
	String driverType = System.getProperty("browser");
	String csvFile = "./resources/Master_input.csv";
	String email = "cher205fre@gmail.com";
	String password = "Tatalukas205";
	
	String url = "http://www.williams-sonoma.com";
	String titleName = null;
	WebDriverWait wait; 
   
    @BeforeTest
    @Step("Open Browser")
    public void Launch() throws FileNotFoundException, IOException, FindFailed, InterruptedException {	
    	  Logger logger = Logger.getLogger("");
          logger.setLevel(Level.OFF);	
          
          properties = new Properties();
          properties.load(new FileInputStream("./resources/QA_13_End2End_Checkout.properties"));
          
		
    	if (driverType.equalsIgnoreCase("CHROME")) {
    	
    		System.setProperty("webdriver.chrome.driver", "./resources/chromedriver");        
            DesiredCapabilities caps = DesiredCapabilities.chrome();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
            driver = new ChromeDriver(caps);
            wait = new WebDriverWait(driver, 20);
    		driver.get(url);
    		
    		 //analyzeLog();
    	}

    	else if (driverType.equalsIgnoreCase("FIREFOX")) {
    		
    		 System.setProperty("webdriver.gecko.driver", "./resources/geckodriver");
    		 DesiredCapabilities caps = DesiredCapabilities.firefox();
             LoggingPreferences logPrefs = new LoggingPreferences();
             logPrefs.enable(LogType.BROWSER, Level.ALL);
             caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    		 driver = new FirefoxDriver(caps);
             driver.manage().window().maximize();
             wait = new WebDriverWait(driver, 30);
             driver.get(url);
    		
    		 //analyzeLog();
    	}
		  driver.get(url);
		    
		 }
    

    @AfterTest 
    @Step("Post")
    public void tearDown() {
      driver.quit();
    }
    		public void analyzeLog() {
    		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
    		for (LogEntry entry : logEntries) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            //do something useful with the data
        } 		
    }
    		
	@Severity(SeverityLevel.NORMAL)
	@Title("One test")
	@Description("Use as a Demo")
	@Test 
	@Step("method")
	public void LogIn() 
	 {
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("myAccount")))).click();
	// Sign In
	driver.findElement(By.xpath(properties.getProperty("login_Email"))).sendKeys(email); // enter email in to "email" field
	driver.findElement(By.xpath(properties.getProperty("login_Password"))).sendKeys(password); // enter  password																						
	driver.findElement(By.xpath(properties.getProperty("login_Button"))).click();

	//System.out.println(driver.getTitle());
	String title = driver.getTitle();
	Assert.assertTrue(driver.getTitle().contains("Your Account"));
	attachText("Page Title", title);
	//makeScreenShot("New Screenshot");
	//try {
		//assertTrue(driver.getTitle().contains("Your Account"));
	//}
		//catch (NoSuchElementException ignored)
	//{
	//}
	//analyzeLog();
	
	}

	
	@Attachment(value = "{0}", type = "image/png")
	public synchronized static byte[] makeScreenShot(String tmp)
	{
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
	
	@Attachment(value = "{0}", type = "text/plain")
	public synchronized static String attachText(String nameofAttachment, String bodyOfMassege)
	{
		return bodyOfMassege;
	}
	//@Attachment(value = "{0}", type = "text/html")
	//public static String saveHtmlAttach() {
	    //return html;
	}

	
