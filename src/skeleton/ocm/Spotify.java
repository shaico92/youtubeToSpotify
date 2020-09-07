package skeleton.ocm;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;


public class Spotify extends BasicMethods {
	private WebElement el;
	private Connection conn;


	public Spotify() throws ParserConfigurationException, IOException, Exception {
		super();
		logPath();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
	
	
	
	public void login(String username,String password) throws Exception{
		ArrayList <WebElement> buttons =(ArrayList<WebElement>) driver.findElements(By.cssSelector("button"));
		for (WebElement e : buttons){
			final String name = e.getText();
			if (name.equals("LOG IN")){
				click(e);
				break;
			}

		}


		hold(5000);
		
		el=driver.findElement(By.cssSelector("input[ng-model='form.username']"));
		click(el);
		fillText(el, username);
		el=driver.findElement(By.cssSelector("input[ng-model='form.password']"));
		click(el);
		fillText(el, password);
		el=driver.findElement(By.cssSelector("#login-button"));
		click(el);
	}
	
	public String createPlaylist(String name) {
		el=driver.findElement(By.cssSelector("button[class='fcdf941c8ffa7d0878af0a4f04aa05bb-scss']"));
		click(el);
		el=driver.findElement(By.cssSelector("input[class='inputBox-input']"));
		fillText(el, name);
		el=driver.findElement(By.cssSelector("button[class='_2221af4e93029bedeab751d04fab4b8b-scss _8fec0262e00c11513faad732021ed012-scss']"));
		click(el);
		return name;
		
	}

	public void addToPlaylis(String album) throws Exception{
		ArrayList<String> songsToAdd=	Youtube.getPlayList();
		int err=0;
	for (int i = 0; i < songsToAdd.size(); i++) {
	
		if (searchSong(songsToAdd.get(i))==true) {
			addingSong(album);	
		} else {
			err++;
			if (err==1) {
				writeToLog("this is the a new run of the program");	
			}
			System.out.println(songsToAdd.get(i)+" song was not found or there are no songs to add to the artist");
			writeToLog(songsToAdd.get(i));
			continue;
		}
		
//		try {
//			if(songNotFound()==true) {
//				System.out.println(songsToAdd.get(i)+" song was not found or there are no songs to add to the artist");
//				
//				
//			}else {
//				
//				addingSong(album);	
//				
//				
//			}
//		} catch (Exception e) {
//			
//		}
//	
	}	
		}
	public void addToPlaylis(String album,String table,String db) throws Exception{
		ArrayList<String> songsToAdd=	Youtube.getPlayList();
	
			
			String url= getSQLiteDBTagVal()+db+".db";
			 conn= DriverManager.getConnection(url);
			String sql= "SELECT song,singer FROM '"+table+"'";
			Statement st = conn.createStatement();
			
				ResultSet rs=st.executeQuery(sql);
				while (rs.next()) {
					String singer=rs.getString("singer");
					String song= rs.getString("song");
					 
					
							if (searchSong(singer+"-"+song)==true) {
								addingSong(album);	
							} else {
								System.out.println(singer+"-"+song+" song was not found or there are no songs to add to the artist");
								writeToLog(singer+"-"+song);
								continue;
							}
							
//							try {
//								if(songNotFound()==true) {
//									System.out.println(songsToAdd.get(i)+" song was not found or there are no songs to add to the artist");
//									
//									
//								}else {
//									
//									addingSong(album);	
//									
//									
//								}
//							} catch (Exception e) {
//								
//							}
					//	
						
					}
				
		
		}
				
				
			
		
		
		
		
		
		
		
	
	
	public boolean searchSong(String name) throws NoSuchElementException, Exception {
	
		
		
				WebElement search=driver.findElement(By.cssSelector("a[href='/search']"));
				hoverToEnable(search);
			
					click(search);

					el=driver.findElement(By.cssSelector("input[class='_2f8ed265fb69fb70c0c9afef329ae0b6-scss']"));
				//	input which song
					fillText(el, name);
					
					
						
						
					
					
						hold(1000);
						
						
						//.col-xs-12.col-sm-8.col-md-9.col-lg-9.col-xl-10				
					try {
					WebElement el1= driver.findElement(By.cssSelector(".row.SearchOverviewContainer__top-result-section"));
					el=el1.findElement(By.cssSelector("ol[class='tracklist']"));
				} catch (NoSuchElementException e) {
					return false;
			}
					
			return true;
		
	
			//finds song
		
		


	}

	public  void extractData(String db, String table) {
	
		
	}
	

//
//
	public boolean songNotFound() {
		
			
		el= driver.findElement(By.cssSelector("section[class='Search']"));
		WebElement el2= el.findElement(By.cssSelector("div[class='Search__content Search__content--empty']"));
			el=el2.findElement(By.cssSelector(".Search__title"));
			
			WebElement el3= driver.findElement(By.cssSelector(".col-xs-12.col-sm-8.col-md-9.col-lg-9.col-xl-10"));
			ArrayList<WebElement> sections=(ArrayList<WebElement>) el3.findElements(By.tagName("section"));
			
			String nootFound= "No results found for";
			String noInput= "Search Spotify";
			String text = el.getText();
			if(text.contains(nootFound)||text.contains(noInput)||sections.size()>0) {
				return true;
			}else {
				return false;
			}
	}
//		
//		
//	}
//	public boolean noSongsToAdd() {
//		try {
//			el= driver.findElement(By.cssSelector(".tracklist-col.name"));
//		} catch (NoSuchElementException e) {
//				return true;
//		}
//		return false;
//	}
	
//	public int trySearch(int i) {
//	
//			if(songNotFound()==true) {
//				return i++;
//			}else {
//				return i;
//			}
//	
//	}
	
	public void addingSong(String album) throws Exception {
		hold(2000);
		WebElement el0= driver.findElement(By.cssSelector(".Search__content"));
		
		WebElement el1= el0.findElement(By.cssSelector(".row.SearchOverviewContainer__top-result-section"));
		hoverToEnable(el1);
		//section[class='tracklist-container tracklist-container--rendering-bug-hack-2019-07-08']
//
		WebElement el2= el1.findElement(By.cssSelector("ol[class='tracklist']"));
		WebElement  el3=el2.findElement(By.cssSelector(".tracklist-col.name"));
		hoverToEnable(el3);
		click(el3);	
		//enable action button
		WebElement el4=driver.findElement(By.cssSelector(".btn.btn-transparent.btn--narrow.btn--no-margin.btn--no-padding"));
		//hovering to action button
		
		hoverToEnable(el4);
	
		
		click(el4);
		try {

			el =driver.findElement(By.cssSelector("nav[class*='react-contextmenu react-contextmenu']"));
			
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			click(el4);
			
		}
		hold(500);
		
		
		

		el =driver.findElement(By.cssSelector("nav[class*='react-contextmenu react-contextmenu']"));
		ArrayList<WebElement> list = (ArrayList<WebElement>)el.findElements(By.cssSelector("div[class='react-contextmenu-item']"));
		el=list.get(3);
		//opens add to playlist menu
		click(el);
		el =driver.findElement(By.cssSelector(".dialog.dialog--without-background"));
		ArrayList<WebElement> els= (ArrayList<WebElement>) driver.findElements(By.cssSelector(".mo-coverArt-hoverContainer"));
//				el.findElements(By.cssSelector(".cover-art.shadow.cover-art--with-auto-height"));
				
		
		for(int i=0;i<els.size();i++) {
			if(els.get(i).findElement(By.cssSelector("div[title='"+album+"']")).isDisplayed()) {
				click(els.get(i));
				break;
			}
			
		}
//		move(400, 500);
//		clickMouse(2);
		try {
		WebElement pop=	driver.findElement(By.cssSelector(".autoplay-modal-container__modal.autoplay-modal--visible"));
		WebElement popClose = pop.findElement(By.cssSelector(".autoplay-modal__close-button"));
			click(popClose);
			
		} catch (NoSuchElementException e) {
			System.out.println("==============");
			System.out.println("closing pop up");
		}
	
	}
	public void popUpMessage(String args) {
		int charsNum = args.length();
		String chars= "=";
		for (int i = 0; i <charsNum; i++) {
			chars= chars+"=";
		}
		
		JOptionPane.showMessageDialog(null, chars+"\n"+args+"\n"+chars);
		
			
			
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
}
	
	
	

