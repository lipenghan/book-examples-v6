package com.vaadin.book.examples.testbench;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;

public class BasicTest extends TestBenchTestCase {
	private WebDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
		driver = TestBench.createDriver(new FirefoxDriver());
		baseUrl = "http://localhost:8080/";
	}

	@Test
	public void testCase1() throws Exception {
		driver.get(baseUrl + "/book-examples/tobetested");
		
		// Get the button's element.
		// Use the debug ID given with setDebugId().
		WebElement button = driver.findElement(By.vaadin(
            "bookexamplestobetested::PID_Smain.button"));
		
		// Get the caption text
		assertEquals("Push Me!", button.getText());
		
		// And click it
		button.click();
		
		// Get the Label's element.
		// Use the automatically generated ID.
		WebElement label = driver.findElement(By.vaadin(
            "bookexamplestobetested::/VVerticalLayout[0]/"+
		    "ChildComponentContainer[1]/VLabel[0]"));

		// Make the assertion
		assertEquals("Thanks!", label.getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
