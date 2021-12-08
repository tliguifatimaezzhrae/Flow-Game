package modele;
import java.util.ArrayList;

public class Chemin {
    private ArrayList<CaseModele> chemin = new ArrayList<CaseModele>();
    
    public Chemin(ArrayList<CaseModele> _chemin) {
    	chemin.addAll(_chemin);
    }
	
    public boolean contient(CaseModele c) {
    	if(chemin.contains(c))
    		return true;
    	else 
    		return false;
    }
    
    public ArrayList<CaseModele> getChemin(){
    	return chemin;
    }
    
}
