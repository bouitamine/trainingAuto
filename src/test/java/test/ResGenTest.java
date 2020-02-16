package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ResGenTest {

	private static WebDriver driver;
	By userLocator = By.name("username");
	By passLocator = By.id("password");
	By loginBtnLocator = By.xpath("//*[@id='login-submit']");
	By homePageLocator = By.xpath("//h1[contains(text(),'AtoS Resource Generator Tool (RESGEN)')]");
	By bauBtnLocator = By.linkText("BAU");
	By envLocator = By.xpath("//select[@id='env']");
	By pageSuccesBauLocator = By.xpath("//p[contains(text(),'Resultado: OK')]");
	By prepararBtnLocator = By.linkText("Preparar");
	By limpiezaPageLocator = By.xpath("//h1[contains(text(),'RESGEN - Limpieza Recursos')]");
	By pageSuccesLimpiezaLocator = By.xpath("//p[contains(text(),'ha sido limpiado')]");

	WebElement dropdown;

	static ExtentHtmlReporter htmlreport;
	static ExtentReports extent;
	static ExtentTest test;

//	@Rule
//	public TestName name = new TestName();

	@Rule
	public TestWatcher watchman = new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {
			test.log(Status.FAIL, description.getMethodName() + " Failed");
			try {
				test.addScreenCaptureFromPath(takeSnapShot(description.getMethodName()));
				driver.quit();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		@Override
		protected void succeeded(Description description) {
			test = extent.createTest(description.getMethodName());
			test.log(Status.PASS, description.getMethodName() + " Passed");
			try {
				test.addScreenCaptureFromPath(takeSnapShot(description.getMethodName()));
				driver.quit();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	};

	@BeforeClass
	public static void startTest() {
		htmlreport = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\ExtentReportResults.html");
		htmlreport.config().setDocumentTitle("Automation Report");
		htmlreport.config().setReportName("ResGen Report");
		htmlreport.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlreport);

	}

	@AfterClass
	public static void endTest() {
		extent.flush();
	}

	@Before
	public void setUp() throws Exception {

		// IE explorer driver
//		System.setProperty("webdriver.ie.driver", "./src/test/resources/drivers/IEDriverServer.exe");
//		driver = new InternetExplorerDriver();

		// Creating a new instance of the HTML unit driver
//         driver = new HtmlUnitDriver();

		// Firefox driver
		System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver.exe");
//		driver = new FirefoxDriver();

		System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("http://10.132.7.63:8082/login");
	}

	@After
	public void tearDown() throws Exception {
//		takeSnapShot(name.getMethodName());
//		driver.quit();
	}

	@Test
	public void login() {
		if (driver.findElement(userLocator).isDisplayed()) {
			driver.findElement(userLocator).sendKeys("admin");
			driver.findElement(passLocator).sendKeys("Password1");
			driver.findElement(loginBtnLocator).click();
		} else {
			System.out.print("Page Login Not Found");
		}

		assertTrue(driver.findElement(homePageLocator).isDisplayed());

	}

//	@Ignore("Test is ignored as a demonstration")
	@Test
	public void bauGenerate() throws InterruptedException {

		login();
		Thread.sleep(2000);
		if (driver.findElement(bauBtnLocator).isDisplayed()) {
			driver.findElement(bauBtnLocator).click();
			// espera
			Thread.sleep(2000);
			driver.findElement(envLocator).click();
			// espera
			Thread.sleep(2000);
			dropdown = driver.findElement(envLocator);
			dropdown.findElement(By.xpath("//option[. = 'ENT1']")).click();
			driver.findElement(By.id("dni")).sendKeys("C6809337F");
			driver.findElement(By.id("nombre")).sendKeys("sqa");
			driver.findElement(By.id("apellido")).sendKeys("sqa");
			driver.findElement(By.id("email")).sendKeys("sqa@atos.net");
			dropdown = driver.findElement(By.id("prgcode"));
			dropdown.findElement(By.xpath("//option[. = 'POSTAUT']")).click();
			driver.findElement(By.id("shdes")).sendKeys("TGOON");
			driver.findElement(By.id("msisdn")).sendKeys("655004584");
			driver.findElement(By.id("imsi")).sendKeys("214032485418788");
			driver.findElement(By.id("iccid")).sendKeys("8934012451103027846");
			driver.findElement(By.id("vap1")).click();
			driver.findElement(By.xpath("//button[text()='Enviar']")).click();
		} else {
			System.out.print("Page Login Not Found");
		}

		// Explicit Wait
		WebDriverWait ewait = new WebDriverWait(driver, 60);
		ewait.until(
				ExpectedConditions.textToBePresentInElement(driver.findElement(pageSuccesBauLocator), "Resultado: OK"));
		assertTrue(driver.findElement(pageSuccesBauLocator).isDisplayed());
		String resultado = driver.findElement(pageSuccesBauLocator).getText();
		System.out.print(resultado);
	}

//	@Ignore("Test is ignored as a demonstration")
	@Test()
	public void limpiar() throws InterruptedException {
		login();
		// espera
		Thread.sleep(4000);
		if (driver.findElement(homePageLocator).isDisplayed()) {
			driver.findElement(prepararBtnLocator).click();
			Thread.sleep(2000);
			dropdown = driver.findElement(envLocator);
			dropdown.findElement(By.xpath("//option[. = 'ENT1']")).click();
			Thread.sleep(2000);
			driver.findElement(By.id("msisdn")).sendKeys("655004584");
			driver.findElement(By.id("imsi")).sendKeys("214032485418788");
			driver.findElement(By.id("iccid")).sendKeys("8934012451103027846");
			driver.findElement(By.xpath("//button[text()='Preparar']")).click();
		} else {
			System.out.print("Page Limpieza Not Found");
		}

		// Explicit Wait
		WebDriverWait ewait = new WebDriverWait(driver, 60);
		ewait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(pageSuccesLimpiezaLocator),
				"ha sido limpiado"));
		assertTrue(driver.findElement(pageSuccesLimpiezaLocator).isDisplayed());
		String resultado = driver.findElement(pageSuccesLimpiezaLocator).getText();
		System.out.print(resultado);
	}

	public static String takeSnapShot(String nombreTest) throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File("C:/Users/A623374/OneDrive - Atos/Desktop/training/ScreenShot/" + nombreTest + ".png");
		FileUtils.copyFile(SrcFile, DestFile);
		return DestFile.getAbsolutePath();
	}

}
