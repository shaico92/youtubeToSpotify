package skeleton.ocm;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.vdurmont.emoji.EmojiParser;

public class Youtube extends BasicMethods{
	public 	WebElement el;
	public 	static Connection conn;
	public static	ArrayList<String> playList= new ArrayList<String>();
		
	public Youtube() throws ParserConfigurationException, IOException, Exception {
		super();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public int getFolderSize() {
		el= driver.findElement(By.cssSelector("div[id='stats']"));
		el= el.findElement(By.tagName("yt-formatted-string"));
		String s= el.getText();
		if(s.contains(" videos")) {
			s= s.replace(" videos", "");	
		}else if(s.contains(" video")) {
			s= s.replace(" video", "");
		}
		
		int res = Integer.parseInt(s);
		return res;
	}
	public WebElement clickSideMenu() throws Exception{
		el= driver.findElement(By.cssSelector("yt-icon[id='guide-icon']"));
		return el;
	}
public Connection CreateDB(String fileName) {
	Connection conn=null;
		
		
		try {	
			String url= getSQLiteDBTagVal()+fileName+".db";
			 conn= DriverManager.getConnection(url);
			if(conn!=null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("the driver name is "+meta.getDriverName());
				System.out.println("a new database has been created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				return conn;
				
	}
	public void findDesiredPlayList(String text) throws Exception {
			try {
				el= clickSideMenu();
				click(el);
			}catch(NoSuchElementException e){
				e.getLocalizedMessage();
			}
		Thread.sleep(1000);
		showMorePlaylist();
		Thread.sleep(1000);
		
			el=driver.findElement(By.cssSelector("ytd-guide-renderer[id='guide-renderer']"));
			ArrayList<WebElement> libs= (ArrayList<WebElement>)el.findElements(By.cssSelector(".title.style-scope.ytd-guide-entry-renderer"));
			for(WebElement e: libs) {
				if(e.getText().equals(text)) {
					click(e);
					System.out.println("clicking "+e.getText());
					break;
				}
				
			}	
		
				
			}
	

		
		
		
		
	
	public void signIn(String username, String password) throws Exception {
	el=driver.findElement(By.cssSelector("paper-button[aria-label='Sign in']"));
	click(el);
	el=driver.findElement(By.cssSelector("#identifierId"));
	fillText(el, username);
	el=driver.findElement(By.cssSelector("#yDmH0d"));
	click(el);
	Thread.sleep(1000);
	try {
		el=driver.findElement(By.cssSelector("input[type='password']"));
		fillText(el, password);
	}catch (NoSuchElementException e){
		System.out.println("no password field");
	}


	el=driver.findElement(By.cssSelector(".VfPpkd-RLmnJb"));
	click(el);
	}
	

	
	public void getSongsFromFolder(String folder) throws Exception {
		findDesiredPlayList(folder);
		System.out.println(getFolderSize());
	collectSongsTitle1();
		
		
		
	}
	
	
	public void addSongsFromPlayList(String songsFolder) throws Exception {
		
		findDesiredPlayList(songsFolder);
	ArrayList<WebElement> songs=(ArrayList<WebElement>)	driver.findElements(By.cssSelector("#video-title"));

		for(int i=0;i<songs.size();i++) {
			el=songs.get(i);
			String songTitle=el.getText();
			this.playList.add(songTitle);
			
		}
	}
	
	public static ArrayList<String> getPlayList(){
		
		return playList;
	}
public static ArrayList<String> getPlayList(ArrayList<String> list){
		list=playList;
		return list;
	}
	public void printList(ArrayList<String> list) {
		for(int i=0;i<list.size();i++) {
			String song = list.get(i);
			System.out.println("song name: "+ song);
		}
}
	
	
	public void showMorePlaylist() {
		ArrayList<WebElement> list =(ArrayList<WebElement>) driver.findElements(By.cssSelector(".title.style-scope.ytd-guide-entry-renderer"));
		for(WebElement e: list) {
			if(e.getText().equals("Show more")) {
				click(e);
			}
		}
		
	}
public  Connection   CreateDBMethod(String fileName) {
	
		
		conn=null;
		try {
			String url= getSQLiteDBTagVal()+fileName+".db";
			conn= DriverManager.getConnection(url);
			if(conn!=null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("the driver name is "+meta.getDriverName());
				System.out.println("a new database has been created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		return conn;		
	}
public  Connection createTable(String tableName,String db) {
	
	  String sql = "CREATE TABLE IF NOT EXISTS "+ tableName +" (\n"
              + "    id integer PRIMARY KEY,\n"
              + "    singer text NOT NULL,\n"
              + "    song text NOT NULL\n"
              + ");";
	  try {
		 conn= CreateDBMethod(db);
		Statement stm= conn.createStatement();
	
		stm.execute(sql);
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.getMessage());
	}
	  return conn;

}
public  void insertData(String table, String db) {
	
	 try {
		conn= createTable(table,db);
		 Statement stmn = conn.createStatement();
			
		for(int i=0;i<playList.size();i++) {
			int num=i+1;
			String singer= cutString(playList.get(i), '-');
			String song= playList.get(i).substring(playList.get(i).indexOf('-') + 1);

			stmn.execute("INSERT INTO '"+table +"' ('id','singer','song')\r\n" + 
					"VALUES('"+num+"','"+singer+"','"+song+"');");
		}
		

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
}
	public ArrayList<String> collectSongsTitle() {
		
	ArrayList<WebElement>elms=(ArrayList<WebElement>)driver.findElements(By.cssSelector(".yt-simple-endpoint.style-scope.ytd-playlist-video-renderer"));
	for(int i=0;i<elms.size();i++) {
		el= elms.get(i);
		WebElement temp= el.findElement(By.cssSelector("#video-title"));
		String title=temp.getAttribute("title");
		
		if(title.contains(",")||title.contains("(")||title.contains("[")||title.contains("}")||title.contains("{")||title.contains("|")||title.contains(")")) {
			title= cutString(title, ',');
			title= cutString(title, '}');
			title= cutString(title, '{');
			title= cutString(title, '|');
			title= cutString(title, '[');
	
			title= cutString(title, '(');
			title= cutString(title, ')');
		}
		title = title.replace("'", "");
		playList.add(title);
	}
return playList;
	}
	
	public ArrayList<String> collectSongsTitle1() {
		
		ArrayList<WebElement>elms=(ArrayList<WebElement>)driver.findElements(By.cssSelector(".yt-simple-endpoint.style-scope.ytd-playlist-video-renderer"));
		while (elms.size()<getFolderSize()) {
			scrolldDown(10000);
			elms=(ArrayList<WebElement>)driver.findElements(By.cssSelector(".yt-simple-endpoint.style-scope.ytd-playlist-video-renderer"));
			if(elms.size()==getFolderSize()) {
				break;
			}
		}
		for(int i=0;i<elms.size();i++) {
			
			el= elms.get(i);
			WebElement temp= el.findElement(By.cssSelector("#video-title"));
			String title=temp.getAttribute("title");
			
			if(title.contains(",")||title.contains("(")||title.contains("[")||title.contains("}")||title.contains("{")||title.contains("|")||title.contains(")")) {
				title= cutString(title, ',');
				title= cutString(title, '}');
				title= cutString(title, '{');
				title= cutString(title, '|');
				title= cutString(title, '[');
		
				title= cutString(title, '(');
				title= cutString(title, ')');
			}
			title = title.replace("'", "");
			title = EmojiParser.removeAllEmojis(title);
			playList.add(title);
		}
	return playList;
		}
	public  String cutString(String string, char symbol) {
		String output="";
		for(int i=0; i<string.length();i++) {
			
			char j = string.charAt(i);
			
			if(string.charAt(i)==symbol) {
				
				return output;
			}
			output=output+j+"";
		
			
		}
		
		return output;
	}

	
}