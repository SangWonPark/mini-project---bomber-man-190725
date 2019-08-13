package GameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String id = "";

	private boolean readyFlag = false;

	private int x = 0;
	private int y = 0;
	private char d = 's';
	private int speed = 0;
	private ArrayList<Bomb> bombList = null;
	private int[][] map = null;

	public Player(String setID, int setX, int setY)
	{
		this.id = setID;
		this.x = setX;
		this.y = setY;
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

	public int[][] getMap()
	{
		return map;
	}
}
