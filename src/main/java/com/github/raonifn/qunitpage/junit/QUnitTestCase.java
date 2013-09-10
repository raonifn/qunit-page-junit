package com.github.raonifn.qunitpage.junit;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class QUnitTestCase {

	private Description description;

	private String url;

	public void setUrl(Class<?> clazz, String url) {
		this.url = url;
		this.description = Description.createTestDescription(clazz, url);
	}

	public Description getDescription() {
		return description;
	}

	public void run(RunNotifier notifier, JUnitMethodCaller jUnitMethodCaller) {
		notifier.fireTestStarted(description);
		try {
			jUnitMethodCaller.before();
			try {
				startDriver(jUnitMethodCaller);
				notifier.fireTestFinished(description);
			} catch (Exception ex) {
				notifier.fireTestFailure(new Failure(description, ex));
			} finally {
				jUnitMethodCaller.after();
			}

		} finally {
			notifier.fireTestFinished(description);
		}
	}

	private void startDriver(JUnitMethodCaller jUnitMethodCaller) {
		DriverProvider driverProvider = jUnitMethodCaller.getDriverProvider();
		WebDriver driver = driverProvider.getDriver();
		String testUrl = driverProvider.getUrl(url);

		driver.get(testUrl);

		WebDriverWait wait = new WebDriverWait(driver, 45, 1000l);
		wait.until(new Function<WebDriver, Boolean>() {

			public Boolean apply(WebDriver driver) {
				List<WebElement> ret = driver.findElements(By
						.cssSelector("#qunit-tests .fail"));
				if (ret.size() > 0) {
					return true;
				}
				ret = driver.findElements(By.cssSelector("#qunit.finished"));
				if (ret.size() > 0) {
					return true;
				}
				return false;
			}

		});
		assertEquals(0,
				driver.findElements(By.cssSelector("#qunit-tests .fail"))
						.size());

	}
}
