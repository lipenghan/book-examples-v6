package com.vaadin.book.examples.testbench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.testbench.By;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;

public class AdvancedTest extends TestBenchTestCase {
    static private WebDriver driver;
	private String appUrl;

	@BeforeClass
    static public void createDriver() throws Exception {
        driver = TestBench.createDriver(new FirefoxDriver());
    }
    
	@Before
	public void setUp() throws Exception {
	    /*
        System.out.println(Parameters.getScreenshotErrorDirectory());
        System.out.println(Parameters.getScreenshotReferenceDirectory());
        System.out.println(Parameters.getScreenshotRetryDelay());
        System.out.println(Parameters.getMaxScreenshotRetries());
        System.out.println(Parameters.getScreenshotComparisonTolerance());
        System.out.println(Parameters.isCaptureScreenshotOnFailure());
        System.out.println(Parameters.isScreenshotComparisonCursorDetection());
        */

        Parameters.setScreenshotErrorDirectory("screenshots/errors");
        Parameters.setScreenshotReferenceDirectory("screenshots/reference");
	    Parameters.setMaxScreenshotRetries(2);
	    Parameters.setScreenshotComparisonTolerance(0.01);
	    Parameters.setScreenshotRetryDelay(10);
	    Parameters.setScreenshotComparisonCursorDetection(true);
        Parameters.setCaptureScreenshotOnFailure(true);

        appUrl = "http://localhost:8080/book-examples/tobetested";
	}

	@Test
	public void testButton() throws Exception {
		driver.get(appUrl + "?restartApplication");
		
		// Get the button's element.
        // (Actually the caption element inside the button.)
		// Use the debug ID given with setDebugId().
		WebElement button = driver.findElement(By.xpath(
            "//div[@id='main.button']/span/span"));
		
		// Check the caption text
		assertEquals("Push Me!", button.getText());

        // And click it. It's OK to click the caption element.
		// This causes the Label to appear.
        button.click();
        
        // Get the Label's element.
        // Use the automatically generated ID.
        WebElement label = driver.findElement(By.vaadin(
            "bookexamplestobetested::/VVerticalLayout[0]/"+
            "ChildComponentContainer[1]/VLabel[0]"));

        // Make the assertion
        assertEquals("Thanks!", label.getText());

        // Compare a screenshot just to be sure
        assertTrue(testBench(driver).compareScreen("clicked"));
        
        
	}		

    @Test
    public void testTooltip() throws Exception {
        driver.get(appUrl + "?restartApplication");
        // driver.navigate().to(appUrl);
        
        // Get the button's element.
        // Use the debug ID given with setDebugId().
        WebElement button = driver.findElement(By.xpath(
            "//div[@id='main.button']/span/span"));
        
        // Show the tooltip
        testBenchElement(button).showTooltip();
        
        // Wait a little to make sure it's up
        Thread.sleep(1000);
        
        // Check that the tooltip text matches
        assertEquals("This is a tip", driver.findElement(
            By.cssSelector("div.v-tooltip")).getText());
    }       

    @AfterClass
    static public void tearDown() throws Exception {
		driver.quit();
	}
}
