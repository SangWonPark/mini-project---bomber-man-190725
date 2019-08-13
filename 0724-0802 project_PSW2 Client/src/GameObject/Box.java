package GameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Box implements Serializable
{
	private static final long serialVersionUID = 99L;
	private ArrayList<Player> sendData = null;
	
	public ArrayList<Player> getSendData()
	{
		return sendData;
	}
	public void setSendData(ArrayList<Player> sendData)
	{
		this.sendData = sendData;
	}
}
