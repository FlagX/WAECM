package at.ac.tuwien.waecm.app.selenium;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SeleniumTests {
	private WebDriver driver;
	private WebDriverWait wait;

	@Value("${local}")
	private String local;

	@Value("${path.chromedriver}")
	private String chromeDriverPath;

	@Value("${path.geckodriver}")
	private String geckoDriverPath;


	@Before
	public void setUp() throws MalformedURLException {
		if(isLocal()) {
//			System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			System.setProperty("selenide.browser", "chrome");
			driver = WebDriverRunner.getWebDriver();
		} else {
			DesiredCapabilities capability = DesiredCapabilities.chrome();

			String chromeDriver_Ip = System.getenv("chromedriver_ip");
			System.out.println("IP chromedriver: " + chromeDriver_Ip);
			driver = new RemoteWebDriver(new URL("http://chromedriver:4444/"), capability);

		}

		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);

		wait = new WebDriverWait(driver, 60);
		LogManager.getLogManager().reset();
		WebDriverRunner.setWebDriver(driver);
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		Selenide.getWebDriverLogs(LogType.BROWSER);
	}

	@Test
	public void loginLogout() {
		login("max", "maxmax");
		logout();
	}

	@Test
	public void detailView() {
		login("max", "maxmax");

		$(By.xpath("//*[@id=\"react\"]/div/div[3]/div/table/tr[2]/td[5]/div/button")).click();
		waitUntilPresent(By.xpath("//*[@id=\"react\"]/div/div[2]/div/h4"));
	}

	@Test
	public void sendTransaction() {
		login("max", "maxmax");

		// enter data
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[1]")).sendKeys("123"); //amount
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[2]")).sendKeys("2"); //target account
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[3]")).sendKeys("SeleniumTest"); //description text
		$(By.xpath("//*[@id=\"createTransactionForm\"]/button")).click();

		// commit transaction
		$(By.xpath("//*[@id=\"commitTransactionForm\"]/input")).sendKeys("1234");;
		$(By.xpath("//*[@id=\"commitTransactionForm\"]/button")).click();

		waitUntilPresent(By.xpath("//*[@id=\"createTransactionForm\"]"));

		logout();
	}

	@Test
	public void formTest() {
		login("max", "maxmax");
		By input = By.xpath("//*[@id=\"createTransactionForm\"]/input[1]");
		$(input).sendKeys("qwrtzuiopasdfghjklyxcvbnm123");
		wait.until(ExpectedConditions.textToBePresentInElementValue(input, "123"));
	}

	private void login(String username, String password) {
		String group6_ip = System.getenv("group6_ip");
		System.out.println("IP group6: " + group6_ip);
		open("https://group6:8080/login.html");
		$(By.id("username")).sendKeys(username);
		$(By.id("password")).sendKeys(password);
		$(By.tagName("button")).click();
		waitUntilPresent(By.tagName("h3"));
	}

	private void logout() {
		$(By.className("glyphicon-log-out")).click();
		waitUntilPresent(By.id("username"));
		waitUntilPresent(By.id("password"));
	}


	protected void waitUntilPresent(By by) {
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
	}

	@After
	public void tearDown() {
		WebDriverRunner.closeWebDriver();
	}

	private boolean isLocal() {
		return local != null && local.equals("true");
	}

}
