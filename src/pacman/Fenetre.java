package pacman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fenetre extends JFrame implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private String mod;
	private static List<String> utilisateurs;
	private int NumActuel;
	private Compte actuel;
	private ImageIcon[] images = {new ImageIcon("images/Pacman_droite_50.png"), 
			new ImageIcon("images/Blinky_droite_50.png"),
			new ImageIcon("images/Pinky_droite_50.png"), 
			new ImageIcon("images/Clyde_droite_50.png"), 
			new ImageIcon("images/Inky_droite_50.png"), 
			new ImageIcon("images/Cerise_50.png"), 
			new ImageIcon("images/Fraise_50.png"),
			new ImageIcon("images/Orange_50.png")};
	private List<Compte> ListeUtilisateurs = new LinkedList<Compte>();
	private int vie = 3;
	private boolean pause = false;
	private boolean go = false;
	private int temps = 0;
	private boolean trouve = false;
	private int score = 0;
	private int[] Highscores = new int[10];
	private String[] noms = new String[10];
	private int bonus = 0;
	private int n = 0;     //Compteur de Niveau
	private static int nmax = 8;
	private static int[][] scores = new int[nmax][5];
	private static String[][] names = new String[nmax][5];
	private Boolean perdu = false;
	private Case[][] Point;
	private int c = 0;  //Compteur de Pièces
	private JLabel info = new JLabel("");
	private JLabel Score = new JLabel("Score : " + Integer.toString(score));
	private JLabel Bonus = new JLabel("Bonus temps : " + Integer.toString(0));
	private JLabel vies = new JLabel("Lives : " + vie);
	private Bouton Choix;
	private Bouton Creer;
	private Bouton Options;
	private Bouton Highscore;
	private Bouton check;
	private Bouton Menu;
	private Bouton Rejouer;
	private JTextField box;
	private String nom;
	private int e = 0;
	private int x0 = 0;
	private int y0 = 0;
	private int xmax = 0;
	private int ymax = 0;
	private int dimX = 0;
	private int dimY = 0;
	private Niveau pan;   // Panneau du niveau actuel
		
  public Fenetre(){
	  
	  while (true){              //Programme principal gérant les niveaux, n=-2 correspond à l'écran de fin
		if (n==0)
			Menu_Principal();
		else if (n>0){  //n=-1 correspond à l'écran où l'on rentre son nom dans le Highscore
			pan = new Niveau(n);
			Niveau();
		}
		else if (n==-2){
			setHighscores();
			if (Highscores[9]<score){
				setHighscores(actuel.nom);
			}
			GameOver();
			while(n==-2){
				pause(100);
			}
		}
		else if (n==-3){
			ChangerIcon();
		}
		else if (n==-4){
			ChoixCompte();
		}
		else if (n==-5){
			PageProfil();
		}
		else if (n==-6){
			ChoixNiveau();
		}
	  } 
  }
  
  public void Menu_Principal(){
	  
	  this.setTitle("Menu Principal");
	  this.setSize(1200, 700);
	  this.setLocationRelativeTo(null);               
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  pan = new Niveau(0);
	  pan.setLayout(new GridLayout(6, 1, 25, 25));
	  JLabel Titre = new JLabel("Pacman");
	  Font police = new Font("Tahoma", Font.BOLD, 60);
	  Titre.setFont(police);
	  Titre.setHorizontalAlignment(JLabel.CENTER);
	  Choix = new Bouton("Choose an account");
	  Choix.setPreferredSize(new Dimension(400,100));
	  Choix.addActionListener(this);
	  Creer = new Bouton("Create an account");
	  Creer.setPreferredSize(new Dimension(400,100));
	  Creer.addActionListener(this);
	  Options = new Bouton("Options");
	  Options.addActionListener(this);
	  Options.setPreferredSize(new Dimension(400,100));
	  Highscore = new Bouton("Highscores");
	  Highscore.addActionListener(this);
	  Highscore.setPreferredSize(new Dimension(400,100));
	  pan.add(new JLabel());
	  pan.add(Titre);
	  pan.add(Choix);
	  pan.add(Creer);
	  pan.add(Options);
	  pan.add(Highscore);
	  this.setContentPane(pan);
	  this.setVisible(true);
	  Point = pan.getPoint();
	  dimX = Point[0].length;
	  dimY = Point.length;
	  e = pan.getEcart();
	  x0 = pan.getX0();
	  y0 = pan.getY0();
	  xmax = pan.getXmax();
	  ymax = pan.getYmax();
	  while(n==0){  //Animation du menu principal
		  for (Personnage perso : pan.getListe()){
			  perso.setPosX(droite(perso));
			  pan.repaint();
		  }
		  pause(5);
	  }
  }
  
  public void Pan_Nom(){
	  JPanel Pan_Nom = new JPanel();
	  JPanel entree = new JPanel();
	  Pan_Nom.setLayout(new GridLayout(4,1));
	  check = new Bouton("ok");
	  check.addActionListener(this);
	  JLabel info = new JLabel("Congratulations, please enter your name");
	  info.setFont(new Font("Tahoma", Font.BOLD, 20));
	  info.setHorizontalAlignment(JLabel.CENTER);
	  box = new JTextField();
	  box.setPreferredSize(new Dimension(200,50));
	  entree.add(box);
	  entree.add(check);
	  Pan_Nom.add(info);
	  Pan_Nom.add(entree);
	  this.setContentPane(Pan_Nom);
	  this.setTitle("Welcome to Highscores");
	  this.setSize(1200, 700);
	  this.setLocationRelativeTo(null);               
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.setVisible(true);
  }
  public void GameOver(){
	  
	  JPanel Highscore = new JPanel();
	  Highscore.setLayout(new GridLayout(14,1));
	  Highscore.setBackground(Color.BLACK);
		
	  Font police = new Font("Tahoma", Font.BOLD, 50);
	  JLabel gameover = new JLabel("Game Over");
	  gameover.setForeground(Color.RED);
	  gameover.setFont(police);
	  Highscore.add(gameover);
		
	  Score.setText("Your score : " + Integer.toString(score));
	  Score.setFont(police);
	  Score.setForeground(Color.RED);
	  Highscore.add(Score);
		
	  gameover.setHorizontalAlignment(JLabel.CENTER);
	  Score.setHorizontalAlignment(JLabel.CENTER);
	  
	  for (int i = 0; i<10; i++){
		  JLabel h = new JLabel(noms[i] + " : " + Integer.toString(Highscores[i]));
		  h.setFont(police);
		  h.setForeground(Color.YELLOW);
		  Highscore.add(h);
		  h.setHorizontalAlignment(JLabel.CENTER);
	  }
	  
	  JPanel Box = new JPanel();
	  Box.setBackground(Color.BLACK);
	  
	  Rejouer = new Bouton("Rejouer");
	  Box.add(Rejouer);
	  Rejouer.addActionListener(this);
	  
	  Menu = new Bouton("Menu");
	  Box.add(Menu);
	  Menu.addActionListener(this);
	  
	  Highscore.add(Box);
		
	  this.setTitle("Menu Principal");
	  this.setSize(1200, 700);
	  this.setLocationRelativeTo(null);               
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.setContentPane(Highscore);
	  this.setVisible(true);
  }
  
  @SuppressWarnings("unchecked")
