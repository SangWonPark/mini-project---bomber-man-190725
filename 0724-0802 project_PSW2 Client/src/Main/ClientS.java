package Main;

import java.io.IOException;
import java.net.Socket;

import GUI_Client.loginFrame;

public class ClientS
{
	Socket server = null;
	Socket objServer = null;
	
	ClientS()
	{
		try
		{
			server = new Socket("10.0.0.111",7777);
			objServer = new Socket("10.0.0.111",7777);
			WithServer me = new WithServer(server,objServer);
			
			me.start();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
