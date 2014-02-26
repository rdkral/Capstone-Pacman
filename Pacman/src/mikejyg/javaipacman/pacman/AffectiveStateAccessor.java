package mikejyg.javaipacman.pacman;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class AffectiveStateAccessor {
		public static void main(String[] args)	throws UnknownHostException	{
			//Define host, socket, and streams.
			InetAddress host = InetAddress.getLocalHost();
			Socket socket;
			ObjectOutputStream oos;
			ObjectInputStream ois;
			try	{
				while(true)	{
					//	Instantiate Socket
					socket = new Socket(host, 4545);
					//Instantiate output stream
					oos = new ObjectOutputStream(socket.getOutputStream());
					//Use writeObject() to write Request String
					oos.writeObject("read, PAD");
					//Instantiate input stream
					ois = new ObjectInputStream(socket.getInputStream());
					//Convert input stream to string
					String response = ois.readObject().toString();
					
					
					//Use string
				
					//Wait 10 Seconds
					
				}
			}
			catch (Exception ex)	{
				ex.printStackTrace();
			}
		}
}
