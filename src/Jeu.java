import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.*;

public class Jeu extends Observable{
    // tableau de cases : i, j -> case
    private CaseModele[][] tabJeu;
    private CaseModele depart;
    private CaseModele arrivee;
    private List<CaseModele> chemin = new ArrayList<CaseModele>();
    private HashMap<CaseModele, Point> cheminH;
    // hashmap : case -> i, j
    private HashMap<CaseModele, Point> hashmap; // voir (*)
    
    public Jeu(int size) {
    	tabJeu = new CaseModele[size][size];
        cheminH = new HashMap<CaseModele, Point>();
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                tabJeu[i][j] = new CaseModele(i, j);
            }
    	}
    }
    
    //pour verifier case voisine il faut faire +1 ou -1 sur l'abscisse OU l'ordonnée

    public void dessineMotif(CaseModele c) {
    	int index = chemin.indexOf(c);
    	CaseModele caseToPaint = chemin.get(index - 1);
    	if(!caseToPaint.getType().equals(CaseType.S1) || !caseToPaint.getType().equals(CaseType.S2) || !caseToPaint.getType().equals(CaseType.S3) || !caseToPaint.getType().equals(CaseType.S4) || !caseToPaint.getType().equals(CaseType.S5))
    		caseToPaint.setType(choixMotif(cheminH.get(caseToPaint), cheminH.get(c)));
    }

    private CaseType choixMotif(Point entree, Point sortie) {
    	if(entree.getX() == 0 && sortie.getY() == 46)
    		return CaseType.h0v0;
    	if(entree.getX() == 0 && sortie.getY() == 0)
    		return CaseType.h0v1;
    	if(entree.getY() == 0 && sortie.getX() == 0)
    		return CaseType.h1v0;
    	if(entree.getX() == 54 && sortie.getY() == 0)
    		return CaseType.h1v1;
    	if(entree.getX() == 0 && sortie.getX() == 0)
    		return CaseType.h0h1;
    	if(entree.getY() == 0 && sortie.getY() == 0)
    		return CaseType.v0v1;
    	
    	return CaseType.empty;
    	
    }
    public CaseModele getCase(int i, int j) {
    	return tabJeu[i][j];
    }
    
    public void setDepart(CaseModele d) {
    	depart = d;
    }
    
    public void setArrive(CaseModele a) {
    	arrivee = a;
    }
    
    public void addCase(CaseModele c) {
    	chemin.add(c);
    }
    
    public void addCoordonnees(CaseModele c, Point p) {
    	cheminH.put(c, p);
    }
    
    public void getArrivee() {
    	System.out.println(arrivee.toString());
    }
    
    public void getDepart() {
    	System.out.println(depart.toString());
    }
    
    public void getChemin() {
    	for(CaseModele c : chemin) {
    		System.out.println(c.toString());
    	}
    }
    
    public CaseModele[][] initPuzzle() {
    	CaseModele[][] tab = new CaseModele[3][3];
    	for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tab[i][j] = new CaseModele(i, j);
            }
    	}
    	tab[0][1].setType(CaseType.S1);
    	tab[2][0].setType(CaseType.S1);
    	tab[0][2].setType(CaseType.S2);
    	tab[2][2].setType(CaseType.S2);
    	
    	return tab;
    }
    
	
    

}
