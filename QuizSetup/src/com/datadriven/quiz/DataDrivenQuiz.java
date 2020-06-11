package com.datadriven.quiz;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.excel.utlity.Xls_Reader;



public class DataDrivenQuiz {
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\jemmy\\Documents\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Xls_Reader reader = new Xls_Reader("C:\\Users\\jemmy\\eclipse-workspace\\QuizSetup\\src\\com\\testdata\\AssessmentTest.xlsx");
		int totalrows = reader.getRowCount("Sheet1");
		System.out.println(totalrows);
		
		driver.get("https://backend.aptination.com/dashboard/dash_login/?next=/dashboard/");
		
		driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys("prabhatiitbhu@gmail.com");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("01pass1234");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		for(int i=2;i<=totalrows;i++) {
		driver.findElement(By.xpath("//a[contains(text(),'Question Paper')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Assessment Test')]")).click();
		
		String grade = reader.getCellData("Sheet1", "grade", i);
		String s = reader.getCellData("Sheet1", "section", i);
		String title1 = reader.getCellData("Sheet1", "title", i);
		String sd = reader.getCellData("Sheet1", "startdate", i);
		String st = reader.getCellData("Sheet1", "starttime", i);
		String ed = reader.getCellData("Sheet1", "enddate", i);
		String et = reader.getCellData("Sheet1", "endtime", i);
		String pd = reader.getCellData("Sheet1", "publishdate", i);
		String pt = reader.getCellData("Sheet1", "publishtime", i);
		String d = reader.getCellData("Sheet1", "duration", i);
		String q = reader.getCellData("Sheet1", "questions", i);
		
		String startdate1 = sd.replaceAll("^.|.$","");
		String starttime1 = st.replaceAll("^.|.$","");
		String enddate1 = ed.replaceAll("^.|.$","");
		String endtime1 = et.replaceAll("^.|.$","");
		String publishdate1 = pd.replaceAll("^.|.$","");
		String publishtime1 = pt.replaceAll("^.|.$","");
		String duration1 = d.replaceAll("^.|.$","");
		String section = s.replaceAll("^.|.$","");
		String questions = q.replaceAll("^.|.$","");
		
		Select course = new Select(driver.findElement(By.id("course")));
		course.selectByVisibleText(grade);
		Select packages = new Select(driver.findElement(By.id("package")));
		packages.selectByVisibleText(section);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		//Create test
		driver.findElement(By.xpath("//button[contains(text(),'Create Assessment Test')]")).click();
		driver.findElement(By.id("title")).sendKeys(title1);
		driver.findElement(By.id("is-active")).click();
		driver.findElement(By.id("shuffle-questions")).click();
		driver.findElement(By.id("show_solution")).click();
		driver.findElement(By.id("show_report")).click();
		
		WebElement startdate = driver.findElement(By.id("exam-start-date"));
		startdate.click();
		startdate.clear();
		startdate.sendKeys(startdate1);
		
		WebElement starttime = driver.findElement(By.id("exam-start-time"));
		starttime.click();
		starttime.clear();
		starttime.sendKeys(starttime1);
		
		WebElement enddate = driver.findElement(By.id("exam-end-date"));
		enddate.click();
		enddate.clear();
		enddate.sendKeys(enddate1);
		
		WebElement endtime = driver.findElement(By.id("exam-end-time"));
		endtime.click();
		endtime.clear();
		endtime.sendKeys(endtime1);
		
		WebElement publishdate = driver.findElement(By.id("publish-date"));
		publishdate.click();
		publishdate.clear();
		publishdate.sendKeys(publishdate1);
		
		WebElement publishtime = driver.findElement(By.id("publish-time"));
		publishtime.click();
		publishtime.clear();
		publishtime.sendKeys(publishtime1);
		
		driver.findElement(By.id("pass-criteria")).sendKeys("40");
		driver.findElement(By.id("correct-answer-marks")).sendKeys("3");
		driver.findElement(By.xpath("//input[@id='wrong-answer-marks']")).sendKeys("-1");
		driver.findElement(By.id("question-unattempted-marks")).sendKeys("0");
		driver.findElement(By.id("negative-marking")).click();
		driver.findElement(By.id("duration")).sendKeys(duration1);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);
		
		//Add Questions
		driver.get("https://backend.aptination.com/dashboard/moderatorassessmentpaper");
		Select course1 = new Select(driver.findElement(By.id("course")));
		course1.selectByVisibleText(grade);
		Select packages1 = new Select(driver.findElement(By.id("package")));
		packages1.selectByVisibleText(section);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("(//button[contains(text(),'Add/Remove Questions')])[last()]")).click();
		driver.findElement(By.xpath("(//input[@id='number-of-question'])[last()]")).clear();
		driver.findElement(By.xpath("(//input[@id='number-of-question'])[last()]")).sendKeys(questions);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.id("checkbox")).click();
		driver.findElement(By.xpath("(//input[@id='chapter_select'])[last()]")).sendKeys(questions);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		//Instructions
		driver.get("https://backend.aptination.com/dashboard/moderatorassessmentpaper");
		Select course2 = new Select(driver.findElement(By.id("course")));
		course2.selectByVisibleText(grade);
		Select packages2 = new Select(driver.findElement(By.id("package")));
		packages2.selectByVisibleText(section);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("(//button[contains(text(),'Edit/Add Instructions')])[last()]")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("cke_1_contents")).click();
		
		String one = "1. The Test consists of "+questions+" questions. The maximum marks is 135.";
		String two = "2. Each question is allotted 3 marks for correct response.";
		String three = "3. Candidates will be awarded marks as stated above in instruction no.2  for correct response of each question. 1 marks will be deducted for indicating incorrect response of each question. "
				+ "No deduction from the total score will be made if no response is indicated for an item in the answer sheet.";
		String four = "4. There is only one correct response for each question(unless mentioned). ";
		String keysPressed =  Keys.chord(Keys.RETURN);
		Actions action = new Actions(driver); 
		action.sendKeys(one).build().perform();
		action.sendKeys(keysPressed).build().perform();
		action.sendKeys(two).build().perform();
		action.sendKeys(keysPressed).build().perform();
		action.sendKeys(three).build().perform();
		action.sendKeys(keysPressed).build().perform();
		action.sendKeys(four).build().perform();
		driver.findElement(By.id("submit")).click();
		
		
		
		
		
		
		}
		
	}

}
