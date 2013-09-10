package com.github.raonifn.qunitpage.junit;

import java.io.Closeable;

import org.openqa.selenium.WebDriver;

public interface DriverProvider extends Closeable {

	public WebDriver getDriver();

	public String getUrl(String test);
}
