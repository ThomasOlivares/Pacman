package pacman;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gom {
	
	public int val = 0;
	
	public int time = 30;
	
	public String nom;
	
	public Image[] picture = new Image[2];
	
	public Gom(String nom){
		this.nom = nom;
		if (nom == "Cerise"){
			this.val = 100;
			try {
				this.picture[0] = ImageIO.read(new File("images/Cerise_100.png"));
				this.picture[1] = ImageIO.read(new File("images/Cerise_50.png"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (nom == "Fraise"){
			this.val = 200;
			try {
				this.picture[0] = ImageIO.read(new File("images/Fraise_100.png"));
				this.picture[1] = ImageIO.read(new File("images/Fraise_50.png"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (nom == "Orange"){
			this.val = 500;
			try {
				this.picture[0] = ImageIO.read(new File("images/Orange_100.png"));
				this.picture[1] = ImageIO.read(new File("images/Orange_50.png"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (nom == "Pomme"){
			this.val = 700;
		}
		else if (nom == "Melon"){
			this.val = 1000;
		}
		else if (nom == "Galboss"){
			this.val = 2000;
		}
		else if (nom == "Cloche"){
			this.val = 3000;
		}
		else if (nom == "Clé"){
			this.val = 5000;
		}
		else if (nom == "SuperPacGom"){
			this.val = 500;
			try {
				this.picture[0] = ImageIO.read(new File("images/SuperPacGom_100.png"));
				this.picture[1] = ImageIO.read(new File("images/SuperPacGom_50.png"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
