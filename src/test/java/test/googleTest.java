package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class googleTest {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
	}
	

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
		public void testGooglePage() throws InterruptedException {
			WebElement searchbox = driver.findElement(By.name("q"));
			searchbox.clear();
			searchbox.sendKeys("Testing Automatizado");
			searchbox.submit();
			Thread.sleep(2000);
			assertEquals("Testing Automatizado - Buscar con Google", driver.getTitle());

		}

}

