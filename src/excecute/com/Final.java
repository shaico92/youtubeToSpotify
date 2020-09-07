package excecute.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import skeleton.ocm.Spotify;
import skeleton.ocm.Youtube;

public class Final {
	

	
	public static void main(String [] args) throws Exception{
	
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document docRoot= builder.parse("RootConfig.xml");
			NodeList paths= docRoot.getElementsByTagName("root");
			Element elmetns= (Element) paths.item(0);
			String rootPath = elmetns.getElementsByTagName("configPath").item(0).getTextContent();
			//youtubeDetails
			String webPageY;
			String userYou;
			String passYou;
			String listYou;
			//spotifyDetails
			String webPageSpo;
			String userSpo;
			String passSpo;
			String listSpo;
			
			try {
				Document document= builder.parse(rootPath+"\\ExtractSongsAndExportConfig.xml");
				NodeList list = document.getElementsByTagName("root");
				Element e =(Element) list.item(0);
				
				 webPageY= e.getElementsByTagName("link").item(0).getTextContent();
				
				 userYou=e.getElementsByTagName("username").item(0).getTextContent();
				
				 passYou=e.getElementsByTagName("password").item(0).getTextContent();
				
				 listYou=e.getElementsByTagName("folder").item(0).getTextContent();
				
				 
				 
				 
				 

				 	 webPageSpo= e.getElementsByTagName("link").item(1).getTextContent();;
					 userSpo=e.getElementsByTagName("username").item(1).getTextContent();;
					 passSpo=e.getElementsByTagName("password").item(1).getTextContent();;
					 listSpo=e.getElementsByTagName("folder").item(1).getTextContent();;
				


				
				
				
				
				
				
				
				
				
				Youtube youtube = new Youtube();
				youtube.goToWebSite(webPageY);
				//youtube.signIn(userYou,passYou);
				Thread.sleep(1500);
				youtube.getSongsFromFolder(listYou);
				
				String table= "newTable";
				String db= "albums";
				youtube.insertData(table, db);
				//youtube.quit();
			
			
				
			
				
			
				Spotify spotify= new Spotify();
				spotify.goToWebSite(webPageSpo);
				
				spotify.login(userSpo, passSpo);
				Thread.sleep(3000);
				spotify.createPlaylist(listSpo);
				Thread.sleep(2000);
			//spotify.addToPlaylis(listSpo, table, db);
			spotify.addToPlaylis(listSpo);
	
			spotify.quit();
			spotify.popUpMessage("the application has finished please check logs"+"\n"+spotify.logPath());
			
			} catch (IOException e) {
				System.out.println();
				System.out.println("failed to find config path, please make sure that '<path>' node in RootConfig has a valid value\r\r");
				
				System.out.println();
				System.out.println(e.getMessage());
				System.exit(0);
			}
			
			
		

	}

}