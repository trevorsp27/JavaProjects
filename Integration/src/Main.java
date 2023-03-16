import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
public class Main extends Canvas 
{
	private static final long serialVersionUID = 1L;
	
	public Main(int w, int h) 
	{
		JFrame frame = new JFrame();
		frame.setSize(w,h);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this);
	}
	public static void main(String args[]) 
	{
		new Main(800, 800);
	}
	public void paint(Graphics g) 
	{
		for(int i = 0; i < 800; i+=25) 
		{
			g.drawString("" + i/25, i, 764);
			g.drawString("" + i/25, 0, 764 - i);
		}
		for(int i = 0; i < 800; i++) 
		{
			g.fillRect(i, i*i, i, i*i);
		}
	}
}
