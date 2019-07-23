package com.vit.FlipkartAssignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Flipkart_Demo {

	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
	}

	@Test 
	public void store_data_to_HashMap() throws InterruptedException {

		String url="https://www.flipkart.com/";
		String search_text= "TV";
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);
		driver.get(url);

		// checking page navigating with expected title
		String expectedTitle ="Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
		Reporter.log("Title Verified", true);

		// Click on cross button
		WebElement signin_cross_button = driver.findElement(By.xpath("//button[@class='_2AkmmA _29YdH8']"));
		signin_cross_button.click();
		Thread.sleep(2000);

		// check search box is displayed
		WebElement search_box = driver.findElement(By.xpath("//input[@type='text' and @name='q']"));
		Assert.assertEquals(search_box.isDisplayed(), true);
		Reporter.log("Search box is Displayed", true);

		//Enter TV in search box
		search_box.sendKeys(search_text);

		// Click on search button
		driver.findElement(By.xpath("//button[@type='submit' and @class='vh79eN']")).click();

		// searched result Validation 1
		WebElement searched_result = driver.findElement(By.xpath("//span[ @class='_2yAnYN']"));
		Assert.assertEquals(searched_result.isDisplayed(), true);
		Reporter.log("TV result searched and validated", true);

		// searched result Validation 2
		String exp_searched_result_Title = search_text;
		String actual_searched_result_Title = driver.getTitle();
		if(actual_searched_result_Title.toUpperCase().contains(exp_searched_result_Title.toUpperCase())) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		Reporter.log("TV is present in page title", true);

		// Fetch all product texts
		List<WebElement> list_of_product_name = driver.findElements(By.xpath("//div[@class='_3wU53n']"));
		List<WebElement> list_of_product_price = driver.findElements(By.xpath("//div[@class='_1vC4OE _2rQ-NK']"));
		/*String product_name ;
		String product_price;
		for(int i=0; i<list_of_product_name.size(); i++) {
			product_name = list_of_product_name.get(i).getText();
			product_price = list_of_product_price.get(i).getText().replaceAll("\\D", ""); // To remove , and Rs. Sign --> replaceAll("\\D", "")
			Reporter.log("Product Name: " + product_name + " --- Price: " +product_price + " <br>", true);
		}*/

		// Creating hashMap 
		HashMap<String,Integer> hm=new HashMap<String,Integer>();  
		for(int i=0; i<list_of_product_name.size(); i++) {
			String product_name = list_of_product_name.get(i).getText();
			String product_price = list_of_product_price.get(i).getText().replaceAll("\\D", ""); // To remove , and Rs. Sign --> replaceAll("\\D", "")
			Integer price_of_product = Integer.valueOf(product_price);
			hm.put(product_name, price_of_product);
		}
		System.out.println("\nTV models with their Prices ");  
		//System.out.println("Hashmap= " + hm );   // 1st way to print HashMap
		for(Map.Entry<String, Integer> entry:hm.entrySet()){    // 2nd way to print HashMap
			System.out.println(entry.getKey()+" "+entry.getValue());    
		}  

		// finding highet price product
		String highest_price_product_name = null;
		Integer highest_product_price = 0;
		for (Map.Entry<String, Integer> entry : hm.entrySet()) {
			if (entry.getValue() > highest_product_price) {
				highest_price_product_name = entry.getKey();
				highest_product_price = entry.getValue();	}
		}
		System.out.println("\nHigest price product is " + highest_price_product_name + " \nwith price of Rs. "+ highest_product_price);

		// find lowest price product
		String first_product_name = list_of_product_name.get(0).getText();
		String first_product_price = list_of_product_price.get(0).getText().replaceAll("\\D", ""); 
		Integer price_of_first_product = Integer.valueOf(first_product_price);

		String lowest_price_product_name = first_product_name;
		Integer lowest_product_price = price_of_first_product;
		for (Map.Entry<String, Integer> entry : hm.entrySet()) {
			if (entry.getValue() < lowest_product_price) {
				lowest_price_product_name = entry.getKey();
				lowest_product_price = entry.getValue();	}
		}
		System.out.println("\nLowest price product is " + lowest_price_product_name + " \nwith price of Rs. "+ lowest_product_price);

	}
}