package com.vaadin.book.examples.testbench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;

public class Testcase1 extends TestBenchTestCase {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = TestBench.createDriver(new FirefoxDriver());
		baseUrl = "http://localhost:8080/";
	}

	@Test
	public void testCase1() throws Exception {
		driver.get(concatUrl(baseUrl, "/book-examples/tobetested?restartApplication"));
		driver.findElement(By.vaadin("bookexamplestobetested::PID_Smain.button/domChild[0]")).click();
		assertEquals("This is different!", driver.findElement(By.vaadin("bookexamplestobetested::/VVerticalLayout[0]/ChildComponentContainer[1]/VLabel[0]")).getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
