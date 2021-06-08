package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import base.Base;

public class BookMyShow extends Base {

	public void signIn() throws InterruptedException {
		driver.findElement(signInBtn).click(); // Click on the sign in button
		Thread.sleep(3000);

		WebElement signIn = driver.findElement(signInWithGoogleBtn); // Click on sign in with google button
		wait = new WebDriverWait(driver, 3000);
		wait.until(ExpectedConditions.visibilityOf(signIn));
		signIn.click();

		ArrayList<String> lst = new ArrayList<>(driver.getWindowHandles()); // For window handle
		driver.switchTo().window(lst.get(1)); // Switch focus on 2nd window of login
		WebElement login = driver.findElement(loginBox);
		wait = new WebDriverWait(driver, 3000);
		wait.until(ExpectedConditions.visibilityOf(login));
		login.sendKeys(loginBoxValue);
		driver.findElement(loginNextBtn).click(); // Click on the next button
		String msg = driver.findElement(errorMsg).getText(); // to capture the error message

		exttest = report.createTest("Validating Sign In with invlaid credentials"); // create report test
		exttest.log(Status.PASS, "error msg will be: " + msg); // Status of test
		takescreenshot("InvalidMail.png"); // Screen shot for invalid credentials
		driver.close(); // to close the 2nd opened window

		driver.switchTo().window(lst.get(0)); // Switch focus on Main window
		driver.findElement(closeBtn).click(); // to close the sign in options
	}

	public void movies() throws Exception {
		try {
			driver.findElement(moviesTab).click(); // click on the movies tab
			driver.findElement(upcomingMovies).click(); // click on the upcoming movies button

			List<WebElement> langaugeList = driver.findElements(movieLanguageList); // Store the web elements of movie
																					// language
			wait = new WebDriverWait(driver, 3000);
			wait.until(ExpectedConditions.visibilityOfAllElements(langaugeList));

			scrollBy(); // to scroll main window
			exttest = report.createTest("Validating Available language"); // create report test for available language
			exttest.log(Status.PASS, "Available languages displayed"); // status of report status
			takescreenshot("LanguageList.png"); // take screen shot of movie language
			String str = "Number of languages: " + langaugeList.size(); // number of movie language available
			moviesList.add(str);

			// for printing list of languages
			for (WebElement lList : langaugeList) {
				String movieLanguage = lList.getText();
				moviesList.add(movieLanguage);
			}

			excelWrite("MovieLanguages"); // write movie language in the excel sheet
			exttest = report.createTest("Validating Movies Language List");
			exttest.log(Status.PASS, "Language list will be  stored in excel sheet");
		} catch (Exception e) {
			exttest = report.createTest("Validating Movies Language List in case of failure");
			exttest.log(Status.FAIL, "Language listis not in excel sheet");
		}
		
	}

	public void sports() throws IOException {
		try {
		driver.findElement(sportsTab).click(); // click on the sports tab
		driver.findElement(thisWeekendBtn).click(); // click on the this weekend option

		// to get the name and date of sports by sorting their price
		String finalOutput = null;
		int price2 = 0;
		Map<Integer, String> map = new TreeMap<>();

		List<WebElement> sList = driver.findElements(sportsList1); // store list of sports available this weekend
		for (int i = 0; i < sList.size(); i++) {
			sList.get(i).click();

			String priceOfSports1 = driver.findElement(priceOfSports).getText(); // To get price of sports
			price2 = Integer.parseInt(priceOfSports1.substring(2).replace(",", "")); // To convert string into integer

			String nameOfSports1 = driver.findElement(nameOfSports).getText(); // To get name of sports
			String dateOfSports1 = driver.findElement(dateOfSports).getText(); // to store date of sports
			finalOutput = "Name: " + nameOfSports1 + " and Date: " + dateOfSports1;

			map.put(price2, finalOutput); // Store price,name,date in map
			driver.navigate().back(); // to navigate back
			sList = driver.findElements(sportsList1);
		}

		wait = new WebDriverWait(driver, 3000);
		wait.until(ExpectedConditions.visibilityOfAllElements(sList));

		// Store value by sorting their keys
		Map<Integer, String> sortedTreeMap = map.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		sportsList.addAll(sortedTreeMap.values()); // add all the values in array list
		excelWrite("sportsEventList"); // create sheet and store values from map
		exttest = report.createTest("Validating Sports List");
		exttest.log(Status.PASS, "Sports list will be  stored in excel sheet");
		} catch(Exception e) {
			exttest = report.createTest("Validating Sports List in case of failure");
			exttest.log(Status.FAIL, "Sports list is not created in excel sheet");
		}
	}
}