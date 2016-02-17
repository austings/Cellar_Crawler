/* 
 * Cellar.java 
 * 
 * Version: 
 *     $Id: Cellar.java,v 1.2 2014/12/01 00:54:25 trs5953 Exp $ 
 * 
 * Revisions: 
 *     $Log: Cellar.java,v $
 *     Revision 1.2  2014/12/01 00:54:25  trs5953
 *     Project is finished. Bug testing may continue.
 *
 *     Revision 1.5  2014/12/01 00:51:12  ags1098
 *     finished most of project. just bug testing and making sure all is well
 *
 *     Revision 1.4  2014/11/29 03:37:54  ags1098
 *     GUI is finished and began work on communication between model and gui
 *
 *     Revision 1.1  2014/11/29 00:26:52  trs5953
 *     Model is implemented. GUI needs to be finished so game can be played.
 *
 *     Revision 1.3  2014/11/27 03:07:17  ags1098
 *     Worked on Model
 *
 *     Revision 1.2  2014/11/27 00:56:19  ags1098
 *     halfway done with reading from config, graph completed. 
 *     Basic getters and setters for each class are done.
 *
 *     Revision 1.1  2014/11/26 23:36:31  ags1098
 *     *** empty log message ***
 * 
 */

package project;

import java.io.File;
import java.io.IOException;

/**
 * This is the main class for the Cellar crawler game project
 * 
 * @author Austin Sierra
 * @author Tyler Shank
 *
 */
public class Cellar
{
	private static GUIInterface run;
	private static Model model;
	/**
	 * run method to call other classes
	 * 
	 * @param config - the arguments sent from command line
	 * @throws IOException
	 */
	public void run(String[] config) throws IOException
	{
		File f = new File(config[0]);
		if(config.length != 1 || !f.exists()){
			System.err.println("Usage: java Cellar config_file_name");
			System.exit(0);
		}
		model = new Model(f);
		run = new GUIInterface(model);
	}
	
	/**
	 * Main method to run program
	 * 
	 * @param args- should send config_file_name
	 * @throws IOException 
	 */
	public static void main(String[]args) throws IOException
	{
		Cellar cellar = new Cellar();
		//check array for errors before sending
		cellar.run(args);
	}
	
	/**
	 * This method is called when an end game condition has been met.
	 * @param hasWon if the player won or lost
	 */
	public static void endGame(Boolean hasWon){
		if(hasWon){
			run.winPopup();
		}
		else{
			run.losePopup();
		}
	}
}