public void CreerCompte(String nom){
	  if (nom == null){
		  return;
	  }
	  
	  actuel = new Compte(nom);
	  
	  ObjectInputStream ois;
	  try {
		  ois = new ObjectInputStream(
				  new BufferedInputStream(
					 new FileInputStream(
						new File("utilisateurs.txt"))));
	        	
		  utilisateurs = (List<String>)ois.readObject();
		  ois.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } catch (ClassNotFoundException e) {
		e.printStackTrace();
	  }
	  
	  utilisateurs.add(actuel.nom);
	  
	  ObjectOutputStream oos;
	  ObjectOutputStream util;
	  try {
		  oos = new ObjectOutputStream(
				  new BufferedOutputStream(
					 new FileOutputStream(
						new File("compte" + nom + ".txt"))));
		  
		  util = new ObjectOutputStream(
				  new BufferedOutputStream(
					 new FileOutputStream(
						new File("utilisateurs.txt"))));
	        	
		  oos.writeObject(actuel);
		  util.writeObject(utilisateurs);
		  oos.close();
		  util.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } 
	  n=0;
  }
  
  public void FinNiveau(int niv, boolean gagne){
	  go = false;
	  writeActuel();
	  Menu choix = new Menu(7 + ((gagne) ? 1 : 0));
	  setScore(niv);
	  for (int i =0; i<5; i++)
		  choix.addLabel(names[n-1][i] + " : " + scores[n-1][i]);
	  choix.addBouton("Replay", new Partie(n));
	  if (gagne)
		  choix.addBouton("Next Level", new Partie(n+1));
	  choix.addBouton("Main menu", new Partie(0));
	  this.setContentPane(choix);
	  this.setVisible(true);
	  while(!go){
		  pause(10);
	  }
	  score=0;
	  Score.setText("Score : " + Integer.toString(score));
	  vie = 3;
	  vies.setText("Lives : " + Integer.toString(vie));
	  c=0;
  }
  
  public void setScore(int niv){
	  lireScore();
	  int remplace = scores[niv-1][4];
	  int i =4;
	  while(score>remplace && i>=0){
		  i--;
		  if (i>=0)
			  remplace = scores[niv-1][i];
	  }
	  for (int j = 4; j>i+1; j--){
		  scores[niv-1][j] = scores[niv-1][j-1];
		  names[niv-1][j] = names[niv-1][j-1];
	  }
	  if (i!=4){
		  scores[niv-1][i+1] = score;
		  names[niv-1][i+1] = actuel.nom;
	  }
	  ecrireScore();
  }
  
  public void lireScore(){
	  ObjectInputStream util;
	  ObjectInputStream ois;
	  try {
		  util = new ObjectInputStream(
				  new BufferedInputStream(
					 new FileInputStream(
						new File("Scores.txt"))));
		  
		  ois = new ObjectInputStream(
				  new BufferedInputStream(
					 new FileInputStream(
						new File("NomScores.txt"))));
	        	
		  scores = (int[][])util.readObject();
		  names = (String[][])ois.readObject();
		  util.close();
		  ois.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } catch (ClassNotFoundException e) {
		e.printStackTrace();
	} 
  }
  
  public static void ecrireScore(){
	  ObjectOutputStream util;
	  ObjectOutputStream oos;
	  try {
		  util = new ObjectOutputStream(
				  new BufferedOutputStream(
					 new FileOutputStream(
						new File("Scores.txt"))));
		  
		  oos = new ObjectOutputStream(
				  new BufferedOutputStream(
					 new FileOutputStream(
						new File("NomScores.txt"))));
	        	
		  util.writeObject(scores);
		  util.close();
		  oos.writeObject(names);
		  oos.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } 
  }
  
  public void ChargerCompte(String nom){
	  ObjectInputStream ois;
	  try {
		  ois = new ObjectInputStream(
				  new BufferedInputStream(
					 new FileInputStream(
						new File("compte" + nom + ".txt"))));
	        	
		  actuel = new Compte((Compte)ois.readObject());
		  ois.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } catch (ClassNotFoundException e) {
		e.printStackTrace();
	  }
	  ChoixGame();
  }
  
  public void ChoixCompte(){
	  go = false;
	  ChargerUtil();
	  Menu choix = new Menu(utilisateurs.size());
	  choix.setLayout(new GridLayout(4,3,10,10));
	  for (int i = 0; i<utilisateurs.size(); i++){
		  choix.addBouton(utilisateurs.get(i), new ChoixCompte(utilisateurs.get(i), i), images[ListeUtilisateurs.get(i).image]);
	  }
	  choix.addBouton("Main menu", new Partie(0), images[4]);
	  this.setContentPane(choix);
	  this.setVisible(true);
	  while(!go){
		  pause(100);
	  }
	  try{
		  ChargerCompte(actuel.nom);
	  }
	  catch(NullPointerException e){}
  }
  
  @SuppressWarnings("unchecked")
