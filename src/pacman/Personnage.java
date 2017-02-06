package pacman;

public class Personnage {
	
	private String nom;
	private String nom0;
	private int posX0;
	private int posY0;
	private int posX;
	private int posY;
	private char d;
	private char d0;
	private char newD;
	private int i = -1;  //tempo de déplacement
	private String vitesse = "Normal";
	private String comportement = "Random";
	private String comportement0 = "Random";
	private double DistBase = 1000;
	private boolean mobile = true;
	private int tempo = 0;
	private char oldDir;
	private int limiteurDemiTour;
	private int limiteurDemiTour0;
	private boolean demiTour = true;
	private int chemin = 0;
	private int cheminmax = -1;
	private Boolean trouve = false;
	
	public Personnage(String nom) throws IllegalStateException{
		if (nom == "Blinky"){
			this.comportement0 = "Chasse";
			limiteurDemiTour0 = 150;
		}
		else if (nom == "Pinky"){
			this.comportement0 = "Embuscade";
			limiteurDemiTour0 = 150;
		}
		else if (nom == "Inky"){
			this.comportement0 = "Fuite";
			limiteurDemiTour0 = 0;
			demiTour = false;//Pas de demi tour
		}
		else if (nom == "Clyde" || nom == "Test"){
			this.comportement0 = "Random";
			limiteurDemiTour0 = 0;
			demiTour = false;//Pas de demi tour
		}
		else if (nom == "Pacman"){
			this.comportement0 = "Joueur";
			limiteurDemiTour0 = -1;  //Pas de limite car ne sert jamais
		}
		else if (nom == "FantomeMange"){
			this.comportement0 = "Retour";
			limiteurDemiTour0 = -1;   //Idem
		}
		else throw new IllegalStateException("nom inconnu");
		setComportement();
		this.i=-1;
		this.nom=nom;
		this.nom0=nom;
	}
	public Personnage(Personnage perso){
		this.i=perso.i;
		this.nom=perso.nom;
		this.nom0=perso.nom0;
		this.posX=perso.posX;
		this.posY=perso.posY;
		this.posX0=perso.posX0;
		this.posY0=perso.posY0;
		this.d=perso.d;
		this.d0=perso.d0;
		this.newD=perso.newD;
		this.comportement = perso.comportement;
		this.comportement0 = perso.comportement0;
		this.vitesse = perso.vitesse;
		this.DistBase = perso.DistBase;
		this.cheminmax = perso.cheminmax;
		this.chemin = perso.chemin;
		this.trouve = perso.trouve;
	}
	public boolean deplacement() throws IllegalStateException{
		if (this.nom == "Pacman"){
			return true;
		}
		else if (!mobile)
			return false;
		else if (this.vitesse == "Pacman")
			return true;
		else if(this.vitesse=="Normal"){
			i=(i+1)%100;
			return (i!=0);
		}
		else if(this.vitesse == "Lent"){
			i=(i+1)%5;
			return (i!=0);
		}
		else
			System.out.println("vitesse non autorisée");
			throw new IllegalStateException("vitesse non autorisée");
	}
	public void setpos0(int x, int y, char d){
		this.posX0=x;
		this.posY0=y;
		this.d0=d;
	}
	public void setpos(){
		this.posX=this.posX0;
		this.posY=this.posY0;
	}
	public void setpos(int x, int y){
		this.posX=x;
		this.posY=y;
	}
	
	public char getD(){
		return d;
	}
	
	public void setD(char d_){
		this.d = d_;
	}
	
	public void setDepl(){
		this.d=this.d0;
		this.newD = d0;
	}
	public void setDepl(char d){
		this.d=d;
		this.i=-1;
	}
	public char getNewDepl(){
		return this.newD;
	}
	public void setNewDepl(char d){
		this.newD=d;
	}
	public void setComportement(String newComportement){
		this.comportement = newComportement;
	}
	public void setComportement(){
		this.comportement = this.comportement0;
		this.limiteurDemiTour = this.limiteurDemiTour0;
	}
	
	public void setLimiteur(){
		this.limiteurDemiTour = this.limiteurDemiTour0;
	}
	
	public void increaseLimiteur(){
		this.limiteurDemiTour++;
	}
	
	public void setNom(){
		this.nom = this.nom0;
	}
	public String toString(){
		System.out.println("Je m'appelle " + this.nom + " je me comporte de maniere : " + this.comportement);
		return "";
	}
	public int getTempo() {
		return tempo;
	}
	public void increaseTempo(){
		tempo++;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public char getOldDir() {
		return oldDir;
	}
	public void setOldDir(char oldDir) {
		this.oldDir = oldDir;
	}
	public int getLimiteurDemiTour() {
		return limiteurDemiTour;
	}
	public void setLimiteurDemiTour(int limiteurDemiTour) {
		this.limiteurDemiTour = limiteurDemiTour;
	}
	public boolean isDemiTour() {
		return demiTour;
	}
	public void setDemiTour(boolean demiTour) {
		this.demiTour = demiTour;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getCheminmax() {
		return cheminmax;
	}
	public void setCheminmax(int cheminmax) {
		this.cheminmax = cheminmax;
	}
	public String getVitesse() {
		return vitesse;
	}
	public void setVitesse(String vitesse) {
		this.vitesse = vitesse;
	}
	public String getComportement() {
		return comportement;
	}
	public boolean isMobile() {
		return mobile;
	}
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
	public int getLimiteurDemiTour0() {
		return limiteurDemiTour0;
	}
	public void setLimiteurDemiTour0(int limiteurDemiTour0) {
		this.limiteurDemiTour0 = limiteurDemiTour0;
	}
}
