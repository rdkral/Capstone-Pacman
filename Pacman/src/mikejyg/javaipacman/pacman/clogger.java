package mikejyg.javaipacman.pacman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

//Make this class static so that it does not need to be instantiated
public final class clogger {
	
	static boolean first;

	public static void outputlog() throws IOException {   
		//Check if the file exists
		File f = new File("log.csv");
		if(f.exists() && !f.isDirectory()) {
			first = false;
		} else {
			first = true;
		}
		
		//Open the file to be written to
		//This file will be found in the Pacman directory
	    FileWriter fileWriter = new FileWriter("log.csv", true);
	    //Get the current time stamp
	    Date date = new Date();
	    if(first) {
	    	fileWriter.append(	"Timestamp,State,Pleasure,Arousal,Dominance,Lives Remaining,Dots Remaining,Score,Number of Ghosts,Song\n");
	    	first = false;
	    }
	    //Add the time stamp and data to the file
	    fileWriter.append(	date.toString()
				    		+ "," + Global.affectiveStateToString() 
				    		+ "," + Global.p 
				    		+ "," + Global.a 
				    		+ "," + Global.d 
				    		+ "," + Global.lives
				    		+ "," + Global.dots_remaining
				    		+ "," + Global.score
				    		+ "," + Global.num_of_ghosts
				    		+ "," + Global.cur_song + "\n");
	    //Close the file
	    fileWriter.close();
	} 
	
	public static void outputdeath() throws IOException {   		
		//Open the file to be written to
		//This file will be found in the Pacman directory
	    FileWriter fileWriter = new FileWriter("log.csv", true);
	    //Get the current time stamp
	    Date date = new Date();
	    //Add the time stamp and data to the file
	    fileWriter.append(	date.toString() + "," + "User Death" + "\n");
	    //Close the file
	    fileWriter.close();
	} 
	
}
