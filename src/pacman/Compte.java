package pacman;

import java.io.Serializable;

public class Compte implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String nom = "Anonyme";
	public int points = 0;
	public int niveau = 1;
	public int xp = 0;
	public int image;
	public boolean[] dispo = new boolean[Fenetre.getNmax()];
	public static int[] tablXp = {10,25,50,100,200,500,1000,2000,5000};
	
	public Compte(String nom){
		this.nom = nom;
		dispo[0] = true;
		image = 0;
		for (int i = 1; i<Fenetre.getNmax(); i++)
			dispo[i] = false;
	}
	
	public Compte(Compte copy){
		this.nom = copy.nom;
		this.image = copy.image;
		this.image = copy.niveau;
		this.image = copy.xp;
		for (int i = 0; i<Fenetre.getNmax(); i++)
			this.dispo[i] = copy.dispo[i];
	}
	
	public void addXp(int bonus){
		this.xp+=bonus;
		if (this.xp>=tablXp[this.niveau-1]){
			this.xp-=tablXp[this.niveau-1];
			this.niveau++;
			this.addXp(0);
		}
	}

}
