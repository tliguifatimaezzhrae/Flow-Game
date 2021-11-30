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
    //private HashMap<CaseModele, Point> cheminH;  //inutile pour le moment penser à l'enlever avec tout ce qui est en rapport
    // hashmap : case -> i, j
    private HashMap<CaseModele, Point> hashmap; // voir (*)
    
    public Jeu(int size) {
    	tabJeu = new CaseModele[size][size];
        hashmap = new HashMap<CaseModele, Point>();
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                tabJeu[i][j] = new CaseModele(i, j);
                hashmap.put(tabJeu[i][j], new Point(j, i));
            }
    	}
    }
    
    //pour verifier case voisine il faut faire +1 ou -1 sur l'abscisse OU l'ordonnée

    public void dessineMotif(CaseModele caseApres) {
    	int index = chemin.indexOf(caseApres);
    	CaseModele caseToPaint = chemin.get(index - 1);
    	if(index - 2 >= 0) {
    		CaseModele caseAvant = chemin.get(index - 2);
    		caseToPaint.setType(choixMotif(caseAvant, caseToPaint, caseApres));
    	}
    }

    private CaseType choixMotif(CaseModele caseAvant, CaseModele caseCourante, CaseModele caseApres) {
    	String[] dir = new String[2];
    	dir[0] = direction(caseAvant, caseCourante);
    	dir[1] = direction(caseCourante, caseApres);
    	if((dir[0].equals("B") && dir[1].equals("G")) || (dir[0].equals("D") && dir[1].equals("H")))
    		return CaseType.h0v0;
    	if((dir[0].equals("D") && dir[1].equals("B")) || (dir[0].equals("H") && dir[1].equals("G")))
    		return CaseType.h0v1;
    	if((dir[0].equals("B") && dir[1].equals("D")) || (dir[0].equals("G") && dir[1].equals("H")))
    		return CaseType.h1v0;
    	if((dir[0].equals("G") && dir[1].equals("B")) || (dir[0].equals("H") && dir[1].equals("D")))
    		return CaseType.h1v1;
    	if((dir[0].equals("G") && dir[1].equals("G")) || (dir[0].equals("D") && dir[1].equals("D")))
    		return CaseType.h0h1;
    	if((dir[0].equals("B") && dir[1].equals("B")) || (dir[0].equals("H") && dir[1].equals("H")) )
    		return CaseType.v0v1;
    	
    	return CaseType.empty;
    }
    
    private String direction(CaseModele case1, CaseModele case2) {
    	if(case2.getX() == case1.getX()-1 && case1.getY() == case2.getY())
    		return "H";
    	if(case2.getX() == case1.getX()+1 && case1.getY() == case2.getY())
    		return "B";
    	if(case1.getX() == case2.getX() && case2.getY() == case1.getY()-1)
    		return "G";
    	if(case1.getX() == case2.getX() && case2.getY() == case1.getY()+1)
    		return "D";
    	return null;
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
    
    /*public void addCoordonnees(CaseModele c, Point p) {
    	cheminH.put(c, p);
    }*/
    
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
    
    public void videChemin() {
    	chemin.clear();
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
