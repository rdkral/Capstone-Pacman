package mikejyg.javaipacman.pacman;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

//Make this class static so that it does not need to be instantiated
public final class clogger {

	public static void outputlog() throws IOException {       
		//Open the file to be written to
		//This file will be found in the Pacman directory
	    FileWriter fileWriter = new FileWriter("log.txt", true);
	    //Get the current time stamp
	    Date date = new Date();
	    //Add the time stamp and data to the file
	    fileWriter.append(	date.toString()
				    		+ "\tState = " + Global.affectiveStateToString() 
				    		+ "\tPleasure = " + Global.p 
				    		+ "\tArousal = " + Global.a 
				    		+ "\tDominance = " + Global.d +"\n");
	    //Close the file
	    fileWriter.close();
	 } 
	
}
