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
    // hashmap : case -> i, j
    private HashMap<CaseModele, Point> hashmap; // voir (*)
    
    public Jeu(int size) {
    	tabJeu = new CaseModele[size][size];
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                tabJeu[i][j] = new CaseModele(i, j);
            }
    	}
    }
    
    //pour verifier case voisine il faut faire +1 ou -1 sur l'abscisse OU l'ordonn�e

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
    	
    	for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
            	if(tab[i][j].getType() == null) {
            		tab[i][j].setType(CaseType.empty);
            	}
            }
    	}
    	
    	return tab;
    }
    
	
    

}
