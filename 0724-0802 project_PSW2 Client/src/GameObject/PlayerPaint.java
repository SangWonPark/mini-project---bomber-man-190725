package GameObject;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class PlayerPaint
{

	private Image[] playerimg = new Image[12];
	private int pNum = 0;

	public PlayerPaint(int p)
	{
		this.pNum = p;
		init();
	}

	public Image getPlayerimg(char d, int i)
	{
		int dn = 0;
		switch (d)
		{
		case 'e':

			break;
		case 'w':
			dn = 3;
			break;
		case 's':
			dn = 6;
			break;
		case 'n':
			dn = 9;
			break;
		}
		return playerimg[dn + i];
	}

	private void init()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					/*
					 * µ¿0 2 ¼­3 5 ³²6 8 ºÏ9 11
					 */
					playerimg[0] = ImageIO.read(new File("playerImage/player" + pNum + "e1.png"));
					playerimg[1] = ImageIO.read(new File("playerImage/player" + pNum + "e2.png"));
					playerimg[2] = ImageIO.read(new File("playerImage/player" + pNum + "e3.png"));

					playerimg[3] = ImageIO.read(new File("playerImage/player" + pNum + "w1.png"));
					playerimg[4] = ImageIO.read(new File("playerImage/player" + pNum + "w2.png"));
					playerimg[5] = ImageIO.read(new File("playerImage/player" + pNum + "w3.png"));

					playerimg[6] = ImageIO.read(new File("playerImage/player" + pNum + "s1.png"));
					playerimg[7] = ImageIO.read(new File("playerImage/player" + pNum + "s2.png"));
					playerimg[8] = ImageIO.read(new File("playerImage/player" + pNum + "s3.png"));

					playerimg[9] = ImageIO.read(new File("playerImage/player" + pNum + "n1.png"));
					playerimg[10] = ImageIO.read(new File("playerImage/player" + pNum + "n2.png"));
					playerimg[11] = ImageIO.read(new File("playerImage/player" + pNum + "n3.png"));

				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}
