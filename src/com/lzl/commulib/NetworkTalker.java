import java.net.*;
import org.json.*;

public class NetworkTalker{
	private static String address="127.0.0.1";
	private int port;
	private DatagramSocket socket;

	public NetworkTalker(int port){
		this.port=port;
		socket=new DatagramSocket(this.port);
		
	}

	public byte[] nextMsg{ //Waits for next message
		
	}
}
