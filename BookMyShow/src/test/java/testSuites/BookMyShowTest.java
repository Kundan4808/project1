package testSuites;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.BookMyShow;
import pages.BookMyShowHome;

public class BookMyShowTest {
	
	@BeforeTest
	public void openBrowserAndLaunchUrl() throws Exception {
		BookMyShowHome b = new BookMyShowHome();
		b.openUrl();
		b.closeNotification();
		b.citySelection();
	}
	
	@Test
	public void smokeTest() throws InterruptedException {
		BookMyShow b1 = new BookMyShow();
		b1.signIn();
	}
	
	@Test(priority=1)
	public void regressionTest() throws Exception {
		BookMyShow b1 = new BookMyShow();
		b1.movies();
	}
	
	@Test(priority=2)
	public void reTest() throws Exception{	
		BookMyShow b2 = new BookMyShow();
		b2.sports();
	}
	
	@AfterTest
	public void closeBrowser() {
		BookMyShowHome b = new BookMyShowHome();
		b.closeBrowser();
	}
}
