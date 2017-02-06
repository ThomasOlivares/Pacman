package pacman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Niveau extends JPanel {
	
	private int i;
	private int cheminmax = 0;
	private int cheminInit = 0;
	private int ChasseAuxFantomes = 0;
	private int nbIter;
	private int cheminBase;
	private List<Integer> BaseX = new LinkedList<Integer>();
	private List<Integer> BaseY = new LinkedList<Integer>();
	private int[][][] Tunnel;
	
	private int[] Rien = {0,0,0,0};  //bas/droite/haut/gauche
	private int[] g = 	 {0,0,0,1};
	private int[] h =    {0,0,1,0};
	private int[] hg =   {0,0,1,1};
	private int[] d =    {0,1,0,0};
	private int[] dg =   {0,1,0,1};
	private int[] dh =   {0,1,1,0};
	private int[] dhg =  {0,1,1,1};
	private int[] b =    {1,0,0,0};
	private int[] bg =   {1,0,0,1};
	private int[] bh =   {1,0,1,0};
	private int[] bhg =  {1,0,1,1};
	private int[] bd =   {1,1,0,0};
	private int[] bdg =  {1,1,0,1};
	private int[] bdh =  {1,1,1,0};
	@SuppressWarnings("unused")
	private int[] bdhg =  {1,1,1,1};
	
	private Case[][] Point;
	private Case[][] Point0 = {{new Case(bh, "Bord"),  new Case(bh, "Bord"), new Case(bh, "Bord"),  new Case(bh, "Bord"), new Case(bh, "Bord"), new Case(bh, "Bord"), new Case(bh, "Bord"), new Case(bh, "Bord"), new Case(bh, "Bord"), new Case(bh, "Bord"), new Case(bh, "Bord")}};
	
	private Case[][] Point1 = {{new Case(d, "Bord"),  new Case(hg, "Piece"), new Case(h, "Piece"),  new Case(bh, new Gom("Cerise")), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(dh, "Piece"), new Case(g, "Bord")},
							   {new Case(d, "Bord"),  new Case(dg, "Piece"), new Case(g, "Piece"),  new Case(bh, "Piece"),           new Case(h, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(d, "Piece"),  new Case(g, "Bord")},
							   {new Case(d, "Bord"),  new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(b, "Camp"),             new Case(dg, "Piece"), new Case(hg, "Piece"), new Case(h, "Piece"),  new Case(d, "Piece"),  new Case(g, "Bord")},
							   {new Case(d, "Bord"),  new Case(dg, "Piece"), new Case(bg, "Piece"), new Case(bh, "Piece"),           new Case(d, "Piece"),  new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(g, "Bord")},
							   {new Case(bd, "Bord"), new Case(g, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"),           new Case(b, "Piece"),  new Case(bd, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(bg, "Bord")},
							   {new Case(bh, "Piece"),new Case(b, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"),           new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(b, "Piece"),  new Case(b, "Piece"),  new Case(bh, "Piece")}};
	
	private Case[][] Point2 = {{new Case(hg, "Piece"), 			  new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"),				  new Case(bh, "Piece"), new Case(h, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(dh, "Piece")},
							   {new Case(dg, "Piece"),			  new Case(hg, "Piece"), new Case(bh, "Piece"), new Case(h, "Piece"), 				  new Case(bh, "Piece"), new Case(b, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(d, "Piece")},
							   {new Case(dg, "Piece"), 			  new Case(dg, "Piece"), new Case(dhg, "Camp"), new Case(g, "Piece"), 				  new Case(h, "Piece"),  new Case(bh, "Piece"), new Case(h, "Piece"),  new Case(bh, "Piece"), new Case(dh, "Piece"), new Case(dg, "Piece")},
							   {new Case(Rien, "Piece"),		  new Case(b, "Piece"),  new Case(bh, "Piece"), new Case(bd, "Piece"),				  new Case(g, "Piece"),  new Case(dh, "Piece"), new Case(dg, "Piece"), new Case(bdg, "Camp"), new Case(g, "Piece"),  new Case(Rien, "Piece")},
							   {new Case(g, "Piece"),             new Case(h, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"), 				  new Case(bd, "Piece"), new Case(dg, "Piece"), new Case(bg, "Piece"), new Case(h, "Piece"),  new Case(bd, "Piece"), new Case(dg, "Piece")},
							   {new Case(bdg, new Gom("Fraise")), new Case(bg, "Piece"), new Case(bh, "Piece"), new Case(bh, new Gom("SuperPacGom")), new Case(bh, "Piece"), new Case(b, "Piece"),  new Case(bh, "Piece"), new Case(b, "Piece"),  new Case(bh, "Piece"), new Case(bd, "Piece")}};
	
	private Case[][] Point3 = {{new Case(hg, "Piece"),   new Case(bh, "Piece"), new Case(bh, new Gom("Cerise")), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(dh, "Piece"), new Case(g, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(dh, "Piece"), new Case(hg, "Piece"), new Case(bh, "Piece"), new Case(dh, "Piece"), new Case(dhg, new Gom("Fraise"))},
							   {new Case(bg, "Piece"),   new Case(bh, "Piece"), new Case(h, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"),   new Case(bd, "Piece"),			     new Case(g, "Piece"),  new Case(bh, "Piece"),            new Case(dh, "Piece"), new Case(dg, "Piece"), new Case(bg, "Piece"),   new Case(dh, "Piece"), new Case(g, "Piece"),  new Case(d, "Piece")}, 
							   {new Case(hg, "Piece"),   new Case(bh, "Piece"), new Case(b, "Piece"),  new Case(h, "Piece"),  new Case(bh, "Piece"),   new Case(bh, "Piece"),				 new Case(b, "Piece"),  new Case(bh, "Piece"), 			  new Case(b, "Piece"),  new Case(b, "Piece"),  new Case(bh, "Piece"),   new Case(b, "Piece"),  new Case(bd, "Piece"), new Case(dg, "Piece")},
							   {new Case(dg, "Piece"),   new Case(hg, "Piece"), new Case(bh, "Piece"), new Case(b, "Piece"),  new Case(h, "Piece"),    new Case(bh, "Piece"), 		   		 new Case(bh, "Piece"), new Case(h, "Piece"), 			  new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"),   new Case(h, "Piece"),  new Case(dh, "Piece"), new Case(dg, "Piece")},
							   {new Case(dg, "Piece"),   new Case(dg, "Piece"), new Case(hg, "Piece"), new Case(bh, "Piece"), new Case(Rien, "Piece"), new Case(bh, "Piece"),				 new Case(bh, "Piece"), new Case(d, "Piece"), 			  new Case(hg, "Piece"), new Case(bh, "Piece"), new Case(dh, "Piece"),   new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece")},
							   {new Case(Rien, "Piece"), new Case(d, "Piece"),  new Case(dg, "Piece"), new Case(bg, "Camp"),  new Case(dg, "Piece"),   new Case(hg, "Logo"), 				 new Case(dh, "Bord"),  new Case(dg, "Piece"), 			  new Case(dg, "Piece"), new Case(bd, "Camp"),  new Case(dg, "Piece"),   new Case(dg, "Piece"), new Case(bg, "Piece"), new Case(Rien, "Piece")},
							   {new Case(dg, "Piece"),   new Case(dg, "Piece"), new Case(g, "Piece"),  new Case(bh, "Piece"), new Case(bd, "Piece"),   new Case(bg, "Bord"), 				 new Case(bd, "Bord"),  new Case(bg, "Piece"),			  new Case(b, "Piece"),  new Case(bh, "Piece"), new Case(d, "Piece"),    new Case(bg, "Piece"), new Case(bh, "Piece"), new Case(d, "Piece")},
							   {new Case(dg, "Piece"),   new Case(bg, "Piece"), new Case(b, "Piece"),  new Case(h, "Piece"),  new Case(bh, "Piece"),   new Case(bh, "Piece"),				 new Case(bh, "Piece"), new Case(bh, "Piece"),			  new Case(bh, "Piece"), new Case(h, "Piece"),  new Case(b, "Piece"),    new Case(h, "Piece"),  new Case(h, "Piece"),  new Case(d, "Piece")},
							   {new Case(dg, "Piece"),   new Case(hg, "Piece"), new Case(h, "Piece"),  new Case(b, "Piece"),  new Case(bh, "Piece"),   new Case(bh, "Piece"),				 new Case(h, "Piece"),  new Case(bh, "Piece"),			  new Case(bh, "Piece"), new Case(d, "Piece"),  new Case(hg, "Piece"),   new Case(bd, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece")},
							   {new Case(g, "Piece"),    new Case(bd, "Piece"), new Case(g, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"),   new Case(dh, "Piece"), 				 new Case(g, "Piece"),  new Case(bh, "Piece"), 			  new Case(bh, "Piece"), new Case(b, "Piece"),  new Case(Rien, "Piece"), new Case(bh, "Piece"), new Case(bd, "Piece"), new Case(dg, "Piece")},
							   {new Case(bg, "Piece"),   new Case(bh, "Piece"), new Case(b, "Piece"),  new Case(bh, "Piece"), new Case(bh, "Piece"),   new Case(bd, new Gom("SuperPacGom")), new Case(dg, "Piece"), new Case(bhg, new Gom("Fraise")), new Case(bh, "Bord"),  new Case(bh, "Bord"),  new Case(b, "Piece"),    new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bd, "Piece")}};
		
	private Case[][] Point4 = {{new Case(hg, "Piece", 1), 			  new Case(bh, "Piece", 1), 			   new Case(bh, "Piece", 1), new Case(h, "Piece", 1),  new Case(h, "Piece", 1),    new Case(h, "Piece", 1),  new Case(bh, "Piece", 1), new Case(d, "Tunnel", 1),  new Case(dhg, new Gom("Fraise"), 2), new Case(hg, "Piece", 2), new Case(h, "Piece", 2),  new Case(b, "Tunnel", 2),  new Case(h, "Piece", 2),  new Case(h, "Piece", 2),  new Case(dh, "Piece", 2)},
							   {new Case(bg, "Piece", 1),			  new Case(bh, "Piece", 1), 			   new Case(h, "Piece", 1),  new Case(bd, "Piece", 1), new Case(dg, "Piece", 1),   new Case(bg, "Piece", 1), new Case(h, "Piece", 1),  new Case(bd, "Piece", 1),  new Case(dg, "", 2),                 new Case(dg, "Piece", 2), new Case(bg, "Piece", 2), new Case(h, "Piece", 2),   new Case(bd, "Piece", 2), new Case(dg, "Piece", 2), new Case(dg, "Piece", 2)}, 
							   {new Case(hg, "Piece", 1), 			  new Case(bh, "Piece", 1), 			   new Case(bd, "Piece", 1), new Case(hg, "Piece", 1), new Case(b, "Piece", 1),	   new Case(dh, "Piece", 1), new Case(bg, "Piece", 1), new Case(dh, "Piece", 1),  new Case(g, "Piece", 2),			   new Case(d, "Piece", 2),  new Case(hg, "Piece", 2), new Case(b, "Piece", 2),   new Case(dh, "Piece", 2), new Case(dg, "Piece", 2), new Case(dg, "Piece", 2)},
							   {new Case(b, "Tunnel", 1),			  new Case(bh, "Piece", 1), 			   new Case(bh, "Piece", 1), new Case(d, "Piece", 1),  new Case(bhg, "Camp", 1),   new Case(g, "Piece", 1),  new Case(bh, "Piece", 1), new Case(d, "Piece", 1),   new Case(dg, "Piece", 2),			   new Case(g, "Piece", 2),  new Case(d, "Piece", 2),  new Case(dhg, "Camp", 2),  new Case(g, "Piece", 2),  new Case(d, "Piece", 2),  new Case(g, "Tunnel", 2)},
							   {new Case(hg, "Piece", 1),			  new Case(h, "Piece", 1),  			   new Case(dh, "Piece", 1), new Case(bg, "Piece", 1), new Case(h, "Piece", 1),    new Case(bd, "Piece", 1), new Case(hg, "Piece", 1), new Case(bd, "Piece", 1),  new Case(g, "Piece", 2), 			   new Case(bd, "Piece", 2), new Case(bg, "Piece", 2), new Case(h, "Piece", 2),   new Case(bd, "Piece", 2), new Case(bg, "Piece", 2), new Case(bd, "Piece", 2)},
							   {new Case(dg, "Piece", 1), 			  new Case(dg, "Piece", 1), 			   new Case(g, "Piece", 1),  new Case(bh, "Piece", 1), new Case(d, "Piece", 1),    new Case(hg, "Piece", 1), new Case(b, "Piece", 1),  new Case(dh, "Piece", 1),  new Case(bg, "Piece", 2),			   new Case(bh, "Piece", 2), new Case(dh, "Piece", 2), new Case(bg, "Piece", 2),  new Case(dh, "Piece", 2), new Case(hg, "Piece", 2), new Case(dh, "Piece", 2)},
							   {new Case(dg, "Piece", 1), 			  new Case(dg, "Piece", 1), 			   new Case(dg, "Piece", 1), new Case(hg, "Piece", 1), new Case(Rien, "Piece", 1), new Case(b, "Bord", 1),   new Case(bh, "Bord", 1),  new Case(d, "Piece", 1),   new Case(hg, "Piece", 2),			   new Case(bh, "Piece", 2), new Case(b, "Piece", 2),  new Case(bh, "Piece", 2),  new Case(b, "Piece", 2),  new Case(d, "Piece", 2),  new Case(dg, "Piece", 2)},
							   {new Case(bg, "Piece", 1), 			  new Case(b, "Piece", 1),  			   new Case(d, "Piece", 1),  new Case(dg, "Piece", 1), new Case(bg, "Piece", 1),   new Case(h, "Piece", 1),  new Case(dh, "Piece", 1), new Case(dg, "Piece", 1),  new Case(g, "Piece", 2),			   new Case(bh, "Piece", 2), new Case(bh, "Piece", 2), new Case(h, "Piece", 2),   new Case(bh, "Piece", 2), new Case(d, "Piece", 2),  new Case(dg, "Piece", 2)},
							   {new Case(bhg, new Gom("Fraise"), 1),  new Case(bh, "", 1),      			   new Case(b, "Piece", 1),  new Case(b, "Piece", 1),  new Case(bh, "Piece", 1),   new Case(bd, "Piece", 1), new Case(bg, "Piece", 1), new Case(bd, "Piece", 1),  new Case(bg, "Piece", 2),			   new Case(bh, "Piece", 2), new Case(bh, "Piece", 2), new Case(b, "Piece", 2),   new Case(bh, "Piece", 2), new Case(b, "Piece", 2),  new Case(bd, "Piece", 2)},
							   {new Case(hg, "Piece", 3),			  new Case(bh, "Piece", 3), 			   new Case(dh, "Piece", 3), new Case(hg, "Piece", 3), new Case(bh, "Piece", 3),   new Case(dh, "Piece", 3), new Case(hg, "Piece", 3), new Case(h, "Piece", 3),   new Case(bh, "Piece", 3),			   new Case(dh, "Piece", 3), new Case(hg, "Piece", 3), new Case(bh, "Piece", 3),  new Case(h, "Piece", 3),  new Case(h, "Piece", 3),  new Case(dhg, new Gom("Fraise"), 2)},
							   {new Case(g, "Piece", 3), 			  new Case(dh, "Piece", 3), 			   new Case(g, "Piece", 3),  new Case(d, "Piece", 3),  new Case(bdh, "Camp", 3),   new Case(g, "Piece", 3),  new Case(d, "Piece", 3),  new Case(dg, "Piece", 3),  new Case(hg, "Piece", 3),    		   new Case(bd, "Piece", 3), new Case(dg, "Piece", 3), new Case(bdg, "Camp", 3),  new Case(dg, "Piece", 3), new Case(dg, "Piece", 3), new Case(dg, "", 2)},
							   {new Case(dg, "Piece", 3), 			  new Case(g, new Gom("SuperPacGom"), 3),  new Case(bd, "Piece", 3), new Case(bg, "Piece", 3), new Case(bh, "Piece", 3),   new Case(bd, "Piece", 3), new Case(dg, "Piece", 3), new Case(dg, "Piece", 3),  new Case(bg, "Piece", 3),			   new Case(dh, "Piece", 3), new Case(bg, "Piece", 3), new Case(bh, "Piece", 3),  new Case(bd, "Piece", 3), new Case(g, "Piece", 3),  new Case(d, "Piece", 2)},
							   {new Case(bg, "Piece", 3), 			  new Case(b, "Piece", 3), 			       new Case(bh, "Piece", 3), new Case(bh, "Piece", 3), new Case(bh, "Piece", 3),   new Case(bh, "Piece", 3), new Case(bd, "Piece", 3), new Case(g, "Piece", 3),  new Case(bh, "Piece", 3), 				   new Case(b, "Piece", 3),  new Case(bh, "Piece", 3), new Case(h, "Piece", 3),  new Case(bh, "Piece", 3), new Case(bd, "Piece", 3), new Case(bdg, new Gom("Cerise"), 2)}};
	
	private Case[][] Point5 = {{new Case(bhg, "Camp"), new Case(h, "Piece"), new Case(h, 'b', 'd'), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(dh, "Piece"), new Case(dhg, "Camp")},
							   {new Case(hg, "Piece"), new Case(d, "Piece"), new Case(bg, "Piece"), new Case(h, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(b, "Piece"), new Case(dh, "Piece")},
							   {new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(hg, "Piece"), new Case(b, "Piece"), new Case(h, "Piece"), new Case(bh, "Piece"), new Case(dh, "Piece"), new Case(hg, "Piece"), new Case(d, 'b', 'g')},
							   {new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(hg, "Piece"), new Case(b, "Piece"), new Case(dh, "Piece"), new Case(g, "Piece"), new Case(d, "Piece"), new Case(dg, "Piece")},
							   {new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(g, "Piece"), new Case(Rien, "Piece"), new Case(bh, "Piece"), new Case(Rien, "Piece"), new Case(d, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece")},
							   {new Case(dg, "Piece"), new Case(g, "Piece"), new Case(d, "Piece"), new Case(bg, "Piece"), new Case(h, "Piece"), new Case(bd, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece")},
							   {new Case(g, 'd', 'h'), new Case(bd, "Piece"), new Case(bg, "Piece"), new Case(bh, "Piece"), new Case(b, "Piece"), new Case(h, "Piece"), new Case(bd, "Piece"), new Case(dg, "Piece"), new Case(dg, "Piece")},
							   {new Case(bg, "Piece"), new Case(h, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(b, "Piece"), new Case(dh, "Piece"), new Case(g, "Piece"), new Case(d, "Piece")},
							   {new Case(bdg, "Camp"), new Case(bg, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(bh, "Piece"), new Case(b, 'h', 'g'), new Case(bd, "Piece"), new Case(bdh, "Camp")}};
	
	private List<Personnage> Liste = new LinkedList<Personnage>();
	private Personnage[][] ListePerso = { {new Personnage("Pacman"), new Personnage("Clyde"), new Personnage("Blinky"), new Personnage("Inky"), new Personnage("Pinky")},
										 {new Personnage("Pacman"), new Personnage("Clyde")}, 
										 {new Personnage("Pacman"), new Personnage("Blinky")},
										 {new Personnage("Pacman"), new Personnage("Inky"),  new Personnage("Inky"),   new Personnage("Clyde")},
										 {new Personnage("Pacman"), new Personnage("Pinky"), new Personnage("Clyde")},
										 {new Personnage("Pacman"), new Personnage("Blinky"),new Personnage("Pinky")},
										 {new Personnage("Pacman"), new Personnage("Clyde"), new Personnage("Blinky"), new Personnage("Inky"), new Personnage("Pinky")},
										 {new Personnage("Pacman"), new Personnage("Clyde"), new Personnage("Blinky"), new Personnage("Inky"), new Personnage("Pinky")},
										 {new Personnage("Pacman"), new Personnage("Blinky"), new Personnage("Blinky"), new Personnage("Blinky"), new Personnage("Blinky")}};
	
	private int dimX;
	private int dimY;
	private int e;    // L'écart doit être un multiple de 10
	private int x0;   // Ces 4 paramètres doivent être supérieur ou égaux à -100
	private int xmax;
	private int y0;
	private int ymax;
	private char[] depl = new char[2]; 
	
	private Image Pacman_droite;
	private Image Pacman_haut;
	private Image Pacman_gauche;
	private Image Pacman_bas;
	private Image Blinky_droite;
	private Image Blinky_haut;
	private Image Blinky_gauche;
	private Image Blinky_bas;
	private Image Inky_droite;
	private Image Inky_haut;
	private Image Inky_gauche;
	private Image Inky_bas;
	private Image Clyde_droite;
	private Image Clyde_haut;
	private Image Clyde_gauche;
	private Image Clyde_bas;
	private Image Pinky_droite;
	private Image Pinky_haut;
	private Image Pinky_gauche;
	private Image Pinky_bas;
	private Image Invisible_droite;
	private Image Invisible_haut;
	private Image Invisible_gauche;
	private Image Invisible_bas;
	
	private int tempo = 0;
	private int bonus = 0;
	
	public Niveau(int niv){
		this.setLayout(new BorderLayout());
		this.i=niv;
		for (Personnage f : ListePerso[i])   //On initialise les fant�mes d'un niveau
			Liste.add(f);
		if (i==0){
			Point = Point0;
			dimX = Point[0].length;
			dimY = Point.length;
			e = 100;    
			x0 = 50;   
			xmax = x0+(dimX-1)*e;
			y0 = 25;
			ymax = y0+(dimY-1)*e;
			cheminBase = 84;
			Liste.get(0).setpos0(x0, y0, 'd');
			Liste.get(1).setpos0(x0+6*e, y0, 'd');
			Liste.get(2).setpos0(x0+7*e, y0, 'd');
			Liste.get(3).setpos0(x0+8*e, y0, 'd');
			Liste.get(4).setpos0(x0+9*e, y0, 'd');
		}
		else if (i==1 || i==2){
			Point = Point1;
			dimX = Point[0].length;
			dimY = Point.length;
			e = 100;    // L'�cart doit �tre un multiple de 10
			x0 = 150;   // Ces 4 param�tres doivent �tre sup�rieur ou �gaux � -100
			xmax = x0+(dimX-1)*e;
			y0 = 25;
			ymax = y0+(dimY-1)*e;
			cheminBase = 72;
			bonus = 500+500*i;
			cheminInit = 1500;
			cheminmax = 1500;
			Liste.get(0).setpos0(x0+2*e, y0+4*e, 'd');
			Liste.get(1).setpos0(x0+3*e, y0+2*e, 'h');
		}
		else if (i==3){
			Point = Point1;
			Point[0][3].setGom(new Gom("Fraise"));
			dimX = Point[0].length;
			dimY = Point.length;
			e = 100;    // L'�cart doit �tre un multiple de 10
			x0 = 150;   // Ces 4 param�tres doivent �tre sup�rieur ou �gaux � -100
			xmax = x0+(dimX-1)*e;
			y0 = 25;
			ymax = y0+(dimY-1)*e;
			cheminBase = 79;
			bonus = 1500;
			cheminInit = 1500;
			cheminmax = 1500;
			Liste.get(0).setpos0(x0+2*e, y0+4*e, 'd');
			Liste.get(1).setpos0(x0+3*e, y0+2*e, 'h');
			Liste.get(2).setpos0(x0+3*e, y0+2*e, 'g');
			Liste.get(3).setpos0(x0+3*e, y0+2*e, 'd');
		}
		else if (i==4 || i==5){
			Point = Point2;
			dimX = Point[0].length;
			dimY = Point.length;
			e = 100;    // L'�cart doit �tre un multiple de 10
			x0 = 85;   // Ces 4 param�tres doivent �tre sup�rieur ou �gaux � -100
			xmax = x0+(dimX-1)*e;
			y0 = 25;
			ymax = y0+(dimY-1)*e;
			cheminBase = 77;
			if (i==4)
				Point[5][3].setNom("Piece");
			else {
				nbIter = 11;
				setValeurs();
			}
			bonus = 2500;
			cheminInit = 1000;
			cheminmax = 2000;
			Liste.get(0).setpos0(x0+4*e, y0+1*e, 'd');
			Liste.get(1).setpos0(x0+2*e, y0+2*e, 'b');
			Liste.get(2).setpos0(x0+7*e, y0+3*e, 'h');
		}
		else if (i==6){
			Point = Point3;
			dimX = Point[0].length;
			dimY = Point.length;
			nbIter = 17;
			e = 50;    // L'�cart doit �tre un multiple de 10
			x0 = 250;   // Ces 4 param�tres doivent �tre sup�rieur ou �gaux � 0
			xmax = x0+(dimX-1)*e;
			y0 = 50;
			ymax = y0+(dimY-1)*e;
			cheminBase = 65;
			setValeurs();
			bonus = 5000;
			cheminInit = 1000;
			cheminmax = 2500;
			setValeurs();
			Liste.get(0).setpos0(x0+6*e, y0+7*e, 'd');
			Liste.get(1).setpos0(x0+3*e, y0+5*e, 'h');
			Liste.get(2).setpos0(x0+3*e, y0+5*e, 'd');
			Liste.get(3).setpos0(x0+9*e, y0+5*e, 'h');
			Liste.get(4).setpos0(x0+9*e, y0+5*e, 'g');
		}
		else if (i==7){
			Point = Point4;
			dimX = Point[0].length;
			dimY = Point.length;
			nbIter = 20;
			e = 50;    // L'�cart doit �tre un multiple de 10
			x0 = 250;   // Ces 4 param�tres doivent �tre sup�rieur ou �gaux � 0
			xmax = x0+(dimX-1)*e;
			y0 = 0;
			ymax = y0+(dimY-1)*e;
			cheminBase = 83;
			setValeurs();
			bonus = 8000;
			cheminInit = 1500;
			cheminmax = 2500;
			setValeurs();
			Liste.get(0).setpos0(x0+8*e, y0+6*e, 'd');
			Liste.get(1).setpos0(x0+4*e, y0+10*e, 'g');
			Liste.get(2).setpos0(x0+11*e, y0+3*e, 'b');
			Liste.get(3).setpos0(x0+11*e, y0+10*e, 'h');
			Liste.get(4).setpos0(x0+4*e, y0+3*e, 'd');
		}
		else if (i==8){
			Point = Point5;
			dimX = Point[0].length;
			dimY = Point.length;
			nbIter = 15;
			e = 50;    // L'�cart doit �tre un multiple de 10
			x0 = 350;   // Ces 4 param�tres doivent �tre sup�rieur ou �gaux � 0
			xmax = x0+(dimX-1)*e;
			y0 = 100;
			ymax = y0+(dimY-1)*e;
			cheminBase = 32;
			setValeurs();
			bonus = 5000;
			cheminInit = 800;
			cheminmax = 2000;
			setValeurs();
			Liste.get(0).setpos0(x0+4*e, y0+4*e, 'd');
			Liste.get(1).setpos0(x0, y0, 'd');
			Liste.get(2).setpos0(x0+8*e, y0, 'b');
			Liste.get(3).setpos0(x0+8*e, y0+8*e, 'g');
			Liste.get(4).setpos0(x0, y0+8*e, 'h');
		}
		reboot();
	}
	
	public void setValeurs(){
		BaseX = new LinkedList<Integer>();
		BaseY = new LinkedList<Integer>();
		for (int i = 0; i<dimY; i++)
			for (int j =0; j<dimX; j++){
				if (Point[i][j].getNom() == "Camp"){
					BaseX.add(x0+j*e);
					BaseY.add(y0+i*e);
					newTabValeur(i,j,0);
				}
			}
	}
	public void newTabValeur(int i, int j, int val){
		Case c = Point[i][j];
		if (c.getValeur2()>val)
			c.setValeur2(val);
		if (val<nbIter){
			if (!c.getHaut())
				newTabValeur((i==0) ? i=dimY-1 : i-1,j,val+1);
			if (!c.getGauche())
				newTabValeur(i,(j==0) ? j=dimX-1 : j-1,val+1);
			if (!c.getBas())
				newTabValeur((i==dimY-1) ? i=0 : i+1,j,val+1);
			if (!c.getDroite())
				newTabValeur(i,(j==dimX-1) ? j=0 : j+1,val+1);
		}
	}

  public void paintComponent(Graphics g){
	  
	if (tempo==0){
		try {
			Pacman_droite = ImageIO.read(new File("images/Pacman_droite_"+ e +".png"));
			Pacman_haut = ImageIO.read(new File("images/Pacman_haut_"+ e +".png"));
			Pacman_gauche = ImageIO.read(new File("images/Pacman_gauche_"+ e +".png"));
			Pacman_bas = ImageIO.read(new File("images/Pacman_bas_"+ e +".png"));
			Blinky_droite = ImageIO.read(new File("images/Blinky_droite_"+ e +".png"));
			Blinky_haut = ImageIO.read(new File("images/Blinky_haut_"+ e +".png"));
			Blinky_gauche = ImageIO.read(new File("images/Blinky_gauche_"+ e +".png"));
			Blinky_bas = ImageIO.read(new File("images/Blinky_bas_"+ e +".png"));
			Inky_droite = ImageIO.read(new File("images/Inky_droite_"+ e +".png"));
			Inky_haut = ImageIO.read(new File("images/Inky_haut_"+ e +".png"));
			Inky_gauche = ImageIO.read(new File("images/Inky_gauche_"+ e +".png"));
			Inky_bas = ImageIO.read(new File("images/Inky_bas_"+ e +".png"));
			Clyde_droite = ImageIO.read(new File("images/Clyde_droite_"+ e +".png"));
			Clyde_haut = ImageIO.read(new File("images/Clyde_haut_"+ e +".png"));
			Clyde_gauche = ImageIO.read(new File("images/Clyde_gauche_"+ e +".png"));
			Clyde_bas = ImageIO.read(new File("images/Clyde_bas_"+ e +".png"));
			Pinky_droite = ImageIO.read(new File("images/Pinky_droite_"+ e +".png"));
			Pinky_haut = ImageIO.read(new File("images/Pinky_haut_"+ e +".png"));
			Pinky_gauche = ImageIO.read(new File("images/Pinky_gauche_"+ e +".png"));
			Pinky_bas = ImageIO.read(new File("images/Pinky_bas_"+ e +".png"));
			Invisible_droite = ImageIO.read(new File("images/Invisible_droite_"+ e +".png"));
			Invisible_haut = ImageIO.read(new File("images/Invisible_haut_"+ e +".png"));
			Invisible_gauche = ImageIO.read(new File("images/Invisible_gauche_"+ e +".png"));
			Invisible_bas = ImageIO.read(new File("images/Invisible_bas_"+ e +".png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
		
  
	g.setColor(Color.BLUE);
	
	g.fillRect(0, 0, this.getWidth(), this.getHeight());
	
	g.setColor(Color.BLACK);
	
	for (int row=0; row<dimY; row++){
		for (int col=0; col<dimX; col++){
			if (Point[row][col].getBas()){
				g.drawLine(x0+e*col, y0+e*(row+1), x0+e*(col+1), y0+e*(row+1));
				g.drawLine(x0+e*col, y0+e*(row+1)+1, x0+e*(col+1), y0+e*(row+1)+1);
			}
			if (Point[row][col].getDroite()){
				g.drawLine(x0+e*(col+1), y0+e*row, x0+e*(col+1), y0+e*(row+1));
				g.drawLine(x0+e*(col+1)+1, y0+e*row, x0+e*(col+1)+1, y0+e*(row+1));
			}
			if (Point[row][col].getHaut()){
				g.drawLine(x0+e*col, y0+e*row, x0+e*(col+1), y0+e*row);
				g.drawLine(x0+e*col, y0+e*row+1, x0+e*(col+1), y0+e*row+1);
			}
			if (Point[row][col].getGauche()){
				g.drawLine(x0+e*col, y0+e*row, x0+e*col, y0+e*(row+1));
				g.drawLine(x0+e*col+1, y0+e*row, x0+e*col+1, y0+e*(row+1));
			}
		}
	}
	
	g.setColor(Color.WHITE);
	
	for (int row=0; row<dimY; row++){
		for (int col=0; col<dimX; col++){
			if (Point[row][col].getNom() == "Clapet"){
				if (Point[row][col].getBas() && (Point[row][col].getDir1() == 'b' || Point[row][col].getDir2() == 'b')){
					g.drawLine(x0+e*col, y0+e*(row+1), x0+e*(col+1), y0+e*(row+1));
					g.drawLine(x0+e*col, y0+e*(row+1)+1, x0+e*(col+1), y0+e*(row+1)+1);
				}
				if (Point[row][col].getDroite() && (Point[row][col].getDir1() == 'd' || Point[row][col].getDir2() == 'd')){
					g.drawLine(x0+e*(col+1), y0+e*row, x0+e*(col+1), y0+e*(row+1));
					g.drawLine(x0+e*(col+1)+1, y0+e*row, x0+e*(col+1)+1, y0+e*(row+1));
				}
				if (Point[row][col].getHaut() && (Point[row][col].getDir1() == 'h' || Point[row][col].getDir2() == 'h')){
					g.drawLine(x0+e*col, y0+e*row, x0+e*(col+1), y0+e*row);
					g.drawLine(x0+e*col, y0+e*row+1, x0+e*(col+1), y0+e*row+1);
				}
				if (Point[row][col].getGauche() && (Point[row][col].getDir1() == 'g' || Point[row][col].getDir2() == 'g')){
					g.drawLine(x0+e*col, y0+e*row, x0+e*col, y0+e*(row+1));
					g.drawLine(x0+e*col+1, y0+e*row, x0+e*col+1, y0+e*(row+1));
				}
			}
		}
	}
    
    g.setColor(Color.YELLOW);
    
    for (int i = 0; i<dimY; i++){
    	for (int j = 0; j<dimX; j++){
    		Case t = Point[i][j];
    		if(t.getNom()=="Piece"){
    			g.fillOval(e*j+x0+(e*3/10), e*i+y0+(e*3/10), e*2/5, e*2/5);
    		}
    		else if (t.isGomme() && t.getNom()!=""){
    			if (e==100)
    				g.drawImage(t.getGom().picture[0], x0+j*e+e/10, y0+i*e+e/10, this);
    			else if (e==50)
    				g.drawImage(t.getGom().picture[1], x0+j*e+e/10, y0+i*e+e/10, this);
    		}
    	}
    }
    for (int f = 0; f<Liste.size(); f++){
    	Personnage perso = Liste.get(f);
    	if (perso.getNom() == "Pacman"){
    		g.setColor(Color.YELLOW);
    		if (perso.getD()=='d')
    			if (tempo%60<30)
    				g.drawImage(Pacman_droite, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    			else
    				g.fillOval(perso.getPosX()+e/10, perso.getPosY()+e/10, e*4/5, e*4/5);
    		else if(perso.getD()=='h')
    			if (tempo%60<30)
    				g.drawImage(Pacman_haut, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    			else
    				g.fillOval(perso.getPosX()+e/10, perso.getPosY()+e/10, e*4/5, e*4/5);
    		else if(perso.getD()=='g')
    			if (tempo%60<30)
    				g.drawImage(Pacman_gauche, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    			else
    				g.fillOval(perso.getPosX()+e/10, perso.getPosY()+e/10, e*4/5, e*4/5);
    		else if(perso.getD()=='b')
    			if (tempo%60<30)
    				g.drawImage(Pacman_bas, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    			else
    				g.fillOval(perso.getPosX()+e/10, perso.getPosY()+e/10, e*4/5, e*4/5);
    	}
    	else if ((perso.getNom() == "Clyde") || (perso.getNom() == "Test")){
    		if (perso.getD()=='d')
    			g.drawImage(Clyde_droite, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='h')
    			g.drawImage(Clyde_haut, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='g')
    			g.drawImage(Clyde_gauche, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='b')
    			g.drawImage(Clyde_bas, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    	}
    	else if (perso.getNom() == "Blinky"){
    		if (perso.getD()=='d')
    			g.drawImage(Blinky_droite, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='h')
    			g.drawImage(Blinky_haut, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='g')
    			g.drawImage(Blinky_gauche, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='b')
    			g.drawImage(Blinky_bas, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    	}
    	else if (perso.getNom() == "Pinky"){
    		if (perso.getD()=='d')
    			g.drawImage(Pinky_droite, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='h')
    			g.drawImage(Pinky_haut, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='g')
    			g.drawImage(Pinky_gauche, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='b')
    			g.drawImage(Pinky_bas, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    	}
    	else if (perso.getNom() == "Inky"){
    		if (perso.getD()=='d')
    			g.drawImage(Inky_droite, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='h')
    			g.drawImage(Inky_haut, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='g')
    			g.drawImage(Inky_gauche, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		else if(perso.getD()=='b')
    			g.drawImage(Inky_bas, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    	}
    	else if(perso.getNom() == "FantomeMange"){
    		if (perso.getD()=='d')
    			g.drawImage(Invisible_droite, perso.getPosX()+e/10+(12*e)/50, perso.getPosY()+e/10+(12*e)/50, this);
    		else if(perso.getD()=='h')
    			g.drawImage(Invisible_haut, perso.getPosX()+e/10+(9*e)/50, perso.getPosY()+e/10+(6*e)/50, this);
    		else if(perso.getD()=='g')
    			g.drawImage(Invisible_gauche, perso.getPosX()+e/10+(4*e)/50, perso.getPosY()+e/10+(12*e)/50, this);
    		else if(perso.getD()=='b')
    			g.drawImage(Invisible_bas, perso.getPosX()+e/10+(9*e)/50, perso.getPosY()+e/10+(17*e)/50, this);
    		}
    	else if (perso.getNom() == "Peur"){
    			if (perso.getD()=='d')
    				g.drawImage(Inky_droite, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    			else if(perso.getD()=='h')
    				g.drawImage(Inky_haut, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    			else if(perso.getD()=='g')
    				g.drawImage(Inky_gauche, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    			else if(perso.getD()=='b')
    				g.drawImage(Inky_bas, perso.getPosX()+e/10, perso.getPosY()+e/10, this);
    		}
    	}
    tempo+=100/e;
  }               
 
  public void reboot(){
	  for (int p = 0; p<Liste.size(); p++){
		  Liste.get(p).setpos();
		  Liste.get(p).setDepl();
	  }
  }
  
  public Case[][] getPoint(){
	  return Point;
  }
  
  public void setPoint(int i, int j, String valeur){
	  this.Point[i][j].setNom(valeur);
  }
  
  public int getEcart(){
	  return e;
  }
  
  public int getX0(){
	  return x0;
  }
  
  public int getY0(){
	  return y0;
  }
  
  public int getXmax(){
	  return xmax;
  }
  
  public int getYmax(){
	  return ymax;
  }
  
  public char[] getDepl(){
	  return depl;
  }
  
  public void FantomesToString(){
	  for (Personnage perso : Liste)
		  perso.toString();
	  System.out.println();
  }
  
  public String toString(){
	  String s = "";
	  for (int i =0; i<dimY; i++){
		  for (int j = 0; j<dimX; j++){
			  s+=Integer.toString(Point[i][j].getValeur2());
			  s+=" ";
		  }
		  System.out.println(s);
		  s="";
	  }
	  return "";
  }

public int getCheminmax() {
	return cheminmax;
}

public void setCheminmax(int cheminmax) {
	this.cheminmax = cheminmax;
}

public int getCheminInit() {
	return cheminInit;
}

public void setCheminInit(int cheminInit) {
	this.cheminInit = cheminInit;
}

public int getChasseAuxFantomes() {
	return ChasseAuxFantomes;
}

public void setChasseAuxFantomes(int chasseAuxFantomes) {
	ChasseAuxFantomes = chasseAuxFantomes;
}

public int getCheminBase() {
	return cheminBase;
}

public void setCheminBase(int cheminBase) {
	this.cheminBase = cheminBase;
}

public int[][][] getTunnel() {
	return Tunnel;
}

public void setTunnel(int[][][] tunnel) {
	Tunnel = tunnel;
}

public int getBonus() {
	return bonus;
}

public void setBonus(int bonus) {
	this.bonus = bonus;
}

public List<Personnage> getListe() {
	return Liste;
}

public void setListe(List<Personnage> liste) {
	Liste = liste;
}

public List<Integer> getBaseX() {
	return BaseX;
}

public void setBaseX(List<Integer> baseX) {
	BaseX = baseX;
}

public List<Integer> getBaseY() {
	return BaseY;
}

public void setBaseY(List<Integer> baseY) {
	BaseY = baseY;
}
}
