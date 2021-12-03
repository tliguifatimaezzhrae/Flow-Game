import java.awt.Point;
import java.util.*;

public class Jeu extends Observable{
    // tableau de cases : i, j -> case
    private CaseModele[][] tabJeu;
    private int size;
    private CaseModele depart;
    private CaseModele arrivee;
    private ArrayList<CaseModele> cheminEnCours = new ArrayList<CaseModele>();
    private List<Chemin> listeChemins = new ArrayList<Chemin>();
    private List<CaseModele> casesChemins = new ArrayList<CaseModele>();
    private static int nbCheminsAttendus;
    //private HashMap<CaseModele, Point> cheminH;  //inutile pour le moment penser à l'enlever avec tout ce qui est en rapport
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

    //verifie la case de départ d'un chemin
    public boolean verifDepartChemin() {
    	if(typeNum(depart.getType()))
    		return true;
    	else 
    		return false;
    }
    
    //vérifie validité d'un chemin
    public void verifChemin() {
    	if(depart.getType() == arrivee.getType() && typeNum(depart.getType()) && cheminEnCours.size()>2) {
    		Chemin c = new Chemin(cheminEnCours);
    		listeChemins.add(c);
    		System.out.println("chemin en cours ajouter dans liste");
    		for(CaseModele ca : cheminEnCours) {
    			System.out.println(ca.toString());
    			casesChemins.add(ca);
    		}
    		cheminEnCours.clear();
    	}
    	
    	else {
    		detruitCheminCourant();
    		cheminEnCours.clear();
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
    	int index = cheminEnCours.indexOf(caseApres);
    	CaseModele caseToPaint = cheminEnCours.get(index - 1);
    	if(!verifVoisin(caseToPaint, caseApres)) {
    		detruitCheminCourant();
    		cheminEnCours.clear();
    	}
    	if(index - 2 >= 0) {
        	if(caseToPaint.getType() != CaseType.empty) {
        		detruitCheminCourant();
        		cheminEnCours.clear();
        		setChanged();
        		notifyObservers();
        	}else {
        		CaseModele caseAvant = cheminEnCours.get(index - 2);
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
    
    public void addCaseCheminEnCours(CaseModele c) {
    	cheminEnCours.add(c);
    }
    
    public boolean typeNum(CaseType t) {
    	if(t == CaseType.S1 || t == CaseType.S2 || t == CaseType.S3 || t == CaseType.S4 || t == CaseType.S5) {
    		return true;
    	}
    	else
    		return false;
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
    	for(CaseModele c : cheminEnCours) {
    		System.out.println(c.toString());
    	}
    }
    
    /*public void videChemin() {
    	chemin.clear();
    }*/
    
    //efface un chemin valide quand on clique dessus
    public void effaceChemin(CaseModele c) {
    	//int nbC = listeChemins.size();
    	//System.out.println(nbC);
    	for(Chemin chem : listeChemins) {
    		System.out.println("Chemin : ");
    		chem.afficheChemin();
    		System.out.println("first for");
    		if(chem.contient(c)) {
    			System.out.println("contains c");
    			for(CaseModele caseC : chem.getChemin()) {
    	    		System.out.println("2e for");
    	    		if(typeNum(caseC.getType()))
    					continue;
    				else {
    					caseC.setType(CaseType.empty);
    					casesChemins.remove(caseC);
    				}
    			}
    			listeChemins.remove(chem);
        		setChanged();
        		notifyObservers();
    		}
    	}
    }
    
    //detruit le chemin en cours
    public void detruitCheminCourant() {
    	for(CaseModele c : cheminEnCours) {
    		if(!casesChemins.contains(c)) {
    			if(typeNum(c.getType()))
					continue;
				else {
					c.setType(CaseType.empty);
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
