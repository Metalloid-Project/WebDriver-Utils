package com.github.metalloid.webdriver.utils;

import com.github.metalloid.pagefactory.controls.Control;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class JavaScript extends Utility {
	private final JavascriptExecutor jsExecutor;

	public JavaScript(WebDriver driver) {
		super(driver);
		jsExecutor = (JavascriptExecutor) driver;
	}

	public void scrollToElement(WebElement element) {
		disallowNullElement(element);
		executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void scrollToElement(Control control) {
		scrollToElement(control.element());
	}

	public void click(WebElement element) {
		disallowNullElement(element);
		executeScript("arguments[0].click();", element);
	}

	public void deleteCookies() {
		// js function
		jsExecutor.executeScript(
				"document.cookie.split(\";\").forEach(function(c) { document.cookie = c.replace(/^ +/, \"\").replace(/=.*/, \"=;expires=\" + new Date().toUTCString() + \";path=/\"); });");
		// jquery function
		jsExecutor.executeScript("for (var it in $.cookie()) $.removeCookie(it);");
		// if cookie persists, set expiration date
		jsExecutor.executeScript("document.cookie = \"username=; expires=\" + new Date().toGMTString(); ");
	}

	public String getValue(WebElement element) {
		disallowNullElement(element);
		return (String) executeScript("return arguments[0].value", element);
	}

	public void setTextContent(WebElement element, String textContent) {
		disallowNullElement(element);
		executeScript("arguments[0].textContent = 'arguments[1]'", element, textContent);
	}

	public String getWidth(WebElement element) {
		disallowNullElement(element);
		return (String) executeScript("return window.getComputedStyle(arguments[0]).width", element);
	}

	public String getHeight(WebElement element) {
		disallowNullElement(element);
		return (String) executeScript("return window.getComputedStyle(arguments[0]).height", element);
	}

	@SuppressWarnings("unchecked")
	public List<WebElement> getElementsInsideIFrame(String iframeID, String cssSelector) {
		driver.switchTo().defaultContent();
		return (List<WebElement>) executeScript(
				"return document.getElementById(arguments[0]).contentDocument.querySelectorAll(arguments[1]);",
				iframeID, cssSelector);
	}

	public Boolean isChecked(String cssSelector) {
		return (Boolean) executeScript("return document.querySelector(arguments[0]).checked", cssSelector);
	}

	public Boolean isChecked(WebElement element) {
		disallowNullElement(element);
		return (Boolean) executeScript("return arguments[0].checked", element);
	}

	public void scroll(int x, int y) {
		executeScript("window.scrollBy(arguments[0],arguments[1])", x, y);
	}

	public void scrollUp() {
		scroll(0, -5000);
	}

	public void scrollDown() {
		scroll(0, 5000);
	}

	public void scrollLeft() {
		scroll(-5000, 0);
	}

	public void scrollRight() {
		scroll(5000, 0);
	}

	public void scrollUp(WebElement div) {
		disallowNullElement(div);
		executeScript("arguments[0].scrollTo(0,0);", div);
	}

	public String getAttribute(WebElement element, String attribute) {
		disallowNullElement(element);
		return (String) executeScript("return arguments[0].getAttribute(\"arguments[1]\");", element, attribute);
	}

	public void setAttribute(WebElement element, String attribute, String value) {
		disallowNullElement(element);
		executeScript("arguments[0].setAttribute('arguments[1]', arguments[2])", element, attribute, value);
	}

	public boolean isEnabled(WebElement element) {
		disallowNullElement(element);
		return !(boolean) executeScript("return arguments[0].disabled", element);
	}

	public void setEnabled(WebElement element) {
		disallowNullElement(element);
		executeScript("arguments[0].enabled = true");
	}

	public void scrollDown(WebElement element) {
		scrollDown(element, 5000);
	}

	public void scrollDown(WebElement element, int howMuch) {
		disallowNullElement(element);
		executeScript("arguments[0].scrollTo(0,arguments[1]);", element, howMuch);
	}

	public void highlightElement(WebElement element) {
		disallowNullElement(element);
		executeScript("arguments[0].style.backgroundColor = \"#FDFF47\";", element);
		executeScript("arguments[0].style.outline = '#f00 solid 2px';", element);
	}
	
	public Object executeScript(String script, Object... arguments) {
		return jsExecutor.executeScript(script, arguments);
	}
	
	/**
	 * This method should NOT be explicitly invoked. Use
	 * selenium.window.openNewTab(); instead
	 */
	public void openNewTab() {
		executeScript("window.open('about:blank','_blank');");
	}

	private void disallowNullElement(WebElement element) {
		if (element == null) {
			throw new IllegalArgumentException("Cannot execute script because of null element!");
		}
	}
}
