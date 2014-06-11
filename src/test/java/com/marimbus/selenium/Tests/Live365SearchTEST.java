package com.marimbus.selenium.Tests;

/* 
 * Data-driven test TestNG. Search and verify top radio stations by genres extracted from tech cloud. 
 * 
 * */
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import com.marimbus.selenium.SeleniumBase;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class Live365SearchTEST extends SeleniumBase{

    private WebDriver driver;
	private final String MAIN_PAGE_TITLE = "Live365 Internet Radio Network - Listen to Free Music, Online Radio";
	private final String EXPECTED_PAGE_TITLE_TEMPLATE = "Listen to Free %s Music Online - Live365 Internet Radio";
	private  String baseUrl = "http://www.live365.com/new/index.live/";
	final File file = new File("C:\\Users\\Alex\\AppData\\Roaming\\Apple Computer\\Safari\\Cookies\\Cookies.binarycookies");

	private  List<String> getListOfGenres(){			
		List<String> strListOfGenreTopStations = new ArrayList<String>();
		
		//						navigate to genres tech cloud page		
		driver = getDriver();
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath(".//*[@class='headerlinks']//li[1]/a")).click();			
		driver.switchTo().frame("contentFrame");
		driver.switchTo().frame(driver.findElement(By.cssSelector(".tabFrame")));		   

		//                  create genre station titles list extracted from tech cloud
		List<WebElement> genreElementsList = driver.findElements(By.xpath(".//*[@id='tagCloud']/a"));

		//        convert it to string list, then  output to a console 
		for (WebElement elem : genreElementsList) {
			String ell= elem.getText();
			strListOfGenreTopStations.add(ell);
			System.out.println("Extracted genre - "+ ell.toUpperCase());
		}			
		System.out.println("=======================================");
		System.out.println();
		return strListOfGenreTopStations;		
	}	

	@DataProvider
	public ListIterator<Object[]> iterOfTopStations(){
		List<Object[]> genresList = new ArrayList<Object[]>();	
	
		//         	convert strings list to list of object arrays and use listIterator for data-driven test
		for (String elem : this.getListOfGenres()) {
			Object[] singleTest = {elem};
			genresList.add(singleTest);
		}
         return genresList.listIterator();
	}

	@Test(dataProvider="iterOfTopStations")
	public void Test(String currentGenre) throws Exception{
		System.out.println("Search and verify genre - "+ currentGenre.toUpperCase());
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl);

		//                   assert page title
		Assert.assertEquals(driver.getTitle(),MAIN_PAGE_TITLE);

		//					navigate to search station field
		WebElement searchField = driver.findElement(By.name("query"));
		searchField.clear();
		searchField.sendKeys(currentGenre);		
		driver.findElement(By.xpath(".//*[@class='searchForm']/input")).click();

		//					assert search result page title
		String s = String.format(EXPECTED_PAGE_TITLE_TEMPLATE, currentGenre).toLowerCase();
		Assert.assertEquals(driver.getTitle().toLowerCase(),s);

		//                  assert crumbhead title
		driver.switchTo().frame("contentFrame");
		WebElement searchResult = driver.findElement(By.id("crumbhead"));
		String expectedResult = String.format("top %s stations", currentGenre).toLowerCase();
		String actualResult = searchResult.getText().toLowerCase();
		Assert.assertEquals(actualResult,expectedResult);
	}	
}