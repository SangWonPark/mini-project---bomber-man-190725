package GameObject;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import Util.GameU;

public class Bomb implements Serializable
{
	private static final long serialVersionUID = 11L;

	private Player owner;
	private Bomb me = this;

	private boolean chkboom = false;
	private int l = 40;

	private int idxX = 0;
	private int idxY = 0;

	public Bomb(int i, int j)
	{
		this.idxX = i;
		this.idxY = j;
		timer();

	}

	public void removeThis()
	{
		owner.removeBomb(this);
	}

	public int getIdxX()
	{
		return idxX;
	}

	public int getIdxY()
	{
		return idxY;
	}

	public int getL()
	{
		return l;
	}

	public boolean isChkboom()
	{
		return chkboom;
	}

	public void setOwner(Player owner)
	{
		this.owner = owner;
	}

	private void timer()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					Thread.sleep(3000);
					if (!chkboom)
					{
						chkboom = true;
						owner.setMapBomb(me, 'e');
						Thread.sleep(1500);
//					chkboom = false;
						owner.setMapBomb(me, 'c');
						removeThis();
					}
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void timer2()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					System.out.println("timer2");
					chkboom = true;
					owner.setMapBomb(me, 'e');
					Thread.sleep(1500);
//					chkboom = false;
					owner.setMapBomb(me, 'c');
					removeThis();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}
