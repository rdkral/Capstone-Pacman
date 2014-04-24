package mikejyg.javaipacman.pacman;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * AffectiveStateAccessor is the base class used for obtaining the raw data from the sensors
 * through a socket connection. Then generating emotional states from the provided values. It
 * is also responsible for signaling when there has been a change to a new emotion. 
 * 
 * This class was not in the original project.
 * 
 * Table for Affective States
 * 1 = engagement
 * 2 = boredom
 * 3 = frustration
 * 4 = meditation
 * 5 = agreement
 * 6 = concentrating
 * 7 = disagreement
 * 8 = interested
 */
public class AffectiveStateAccessor {
	
	/**
	 * Creates a connection to the sensors via socket, then calculates the user state
     * from the values obtained. The socket is then closed, and the games state 
     * variables currState, prevState, newEmotion, and shouldUpdateGhost is updated.
     * 
	 * @throws UnknownHostException
	 */
	public static void getAffectiveState()	throws UnknownHostException	{
		//Define host, socket, and streams.
		InetAddress host = InetAddress.getLocalHost();
		Socket socket;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		try	{
            //	Instantiate Socket
            socket = new Socket(host, 4545);
            //Instantiate output stream
            oos = new ObjectOutputStream(socket.getOutputStream());
            //Use writeObject() to write Request String
            oos.writeObject("read,PAD");
            //Instantiate input stream
            ois = new ObjectInputStream(socket.getInputStream());
            //Convert input stream to string
            String response = ois.readObject().toString();
            //out.println(response);
            //Use string
            //Update Affective State
            Global.affectiveState=calculateState(response);
            
            //close socket
            socket.close();
            //Wait 10 Seconds
            //Thread.sleep(10000);
            clogger.outputlog();
            
            cpcman.currState = Global.affectiveState;
            
            if (cpcman.currState == cpcman.prevState)
            	cpcman.newEmotion = false;
            else
            {
            	cpcman.newEmotion = true;
            	cpcman.prevState = cpcman.currState;
            }
            if(cpcman.isNotFirstState)
            	cpcman.shouldUpdateGhost = true;
            
		}
		catch (Exception ex)	{
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Parses the p, a, and d from the provided string. Then rounds each value to 1 or -1. 
	 * The global "pad values" are set to the rounded values, which are then used to calculate the returned state.
	 * @param data			String containing the raw data from the sensors for the "p a d" values.
	 * @return				An enumerated value between 1-8, which is a numerical representation of a users state.
	 */
	static int calculateState(String data){
		String delims = "[,]";
		double value1=0;
		double value2=0;
		double value3=0;
		
		int p, a, d;
		
		String[] values = data.split(delims);		//split string to access data
		
		value1 = Double.parseDouble(values[2]);		//get value 1 from server string
		value2 = Double.parseDouble(values[3]);		//get value 2 from server string
		value3 = Double.parseDouble(values[4]);		//get value 3 from server string
		
		p = roundPAD(value1);		//round value 1 to 1 or -1
		a = roundPAD(value2);		//round value 2 to 1 or -1
		d = roundPAD(value3);		//round value 3 to 1 or -1
		
		//These values are used for logger
		Global.p = p;
		Global.a = a;
		Global.d = d;
		//--------------------------------
        
		return calculatedPad(p, a, d);	//return calculated PAD value
	}
	
	
	/**
	 * Rounds the provided value to 1 or -1, which ever it is closets to.
	 * @param rawData			Raw value obtained from the sensors.
	 * @return					1 if the value is larger than 0;
	 * 						   -1 otherwise.
	 */
	static int roundPAD(double rawData){
		int rounded;
		if(rawData>0)
			rounded = 1;
		else
			rounded = -1;
		
		return rounded;	//return 1 or -1
	}
	
	
	/**
	 * Calculates the users emotional state from the provided values
	 * @param p			#description here
	 * @param a			#description here
	 * @param d			#description here
	 * @return			1 if p = 1, a = 1, d = 1;
	 * 					2 if p = -1. a = -1, d = 1;
	 * 					3 if p = -1. a = 1, d = -1;
	 * 					4 if p = 1, a = -1, d = 1;
	 * 					5 if p = 1, a = -1, d = -1;
	 * 					6 if p = -1, a = -1, d = -1;
	 * 					7 if p = -1, a = 1, d = 1;
	 * 					8 if p = 1, a = 1, d = -1;
	 * 					0 otherwise.
	 */
	static int calculatedPad(int p, int a, int d){
		switch (p){
            case 1:	if(a == 1){
                if(d == 1){
                	//if(cpcman.engagementEnable)
                		return 1;	//P=1,A=1,D=1
                }
                //if(cpcman.interestedEnable)
                	return 8;		//P=1,A=1,D=-1
            }
				
				if(d == 1){
				//	if(cpcman.engagementEnable)
						return 4;		//P=1,A=-1,D=1
				}
				//if(cpcman.agreementEnable)
					return 5;			//P=1,A=-1,D=-1
            case -1: if(a == 1){
                if(d == 1){
                	//if(cpcman.disagreementEnable)
                		return 7;	//P=-1,A=1,D=1
                }
                //if(cpcman.frustrationEnable)
                	return 3;		//P=-1,A=1,D=-1
            }
				
				if(d == 1){
				//	if(cpcman.boredomEnable)
						return 2;		//P=-1,A=-1,D=1
				}
				//if(cpcman.concentratingEnable)
					return 6;			//P=-1,A=-1,D-1
		}
		
		return 0;	//return value from 1-8 to represent affective state (see table above)
	}
    
}