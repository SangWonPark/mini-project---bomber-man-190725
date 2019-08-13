package GUI_Client;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import GameObject.Bomb;
import GameObject.Player;
import GameObject.PlayerPaint;
import GameObject.objectImg;
import Main.WithServer;

public class GameP extends JPanel
{
	WithServer toServer;

	/**
	 * Create the panel.
	 */
	private ArrayList[] bomb = new ArrayList[2];

	private Player p1;
	private Player p2;

	private int exX1 = 45;
	private int exY1 = 320;
	private int exX2 = 685;
	private int exY2 = 320;

	private ArrayList<Player> playerList = null;

	private objectImg objectImg = null;
	private Bomb bombImg = null;
	private PlayerPaint p1img = null;
	private PlayerPaint p2img = null;
	private int mov1 = 0;
	private int mov2 = 0;

	private int l1 = 40;

	int[][] map1 = null;
	
	public GameP(WithServer getServer)
	{
		this.toServer = getServer;
		init();

		this.requestFocus();
		setFocusable(true);
		setLayout(null);
		toServer.setGP(this);
	}

	private void init()
	{

		playerList = new ArrayList<>();

		p1img = new PlayerPaint(1);
		p2img = new PlayerPaint(2);
		Player p1 = new Player(null, 45, 320);
		Player p2 = new Player(null, 685, 320);

		playerList.add(p1);
		playerList.add(p2);

		bombImg = new Bomb();
		objectImg = new objectImg();

		repaint();

		toServer.objReceive();
	}

	public void setPlayerObj(ArrayList<Player> pList)
	{
		this.playerList = pList;
		bomb[0] = playerList.get(0).getBombList();
		bomb[1] = playerList.get(1).getBombList();
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				p1 = playerList.get(0);
				p2 = playerList.get(1);
				map1 = p2.getMap();
				repaint();
				if (exX1 != p1.getX() | exY1 != p1.getY())
				{
					exX1 = p1.getX();
					exY1 = p1.getY();
					mov1++;
					if (mov1 > 2)
					{
						mov1 = 0;
					}
				}
				if (exX2 != p2.getX() | exY2 != p2.getY())
				{
					exX2 = p2.getX();
					exY2 = p2.getY();
					mov2++;
					if (mov2 > 2)
					{
						mov2 = 0;
					}
				}
			}
		}).start();
	}

	@Override
	public void paint(Graphics G)
	{
		super.paint(G);

		for (int i = 0; i < map1.length; i++)
		{
			for (int j = 0; j < map1[i].length; j++)
			{
				if (map1[i][j] == 1)
				{
					G.drawImage(objectImg.getTile(), j*l1, i*l1, l1, l1, this);
				} else if (map1[i][j] == 2)
				{
					G.drawImage(bombImg.getBomb(), j*l1, i*l1, l1, l1, this);
				} else if (map1[i][j] == 3)
				{
					G.drawImage(bombImg.getBoom(), j*l1, i*l1, l1, l1, this);
				}
			}
		}
		
		G.drawImage(p1img.getPlayerimg(p1.getD(), mov1), p1.getX(), p1.getY(), this);
		G.drawImage(p2img.getPlayerimg(p2.getD(), mov2), p2.getX(), p2.getY(), this);
	}

	@Override
	public void update(Graphics G)
	{
		super.update(G);

		for (int i = 0; i < map1.length; i++)
		{
			for (int j = 0; j < map1[i].length; j++)
			{
				if (map1[i][j] == 1)
				{
					G.drawImage(objectImg.getTile(), j*40, i*40, 40, 40, this);
				}
			}
		}

		G.drawImage(p1img.getPlayerimg('s', mov1), 45, 320, this);
		G.drawImage(p2img.getPlayerimg('s', mov2), 685, 320, this);
	}

//	public boolean setMap(Object o)
//	{
//		map1 = (int[][]) o;
//		return true;
//	}
}