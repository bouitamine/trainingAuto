package test;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class appTest {
	
	
	private static WebDriver driver;

	@Before
	public void setUp() throws Exception {

		System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/login");

	}

	@Test
	public void login() throws InterruptedException {
		
		driver.findElement(By.id("username")).sendKeys("bouitamine@hotmail.es");
		driver.findElement(By.name("password")).sendKeys("password");
		driver.findElement(By.id("login-submit")).click();
		Thread.sleep(2000);
		assertTrue(driver.findElement(By.xpath("//h2[contains(.,'Bienvenido')]")).isDisplayed());
		
	}
	
	
  @After
  public void after() throws Exception {
//		driver.quit();
	}
  
  @Rule
  public TestWatcher watchman= new TestWatcher() {
      @Override
      protected void failed(Throwable e, Description description) {
          try {
			takeSnapShot(description.getMethodName());
			driver.quit();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
      }

      @Override
      protected void succeeded(Description description) {
          try {
				takeSnapShot(description.getMethodName());
				driver.quit();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
         }
     };
     
     public static void takeSnapShot(String nombreTest) throws Exception {
 		TakesScreenshot scrShot = ((TakesScreenshot) driver);
 		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
 		File DestFile = new File("C:/Users/A623374/OneDrive - Atos/Desktop/training/ScreenShot/" + nombreTest + ".png");
 		FileUtils.copyFile(SrcFile, DestFile);

 	}


}
