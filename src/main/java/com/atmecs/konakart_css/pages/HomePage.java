package com.atmecs.konakart_css.pages;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.atmecs.konakart_css.helper.SeleniumHelper;
import com.atmecs.konakart_css.helper.ValidaterHelper;
import com.atmecs.konakart_css.logreports.LogReporter;
/**
 * this class contains the konakartValidate method to validate and automate the drop down and validate product
 * with negative and positive scenario.
 */
public class HomePage {
	LogReporter log=new LogReporter();
	SeleniumHelper help=new SeleniumHelper();
	ValidaterHelper validate=new ValidaterHelper();
	/**
	 * this method take the below parameters
	 * @param driver
	 * @param prop
	 * @param data
	 * and call the recursive method to validate the each product.
	 */
	public void konakartValidate(WebDriver driver,Properties prop,String[] data) {
		validate.titleValidater(driver,data[0]);
		help.dropDown(prop.getProperty("loc.dropdown.ecom"), driver, Integer.parseInt(data[4]));
		String temp=data[1]+","+data[2]+","+data[3];
		String[] temparray=temp.split(",");
		recursiveMethod(driver,prop,data[5],temparray);
	}
	/**
	 * this method take the below parameters
	 * @param driver
	 * @param prop
	 * @param option
	 * @param temparray
	 * and validate the product name,reviews and price of the product.
	 */
	public void recursiveMethod(WebDriver driver,Properties prop,String option,String[] temparray)
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		help.sendKeys(prop.getProperty("loc.txtfield.search"), driver, option);
		help.clickElement(driver,prop.getProperty("loc.btn.search"));
		WebElement element=driver.findElement(help.matchElement(prop.getProperty("loc.container.root")));
		String[] countString=element.getText().split("\n");
		if(countString.length>1) {
		WebElement prodelement=driver.findElement(help.matchElement(prop.getProperty("loc.panel.konakartproduct")));
		String[] elementarray=prodelement.getText().split("\n");
		int count=0;
		for(String string:elementarray) {
			validate.assertValidater(string, temparray[count]);
			count++;
		}
		}else {
		WebElement emptyelement=driver.findElement(help.matchElement(prop.getProperty("loc.txt.prodnotavailabe")));
			validate.assertValidater(emptyelement.getText(), "There are no available products.");	
		}
		}
}
