package Util;

import java.util.ArrayList;

import GameObject.Bomb;
import GameObject.Map;
import GameObject.Player;
import Main.Room;
import Main.WithClient;

public class GameU
{
	private WithClient[] guests = null;
	private String[] id = null;

	private Room gameRoom = null;

	private ArrayList<Player> pList = null;
	private ArrayList<Bomb> AllbombList = null;

	private Map map = null;
	private int[][] selMap = null;

	private boolean startChk = false;

	private int x1 = 45;
	private int y1 = 320;
	private int x2 = 685;
	private int y2 = 320;
	private char d1 = 's';
	private char d2 = 's';

	public GameU(WithClient[] guests2, String[] id2, Room room)
	{
		pList = new ArrayList<>();
		AllbombList = new ArrayList<>();
		guests2[0].sendMsg("/game start /end");
		guests2[1].sendMsg("/game start /end");
		init(guests2, id2, room);
	}

	private void init(WithClient[] guests2, String[] id2, Room room)
	{
		System.out.println("create gameU");
		startChk = true;

		map = new Map();
		this.selMap = map.setMap();

		this.gameRoom = room;
		this.id = id2;
		this.guests = guests2;

		Player p1 = new Player(id[0], x1, y1, selMap, this);
		Player p2 = new Player(id[1], x2, y2, selMap, this);

		this.pList.add(p1);
		this.pList.add(p2);

		guests[0].setGameU(this);
		guests[1].setGameU(this);

		Bomblistener();

		sendAllMovInfo();
	}

	public void setPleaceInfo(int xcal, int ycal, char d, String me)
	{
		if (me.equals(id[0]))
		{
			this.d1 = d;
			int tempx = x1;
			int tempy = y1;
			x1 += xcal;
			y1 += ycal;
			int xidx = (x1 + 15) / 40;
			int yidx = (y1 + 20) / 40;
			if (selMap[yidx][xidx] == 1)
			{
				x1 = tempx;
				y1 = tempy;
			}
		} else if (me.equals(id[1]))
		{
			this.d2 = d;
			int tempx = x2;
			int tempy = y2;
			x2 += xcal;
			y2 += ycal;
			int xidx = (x2 + 15) / 40;
			int yidx = (y2 + 20) / 40;
			if (selMap[yidx][xidx] == 1)
			{
				x2 = tempx;
				y2 = tempy;
			}
		}

		pList.get(0).setX(x1);
		pList.get(0).setY(y1);
		pList.get(0).setD(d1);
		pList.get(1).setX(x2);
		pList.get(1).setY(y2);
		pList.get(1).setD(d2);

	}

	public String setBomb(String me)// sample
	{
		String bombIdx = "";
		Bomb newBomb = null;
		int xd = 0;
		int yd = 0;
		if (me.equals(pList.get(0).getId()))
		{
			xd = ((x1 + 15) / 40) * 40;
			yd = ((y1 + 39) / 40) * 40;
			newBomb = new Bomb(xd / 40, yd / 40);
			pList.get(0).setBombList(newBomb);
			AllbombList.add(newBomb);
		} else if (me.equals(pList.get(1).getId()))
		{
			xd = ((x2 + 15) / 40) * 40;
			yd = ((y2 + 39) / 40) * 40;
			newBomb = new Bomb(xd / 40, yd / 40);
			pList.get(1).setBombList(newBomb);
			AllbombList.add(newBomb);
		}
		System.out.println("set bomb :" + xd + "/" + yd);
		System.out.println("ABL size :" + AllbombList.size());
		return bombIdx;
	}

	public void removeBomb(Bomb remB)
	{
		System.out.println("remov boom");
		AllbombList.remove(remB);
	}

	private void Bomblistener()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (startChk)
				{
					System.out.println(1);
					for (int i = 0; i < AllbombList.size(); i++)
					{
						Bomb b = AllbombList.get(i);
						if (b.isChkboom())
						{
							chkdie(b);
						}
					}
				}
			}
		}).start();
	}

	public void chkdie(Bomb b)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("chk");
				int l = 2 * b.getL();
				int x1p = (x1 + 15) / 40;
				int y1p = (y1 + 30) / 40;
				int x2p = (x2 + 15) / 40;
				int y2p = (y2 + 30) / 40;
				if (selMap[y1p][x1p] == 3)
				{
					System.out.println("p1 die : " + id[0]);
					gameEnd(id[0]);
				}
				if (selMap[y2p][x2p] == 3)
				{
					System.out.println("p2 die : " + id[1]);
					gameEnd(id[1]);
				}
			}
		}).start();
	}

	public void gameEnd(String winP)
	{
		try
		{
			Thread.sleep(1000);
			startChk = false;
			if (winP.equals(id[0]))
			{
				guests[0].sendMsg("/gamend You Lose /end");
				guests[1].sendMsg("/gamend You Win /end");
				endgame(false);
				gameRoom.removeGU();
			} else if (winP.equals(id[1]))
			{
				guests[0].sendMsg("/gamend You Win /end");
				guests[1].sendMsg("/gamend You Lose /end");
				endgame(false);
				gameRoom.removeGU();
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void chainExplosion(int i, int j)
	{
//		System.out.println("chainExplosion :" + i + "/" + j);
		for (int j2 = 0; j2 < AllbombList.size(); j2++)
		{
			Bomb ceB = AllbombList.get(j2);
//			System.out.println(ceB.getIdxY() + "/" + ceB.getIdxX() + "/" +ceB.isChkboom());
			if (ceB.getIdxY() == i & ceB.getIdxX() == j & !ceB.isChkboom())
			{
				ceB.timer2();
//				System.out.println("chainExplosion out :" + ceB.getIdxY() + "/" + ceB.getIdxX());
				break;
			}
		}
	}

	public void sendAllMovInfo()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (startChk)
				{
					for (int i = 0; i < guests.length; i++)
					{
						guests[i].objSend(pList);
					}
					try
					{
						Thread.sleep(50);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void endgame(boolean fingersnap)
	{
		startChk = fingersnap;
//		try
//		{
//			Thread.sleep(1000);
//		} catch (InterruptedException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
