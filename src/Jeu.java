import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.*;

public class Jeu extends Observable{
    // tableau de cases : i, j -> case
    private CaseModele[][] tabJeu;
    private int size;
    private CaseModele depart;
    private CaseModele arrivee;
    private ArrayList<CaseModele> chemin = new ArrayList<CaseModele>();
    private List<ArrayList<CaseModele>> listeChemins = new ArrayList<ArrayList<CaseModele>>();
    private List<CaseModele> casesChemins = new ArrayList<CaseModele>();
    private static int nbCheminsAttendus;
    //private HashMap<CaseModele, Point> cheminH;  //inutile pour le moment penser � l'enlever avec tout ce qui est en rapport
    // hashmap : case -> i, j
    private HashMap<CaseModele, Point> hashmap; // voir (*)
    
    public Jeu(int size) {
    	this.size = size;
    	tabJeu = new CaseModele[size][size];
        hashmap = new HashMap<CaseModele, Point>();
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                tabJeu[i][j] = new CaseModele(i, j);
                hashmap.put(tabJeu[i][j], new Point(j, i));
            }
    	}
    }

    //verifie la case de d�part d'un chemin
    public boolean verifDepartChemin() {
    	if(depart.getType() == CaseType.S1 || depart.getType() == CaseType.S2 || depart.getType() == CaseType.S3 || depart.getType() == CaseType.S4 || depart.getType() == CaseType.S5)
    		return true;
    	else 
    		return false;
    }
    
    //v�rifie validit� d'un chemin
    public void verifChemin() {
    	if(depart.getType() == arrivee.getType()) {
    		listeChemins.add(chemin);
    		for(CaseModele c : chemin) {
    			casesChemins.add(c);
    		}
    		chemin.clear();
    	}
    	
    	else {
    		detruitChemin();
    		chemin.clear();
			setChanged();
			notifyObservers();
    	}
    }
    
    public boolean verifPuzzle() {
    	if(nbCheminsAttendus == listeChemins.size()) {
	    	for(int i=0; i<size; i++) {
	    		for(int j=0; j<size; j++) {
	    			if(tabJeu[i][j].getType() == CaseType.empty )
	    				return false;
	    		}
	    	}
    	}
    	else
    		return false;
    	
    	return true;
    }
    
    public boolean verifVoisin(CaseModele c1, CaseModele c2) {
    	if(direction(c1,c2) == null)
    		return false;
    	else 
    		return true;
    }
    
    public void dessineMotif(CaseModele caseApres) {
    	int index = chemin.indexOf(caseApres);
    	CaseModele caseToPaint = chemin.get(index - 1);
    	if(!verifVoisin(caseToPaint, caseApres)) {
    		detruitChemin();
    		chemin.clear();
    	}
    	if(index - 2 >= 0) {
        	if(caseToPaint.getType() != CaseType.empty) {
        		detruitChemin();
        		chemin.clear();
        		setChanged();
        		notifyObservers();
        	}else {
        		CaseModele caseAvant = chemin.get(index - 2);
        		caseToPaint.setType(choixMotif(caseAvant, caseToPaint, caseApres));
        		setChanged();
        		notifyObservers();
        	}
    	}
    }

    private CaseType choixMotif(CaseModele caseAvant, CaseModele caseToPaint, CaseModele caseApres) {
    	String[] dir = new String[2];
    	dir[0] = direction(caseAvant, caseToPaint);
    	dir[1] = direction(caseToPaint, caseApres);
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
    
    /*public void videChemin() {
    	chemin.clear();
    }*/
    
    //efface un chemin valide quand on clique dessus
    public void effaceChemin(CaseModele c) {
    	int nbC = listeChemins.size();
    	System.out.println(nbC);
    	for(int i=0; i<nbC; i++) {
    		ArrayList<CaseModele> liste = listeChemins.get(i);
    		System.out.println("for");
    		if(liste.contains(c)) {
    			System.out.println("contains c");
    			for(CaseModele caseC : listeChemins.get(i)) {
    				if(caseC.getType() == CaseType.S1 || caseC.getType() == CaseType.S2 || caseC.getType() == CaseType.S3 || caseC.getType() == CaseType.S4 || caseC.getType() == CaseType.S5)
    					continue;
    				else {
    					caseC.setType(CaseType.empty);
    					casesChemins.remove(caseC);
    				}
    			}
    			listeChemins.remove(i);
        		setChanged();
        		notifyObservers();
    		}
    	}
    }
    
    //detruit le chemin en cours
    public void detruitChemin() {
    	for(CaseModele c : chemin) {
    		if(!casesChemins.contains(c)) {
	    		switch(c.getType()) {
	    		case S1 : 
	    			break;
	    		case S2:
	    			break;
	    		case S3:
	    			break;
	    		case S4:
	    			break;
	    		case S5:
	    			break;
				default :
					c.setType(CaseType.empty);
					break;
	    		}
    		}
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
    	nbCheminsAttendus = 2;
    	
    	return tab;
    }
    
	
    

}
