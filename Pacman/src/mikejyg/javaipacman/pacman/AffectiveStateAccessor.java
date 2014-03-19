package mikejyg.javaipacman.pacman;


//import static java.lang.System.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/*Table for Affective State
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
            
            //Use string
            //Update Affective State
            Global.affectiveState=calculateState(response);
            
            //close socket
            socket.close();
            //Wait 10 Seconds
            //Thread.sleep(10000);
		}
		catch (Exception ex)	{
			ex.printStackTrace();
		}
	}
	
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
		
        
		return calculatedPad(p, a, d);	//return calculated PAD value
	}
	
	
	
	static int roundPAD(double rawData){
		int rounded;
		if(rawData>0)
			rounded = 1;
		else
			rounded = -1;
		
		return rounded;	//return 1 or -1
	}
	
	
	
	static int calculatedPad(int p, int a, int d){
		switch (p){
            case 1:	if(a == 1){
                if(d == 1){
                    return 1;	//P=1,A=1,D=1
                }
                return 8;		//P=1,A=1,D=-1
            }
				
				if(d == 1){
					return 4;		//P=1,A=-1,D=1
				}
				return 5;			//P=1,A=-1,D=-1
            case -1: if(a == 1){
                if(d == 1){
                    return 7;	//P=-1,A=1,D=1
                }
                return 3;		//P=-1,A=1,D=-1
            }
				
				if(d == 1){
					return 2;		//P=-1,A=-1,D=1
				}
				return 6;			//P=-1,A=-1,D-1
		}
		
		return 0;	//return value from 1-8 to represent affective state (see table above)
	}
    
}






