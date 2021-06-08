package pageObjects;

import org.openqa.selenium.By;

public class BookMyShowPOM {
	//page object model for Home
	public By closeNotification = By.id("wzrk-cancel");
	public By citySelectionBox = By.xpath("//input[@class='sc-fihHvN bSUQJL']");
	public By errorMsgCity = By.xpath("//*[@class='sc-bqjOQT daJpxV']");
	
	//page object model for sports 
	public By sportsTab = By.xpath("//a[@class='sc-iGrrsa FYYCw' and contains(text(),'Sports')]");
	public By thisWeekendBtn = By.xpath("(//div[@class='style__StyledText-sc-7o7nez-0 boewjJ'])[3]");
	public By sportsList1 = By.xpath("//div[@class='style__StyledText-sc-7o7nez-0 gBgfCW']");
	public By priceOfSports = By.xpath("//*[@class='df-bj df-cu df-cv df-cw df-o' or contains(text(),'₹')]");
	public By nameOfSports = By.xpath("//*[@class='df-be df-bf df-bg df-bh df-bi' or @class='df-at df-au df-av df-aw df-ax']");
	public By dateOfSports = By.xpath("(//*[@class='df-bg df-bj df-bk df-bl df-o' or @class='df-av df-ay df-az df-ba df-bb'])[2]");
		
	//page object model for movies 
	public By moviesTab = By.xpath("//a[@class='sc-iGrrsa FYYCw' and contains(text(),'Movies')]");
	public By upcomingMovies = By.xpath("(//div[@class='commonStyles__VerticalFlexBox-sc-133848s-2 style__ImageWrapper-sc-1t5vwh0-1 bRCLhZ']/img)[2]");
	public By movieLanguageList = By.xpath("//div[@class='style__StyledText-sc-7o7nez-0 boewjJ']");
		
	//page object model for sign in
	public By signInBtn = By.xpath("//*[text()='Sign in']");
	public By signInWithGoogleBtn = By.xpath("//*[text()='Continue with Google']");
	public By loginBox = By.id("identifierId");
	public static String loginBoxValue = "fifers123456789";
	public By loginNextBtn = By.xpath("//*[@class='VfPpkd-vQzf8d']");
	public By errorMsg = By.xpath("//div[@class='o6cuMc']");
	public By closeBtn = By.xpath("//div[@class='sc-hMqMXs kiiMpR']");
	
}
