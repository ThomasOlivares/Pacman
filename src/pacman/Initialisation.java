package pacman;

import java.util.LinkedList;

public class Initialisation {
	
	public static void main(String[] args){
		Fenetre.setUtilisateurs(new LinkedList<String>());
		Fenetre.writeUtil();
		Fenetre.setScores(new int[Fenetre.getNmax()][5]);
		Fenetre.setNames(new String[Fenetre.getNmax()][5]);
		for (int i = 0; i<Fenetre.getNmax(); i++)
			for (int j = 0; j<5; j++)
				Fenetre.getNames()[i][j] = "";
		Fenetre.ecrireScore();
	}
}
