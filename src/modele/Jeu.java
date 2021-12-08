package modele;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.JComponent;

public class Jeu extends Observable{
    // tableau de cases : i, j -> case
    private CaseModele[][] tabJeu;
    private int sizeTab;
    private CaseModele depart;
    private CaseModele arrivee;
    private ArrayList<CaseModele> cheminEnCours = new ArrayList<CaseModele>();
    private List<Chemin> listeChemins = new ArrayList<Chemin>();
    private List<CaseModele> casesAllChemins = new ArrayList<CaseModele>();
    private int nbCheminsAttendus;
    

    public Jeu(int size) {
    	this.sizeTab = size;
    	tabJeu = new CaseModele[size][size];
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tabJeu[i][j] = new CaseModele(i, j);
            }
    	}
    }
    
    public CaseModele getCase(int i, int j) {
    	return tabJeu[i][j];
    }
    
    public void setDepart(CaseModele d) {
    	depart = d;
    }
    
    public void setArrivee(CaseModele a) {
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

    //verifie la case de départ d'un chemin
    public boolean verifDepartChemin() {
    	if(typeNum(depart.getType()) && !casesAllChemins.contains(depart))
    		return true;
    	else 
    		return false;
    }
    
    //vérifie validité d'un chemin
    public void verifCheminValide() {
    	if(depart.getType() == arrivee.getType() && typeNum(depart.getType()) && cheminEnCours.size()>2 && cheminSansCaseVide() && depart.equals(cheminEnCours.get(0))) {
    		Chemin chemin = new Chemin(cheminEnCours);
    		listeChemins.add(chemin);
    		for(CaseModele c : cheminEnCours) {
    			casesAllChemins.add(c);
    		}
    		cheminEnCours.clear();
    	}
    	
    	else {
    		effaceCheminCourant();
    		cheminEnCours.clear();
			setChanged();
			notifyObservers();
    	}
    }
    
    private boolean cheminSansCaseVide() {
    	for(CaseModele c : cheminEnCours) {    		
    		if(c.getType() == CaseType.empty)
    			return false;
    	}
    	return true;
    }
    
    public boolean verifPuzzle() {
    	if(nbCheminsAttendus == listeChemins.size()) {
	    	for(int i=0; i<sizeTab; i++) {
	    		for(int j=0; j<sizeTab; j++) {
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
    	if(index - 1 > 0) {
    		CaseModele caseToPaint = cheminEnCours.get(index - 1);
	    	if(!verifVoisin(caseToPaint, caseApres)) {
	    		effaceCheminCourant();
	    		cheminEnCours.clear();
	    	}
	    	if(index - 2 >= 0) {
	        	if(caseToPaint.getType() == CaseType.empty) {
	        		CaseModele caseAvant = cheminEnCours.get(index - 2);
	        		caseToPaint.setType(choixMotif(caseAvant, caseToPaint, caseApres));
	        		setChanged();
	        		notifyObservers();
	        	}else {
	        		effaceCheminCourant();
	        		cheminEnCours.clear();
	        		setChanged();
	        		notifyObservers();
	        	}
	    	}
    	}
    }

    private CaseType choixMotif(CaseModele caseAvant, CaseModele caseToPaint, CaseModele caseApres) {
    	String[] dir = new String[2];
    	dir[0] = direction(caseAvant, caseToPaint);
    	dir[1] = direction(caseToPaint, caseApres);
    	if(dir[0] !=null && dir[1] != null) {
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
	    	if((dir[0].equals("B") && dir[1].equals("B")) || (dir[0].equals("H") && dir[1].equals("H")))
	    		return CaseType.v0v1;
    	}
    	
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
    

    //efface un chemin valide quand on clique dessus
    public void detruitCheminValide(CaseModele c) {
    	for(int i=0; i<listeChemins.size(); i++) {
    		Chemin chemin = listeChemins.get(i);
    		if(chemin.contient(c)) {
    			List <CaseModele> casesChemin = chemin.getChemin();
    			for(int x=0; x < casesChemin.size(); x++) {
					casesAllChemins.remove(casesChemin.get(x));
    	    		if(typeNum(casesChemin.get(x).getType()))
    					continue;
    				else {
    					casesChemin.get(x).setType(CaseType.empty);
    				}
    			}
    			listeChemins.remove(chemin);
		        setChanged();
        		notifyObservers();
    		}
    	}
    }
    
    //detruit le chemin en cours
    public void effaceCheminCourant() {
    	for(CaseModele c : cheminEnCours) {
    		if(!casesAllChemins.contains(c)) {
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
