package mikejyg.javaipacman.pacman;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

//Make this class static so that it does not need to be instantiated
public final class clogger {
	
	static boolean first = true;

	public static void outputlog() throws IOException {       
		//Open the file to be written to
		//This file will be found in the Pacman directory
	    FileWriter fileWriter = new FileWriter("log.csv", true);
	    //Get the current time stamp
	    Date date = new Date();
	    if(first) {
	    	fileWriter.append(	"Timestamp,State,Pleasure,Arousal,Dominance,Score,Number of Ghosts\n");
	    	first = false;
	    }
	    //Add the time stamp and data to the file
	    fileWriter.append(	date.toString()
				    		+ "," + Global.affectiveStateToString() 
				    		+ "," + Global.p 
				    		+ "," + Global.a 
				    		+ "," + Global.d 
				    		+ "," + Global.score
				    		+ "," + Global.num_of_ghosts +"\n");
	    //Close the file
	    fileWriter.close();
	 } 
	
}
