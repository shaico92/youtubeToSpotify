package skeleton.ocm;


import java.awt.RenderingHints.Key;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class BasicMethods {
	protected WebDriver driver;
	protected JavascriptExecutor je;
	protected Actions actions;
	protected Robot robot=new Robot();
	protected ChromeOptions options;
public BasicMethods() throws ParserConfigurationException, IOException, Exception {
//this.driver =gecko();
this.driver=chrome();
		je=(JavascriptExecutor) driver;
		driver.manage().window().setPosition(new Point(0, -2000));
		actions= new Actions(driver);
	}



	public void click(WebElement el) {
		el.click();
	}
	public void clickR(int howMuch) {
		for(int i=0;i<=howMuch;i++) {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}	
		 
	}
	public void click(WebElement el,int howMuch) {
		try {
			for(int i=0;i<=howMuch;i++) {
				Thread.sleep(500);
			el.click();}	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void fillText(WebElement el,String text) {
		el.clear();
		el.sendKeys(text);
	}
	public void clearText(WebElement el) {
		el.clear();
		
	}
	public String getText(WebElement el) {
	return	el.getText();
}
	
	public String driversPath(int i) throws ParserConfigurationException, Exception, IOException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document docRoot= builder.parse("ExtractSongsAndExportConfig.xml");
		NodeList paths= docRoot.getElementsByTagName("drivers");
		Element elmetns= (Element) paths.item(0);
		String chrome = elmetns.getElementsByTagName("chrome").item(0).getTextContent();
		String firefox=elmetns.getElementsByTagName("gecko").item(0).getTextContent();
		ArrayList<String> list =new ArrayList<String>();
		list.add(firefox+"\\geckodriver.exe");
		list.add(chrome+"\\chromedriver.exe");
return list.get(i);
	}
	public ChromeOptions options() throws Exception {
		options = new ChromeOptions();
		//options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		//options.setExperimentalOption("useAutomationExtension", false);
		//options.addArguments("--remote-debugging-port=9222"+ driversPath(0));
		options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

		return options;

	}
	
	public  WebDriver chrome() throws ParserConfigurationException, IOException, Exception {
		String path =driversPath(1);
		String prop= "webdriver.chrome.driver";
		System.setProperty(prop, path);
		WebDriver driver = new ChromeDriver(options());
		
		
		
		return driver;
	}

	public  String getSQLiteDBTagVal() throws ParserConfigurationException, SAXException {
		String db=null;
		try {

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document docRoot= builder.parse("ExtractSongsAndExportConfig.xml");
			NodeList paths= docRoot.getElementsByTagName("root");
			Element elmetns= (Element) paths.item(0);
			 db = elmetns.getElementsByTagName("DBPath").item(0).getTextContent();
			db= "jdbc:sqlite:"+db+"\\";
			
		} catch (IOException e) {
			System.out.println("path is does not exist");
		}
		return db;
	}
	
	public  String logPath() throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document docRoot= builder.parse("ExtractSongsAndExportConfig.xml");
		NodeList paths= docRoot.getElementsByTagName("root");
		Element elmetns= (Element) paths.item(0);
		String logs = elmetns.getElementsByTagName("Logs").item(0).getTextContent();
		return logs;
	}
	
	public   void writeToLog(String song) throws SecurityException, ParserConfigurationException, SAXException, IOException {
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			
			

			File file = new File(logPath()+"\\spotify.log");
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			if (song.equals("this is the a new run of the program")) {
				bw.write("this is the a new run of the program");
			}
			bw.write(getTime()+"'"+song+"'"+" spotify has not found the song you searched for.\r");

		

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}  
	
//	public  void writeToLog(String song) throws SecurityException, ParserConfigurationException, SAXException, IOException {
//	    try{    
//	           FileWriter fw=new FileWriter(logPath()+"\\spotifyNotFoundList.log");
//	     
//	    	   fw.write(); 
//	               
//	          }catch(Exception e){System.out.println(e);}    
//	          System.out.println(song+"was added to log");    
//	     }    
	
	
	
	
	
	public  String getTime() {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   String time= "["+dtf.format(now)+"]--";
		   return time;
	}
	public  String getTime(int i) {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   String time= "["+dtf.format(now)+"]";
		   time= time.replace("/", "_");
		   time=time.replace(":", "-");
		   return time;
	}
	
	
	public  WebDriver gecko() throws ParserConfigurationException, IOException, Exception {
		String path =driversPath(0);
		String prop= "webdriver.gecko.driver";
		System.setProperty(prop, path);
		WebDriver driver = new FirefoxDriver();
		
		
		
		return driver;
	}
	public void goToWebSite(String webPage) {
		driver.get(webPage);
	}
	public void quit() {
		driver.close();
	}
	public void hoverToEnable(WebElement target) throws Exception{
	
		this.actions.moveToElement(target).build().perform();
		
	}
	public void hoverToEnable(WebElement target,int x, int y) throws Exception{
		
		this.actions.moveToElement(target,x ,y).build().perform();
		new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(target));
	}
	
	
	
	public void clickHiddenElms(WebElement el) {
		je.executeScript("arguments[0].click();",el.getTagName());
	}
	public void hold(int howmuch) throws Exception{
		Thread.sleep(howmuch);
	}
	public void scrolldDown(int i) {
je.executeScript("window.scrollBy(0,"+i+")");

	}

	

}
