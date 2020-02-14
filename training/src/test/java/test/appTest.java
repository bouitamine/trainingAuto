package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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
	public void test() throws InterruptedException {
		
		driver.findElement(By.id("username")).sendKeys("bouitamine@hotmail.es");
		driver.findElement(By.name("password")).sendKeys("password1");
		driver.findElement(By.id("login-submit")).click();
		Thread.sleep(2000);
		assertTrue(driver.findElement(By.xpath("//h2[contains(.,'Bienvenido')]")).isDisplayed());
		
	}
	
	
  @After
  public void after() throws Exception {
		driver.quit();
	}

}
