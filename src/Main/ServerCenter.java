package Main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerCenter
{

	ServerSocket ss = null;

	ServerCenter()
	{
		connAcc();
	}

	private void connAcc()
	{
		try
		{

			ss = new ServerSocket();
			ss.bind(new InetSocketAddress("10.0.0.111", 7777));

			while (true)
			{

				Socket nextClient = ss.accept();
				Socket objClient = ss.accept();
				WithClient Client = new WithClient(nextClient, objClient);
				Client.start();

			}

		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (ss != null)
					ss.close();
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}
}
