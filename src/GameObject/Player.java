package GameObject;

import java.io.Serializable;
import java.util.ArrayList;

import Util.GameU;

public class Player implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String id = "";

	private boolean readyFlag = false;

	private transient GameU gu = null;

	private int x = 0;
	private int y = 0;
	private char d = 's';
	private int speed = 0;
	private ArrayList<Bomb> bombList = null;
	private int[][] map = null;

	public Player(String setID, int setX, int setY, int[][] selmap, GameU GU)
	{
		this.id = setID;
		this.x = setX;
		this.y = setY;
		this.map = selmap;
		this.bombList = new ArrayList<>();
		this.gu = GU;
	}

	public String getId()
	{
		return id;
	}

	public boolean isReadyFlag()
	{
		return readyFlag;
	}

	public void setReadyFlag(boolean readyFlag)
	{
		this.readyFlag = readyFlag;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int[] move(int xcal, int ycal)
	{

		return null;
	}

	public char getD()
	{
		return d;
	}

	public void setD(char d)
	{
		this.d = d;
	}

	public ArrayList<Bomb> getBombList()
	{
		return bombList;
	}

	public void setBombList(Bomb newbomb)
	{
		if (bombList.size() < 3)
		{
			newbomb.setOwner(this);
			bombList.add(newbomb);
			map[newbomb.getIdxY()][newbomb.getIdxX()] = 2;
		}
	}

	public void setMap(int i, int j, int set)
	{
		this.map[i][j] = set;
	}

	public void setMapBomb(Bomb b, char c)
	{

		if (c == 'e')
		{
			if (map[b.getIdxY() - 1][b.getIdxX()] == 2)
				gu.chainExplosion(b.getIdxY() - 1, b.getIdxX());
			if (map[b.getIdxY() + 1][b.getIdxX()] == 2)
				gu.chainExplosion(b.getIdxY() + 1, b.getIdxX());
			if (map[b.getIdxY()][b.getIdxX() - 1] == 2)
				gu.chainExplosion(b.getIdxY(), b.getIdxX() - 1);
			if (map[b.getIdxY()][b.getIdxX() + 1] == 2)
				gu.chainExplosion(b.getIdxY(), b.getIdxX() + 1);
			if (map[b.getIdxY()][b.getIdxX()] != 1)
			{
				map[b.getIdxY()][b.getIdxX()] = 3;
			}
			if (map[b.getIdxY() - 1][b.getIdxX()] != 1)
			{
				map[b.getIdxY() - 1][b.getIdxX()] = 3;
			}
			if (map[b.getIdxY() + 1][b.getIdxX()] != 1)
			{
				map[b.getIdxY() + 1][b.getIdxX()] = 3;
			}
			if (map[b.getIdxY()][b.getIdxX() - 1] != 1)
			{
				map[b.getIdxY()][b.getIdxX() - 1] = 3;
			}
			if (map[b.getIdxY()][b.getIdxX() + 1] != 1)
			{
				map[b.getIdxY()][b.getIdxX() + 1] = 3;
			}
		} else if (c == 'c')
		{
			if (map[b.getIdxY()][b.getIdxX()] == 3)
				map[b.getIdxY()][b.getIdxX()] = 0;
			if (map[b.getIdxY() - 1][b.getIdxX()] == 3)
				map[b.getIdxY() - 1][b.getIdxX()] = 0;
			if (map[b.getIdxY() + 1][b.getIdxX()] == 3)
				map[b.getIdxY() + 1][b.getIdxX()] = 0;
			if (map[b.getIdxY()][b.getIdxX() - 1] == 3)
				map[b.getIdxY()][b.getIdxX() - 1] = 0;
			if (map[b.getIdxY()][b.getIdxX() + 1] == 3)
				map[b.getIdxY()][b.getIdxX() + 1] = 0;
		}
	}

	public void removeBomb(Bomb remB)
	{
		System.out.println("remov boom");
		bombList.remove(remB);
		gu.removeBomb(remB);
	}
}
