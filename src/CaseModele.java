import java.util.Observable;
import java.util.Random;

public class CaseModele{
    private int x, y;
    private CaseType type;

	public CaseModele(int _x, int _y) {
        x = _x;
        y = _y;
        type = CaseType.empty;
    }
    
    public void setType(CaseType t) {
    	type = t;
    }
    
    public CaseType getType() {
    	return type;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public boolean equals(Object o) {
    	if(o == null)
    		return false;
    	if(o instanceof CaseModele) {
    		CaseModele obj = (CaseModele) o;
    		if(this.x == obj.x && this.y == obj.y && this.type == obj.type)
    			return true;
    		else
    			return false;
    	}
    	else
    		return false;
    }
    
    
    public String toString() {
        return x + ", " + y;

    }
}
