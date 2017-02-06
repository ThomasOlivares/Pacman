package pacman;

public class Case {
	private Boolean bas;
	private Boolean droite;
	private Boolean haut;
	private Boolean gauche;
	private char dir1;
	private char dir2;
	private String nom;
	private Gom gom = new Gom ("fake");
	private boolean gomme = false;
	private int valeur = 99;  //Grandes valeurs destinées à être remplacées
	private int valeur2 = 99;
	private int valeur3 = 99;
	private int zone;
	
	public Case(int[] relief, String nom){
		this.bas = relief[0]==1;
		this.droite = relief[1]==1;
		this.haut = relief[2]==1;
		this.gauche = relief[3]==1;
		this.nom = nom;
		this.zone = 1;
	}
	public Case(int[] relief, Gom gomme){
		this.bas = relief[0]==1;
		this.droite = relief[1]==1;
		this.haut = relief[2]==1;
		this.gauche = relief[3]==1;
		this.nom = gomme.nom;
		this.gom = gomme;
		this.gomme = true;
		this.zone = 1;
	}
	
	public Case(int[] relief, char dir1, char dir2){
		this.bas = relief[0]==1;
		this.droite = relief[1]==1;
		this.haut = relief[2]==1;
		this.gauche = relief[3]==1;
		this.dir1=dir1;
		if (this.dir1 == 'd')
			this.droite = true;
		else if (this.dir1 == 'g')
			this.gauche = true;
		else if (this.dir1 == 'h')
			this.haut = true;
		else if (this.dir1 == 'b')
			this.bas = true;
		this.dir2=dir2;
		this.nom = "Clapet";
		this.zone = 1;
	}
	
	public Case (int[] relief, String nom, int Zone){
		this.bas = relief[0]==1;
		this.droite = relief[1]==1;
		this.haut = relief[2]==1;
		this.gauche = relief[3]==1;
		this.nom = nom;
		this.zone = Zone;
	}
	
	public Case(int[] relief, Gom gomme, int Zone){
		this.bas = relief[0]==1;
		this.droite = relief[1]==1;
		this.haut = relief[2]==1;
		this.gauche = relief[3]==1;
		this.nom = gomme.nom;
		this.gom = gomme;
		this.gomme = true;
		this.zone = Zone;
	}
	public Boolean getBas() {
		return bas;
	}
	public void setBas(Boolean bas) {
		this.bas = bas;
	}
	public Boolean getDroite() {
		return droite;
	}
	public void setDroite(Boolean droite) {
		this.droite = droite;
	}
	public Boolean getHaut() {
		return haut;
	}
	public void setHaut(Boolean haut) {
		this.haut = haut;
	}
	public Boolean getGauche() {
		return gauche;
	}
	public void setGauche(Boolean gauche) {
		this.gauche = gauche;
	}
	public char getDir1() {
		return dir1;
	}
	public void setDir1(char dir1) {
		this.dir1 = dir1;
	}
	public char getDir2() {
		return dir2;
	}
	public void setDir2(char dir2) {
		this.dir2 = dir2;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Gom getGom() {
		return gom;
	}
	public void setGom(Gom gom) {
		this.gom = gom;
	}
	public boolean isGomme() {
		return gomme;
	}
	public void setGomme(boolean gomme) {
		this.gomme = gomme;
	}
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	public int getValeur2() {
		return valeur2;
	}
	public void setValeur2(int valeur2) {
		this.valeur2 = valeur2;
	}
	public int getValeur3() {
		return valeur3;
	}
	public void setValeur3(int valeur3) {
		this.valeur3 = valeur3;
	}
	public int getZone() {
		return zone;
	}
	public void setZone(int zone) {
		this.zone = zone;
	}
}
