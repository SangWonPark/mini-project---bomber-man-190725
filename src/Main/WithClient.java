package Main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import DB.DTO;
import GameObject.Box;
import GameObject.Player;
import Util.GameU;
import Util.Util;

public class WithClient extends Thread
{
	
	private ServerSocket SS = null;

	private Socket client = null;
	private InputStream inS = null;
	private OutputStream outS = null;
	private BufferedInputStream buffInS = null;
	private BufferedOutputStream buffOutS = null;
	
	private Socket objClient = null;
	private OutputStream oOutS = null;
	private BufferedOutputStream buffoOutS = null;
	private ObjectOutputStream objOutS = null;
	
	private Util getutil = null;

	private String id = null;

	private DTO dto = null;
	private GreenRoom gr = null;
	private Room myroom = null;
	private GameU gu = null;

	private WithClient me = this;

	public WithClient(Socket nextClient, Socket objC)
	{
		this.client = nextClient;
		this.objClient = objC;
		gr = GreenRoom.getGreenroom();
		getutil = new Util();

	}

	@Override
	public void run()
	{
		init();
		receiveMsg();
	}

	private void init()
	{
		try
		{

			byte[] getMsg = new byte[1024];

			dto = new DTO(client);
			inS = client.getInputStream();
			outS = client.getOutputStream();
			buffInS = new BufferedInputStream(inS);
			buffOutS = new BufferedOutputStream(outS);
			
			oOutS = objClient.getOutputStream();
			buffoOutS = new BufferedOutputStream(oOutS);
			objOutS = new ObjectOutputStream(oOutS);
			
			boolean loginFlag = false;

			while (!loginFlag)
			{
				inS.read(getMsg);
				String msg = new String(getMsg).trim();
				System.out.println(msg);
				loginFlag = getutil.accChk(msg, dto);
				id = msg.substring(msg.indexOf(" "), msg.indexOf(" ", msg.indexOf(" ") + 1));
				System.out.println(id);
				if (!loginFlag)
				{
					outS.write("/login access denied /end".getBytes());
				}
			}

			gr.addLoginAcc(dto, me);
			outS.write("/login access /end".getBytes());
//			gr.updateInfo();
		} catch (IOException e)
		{
			System.out.println("disconnected");
//			e.printStackTrace();
		}
	}
	
	public void objSend(Object pList) {
		
		try
		{
			if (gu!=null)
			{
				Box sendBox = new Box(pList);
//				System.out.println(objOutS+"/ sendObj :"+sendBox);
				objOutS.writeObject(sendBox);
				objOutS.reset();
			}
		} catch (IOException e)
		{
			System.out.println("objsend disconnected");
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String msg)
	{
		try
		{
//			System.out.println("send message :" +msg);
			outS.write(msg.getBytes());
		} catch (IOException e)
		{
			System.out.println("send msg disconnected");
//			e.printStackTrace();
		}
	}

	private void receiveMsg()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				byte[] getMsg;
				boolean endFlag = false;
				getMsg = new byte[1024];
				try
				{
					while (!endFlag)
					{

						if (inS == null)
							endFlag = true;
						inS.read(getMsg);
						String clMsg = new String(getMsg).trim();

						Distributor(clMsg);

//						System.out.println("receive message :"+clMsg);
					}

				} catch (IOException e)
				{
					System.out.println("disconnected");
//					e.printStackTrace();
				}

			}
		}).start();
	}

	private void Distributor(String clMsg)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				StringTokenizer tokens = new StringTokenizer(clMsg);

				String nextStep = tokens.nextToken();

				String comm = clMsg.substring(nextStep.length() + 1);

				try
				{
					if (nextStep.equals("/roomnum"))
					{
						joinroom(comm);
						
					} else if (nextStep.equals("/to"))
					{
						WithClient[] roommate = myroom.getGuests();
						for (int i = 0; i < roommate.length; i++)
						{
							if (!roommate[i].equals(me))
							{
								roommate[i].sendMsg(comm);
							}
						}
					} else if (nextStep.equals("/outroom"))
					{
						System.out.println(clMsg);
						gotogreenroom();
						
						objOutS.close();
						buffoOutS.close();
						oOutS.close();
						objClient.close();
						objClient =  SS.accept();
						oOutS = objClient.getOutputStream();
						buffoOutS = new BufferedOutputStream(oOutS);
						objOutS = new ObjectOutputStream(oOutS);
					} else if (nextStep.equals("/all"))
					{
						gr.sendAllClient(clMsg);
					} else if (nextStep.equals("/logout"))
					{
						gr.logout(clMsg, me, dto);
						inS.close();
						outS.close();
						client.close();
						removeallinfo();
						me = null;
					} else if (nextStep.equals("/mov"))
					{
						movset(tokens.nextToken());
					} else if (nextStep.equals("/game"))
					{
						String str = tokens.nextToken();
						System.out.println("str :"+str);
						System.out.println("comm :"+comm);
						myroom.gameset(str, id);
					}
				} catch (Exception e)
				{
					System.out.println("disconnected Distributor");
//					e.printStackTrace();
				}

			}
		}).start();
	}

	private void gotogreenroom()
	{
		System.out.println("outroom(gotogreenroom)");
		if (myroom.outroom(me))
		{
			sendMsg("/roomout access /end");
			gu = null;
			myroom = null;
			gr.updateInfo();
		} else
		{
			sendMsg("/roomout access denied /end");
		}
	}

	private void joinroom(String comm)
	{
		System.out.println("room select :"+comm);
		try
		{
			myroom = getutil.selRoom(comm, me);
			if (myroom != null)
			{
				sendMsg(("/roomin access " + comm + " /end"));
			} else
			{
				sendMsg("/roomin access denied /end");
			}
		} catch (Exception e)
		{
			System.out.println("joinroom disconnected :"+e);
			e.printStackTrace();
		}
	}

	public Room getMyroom()
	{
		return myroom;
	}

	public void movset(String movcomm)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				int xcal = 0;
				int ycal = 0;
				char d = 's';
				String getkey = movcomm;
				switch (getkey)
				{
				case "37":
					xcal -= 3;
					d = 'w';
					break;
				case "38":
					ycal -= 3;
					d = 'n';
					break;
				case "39":
					xcal += 3;
					d = 'e';
					break;
				case "40":
					ycal += 3;
					d = 's';
					break;
				case "32":// ÆøÅº
					gu.setBomb(id);
				}
				gu.setPleaceInfo(xcal, ycal, d, id);
			}
		}).start();
	}

	public String getID()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	private void removeallinfo()
	{
		if (myroom != null)
			myroom.outroom(me);
		if (gr != null)
			gr.logout("", me, dto);
		client = null;
		inS = null;
		outS = null;
		buffInS = null;
		buffOutS = null;
		getutil = null;
		id = null;
		dto = null;
		gr = null;
		myroom = null;
		gu = null;
		me = null;
	}

	public void setGameU(GameU gameU)
	{
		this.gu = gameU;
	}
}
