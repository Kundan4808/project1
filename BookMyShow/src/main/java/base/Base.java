package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import pageObjects.BookMyShowPOM;

public class Base extends BookMyShowPOM{

	public static WebDriver driver;
	public static Properties prop = new Properties();
	public static String city;
	public static WebDriverWait wait;
	public static XSSFWorkbook wb;
	public static List<String> moviesList=new ArrayList<String >();
	public static List<String> sportsList=new ArrayList<String >();
	public static ExtentHtmlReporter exthtml;
	public static ExtentTest exttest;
	public static ExtentReports report;
	
	public static String fileName= System.getProperty("user.dir")+"\\src\\test\\resources\\Excel\\movies.xlsx";
	public static String fileName1= System.getProperty("user.dir")+"\\src\\test\\resources\\Excel\\sports.xlsx";
	public static String screenshots = System.getProperty("user.dir")+"\\src\\test\\resources\\SnapShots\\";
	public static String reportlocation = System.getProperty("user.dir")+"\\reports\\bookmyshow.html";
	
	
	/*******************************FOR SET UP*******************************************************/
	@BeforeSuite
	public void setUp() {
		try{
			prop.load(new FileInputStream("src/main/java/config/Config.properties"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// based on the browserName initialize the required browser
		switch(prop.getProperty("browserName")){
		case "Firefox":
			driver = new FirefoxDriver();
			break;
			
		case "Chrome":
			driver = new ChromeDriver();
			break;
			
		default:
			System.out.println("Please choose form above only.");
		}

		city = prop.getProperty("city");
		driver.manage().window().maximize();  //to maximize window
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		
		exthtml = new ExtentHtmlReporter(reportlocation);
		report = new ExtentReports();
		report.attachReporter(exthtml);
		report.setSystemInfo("Host Name", "TestSystem"); // name of the system
		report.setSystemInfo("Environment", "Test Env");
		report.setSystemInfo("User Name", "fifers");
		exthtml.config().setDocumentTitle("BookMyShow");
		exthtml.config().setReportName("BookMyShow Functional Testing");
		exthtml.config().setTestViewChartLocation(ChartLocation.TOP);
		exthtml.config().setTheme(Theme.STANDARD);
	}

    /*************************For Taking ScreenShots********************************************/
	public void takescreenshot(String imagename) {
		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(f, new File(screenshots + imagename));
			exttest.addScreenCaptureFromPath(screenshots + imagename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
   /***************************For Window Scroll***********************************************/
	public void scrollBy() {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,500)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*************************For Excel Write**************************************************/
	public void excelWrite(String sheetName) throws IOException {
		try {
			int x;
			wb = new XSSFWorkbook();
			if (sheetName == "MovieLanguages") {
				XSSFSheet sheet1 = wb.createSheet(sheetName);
				int row = 0;
				for (x = 0; x < moviesList.size(); x++) {
					try {
						sheet1.createRow(row).createCell(0).setCellValue(moviesList.get(x));
						row++;
					} catch (Exception e) {
						e.printStackTrace();
					}
					FileOutputStream writeFile1 = new FileOutputStream(new File(fileName));
					wb.write(writeFile1);
					writeFile1.close();
					}
				}
			else if (sheetName == "sportsEventList") {
				XSSFSheet sheet2 = wb.createSheet(sheetName);
				int row = 0;
				for (x = 0; x < sportsList.size(); x++) {
					try {
						sheet2.createRow(row).createCell(0).setCellValue(sportsList.get(x));
						row++;
					} catch (Exception e) {
						e.printStackTrace();
					}
					FileOutputStream writeFile = new FileOutputStream(new File(fileName1));
					wb.write(writeFile);
					writeFile.close();
				}
			}
			wb.close();
		} catch (Exception e) {
		}
	}


	/****************************Close Browser***************************************************/
	@AfterSuite
	public void teadDown() {
		report.flush();
		try {
			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /f /im geckodriver.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
