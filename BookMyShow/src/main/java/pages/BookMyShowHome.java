package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import base.Base;

public class BookMyShowHome extends Base {

	//to open URL of bookMyShow
	public void openUrl() {
		driver.get(prop.getProperty("url"));
	}

	// to close notification
	public void closeNotification() {
		try {
			driver.findElement(closeNotification).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//For City Selection
	public void citySelection() {
		String cityFromPopularCity = null;
		int i = 1;
		
		//For selecting city from popular city list
		while (i <= 10) {
			String city1 = driver.findElement(By.xpath("(//span[@class='sc-eEieub jpadjB'])[" + i + "]")).getText();
			if (city.equalsIgnoreCase(city1)) {
				driver.findElement(By.xpath("(//span[@class='sc-eEieub jpadjB'])[" + i + "]")).click();
				cityFromPopularCity = " ";
				exttest = report.createTest("Validating city");
				exttest.log(Status.PASS, "Selected city is: " + city);
				break;
			} else {
				city1 = "";
				i++;
			}
		}
		
		//For selecting city from other  list
		if (cityFromPopularCity == null) {
			driver.findElement(citySelectionBox).sendKeys(city);
			driver.findElement(citySelectionBox).click();
			WebElement cityPanel = driver.findElement(By.xpath("//*[@class='sc-eopZyb kQJFcF']"));
			String city2 = cityPanel.getText();

			if (city.equalsIgnoreCase(city2)) {
				driver.findElement(citySelectionBox).sendKeys(Keys.ENTER);
				exttest = report.createTest("Validating city");
				exttest.log(Status.PASS, "Selected city is: " + city);
			}
			else{				
				try {
					String output = driver.findElement(errorMsgCity).getText(); //error when city is invalid
					exttest = report.createTest("Validating Invalid City");
					exttest.log(Status.FAIL, "Error msg will be: " + output);
					takescreenshot("InvalidCity.png");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			
	}
	
	//Close Browser
	public void closeBrowser() {
		driver.close();
	}
}
