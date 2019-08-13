package Main;

import java.util.ArrayList;

import DB.DTO;

public class GreenRoom
{
	private static GreenRoom getGreenr = null;

	public static GreenRoom getGreenroom()
	{
		if (getGreenr == null)
		{
			getGreenr = new GreenRoom();
		}
		return getGreenr;
	}

	private ArrayList<DTO> LoginAccInfo = null;
	private ArrayList<WithClient> LoginAccClient = null;
	private Room[] roomList = new Room[3];
	
	String roomInfo = "";

	private GreenRoom()
	{
		LoginAccInfo = new ArrayList<>();
		LoginAccClient = new ArrayList<>();
	}

	public void updateInfo()
	{
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				try
				{
					
					roomInfo = "/green ";
					for (int i = 0; i < roomList.length; i++)
					{
						if (roomList[i]!=null)
						{
							roomInfo += roomList[i].getId(0)+ " " + "null" + " " + roomList[i].getId(1)+ " " + "null ";
						} else {
							roomInfo += "null"+ " null" + " null"+ " null ";
						}
					}
					if (LoginAccInfo.size()>0)
					{
						for (int j = 0; j < LoginAccInfo.size(); j++)
						{
							roomInfo += LoginAccInfo.get(j).getId()+" ";
						}
					}
					
					roomInfo += "/end";

					Thread.sleep(100);
					for (int i = 0; i < LoginAccClient.size(); i++)
					{
						LoginAccClient.get(i).sendMsg(roomInfo);
					}
					System.out.println(roomInfo);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	public void addLoginAcc(DTO dto, WithClient accClient)
	{
		LoginAccInfo.add(dto);
		LoginAccClient.add(accClient);
		updateInfo();
	}

	public Room joinRoom(int roomNum, WithClient accClient)
	{

		if (roomList[roomNum] != null)
		{
			if (roomList[roomNum].chkEmpty(0))
			{
				System.out.println("check1");
				roomList[roomNum].setGuests(accClient, 0);
				
			} else if (roomList[roomNum].chkEmpty(1))
			{
				System.out.println("check2");
				roomList[roomNum].setGuests(accClient, 1);
			}
		} else
		{
			System.out.println("check3");
			roomList[roomNum] = new Room();
			roomList[roomNum].setGuests(accClient, 0);
		}

		updateInfo();
		return roomList[roomNum];
	}

	public void sendAllClient(String msg)
	{
		for (int i = 0; i < LoginAccClient.size(); i++)
		{
			LoginAccClient.get(i).sendMsg(msg);
		}
	}

	public void logout(String clMsg, WithClient me, DTO dto)
	{
		LoginAccInfo.remove(dto);
		LoginAccClient.remove(me);
		updateInfo();
		me.sendMsg("/logout access /end");
	}
}