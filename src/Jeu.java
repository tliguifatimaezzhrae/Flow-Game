import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Jeu extends Observable{
    // tableau de cases : i, j -> case
    private CaseModele[][] tabJeu;
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

    public CaseModele getCase(int i, int j) {
    	return tabJeu[i][j];
    }
    
	
    

}
