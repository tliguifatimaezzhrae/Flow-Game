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
    
    public void afficheChemin() {
    	for(CaseModele c : chemin) {
    		System.out.println(c.toString());
    	}
    }
}
