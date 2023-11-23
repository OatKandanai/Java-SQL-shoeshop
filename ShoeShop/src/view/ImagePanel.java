package view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	private static Image img;

	public ImagePanel()
	{

	}

	public ImagePanel(Image ximg)
	{
		img = ximg;
	}

	public void paint(Graphics g)
	{
		if (img != null) // getWidth , getHeight คือ get ขนาดของ img
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(this), img.getHeight(this), this);
		else
			g.fillRect(0, 0, getWidth(), getHeight()); // ถ้าไม่มีรูปภาพให้วาด rectangle
	}

	public static void setImage(Image ximg)
	{
		img = ximg;
	}

	public static Image getImage()
	{
		return img;
	}
}