public void ChargerUtil(){
	  ObjectInputStream ois;
	  try {
		  ois = new ObjectInputStream(
				  new BufferedInputStream(
					 new FileInputStream(
						new File("utilisateurs.txt"))));
	        	
		  utilisateurs = (List<String>)ois.readObject();
		  ois.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } catch (ClassNotFoundException e) {
		e.printStackTrace();
	  }
	  
	  ObjectOutputStream util;
	  try {
		  util = new ObjectOutputStream(
				  new BufferedOutputStream(
					 new FileOutputStream(
						new File("utilisateurs.txt"))));
	        	
		  util.writeObject(utilisateurs);
		  util.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } 
	  
	  for (int i = 0; i<utilisateurs.size(); i++){
		  ObjectInputStream ois2;
		  ObjectOutputStream oos2;
		  try {
			  ois2 = new ObjectInputStream(
					  new BufferedInputStream(
						 new FileInputStream(
							new File("compte" + utilisateurs.get(i) + ".txt"))));
			  
			  oos2 = new ObjectOutputStream(
					  new BufferedOutputStream(
						 new FileOutputStream(
							new File("compte" + utilisateurs.get(i) + ".txt"))));
		        	
			  ListeUtilisateurs.add((Compte)ois2.readObject());
			  ois2.close();
			  oos2.writeObject(ListeUtilisateurs.get(i));
			  oos2.close();
		      
		  } catch (FileNotFoundException e) {
		  	  e.printStackTrace();
		  } catch (IOException e) {
		      e.printStackTrace();
		  } catch (ClassNotFoundException e) {
			e.printStackTrace();
		  }
	  }
  }
  
  public class Supress implements ActionListener{
	  public int num;
	  public Supress(String name){
		  for (int i =0; i<utilisateurs.size(); i++){
			  if (utilisateurs.get(i).equals(name)){
				  this.num = i;
			  }
		  }
	  }
	  public void actionPerformed(ActionEvent e){
		  utilisateurs.remove(num);
		  writeUtil();
		  n=0;
		  go=true;
	  }
  }
  
  public class Partie implements ActionListener{
	  public int num;
	  public Partie(int i){
		  this.num = i;
	  }
	  public void actionPerformed(ActionEvent e){
		  n=num;
		  go=true;
	  }
  }
  
  public class ChoixCompte implements ActionListener{
	  public String nom;
	  public int num;
	  public ChoixCompte(String nom, int num){
		  this.nom = nom;
		  this.num = num;
	  }
	  public void actionPerformed(ActionEvent e){
		  actuel = new Compte(nom);
		  NumActuel = num;
		  go=true;
	  }
  }
  
  public class ChangeIcon implements ActionListener{
	  public int chgt;
	  public ChangeIcon(int icon){
		  chgt = icon;
	  }
	  public void actionPerformed(ActionEvent e){
		  ListeUtilisateurs.get(NumActuel).image = chgt;
		  n=0;
		  go=true;
	  }
  }
  
  public void ChoixGame(){
	  go = false;
	  Menu choix = new Menu(5);
	  choix.addLabel("Game mod");
	  choix.addBouton("Aventure",new Mode("Aventure"), images[0]);
	  choix.addBouton("Complete tour",new Mode("Complete tour"), images[1]);
	  choix.addBouton("My Profile",new Partie(-5), images[2]);
	  choix.addBouton("Main menu", new Partie(0), images[3]);
	  this.setContentPane(choix);
	  this.setVisible(true);
	  while(!go){
		  pause(100);
	  }
  }
  
  public static void writeUtil(){
	  ObjectOutputStream util;
	  try {
		  util = new ObjectOutputStream(
				  new BufferedOutputStream(
					 new FileOutputStream(
						new File("utilisateurs.txt"))));
	        	
		  util.writeObject(utilisateurs);
		  util.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } 
  }
  
  public void writeActuel(){
	  ObjectOutputStream util;
	  try {
		  util = new ObjectOutputStream(
				  new BufferedOutputStream(
					 new FileOutputStream(
						new File("compte" + actuel.nom + ".txt"))));
		  
		  util.writeObject(actuel);
		  util.close();
	      
	  } catch (FileNotFoundException e) {
	  	  e.printStackTrace();
	  } catch (IOException e) {
	      e.printStackTrace();
	  } 
  }
  
  public void PageProfil(){
	  Menu choix = new Menu(6);
	  choix.addLabel(actuel.nom);
	  choix.addLabel("Level : " + actuel.niveau);
	  choix.addLabel("Expérience : " + actuel.xp);
	  choix.addBouton("Change profile icon", new Partie(-3), images[0]);
	  choix.addBouton("Delete profile", new Supress(actuel.nom), images[1]);
	  choix.addBouton("Main menu", new Partie(0), images[4]);
	  this.setContentPane(choix);
	  this.setVisible(true);
	  go=false;
	  while(!go){
		  pause(10);
	  }
  }
  
  public void ChangerIcon(){
	  go = false;
	  Menu choix = new Menu(9);
	  for (int i = 0; i<8; i++)
		  choix.addBouton("", new ChangeIcon(i), images[i]);
	  choix.addBouton("Cancel", new Partie(0));
	  this.setContentPane(choix);
	  this.setVisible(true);
	  while (!go){
		  pause(10);
	  }
  }
  
  public void ChoixNiveau(){
	  go = false;
	  Menu choix = new Menu(nmax+1);
	  for (int i = 1; i<=nmax; i++){
		  choix.addBouton(Integer.toString(i), new Partie(i), actuel.dispo[i-1]);
	  }
	  choix.addBouton("Main menu", new Partie(0), images[4]);
	  this.setContentPane(choix);
	  this.setVisible(true);
	  while(!go){
		  pause(100);
	  }
  }
  
  public class Mode implements ActionListener{
	  public String mode;
	  public Mode(String mode){
		  this.mode = mode;
	  }
	  public void actionPerformed(ActionEvent e){
		  if (mode == "Aventure"){
			  n=-6;
			  mod = "Aventure";
		  }
		  else{
			  n=1;
			  mod = "Complete tour";
		  }
		  go = true;
	  }
  }
  
  public int[] readTab(String nom){
	  FileInputStream h = null;
	  int[] tab = new int[10];
	  try{
		  h = new FileInputStream(new File(nom + ".txt"));
		  int n = h.read();
		  for (int i = 0; i<10; i++){
			  String s = "";
			  while(n!=47 && n!=-1){  //47 est le /
				  char c = (char)n;
				  s+=c;
				  n=h.read();
			  }
			  n=h.read();
			  tab[i] = Integer.valueOf(s);
		  }
		  h.close();
	  }
	  catch (FileNotFoundException e) {
	         e.printStackTrace();
	  } 
	  catch (IOException e) {
	         e.printStackTrace();
	  }
	  return tab;
  }
  public String[] readNom(String nom){
	  FileInputStream h = null;
	  String[] tab = new String[10];
	  try{
		  h = new FileInputStream(new File(nom + ".txt"));
		  int n = h.read();
		  for (int i = 0; i<10; i++){
			  String s = "";
			  while(n!=47 && n!=-1){  //47 est le /
				  char c = (char)n;
				  s+=c;
				  n=h.read();
			  }
			  n=h.read();
			  tab[i] = s;
		  }
		  h.close();
	  }
	  catch (FileNotFoundException e) {
	         e.printStackTrace();
	  } 
	  catch (IOException e) {
	         e.printStackTrace();
	  }
	  return tab;
  }
  
  public void setHighscores(){
	  Highscores = readTab("Highscores");
	  noms = readNom("Noms");
	  writeHighscores();
	  writeNoms();
  }
  
  public void setHighscores(String name){
	  Highscores = readTab("Highscores");
	  noms = readNom("Noms");
	  if (score>Highscores[9]){
		  Highscores[9] = score;
		  noms[9] = name;
		  boolean ok = false;
		  int i = 9;
		  if (Highscores[i]<Highscores[i-1])
			  ok = true;
		  while(!ok){
			  int temp = Highscores[i-1];
			  String nom = noms[i-1];
			  Highscores[i-1] = Highscores[i];
			  noms[i-1] = noms[i];
			  Highscores[i] = temp;
			  noms[i] = nom;
			  i--;
			  if (i!=0){
				  if (Highscores[i]<Highscores[i-1])
					  ok = true;
			  }
			  else{
				  ok = true;
			  }
		  }
	  }
	  writeHighscores();
	  writeNoms();
  }
  public void writeHighscores(){
	  FileOutputStream h = null;
	  char c;
	  byte b;
	  try {
		  h = new FileOutputStream(new File("Highscores.txt"));
		  for (int i = 0; i<10; i++){
			  String s = Integer.toString(Highscores[i]);
			  int n=s.length();
			  for (int  j =0; j<n; j++){
				  c = s.charAt(j);
				  b = (byte)c;
				  h.write(b);
			  }
			  c = '/';
			  b = (byte)c;
			  h.write(b);
		  }
	  } 
	  catch (FileNotFoundException e) {
		  e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	}
  }
  public void writeNoms(){
	  FileOutputStream h = null;
	  char c;
	  byte b;
	  try {
		  h = new FileOutputStream(new File("Noms.txt"));
		  for (int i = 0; i<10; i++){
			  String s = noms[i];
			  int n=s.length();
			  for (int  j =0; j<n; j++){
				  c = s.charAt(j);
				  b = (byte)c;
				  h.write(b);
			  }
			  c = '/';
			  b = (byte)c;
			  h.write(b);
		  }
	  } 
	  catch (FileNotFoundException e) {
		  e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  public void actionPerformed(ActionEvent arg0){
	  if (arg0.getSource() == Creer){
	  		String nom = JOptionPane.showInputDialog(null, "Veuillez saisir votre nom", 
	  				"Création d'un compte", JOptionPane.QUESTION_MESSAGE);
	  		CreerCompte(nom);
	  }
	  else if (arg0.getSource() == Choix){
		 this.n=-4;
	  }
	  else if (arg0.getSource() == check){
		  nom = box.getText();
		  setHighscores(nom);
		  n=-2;
		  go=true;
	  }
	  else if (arg0.getSource() == Highscore){
		  setHighscores();
		  n=-2;
	  }
	  else if (arg0.getSource() == Menu){
		  n=0;
		  vie = 3;
		  vies.setText("Life : "+ Integer.toString(vie));
		  score = 0;
		  Score.setText("Score : " + Integer.toString(score));
		  c=0;
	  }
	  else if (arg0.getSource() == Rejouer){
		  n=1;
		  vie = 3;
		  vies.setText("Life : "+ Integer.toString(vie));
		  score = 0;
		  Score.setText("Score : " + Integer.toString(score));
		  c=0;
	  }
  }
  
  public void Niveau(){   //Initialisation des paramètres du niveau
	  	Point = pan.getPoint();
		dimX = Point[0].length;
		dimY = Point.length;
		e = pan.getEcart();
		x0 = pan.getX0();
		y0 = pan.getY0();
		xmax = pan.getXmax();
		ymax = pan.getYmax();
		
		addKeyListener(this);
		for (Personnage perso : pan.getListe())
			perso.setCheminmax(pan.getCheminmax());
	  
		for (Case[] t : Point)
			for(Case p : t)
				if (p.getNom().equals("Piece"))
					c++;
		
		JPanel msg = new JPanel();
		msg.setLayout(new BorderLayout());
		msg.setOpaque(false);
		msg.setPreferredSize(new Dimension(1150, 50));
		
		Font police = new Font("Tahoma", Font.BOLD, 30);
		info.setFont(police);
		info.setText("");
		info.setForeground(Color.YELLOW);
		msg.add(info, BorderLayout.CENTER);
		
		Bonus.setFont(police);
		Bonus.setText("Bonus : " + Integer.toString(pan.getBonus()));
		Bonus.setForeground(Color.YELLOW);
		pan.add(Bonus, BorderLayout.SOUTH);
		
		vies.setFont(police);
		vies.setForeground(Color.YELLOW);
		msg.add(vies, BorderLayout.WEST);
		
		Score.setFont(police);
		Score.setForeground(Color.YELLOW);
		Score.setPreferredSize(new Dimension(300, msg.getHeight()));
		msg.add(Score, BorderLayout.EAST);
		
		info.setHorizontalAlignment(JLabel.CENTER);
		
		pan.add(msg, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(pan);
		this.setTitle("Niveau " + n);
		this.setSize(1200, 700);
		this.requestFocus();
		this.setVisible(true);
		pan.repaint();
		go();
  }
  
  private void pause(int t){
	  if (t<=0){
		  return;
	  }
	  try{
		  Thread.sleep(t);
	  }catch(InterruptedException e) {
		  e.printStackTrace();
	  }
  }
  
  public void keyPressed(KeyEvent e) {
	  if (e.getKeyCode() == 37 || e.getKeyChar() == 'q')
		  pan.getListe().get(0).setNewDepl('g');
	  else if (e.getKeyCode() == 38 || e.getKeyChar() == 'z')
		  pan.getListe().get(0).setNewDepl('h');
	  else if (e.getKeyCode() == 39 || e.getKeyChar() == 'd')
		  pan.getListe().get(0).setNewDepl('d');
	  else if (e.getKeyCode() == 40 || e.getKeyChar() == 's')
		  pan.getListe().get(0).setNewDepl('b');
	  else if (e.getKeyChar() == 'p')
		  pause = !pause;
		  
  }
  
  public void keyReleased(KeyEvent e){}
  
  public void keyTyped(KeyEvent e){
  }
  public double distance(int x1, int y1, int x2, int y2) throws IllegalStateException{
	  double diffx = (x1>x2) ? x1-x2 : x2-x1;
	  double diffy = (y1>y2) ? y1-y2 : y2-y1;
	  double dist =  diffx+diffy;
	  return dist;
  }
  
  public double distance(Personnage p1, Personnage p2){
	  return distance(p1.getPosX(), p1.getPosY(), p2.getPosX(), p2.getPosY());
  }
  
  public double distance(Personnage p1, int x, int y){
	  return distance(p1.getPosX(), p1.getPosY(), x, y);
  }
  
  public void setValeur(Personnage centre, int val){
	  int valeur = val;
	  int X = (centre.getPosX()-x0)/e;
	  int Y = (centre.getPosY()-y0)/e;
	  if (val == 0)
		  for (int j = 0; j<dimY; j++)
			  for (int i = 0; i<dimX; i++)
				  Point[j][i].setValeur(99);
	  if (Point[Y][X].getValeur()> valeur){
		  Point[Y][X].setValeur(valeur);
		  valeur++;
		  Personnage temp = new Personnage(centre);  // On prend un personnage sans demi tour
		  temp.setpos(x0+e*X, y0+e*Y);
		  List<Character> possible;
		  if (valeur == 1){
			  possible = getAllPossibles(temp);
		  }
		  else
			  possible = getPossibles(temp);
		  for (char dir : possible){
			  Personnage temp2 = new Personnage(temp);
			  temp2.setDepl(dir);
			  temp2.setVitesse("Pacman");
			  for (int a = 0; a<e; a++)
				  deplacement(temp2);
			  setValeur(temp2, valeur);
		  }
	  }
  }
  
  public Boolean Perdu(){
	  for (int f = 1; f<pan.getListe().size(); f++){
	  	  double d = distance(pan.getListe().get(0), pan.getListe().get(f));
	  	  if (d<4*e/5){
	  		  if (pan.getChasseAuxFantomes()>0 && pan.getListe().get(f).getNom() != "FantomeMange"){
	  			  pan.getListe().get(f).setNom("FantomeMange");
	  			  pan.getListe().get(f).setComportement("Retour");
	  			  pan.getListe().get(f).setVitesse("Pacman");
	  			  score+=200;
	  		  }
	  		  else if (pan.getChasseAuxFantomes()==0 && pan.getListe().get(f).getNom() != "FantomeMange")
	  			 return true;
	  	  }
	  }
	  return false;
  }
  
  private void deplacement(Personnage perso){
	  if (temps <e && perso.getNom()!="Pacman" && perso.getNom() != "Test");
	  else if(perso.deplacement() && perso.isMobile()){
		  if (perso.getD()=='d')
			  perso.setPosX(droite(perso));
		  else if (perso.getD()=='h')
			  perso.setPosY(haut(perso));
		  else if (perso.getD()=='g')
			  perso.setPosX(gauche(perso));
		  else if (perso.getD()=='b')
			  perso.setPosY(bas(perso));
	  }
  }
	
  private int droite(Personnage perso){
	  int x = 0;
	  int y = 0;
	  int X = 0;
	  int Y = 0;
	  boolean s = false;
		  x=perso.getPosX();
		  y=perso.getPosY();
		  if (x%e==(x0+e)%e && y%e==(y0+e)%e){  //On vérifie si (x,y) est un point clé
			  X = (x-x0)/e;
			  Y = (y-y0)/e;
			  s = Point[Y][X].getDroite();
		  }
		  if (!s || perso.getNom()=="FantomeMange")
			  x++;
		  if (x==xmax+e/2+1)
			  x=x0-e/2;
		  return x;
	  }
  
  private int haut (Personnage perso){
	  int x = 0;
	  int y = 0;
	  int X = 0;
	  int Y = 0;
	  boolean s = false;
	  x=perso.getPosX();
	  y=perso.getPosY();
	  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
		  X = (x-x0)/e;
		  Y = (y-y0)/e;
		  s = Point[Y][X].getHaut();
	  }
	  if (!s || perso.getNom()=="FantomeMange")
		  y--;
	  if (y==y0-e/2-1)
		  y=ymax+e/2;
	  return y;
  }

  private int gauche(Personnage perso){
	  int x = 0;
	  int y = 0;
	  int X = 0;
	  int Y = 0;
	  boolean s = false;
	  x=perso.getPosX();
	  y=perso.getPosY();
	 if (x%e==(x0+e)%e && y%e==(y0+e)%e){
		 X = (x-x0)/e;
		 Y = (y-y0)/e;
		 s = Point[Y][X].getGauche();
	 }
	 if (!s || perso.getNom()=="FantomeMange")
		 x--;
	 if (x==x0-1-e/2)
		 x=xmax+e/2;
	 return x;
  }
  
  private int bas(Personnage perso){
	  int x = 0;
	  int y = 0;
	  int X = 0;
	  int Y = 0;
	  boolean s = false;
	  x=perso.getPosX();
	  y=perso.getPosY();
	  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
		  X = (x-x0)/e;
		  Y = (y-y0)/e;
		  s = Point[Y][X].getBas();
	  }
	  if (!s || perso.getNom()=="FantomeMange")
		  y++;
	  if (y==ymax+1+e/2)
		  y=y0-e/2;
	  return y;
  }
  
  private void choixPacman(Personnage perso){
	  int x = perso.getPosX();      
	  int y = perso.getPosY();
	  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
		  int X = (x-x0)/e;
		  int Y = (y-y0)/e;          
		  if (perso.getNewDepl()=='h'){
			  boolean s = Point[Y][X].getHaut();
			  if (!s)
				  perso.setD(perso.getNewDepl());
		  }
		  else if (perso.getNewDepl()=='b'){
			  boolean s = Point[Y][X].getBas();
			  if (!s)
				  perso.setD(perso.getNewDepl());
		  }
		  else if (perso.getNewDepl()=='d'){
			  boolean s = Point[Y][X].getDroite();
			  if (!s)
				  perso.setD(perso.getNewDepl());
		  }
		  else if (perso.getNewDepl()=='g'){
			  boolean s = Point[Y][X].getGauche();
			  if (!s)
				  perso.setD(perso.getNewDepl());
		  }
	  }
	  else if (perso.getD()=='g' && perso.getNewDepl()=='d' || perso.getD()=='d' && perso.getNewDepl()=='g' 
			  || perso.getD()=='h' && perso.getNewDepl()=='b'|| perso.getD()=='b' && perso.getNewDepl()=='h'){
		  perso.setD(perso.getNewDepl());
	  }
  
  }
  
  private void Random(Personnage perso){
	  List<Character> possible2 = getPossibles(perso);
	  int depl = (int)((Math.random()*possible2.size()));
	  perso.setD(possible2.get(depl)); 
  }
  public List<Character> getPossibles(Personnage perso){  //Donne les directions de déplacement possible pour le personnage
	  int x = perso.getPosX();
	  int y = perso.getPosY();
	  int X = (x-x0)/e;
	  int Y = (y-y0)/e;
	  char [] possible = {'d', 'g', 'h', 'b'};
	  boolean dr = Point[Y][X].getDroite();
	  boolean g = Point[Y][X].getGauche();
	  boolean b = Point[Y][X].getBas();
	  boolean h = Point[Y][X].getHaut();
	  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
		  if (perso.getD()=='d' || perso.getD()=='g'){     
			  if (h)
				  possible[2]=' ';
			  if (b)
				  possible[3]=' ';
			  if ((perso.getD()=='d' && (perso.getLimiteurDemiTour()<perso.getLimiteurDemiTour0() || !perso.isDemiTour()))|| g)
				  possible[1]=' ';
			  if ((perso.getD()=='g' && (perso.getLimiteurDemiTour()<perso.getLimiteurDemiTour0() || !perso.isDemiTour())) || dr)
				  possible[0]=' ';
		  }
		  else if (perso.getD()=='h' || perso.getD()=='b'){
			  if (dr)
				  possible[0]=' ';
			  if (g)
				  possible[1]=' ';
			  if ((perso.getD()=='h' && (perso.getLimiteurDemiTour()<perso.getLimiteurDemiTour0() || !perso.isDemiTour())) || b)
				  possible[3]=' ';
			  if ((perso.getD()=='b' && (perso.getLimiteurDemiTour()<perso.getLimiteurDemiTour0() || !perso.isDemiTour())) || h)
				  possible[2]=' ';
		  }
	  }
	  else{
		  if (perso.getD()=='d' || perso.getD()=='g'){     
			  possible[2]=' ';
			  possible[3]=' ';
			  if (perso.getD()=='d' && (perso.getLimiteurDemiTour()<perso.getLimiteurDemiTour0() || !perso.isDemiTour()))
				  possible[1]=' ';
			  if (perso.getD()=='g' && (perso.getLimiteurDemiTour()<perso.getLimiteurDemiTour0() || !perso.isDemiTour()))
				  possible[0]=' ';
		  }
		  else if (perso.getD()=='h' || perso.getD()=='b'){
			  possible[0]=' ';
			  possible[1]=' ';
			  if (perso.getD()=='h' && (perso.getLimiteurDemiTour()<perso.getLimiteurDemiTour0() || !perso.isDemiTour()))
				  possible[3]=' ';
			  if (perso.getD()=='b' && (perso.getLimiteurDemiTour()<perso.getLimiteurDemiTour0() || !perso.isDemiTour()))
				  possible[2]=' ';
		  }
	  }
	  List<Character> possible2 = new LinkedList<Character>();
	  for (char c : possible)
		  if (c!=' ')
			  possible2.add(c);
	  if (possible2.size()>0)
		  return possible2;
	  else					 //Si on ne peut rien faire d'autre on autorise le demi-tour 1 fois
		  return getAllPossibles(perso);
  }
  
  public List<Character> getAllPossibles(Personnage perso){  //Donne les directions de déplacement possible pour le personnage
	  int x = perso.getPosX();
	  int y = perso.getPosY();
	  int X = (x-x0)/e;
	  int Y = (y-y0)/e;
	  char [] possible = {'d', 'g', 'h', 'b'};
	  boolean dr = Point[Y][X].getDroite();
	  boolean g = Point[Y][X].getGauche();
	  boolean b = Point[Y][X].getBas();
	  boolean h = Point[Y][X].getHaut();
	  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
		  if (perso.getD()=='d' || perso.getD()=='g'){     
			  if (h)
				  possible[2]=' ';
			  if (b)
				  possible[3]=' ';
			  if (g)
				  possible[1]=' ';
			  if (dr)
				  possible[0]=' ';
		  }
		  else if (perso.getD()=='h' || perso.getD()=='b'){
			  if (dr)
				  possible[0]=' ';
			  if (g)
				  possible[1]=' ';
			  if (b)
				  possible[3]=' ';
			  if (h)
				  possible[2]=' ';
		  }
	  }
	  else{
		  if (perso.getD()=='d' || perso.getD()=='g'){     
			  possible[2]=' ';
			  possible[3]=' ';
		  }
		  else if (perso.getD()=='h' || perso.getD()=='b'){
			  possible[0]=' ';
			  possible[1]=' ';
		  }
	  }
	  List<Character> possible2 = new LinkedList<Character>();
	  for (char c : possible)
		  if (c!=' ')
			  possible2.add(c);
	  return possible2;
  }
  
  public void setLimiteur(Personnage perso){
	  if ((perso.getD()=='b' && perso.getOldDir()=='h') || (perso.getD()=='h' && perso.getOldDir()=='b') ||
			  (perso.getD()=='d' && perso.getOldDir()=='g') || (perso.getD()=='g' && perso.getOldDir()=='d'))
		  perso.setLimiteurDemiTour(0);
	  else
		  perso.increaseLimiteur();
  }
  
  private void Chasse (Personnage chasseur, Personnage cible){
	  int Xch = (chasseur.getPosX()-x0)/e;
	  int Ych = (chasseur.getPosY()-y0)/e;
	  int Xc = (cible.getPosX()-x0)/e;
	  int Yc = (cible.getPosY()-y0)/e;
	  //On regarde si les deux personnage sont dans la même zone
	  if (Point[Ych][Xch].getZone() == Point[Yc][Xc].getZone()){  
		  Chasse2(chasseur, cible);
	  }
	  else
		  Chasse3(chasseur, cible);
  }
 
  private void Chasse1(Personnage perso, int xc, int yc){
	  List<Character> possible2 = getPossibles(perso);
	  double[] dist = new double[possible2.size()];
	  int min = 0;
	  for (int p = 0; p<possible2.size(); p++){
		  Personnage temp = new Personnage(perso);
		  temp.setVitesse("Pacman");
		  temp.setDepl(possible2.get(p));
		  deplacement(temp);
		  dist[p] = distance(temp, xc, yc);
	  }
	  for (int i = 1; i<dist.length; i++){
		  if (dist[min]>dist[i])
			  min=i;
	  }
	  perso.setDepl(possible2.get(min));
  }
  
  public void Chasse1(Personnage chasseur, Personnage cible){
	  Chasse1(chasseur, cible.getPosX(), cible.getPosY());
  }
  
  public void Chasse2(Personnage perso, Personnage cible0){
	  Personnage cible = new Personnage(cible0);
	  if (perso.getCheminmax() == -1)
		  perso.setCheminmax(pan.getCheminInit());
	  String[] resultat = Chemin2(perso, cible, 0, perso.getCheminmax());
	  if (resultat[2] == "true"){
		  perso.setDepl(resultat[1].charAt(0));
		  if (perso.getNom() == "Blinky")
			  perso.setCheminmax(Integer.valueOf(resultat[0])+10);
		  else if (perso.getNom() == "Pinky"){
			  perso.setCheminmax((int)(Integer.valueOf(resultat[0])+distance(perso, cible0)));
		  }
	  }
	  else{
		  Chasse1(perso, cible0);
		  if (perso.getNom() == "Blinky") //On y va franco sur l'augmentation des cheminmax pour retrouver pacman
			  perso.setCheminmax(perso.getCheminmax()+100);
		  else if (perso.getNom() == "Pinky"){
			  perso.setCheminmax(perso.getCheminmax()+(int)(2*distance(perso, cible0)));
		  }
	  }
  }
  
  public void Chasse3(Personnage chasseur, Personnage  cible){
	  Personnage temp = new Personnage(cible);
	  temp.setLimiteurDemiTour0(0);
	  temp.setNom("Test");
	  setValeur(temp, 0);
	  int X=(chasseur.getPosX()-x0)/e;
	  int Y=(chasseur.getPosY()-y0)/e;
	  int val = Point[Y][X].getValeur();
	  List<Character> possible = getPossibles(chasseur);
	  for (char dir : possible){
		  if (dir == 'd' && (Point[Y][(X==dimX-1) ? 0 : X+1].getValeur()<val)){
			  chasseur.setDepl('d');
		  }
		  else if (dir == 'g' && (Point[Y][(X==0) ? dimX-1 : X-1].getValeur()<val)){
			  chasseur.setDepl('g');
		  }
		  else if (dir == 'h' && (Point[(Y==0) ? dimY-1 : Y-1][X].getValeur()<val)){
			  chasseur.setDepl('h');
		  }
		  else if (dir == 'b' && (Point[(Y==dimY-1) ? 0 : Y+1][X].getValeur()<val)){
		  chasseur.setDepl('b');
		  }  
	  }
  }

  private String[] Chemin(Personnage chasseur0, Personnage cible0, int chemin0, int chemMax){ 
	  Personnage chasseur = new Personnage(chasseur0);
	  chasseur.setCheminmax(chemMax);
	  boolean trouve = false;
	  int x = chasseur.getPosX();
	  int y = chasseur.getPosY();
	  int chemin = chemin0;
	  String[] resultat = new String[3];
	  resultat[2] = Boolean.toString(trouve);
	  if (distance(chasseur, cible0)<e){
		  resultat[0] = Integer.toString(chemin);
		  Chasse1(chasseur, cible0);
		  resultat[1] = Character.toString(chasseur.getD());
		  if (chemin<chasseur.getCheminmax()){
			  chasseur.setCheminmax(chemin);
		  }  
		  trouve = true;
		  resultat[2] = Boolean.toString(trouve);
		  return resultat;
	  }
	  chasseur.setDemiTour(false);
	  List<Character> possible2 = getPossibles(chasseur);
	  if (chemin + distance(chasseur, cible0) - e > chasseur.getCheminmax()){
		  resultat[0] = Integer.toString(chemin);
		  resultat[1] = Character.toString(possible2.get(0));
		  return resultat;
	  }
	  List<Integer> chemins = new LinkedList<Integer>();
	  int cheminDefaut = 0;
	  for (int i = 0; i<possible2.size(); i++){
		  Personnage temp = new Personnage(chasseur);
		  temp.setVitesse("Pacman");
		  x = temp.getPosX();
		  y = temp.getPosY();
		  temp.setDepl(possible2.get(i));
		  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
			  for (int j = 0; j<e; j++){
				  deplacement(temp);
			  }
			  chemin+=e;
		  }
		  else{
			  deplacement(temp);
			  chemin++;
		  }
		  String[] res = Chemin(temp, cible0, chemin, chasseur.getCheminmax());
		  if(res[2] == "true")
			  chemins.add(Integer.valueOf(res[0]));
		  else{
			  cheminDefaut = Integer.valueOf(res[0]);
			  chemins.add(100000);  //valeur trop grande pour etre sur qu'on ne choisisse pas celle-ci
		  }
	  }
	  boolean trouver = false;
	  for (int c : chemins)
		  if (c<100000)
			  trouver = true;
	  if (!trouver){
		  resultat[0] = Integer.toString(cheminDefaut);
		  resultat[1] = Character.toString(possible2.get(0));
	  }
	  else{
		  trouve = true;
		  int min = 0;
		  int cheminMin = chemins.get(0);
		  for(int j = 0; j<chemins.size(); j++){
			  if (chemins.get(j)<chemins.get(min)){
				  min = j;
				  cheminMin = chemins.get(j);
			  }
		  }
		  resultat[0] = Integer.toString(cheminMin);
		  resultat[1] = Character.toString(possible2.get(min));
		  resultat[2] = Boolean.toString(trouve);
	  }
	  return resultat;
  }
  
  private String[] Chemin2(Personnage chasseur0, Personnage cible0, int chemin0, int chemMax){ 
	  Personnage chasseur = new Personnage(chasseur0);
	  chasseur.setCheminmax(chemMax);
	  boolean trouve = false;
	  int x = chasseur.getPosX();
	  int y = chasseur.getPosY();
	  int chemin = chemin0;
	  String[] resultat = new String[3];
	  resultat[2] = Boolean.toString(trouve);
	  if (distance(chasseur, cible0)<e){
		  resultat[0] = Integer.toString(chemin);
		  Chasse1(chasseur, cible0);
		  resultat[1] = Character.toString(chasseur.getD());
		  if (chemin<chasseur.getCheminmax()){
			  chasseur.setCheminmax(chemin);
		  }  
		  trouve = true;
		  resultat[2] = Boolean.toString(trouve);
		  return resultat;
	  }
	  chasseur.setDemiTour(false);
	  List<Character> possible2 = getPossibles(chasseur);
	  if (chemin + distance(chasseur, cible0) - e > chasseur.getCheminmax()){
		  resultat[0] = Integer.toString(chemin);
		  resultat[1] = Character.toString(possible2.get(0));
		  return resultat;
	  }
	  List<Integer> chemins = new LinkedList<Integer>();
	  int cheminDefaut = 0;
	  List<Personnage> liste = new LinkedList<Personnage>();
	  List<Double> dist = new LinkedList<Double>();
	  List<Integer> chem = new LinkedList<Integer>();
	  for (int i = 0; i<possible2.size(); i++){
		  Personnage temp = new Personnage(chasseur);
		  temp.setVitesse("Pacman");
		  x = temp.getPosX();
		  y = temp.getPosY();
		  temp.setDepl(possible2.get(i));
		  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
			  for (int j = 0; j<e; j++){
				  deplacement(temp);
			  }
			  chemin+=e;
		  }
		  else{
			  deplacement(temp);
			  chemin++;
		  }
		  double distance = distance(temp, cible0);
		  int j= 0;
		  Boolean continu = true;
		  if (dist.size() == 0){
			  liste.add(temp);
			  dist.add(distance);
			  chem.add(chemin);
		  }
		  else{
			  continu = distance>dist.get(0);
			  while(continu){
				  j++;
				  if (j == dist.size()){
					  continu = false;
				  }
				  else{
					  continu = distance>dist.get(0);
				  }
			  }
			  liste.add(j, temp);
			  dist.add(j, distance);
			  chem.add(j, chemin);
		  }
	  }
	  if (liste.size() != possible2.size())
		  throw new IllegalStateException("Oublie d'un fantome");
	  for (int i = 0; i<liste.size(); i++){
		  String[] res = Chemin(liste.get(i), cible0, chem.get(i), chasseur.getCheminmax());
		  if(res[2] == "true")
			  chemins.add(Integer.valueOf(res[0]));
		  else{
			  cheminDefaut = Integer.valueOf(res[0]);
			  chemins.add(100000);  //valeur trop grande pour etre sur qu'on ne choisisse pas celle-ci
		  }
	  }
	  boolean trouver = false;
	  for (int c : chemins)
		  if (c<100000)
			  trouver = true;
	  if (!trouver){
		  resultat[0] = Integer.toString(cheminDefaut);
		  resultat[1] = Character.toString(possible2.get(0));
	  }
	  else{
		  trouve = true;
		  int min = 0;
		  int cheminMin = chemins.get(0);
		  for(int j = 0; j<chemins.size(); j++){
			  if (chemins.get(j)<chemins.get(min)){
				  min = j;
				  cheminMin = chemins.get(j);
			  }
		  }
		  resultat[0] = Integer.toString(cheminMin);
		  resultat[1] = Character.toString(possible2.get(min));
		  resultat[2] = Boolean.toString(trouve);
	  }
	  return resultat;
  }
  /*
  private String[] Chemin3(Personnage chasseur0, Personnage cible0, int chemin0, int chemMax){ 
	  List<Personnage> bdd = new LinkedList<Personnage>();
	  bdd.add(chasseur0);
	  chasseur0.chemin = 0;
	  chasseur0.trouve = false;
	  while (bdd.size() != 0){
		  Personnage actuel = bdd.get(0);
		  List<Character> possibles = getPossibles(actuel);
		  for (Character dir : possibles){
			  Personnage temp = new Personnage(actuel);
			  temp.d = dir;
			  int x=temp.getPosX();
			  int y=temp.getPosY();
			  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
				  for (int j = 0; j<e; j++){
					  deplacement(temp);
				  }
				  temp.chemin+=e;
			  }
			  else{
				  deplacement(temp);
				  temp.chemin++;
			  }
			  double distance = distance(actuel, cible0);
			  int pos = 0;
			  Boolean continu = distance>distance(bdd.get(0), cible0);
			  while(continu){
				  pos++;
				  if (pos == bdd.size()-1){
					  continu = false;
				  }
				  else
					  continu = distance>distance(bdd.get(pos), cible0);
			  }
			  bdd.add(pos, temp);
			  bdd.remove(actuel);
		  }
	  }
	  String[] bidon = {"", ""};
	  return bidon;
  }
  */
  
  private void Fuir(Personnage perso){
	  int depl = (int)((Math.random()*3));
	  if (depl!=2)
		  Random(perso);
	  else{
		  List<Character> possible2 = getPossibles(perso);
		  Personnage temp = new Personnage(perso);
		  int[] dist = new int[possible2.size()];
		  int max = 0;
		  for (int p = 0; p<possible2.size(); p++){
			  temp = new Personnage(perso);
			  temp.setDepl(possible2.get(p));
			  if (temp.getD()=='d')
				  temp.setPosX(droite(temp));
			  else if (temp.getD()=='h')
				  temp.setPosY(haut(temp));
			  else if (temp.getD()=='g')
				  temp.setPosX(gauche(temp));
			  else if (temp.getD()=='b')
				  temp.setPosY(bas(temp));
			  dist[p] = (int)distance(temp, pan.getListe().get(0));
		  }
		  max = 0;
		  for (int i = 1; i<dist.length; i++){
			  if (dist[max]<dist[i])
				  max=i;
		  }
		  perso.setDepl(possible2.get(max));
	  }
  }
  
  private void Embuscade(Personnage perso, Personnage cible){
	  Personnage temp = new Personnage(cible);
	  temp.setComportement("Random");
	  temp.setLimiteurDemiTour(0);
	  temp.setLimiteurDemiTour0(0);
	  for (int i =0; i<5*e; i++)
		  deplacement(temp);
	  Chasse(perso, temp);
  }
  
  private void Retour(Personnage perso){
	  int x =perso.getPosX();
	  int y =perso.getPosY();
	  if (x%e==(x0+e)%e && y%e==(y0+e)%e){
		  int X = (x-x0)/e;
		  int Y = (y-y0)/e;
		  Case c = Point[Y][X];
		  if (Point[(Y==0) ? dimY-1 : Y-1][X].getValeur2()<Point[Y][X].getValeur2() 
				  && (!c.getHaut() || c.getValeur2()<=1)){
			  perso.setDepl('h');
		  }
		  else if (Point[Y][(X==0) ? dimX-1 : X-1].getValeur2()<Point[Y][X].getValeur2() 
				  && (!c.getGauche() || c.getValeur2()<=1)){
			  perso.setDepl('g');
		  }
		  else if (Point[(Y==dimY-1) ? 0 : Y+1][X].getValeur2()<Point[Y][X].getValeur2() 
				  && (!c.getBas() || c.getValeur2()<=1)){
			  perso.setDepl('b');
		  }
		  else if (Point[Y][(X==dimX-1) ? 0 : X+1].getValeur2()<Point[Y][X].getValeur2() 
				  && (!c.getDroite() || c.getValeur2()<=1)){
			  perso.setDepl('d');
		  }
	  }
  }
  
  private void choixDir(List<Personnage> L){   
	  for (Personnage perso : pan.getListe()){
		  if (perso.getNom()=="Pacman"){
			  choixPacman(perso);
		  }
		  else{
			  if (temps<=2*e || !perso.isMobile());
			  else{
				  if (perso.getComportement()=="Random")
					  Random(perso);
				  else if (perso.getComportement()=="Chasse")
					  Chasse(perso, pan.getListe().get(0));
				  else if (perso.getComportement() == "Fuite")
					  Fuir(perso);
				  else if (perso.getComportement() == "Embuscade")
					  Embuscade(perso, pan.getListe().get(0));
				  else if (perso.getComportement() == "Retour")
					  Retour(perso);
				  }
		  	  }
	  }
  }
   
  private void setPieces(){
	  int x = pan.getListe().get(0).getPosX();      
	  int y = pan.getListe().get(0).getPosY();  
	  int X = (x-x0)/e;
	  int Y = (y-y0)/e;
	  float[][] dist = new float[3][3];
	  dist[1][1] = Math.abs(x-((X-3/10)*e+x0))+Math.abs(y-((Y-3/10)*e+y0));
	  dist[1][2] = Math.abs(x-((X-3/10)*e+x0))+Math.abs(y-(((Y-3/10)+1)*e+y0));
	  dist[2][1] = Math.abs(x-(((X-3/10)+1)*e+x0))+Math.abs(y-((Y-3/10)*e+y0));
	  dist[1][0] = Math.abs(x-((X-3/10)*e+x0))+Math.abs(y-(((Y-3/10)-1)*e+y0));
	  dist[0][1] = Math.abs(x-(((X-3/10)-1)*e+x0))+Math.abs(y-((Y-3/10)*e+y0));
	  for (int i =0; i<3; i++)
		  for (int j = 0; j<3; j++)
			  if ((i==1 || j==1) && dist[i][j]<e/2){
				  if (Point[Y+j-1][X+i-1].getNom() == "Piece"){
					  Point[Y+j-1][X+i-1].setNom("");
					  c--;
					  score+=10;
				  }
				  else if (Point[Y+j-1][X+i-1].isGomme()){
					  score+= Point[Y+j-1][X+i-1].getGom().val;
					  Point[Y+j-1][X+i-1].getGom().val = 0;
					  if (Point[Y+j-1][X+i-1].getNom().equals("SuperPacGom")){
						  pan.setChasseAuxFantomes(10000);  //temps en ms
						  for (Personnage perso : pan.getListe()){
							  if (perso.getNom()!="Pacman"){
								  perso.setNom("Peur");
								  perso.setComportement("Fuite");
								  perso.setVitesse("Lent");
							  }
						  }
					  }
					  Point[Y+j-1][X+i-1].setNom("");
				  }
			  }
  }
  
  public void setFantomes(){
	  for (Personnage perso : pan.getListe()){
		  if (pan.getChasseAuxFantomes() >0){
			  for (int p =0; p<pan.getBaseX().size(); p++){
				  if(perso.getPosX() == pan.getBaseX().get(p) && perso.getPosY() == pan.getBaseY().get(p)){
					  perso.setNom();
					  perso.setComportement();
					  perso.setCheminmax(-1);
					  perso.setMobile(false);
				  }
			  }
		  }
		  else{
			  for (int p =0; p<pan.getBaseX().size(); p++){
				  if(perso.getPosX() == pan.getBaseX().get(p) && perso.getPosY() == pan.getBaseY().get(p)){
					  perso.setNom();
					  perso.setComportement();
					  perso.setVitesse("Normal");
					  perso.setCheminmax(-1);
					  perso.setMobile(true);
				  }
			  }
		  }
	  }
  }
  
  public void setChasse(){
	  if (pan.getChasseAuxFantomes() > 0){
		  pan.setChasseAuxFantomes(pan.getChasseAuxFantomes()-500/e);
		  info.setForeground(Color.RED);
		  info.setText((Integer.toString((int)(pan.getChasseAuxFantomes()/1000))));
		  if (pan.getChasseAuxFantomes() <= 0){
			  for (Personnage perso : pan.getListe()){
				  if (perso.getNom()!= "Pacman" && perso.getNom()!="FantomeMange"){
					  perso.setNom();
					  perso.setComportement();
					  perso.setVitesse("Normal");
					  int d = (int)distance(perso, pan.getListe().get(0));
					  perso.setCheminmax((d>pan.getCheminInit()) ? (int)(1.5*d) : pan.getCheminInit());
				  }
			  }
			  pan.setChasseAuxFantomes(0);
			  info.setForeground(Color.YELLOW);
			  info.setText("");
		  }
	  }
  }
  
  public void setBonus(){
	  while(bonus>500){
		  bonus-=5;
		  int previousScore = score;
		  score+=5;
		  if (score%10000 - previousScore%10000<0){
			  vie++;
			  vies.setText("Lives : " + vie);
		  }
		  Bonus.setText("Bonus : " + Integer.toString(bonus));
		  Score.setText("Score : " + Integer.toString(score));
		  pause(5);
	  }
	  while(bonus>0){
		  bonus--;
		  int previousScore = score;
		  score++;
		  if (score%10000 - previousScore%10000<0){
			  vie++;
			  vies.setText("Lives : " + vie);
		  }
		  Bonus.setText("Bonus : " + Integer.toString(bonus));
		  Score.setText("Score : " + Integer.toString(score));
		  pause(5);
	  }
  }
  
  private void setClapet(){
	  for (int X = 0; X<dimX; X++)
		  for (int Y = 0; Y<dimY; Y++)
			  if (Point[Y][X].getNom().equals("Clapet")){
				  Case clap = pan.getPoint()[Y][X];
				  if(temps%200 == 0){  //On change la position
					  if (clap.getDir1()=='d' || clap.getDir2() == 'd'){
						  if (!clap.getDroite())
							  clap.setDroite(true);
						  else
							  clap.setDroite(false);
					  }
					  if (clap.getDir1()=='g' || clap.getDir2() == 'g'){
						  if (!clap.getGauche())
							  clap.setGauche(true);
						  else
							  clap.setGauche(false);
					  }
					  if (clap.getDir1()=='h' || clap.getDir2() == 'h'){
						  if (!clap.getHaut())
							  clap.setHaut(true);
						  else
							  clap.setHaut(false);
					  }
					  if (clap.getDir1()=='b' || clap.getDir2() == 'b'){
						  if (!clap.getBas())
							  clap.setBas(true);
						  else
							  clap.setBas(false);
					  }
				  }
					  
			  }
  }
	  
  
  private void go(){
	  bonus=pan.getBonus();
	  while(c!=0 && vie!=0){   // Boucle gérant le positionnement ou le repositionnement après avoir perdu une vie
		  pan.reboot();
		  pan.repaint();
		  info.setText("3");
		  pause(1000);
		  info.setText("2");
		  pause(1000);
		  info.setText("1");
		  pause(1000);
		  info.setText("Go !");
		  temps = 0;
		  while (c!=0 && !perdu){  // Boucle gérant la partie en cours
			  int previousScore = score;
			  long t1 = System.nanoTime();
			  for (Personnage perso : pan.getListe()){
				  if (perso.getNom()=="Pacman" || perso.getTempo()>=e){
					  perso.setOldDir(perso.getD());
				  }
			  }
			  choixDir(pan.getListe()); // Choix des prochains déplacements
			  for (Personnage perso : pan.getListe())  //Dépacements
				  if (perso.getNom()=="Pacman" || perso.getTempo()>=e){
					  deplacement(perso);
					  setLimiteur(perso);
				  }
			  setPieces();  //On regarde si des pièce on été mangées et le cas échant on les fait disparaitre
			  setFantomes();
			  if (n==8)
				  setClapet();
			  if (score%10000 - previousScore%10000<0){
				  vie++;
				  vies.setText("Lives : " + vie);
			  }
			  Score.setText("Score : " + Integer.toString(score)); //Actualisation du score
			  perdu=Perdu();  //On vérifie si on a perdu (contact avec un fantome)
			  setChasse();  // On regarde si la chasse aux fantomes est terminée
			  temps++;
			  for (Personnage perso : pan.getListe())
				  perso.increaseTempo();
			  pan.repaint();
			  if (pause){
				  info.setText("Pause");
				  while(pause)
					  pause(10);
				  info.setText("");
			  }
			  if (bonus>0 && temps%10==0){
				  bonus--;
				  Bonus.setText("Bonus : " + Integer.toString(bonus));
			  }
			  long t2 = System.nanoTime();
			  double temps = (double)(t2-t1)/(Math.pow(10, 6));
			  pause(5-(int)temps);
			  
		  }
		  if (!perdu){  //On passe au niveau suivant
		      info.setText("You win !");
		      pause(500);
			  setBonus();
		      Score.setText("Score : " + Integer.toString(score));
			  actuel.addXp(score/100);
			  if(mod == "Complete tour"){
				  if (n!=nmax)
					  n++;
				  else
					  n=-2;
			  }
			  else if (n!=nmax){
				  actuel.dispo[n] = true;
				  pause(250);
				  FinNiveau(n, true);
			  }
			  else
				  n=0;
			  writeActuel();
		  }
		  else{ 		// On perd une vie
			  pause(250);
			  info.setText("You loose !");
			  perdu=false;
			  vie--;
			  vies.setText("Lives : " + vie);
			  if (vie==0){
				  actuel.addXp(score/200);
				  if (mod == "Complete tour"){
					  n=-2;
					  pause(250);
				  }
				  else{
					  pause(250);
					  FinNiveau(n, false);
				  }
			  }
			  else{
				  int newBonus = (int)(bonus/2);
				  while(bonus>500+newBonus){
					  bonus-=5;
					  Bonus.setText("Bonus : " + Integer.toString(bonus));
					  pause(5);
				  }
				  while(bonus>newBonus){
					  bonus--;
					  Bonus.setText("Bonus : " + Integer.toString(bonus));
					  pause(5);
				  }
			  }
			  pan.repaint();
			  pause(500);
		  }
		  pause(2000);
	  }
}
  	public static void main(String[] args){
		new Fenetre();
	}

	public static List<String> getUtilisateurs() {
		return utilisateurs;
	}

	public static void setUtilisateurs(List<String> utilisateurs) {
		Fenetre.utilisateurs = utilisateurs;
	}

	public static int getNmax() {
		return nmax;
	}

	public static void setNmax(int nmax) {
		Fenetre.nmax = nmax;
	}

	public static int[][] getScores() {
		return scores;
	}

	public static void setScores(int[][] scores) {
		Fenetre.scores = scores;
	}

	public static String[][] getNames() {
		return names;
	}

	public static void setName(int i, int j, String name) {
		Fenetre.names[i][j] = name;
	}
	
	public static void setNames(String[][] names) {
		Fenetre.names = names;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isTrouve() {
		return trouve;
	}

	public void setTrouve(boolean trouve) {
		this.trouve = trouve;
	}
}
