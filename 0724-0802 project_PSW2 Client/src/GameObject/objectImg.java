package GameObject;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class objectImg
{
	private Image tile = null;

	public objectImg() {
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				try
				{
					tile = ImageIO.read(new File("objectImage/tile.png"));
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	public Image getTile()
	{
		return tile;
	}
}
