package at.ac.tuwien.waecm.oauth.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SeleniumIT{

	private WebDriver driver;
	private WebDriverWait wait;

	@Value("${path.chromedriver}")
	private String chromeDriverPath;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("no-sandbox");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

	}

	@Test
	public void loginLogout() {
		login("max", "maxmax");
		logout();
	}

	@Test
	public void loginWithWrongCredentials() {
		login("mari", "marimari");
		waitUntilPresent(By.id("username"));
		waitUntilPresent(By.id("password"));
	}


	@Test
	public void detailView() {
		login("max", "maxmax");
		waitUntilPresent(By.xpath("//*[@id=\"transactionTable\"]/tr[2]/td[5]/div/button"));
		$(By.xpath("//*[@id=\"transactionTable\"]/tr[2]/td[5]/div/button")).click();
		waitUntilPresent(By.id("transactionDetails"));
	}

	@Test
	public void sendTransaction() {
		login("max", "maxmax");

		waitUntilPresent(By.xpath("//*[@id=\"transactionTable\"]/tr[2]"));

		int numberOfTransactions = $$(By.xpath("//*[@id=\"transactionTable\"]/tr")).size();

		// enter data
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[1]")).sendKeys("123"); //amount
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[2]")).sendKeys("2"); //target account
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[3]")).sendKeys("SeleniumTest"); //description text
		$(By.xpath("//*[@id=\"createTransactionForm\"]/button")).click();

		waitUntilPresent(By.id("commitTransactionForm "));

		// commit transaction
		$(By.xpath("//*[@id=\"commitTransactionForm\"]/input")).clear();
		$(By.xpath("//*[@id=\"commitTransactionForm\"]/input")).sendKeys("secret123");
		$(By.xpath("//*[@id=\"commitTransactionForm\"]/button")).click();

		waitUntilPresent(By.id("createTransactionForm"));
		waitUntilPresent(By.xpath("//*[@id=\"transactionTable\"]/tr[2]"));

		assertThat($$(By.xpath("//*[@id=\"transactionTable\"]/tr")).size(),is(numberOfTransactions+1));

		logout();
	}

	@Test
	public void sendTransactionWithWrongTan() {
		login("max", "maxmax");

		waitUntilPresent(By.xpath("//*[@id=\"transactionTable\"]/tr[2]"));

		int numberOfTransactions = $$(By.xpath("//*[@id=\"transactionTable\"]/tr")).size();

		// enter data
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[1]")).sendKeys("123"); //amount
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[2]")).sendKeys("2"); //target account
		$(By.xpath("//*[@id=\"createTransactionForm\"]/input[3]")).sendKeys("SeleniumTest"); //description text
		$(By.xpath("//*[@id=\"createTransactionForm\"]/button")).click();

		waitUntilPresent(By.id("commitTransactionForm "));

		// commit transaction
		$(By.xpath("//*[@id=\"commitTransactionForm\"]/input")).sendKeys("wrong");
		$(By.xpath("//*[@id=\"commitTransactionForm\"]/button")).click();

		waitUntilPresent(By.id("createTransactionForm"));
		waitUntilPresent(By.xpath("//*[@id=\"transactionTable\"]/tr[1]"));

		assertThat($$(By.xpath("//*[@id=\"transactionTable\"]/tr")).size(),is(numberOfTransactions));

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
		driver.get("https://localhost:8080/login.html");
		$(By.id("username")).sendKeys(username);
		$(By.id("password")).sendKeys(password);
		$(By.tagName("button")).click();
	}

	private void logout() {
		$(By.className("glyphicon-log-out")).click();
		waitUntilPresent(By.id("username"));
		waitUntilPresent(By.id("password"));
	}

	private WebElement $(By by) {
		return driver.findElement(by);
	}

	private List<WebElement> $$(By by) {
		return driver.findElements(by);
	}

	protected void waitUntilPresent(By by) {
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
	}

	@After
	public void tearDown() {
		driver.close();
	}

}
