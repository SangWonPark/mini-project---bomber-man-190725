package Main;

import Util.GameU;

public class Room
{
	private WithClient[] guests = new WithClient[2];
	private String[] id = new String[2];
	private boolean[] readyFlag = new boolean[2];

	private GameU game = null;

	public WithClient[] getGuests()
	{
		return guests;
	}

	public void setGuests(WithClient guest, int idx)
	{
		synchronized (this)
		{
			if (guests[idx] == null)
			{
				System.out.println("setGuest");
				this.guests[idx] = guest;
				this.id[idx] = guest.getID();
			}
		}
	}

	public boolean chkEmpty(int idx)
	{

		if (guests[idx] == null)
		{
			return true;
		}

		return false;
	}

	public String getId(int i)
	{
		return id[i];
	}

	public boolean outroom(WithClient me)
	{
		if (guests[0]!=null)
		{
			if (guests[0].equals(me))
			{
				if (guests[0].equals(me))
				{
					guests[0] = null;
					id[0] = null;
					readyFlag[0] = false;
					chkGuest();
				}
			} 
		}
		else if (guests[1]!=null)
		{
			if (guests[1].equals(me))
			{
				if (guests[1].equals(me))
				{
					guests[1] = null;
					id[1] = null;
					readyFlag[1] = false;
					if (game != null)
					{
						game.endgame(false);
					}
				}
			}
		}
		return true;
	}

	public void chkGuest()
	{
		if (readyFlag[0] & readyFlag[1])
		{
			System.out.println("start");
			game = new GameU(guests, id, this);
			return;
		} else
		{
			if (game != null)
			{
				game.endgame(false);
				game = null;
			}
		}
		System.out.println("stop");
	}

	public void gameset(String msg, String id)
	{
		System.out.println("gameset msg = " + msg);
		if (msg.equals("prep"))
		{
			if (this.id[0].equals(id) & !readyFlag[0])
			{
//				System.out.println("this.id[0].equals(id) :" + readyFlag[0]);
				readyFlag[0] = true;
//				System.out.println("goto chkGuest()");
				chkGuest();
			} else if (this.id[1].equals(id) & !readyFlag[1])
			{
//				System.out.println("this.id[1].equals(id) :" + readyFlag[1]);
				readyFlag[1] = true;
//				System.out.println("goto chkGuest()");
				chkGuest();
			}
		}
	}

	public void removeGU()
	{
		game.endgame(false);
		game = null;
		readyFlag = new boolean[2];
	}

}
