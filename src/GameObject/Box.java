package GameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Box implements Serializable
{
	private static final long serialVersionUID = 99L;
	private ArrayList<Player> sendData = null;
	
	public Box(Object pList)
	{
		this.sendData = (ArrayList<Player>) pList;
	}
	public ArrayList<Player> getSendData()
	{
		return sendData;
	}
}
