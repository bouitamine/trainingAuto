package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GoogleSearchTestNG {

	static WebDriver driver;

		@AfterTest
		public void closeBrowser() throws Exception {
		driver.quit();
	}

	@BeforeClass
	@Parameters({ "BrowserType" })
	public void beforeClass(@Optional("Chrome") String browserType) {


		if (browserType.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserType.equalsIgnoreCase("Firefox")) {
			// Firefox driver
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
	}

	
//	@DataProvider(name="SearchProvider")
//	    public Object[][] getDataFromDataprovider(){
//	    return new Object[][] {
//            { "Canada" },
//            { "Russia" },
//            { "Japan" },
//            { "España" }
//        };}       

//	@Test(dataProvider="SearchProvider")
	@Test
	public void testGooglePage(@Optional("España")String SearchProvider) throws InterruptedException {
		WebElement searchbox = driver.findElement(By.name("q"));
		searchbox.clear();
		searchbox.sendKeys(SearchProvider);
		searchbox.submit();
		Thread.sleep(2000);
		AssertJUnit.assertEquals(SearchProvider+" - Buscar con Google", driver.getTitle());
	}


}
