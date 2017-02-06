package pacman;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class Bouton extends JButton{
	
	private static final long serialVersionUID = 1L;

	public Bouton(String nom){
		super(nom);
		Font police2 = new Font("Tahoma", Font.BOLD, 30);
		this.setSize(new Dimension(800,50));
		this.setFont(police2);
	}

}
