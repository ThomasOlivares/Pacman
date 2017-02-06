package pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Menu_Principal extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Menu_Principal(){
		super();
		this.setPreferredSize(new Dimension(600,500));
	}
	
	  public void paintComponent(Graphics g){
		  g.setColor(Color.BLUE);
		  g.fillRect(0, 0, this.getWidth(), this.getHeight());
	  }
}