import java.util.ArrayList;

public class Chemin {
    private ArrayList<CaseModele> chemin;
    
    public Chemin(ArrayList<CaseModele> chemin) {
    	this.chemin = chemin;
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
