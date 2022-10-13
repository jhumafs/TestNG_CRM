package pakOne;


import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login_TestNG {
	WebDriver driver;
	String browser=null;
	String url;
	
	String username = "demo@techfios.com";
	String password = "abc123";
	String full_name = "JDKj";
	String company_name = "Amazon";
	String email = "abcdefgh@hotmail.com"; 
	String phone= "896-345-1"; 
	String address = " Queil ct."; 
	String city = "Manassas"; 
	String state = "Virginia"; 
	String zipcode = "77"; 
	String country_name = "Uruguay"; 
	String currency_name = "USD";
	String group_name = "Java";
	
	
	By USERNAME = By.xpath("//input[@id='username']");
	By PASSWORD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON = By.xpath("//button[text()='Sign in']");
	By DASHBOARD = By.xpath("//h2[text()=' Dashboard ']");
	By CUSTOMERS = By.xpath("//span[text()='Customers']");
	By ADD_CUSTOMER = By.xpath("//a[text()='Add Customer']");
	By FULLNAME = By.xpath("//input[@name='account']");
	By COMPANY = By.xpath("//select[@id='cid']");
	By EMAIL = By.xpath("//input[@id='email']");
	By PHONE = By.xpath("//input[@id='phone']");
	By ADDRESS = By.xpath("//input[@name='address']");
	By CITY = By.xpath("//input[@name='city']");
	By STATE = By.xpath("//input[@name='state']");
	By ZIPCODE = By.xpath("//input[@name='zip']");
	By COUNTRY = By.xpath("//select[@id='country']");
	By TAGS = By.xpath("//input[@type='search']");
	By CURRENCY = By.xpath("//select[@id='currency']");
	By GROUP = By.xpath("//select[@id='group']");
	By SAVE_BUTTON = By.xpath("//button[@id='submit']");
	
	@BeforeClass
	public void readConfig() {
		Properties prop = new Properties();
		
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	@BeforeMethod
	public void init() {
		
		if(browser.equalsIgnoreCase("firefox")) {
		System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver_31.exe");
		driver = new FirefoxDriver();}
		else if (browser.equalsIgnoreCase("chrome"))
		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver_106.exe");
		driver = new ChromeDriver();
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	@Test(priority=1)
	public void loginTest() {
						
		driver.findElement(USERNAME).sendKeys(username);
		driver.findElement(PASSWORD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON).click();
		String DashBoard = driver.findElement(DASHBOARD).getText();
		
		Assert.assertEquals(DashBoard, "Dashboard", "Wrong Page");
	}
	@Test(priority=2)
	public void addCustomerTest() {
		
		loginTest();
			
		driver.findElement(CUSTOMERS).click();
		driver.findElement(ADD_CUSTOMER).click();
		driver.findElement(FULLNAME).sendKeys(full_name + generateRandomNumber(999));
		selectFromDropdown(COMPANY, company_name );
		driver.findElement(EMAIL).sendKeys(generateRandomNumber(999) + email);
		driver.findElement(PHONE).sendKeys(phone + generateRandomNumber(9999));
		driver.findElement(ADDRESS).sendKeys(generateRandomNumber(999) + address);
		driver.findElement(CITY).sendKeys(city);
		driver.findElement(STATE).sendKeys(state);
		driver.findElement(ZIPCODE).sendKeys(zipcode + generateRandomNumber(999));
		selectFromDropdown(COUNTRY, country_name);
		driver.findElement(TAGS).sendKeys("IT Training");
		selectFromDropdown(CURRENCY, currency_name);
		selectFromDropdown(GROUP, group_name);
		driver.findElement(SAVE_BUTTON).click();
	}

	public int generateRandomNumber(int boundaryNumber) {
		Random rnd = new Random();
		int rand =rnd.nextInt(boundaryNumber);
		return rand;
		}

	public void selectFromDropdown(By element, String VisibleText) {
		Select sel = new Select(driver.findElement(element));
		sel.selectByVisibleText(VisibleText);
		
	}
}
