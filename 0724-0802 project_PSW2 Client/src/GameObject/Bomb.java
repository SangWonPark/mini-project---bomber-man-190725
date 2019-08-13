package GameObject;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Bomb implements Serializable
{
	private static final long serialVersionUID = 11L;

	private int x;
	private int y;

	private boolean chkboom = false;
	private Player owner;
	private int l = 40;
	
	private Image bomb = null;
	private Image bang = null;
	private Image boom = null;

	public Bomb()
	{
		try
		{
			bomb = ImageIO.read(new File("image/bomb.png"));
			bang = ImageIO.read(new File("image/bang.png"));
			boom = ImageIO.read(new File("image/boom.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Image getBomb()
	{
		return bomb;
	}

	public Image getBang()
	{
		return bang;
	}

	public Image getBoom()
	{
		return boom;
	}
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public boolean isChkboom()
	{
		return chkboom;
	}

}
