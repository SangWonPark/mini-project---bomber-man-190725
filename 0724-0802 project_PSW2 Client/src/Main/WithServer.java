package Main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import GUI_Client.ClientRoom;
import GUI_Client.GameP;
import GUI_Client.GreenRoom;
import GUI_Client.loginFrame;
import GameObject.Box;
import GameObject.Player;

public class WithServer extends Thread
{
	private WithServer me = null;

	private GameP gp = null;

	private loginFrame loginF = null;
	private GreenRoom gr = null;
	private ClientRoom cr = null;

	private Socket Server = null;
	private InputStream InS = null;
	private OutputStream OutS = null;
	private BufferedInputStream buffInS = null;
	private BufferedOutputStream buffOutS = null;

	private Socket objRe = null;
	private InputStream oInS = null;
	private BufferedInputStream buffoInS = null;
	private ObjectInputStream objInS = null;

	String receiveMsg = "";

	public WithServer(Socket server, Socket objServer)
	{
		this.Server = server;
		this.objRe = objServer;
		me = this;
	}

	@Override
	public void run()
	{
		init();
		this.loginF = new loginFrame(this);
		reaceiveMsg();
	}

	private void init()
	{
		try
		{
			InS = Server.getInputStream();
			OutS = Server.getOutputStream();
			buffInS = new BufferedInputStream(InS);
			buffOutS = new BufferedOutputStream(OutS);

			Thread.sleep(500);
			oInS = objRe.getInputStream();
			buffoInS = new BufferedInputStream(oInS);
			objInS = new ObjectInputStream(oInS);

		} catch (IOException | InterruptedException e)
		{
			System.out.println("disconnected");
		}

	}

	public void objReceive()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				Object o = null;
				try
				{
					while (gp != null)
					{
						o = objInS.readObject();
//						System.out.println("o = objInS.readObject() :" + o);
						Box receBox = (Box) o;
						ArrayList<Player> pList = receBox.getSendData();
						gp.setPlayerObj(pList);
					}
				} catch (ClassNotFoundException e)
				{
					System.out.println("ClassNotFoundException");
					System.out.println(e.getMessage() + "/" + o);
					e.printStackTrace();
				} catch (StreamCorruptedException e) {
					System.out.println("StreamCorruptedException ::"+e.getMessage());
					e.printStackTrace();
				} catch (IOException e)
				{
					System.out.println(e);
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void sendMsg(String msg)
	{
		try
		{
			OutS.write(msg.getBytes());
//			System.out.println("send message :" + msg);
		} catch (IOException e)
		{
			System.out.println("disconnected");
//			e.printStackTrace();
		}
	}

	public void reaceiveMsg()
	{

		byte[] getMsg;
		try
		{
			boolean endFlag = false;
			while (!endFlag)
			{
				getMsg = new byte[1024];
				InS.read(getMsg);

				String message = new String(getMsg).trim();
//				System.out.println("receive message :"+message);

				Distributor(message);
				if (message.equals("/logout access /end"))
				{
					endFlag = true;
				}
			}

		} catch (IOException e)
		{
			System.out.println("disconnected");
//			e.printStackTrace();
		}
	}

	public void Distributor(String message)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{

//				System.out.println("receive message :" + message);
				StringTokenizer tokens = new StringTokenizer(message);

				String commend = tokens.nextToken();

				int endIdx = message.indexOf("/end");

				if (endIdx != -1)
				{
					String msg = message.substring(commend.length() + 1, endIdx - 1);
//					System.out.println(msg);
					if (commend.equals("/login"))
					{
						if (loginF.visibleset(msg))
						{
							gr = new GreenRoom(me);
						}
					} else if (commend.equals("/green"))
					{
						if (gr != null)
						{
							gr.setInfo(msg);
						}
						if (cr != null)
						{
							cr.setInfo(msg);
						}
					} else if (commend.equals("/roomin"))
					{
//						System.out.println("/roomin " + msg);
						String accessTF = tokens.nextToken();
						if (gr.visibleset(accessTF))
						{
							cr = new ClientRoom(me, Integer.valueOf(tokens.nextToken()));
							objReceive();
							
						}
					} else if (commend.equals("/roomout"))
					{
						if (cr.visibileset(msg))
						{
//							System.out.println(message);
							gr.visibleset(msg);
							gp = null;
							cr = null;
							
							try
							{
								objInS.close();
								buffoInS.close();
								oInS.close();
								objRe.close();
								Thread.sleep(300);
								objRe = new Socket("10.0.0.111",6666);
								oInS = objRe.getInputStream();
								buffoInS = new BufferedInputStream(oInS);
								objInS = new ObjectInputStream(oInS);
								
							} catch (IOException | InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					} else if (commend.equals("/all"))
					{
						gr.chat(msg);
					} else if (commend.equals("/logout"))
					{
						logoutP(msg);
						me = null;
					} else if (commend.equals("/gamend"))
					{
						System.out.println("gamend :" + msg);
						cr.gamEnd(msg);
					} else if (commend.equals("/game"))
					{
						System.out.println("/game" + message);
						cr.gamemsg(msg);
					}
				}
			}
		}).start();
	}

	private void logoutP(String msg)
	{
		gr.visibleset(msg);
		removeAllInfo();
	}

	public void setGP(GameP GP)
	{
		this.gp = GP;
	}

	private void removeAllInfo()
	{
		me = null;

		loginF = null;
		gr = null;
		cr = null;

		Server = null;
		InS = null;
		OutS = null;
		buffInS = null;
		buffOutS = null;

		receiveMsg = "";
	}
}
