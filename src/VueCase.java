import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;


// TODO : redéfinir la fonction hashValue() et equals(Object) si vous souhaitez utiliser la hashmap de VueControleurGrille avec VueCase en clef

public class VueCase extends JPanel {
	private int x;
	private int y;
	private CaseModele caseM;
	
    public VueCase(int x, int y, Jeu j) {
    	caseM = j.getCase(x, y);
    	this.x = x;
    	this.y = y;
    	repaint();
    }
    
    public CaseModele getCaseM() {
    	return caseM;
    }
    public  void setCaseM(CaseModele caseM ) {
    	this.caseM=caseM;
    }
    

    private void drawNoon(Graphics g) {
    	//g.setColor(caseM.getColor());
    	//g.drawRect(50, 35, 150, 150);
    	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
       g.drawLine(getWidth()/2, getHeight()/2, getWidth()/2, 0);
    }

    private void drawNine(Graphics g) {
    	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
g.drawLine(0, getHeight()/2, getWidth()/2, getHeight()/2);
    }

    private void drawSix(Graphics g) {
       g.drawLine(getWidth()/2, getHeight()/2, getWidth()/2, getHeight());
  //g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
    }

    private void drawThree(Graphics g) {
    	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
        g.drawLine(getWidth()/2, getHeight()/2, getWidth(), getHeight()/2);
    }



    @Override
    public void paintComponent(Graphics g) {
     g.clearRect(0, 0, getWidth(), getHeight());
    // Rectangle2D deltaText=g.getFont().getStringBounds("0", g.getFontMetrics().getFontRenderContext());

g.drawRoundRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2, 5, 5);

        switch(caseM.getType()) {
        case S1 :
            g.setColor(Color.BLACK);
            g.fillOval(getWidth()/3+5, getHeight()/3 +5,getWidth()/3, getHeight()/3);
            break;
        case S2 :
            g.setColor(Color.RED);
            g.fillOval(getWidth()/3 +5, getHeight()/3 +5,getWidth()/3, getHeight()/3);
            break;
       
            case h0v0 :
            	
            	
            	g.setColor(Color.BLACK);
            	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
            	
                drawNine(g);
                drawNoon(g);
                break;
            case h0v1 :
            	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
            	g.setColor(Color.black);
                drawNine(g);
                drawSix(g);
                break;
            case h1v0:
            	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
            	g.setColor(Color.BLACK);
                drawNoon(g);
                drawThree(g);
                break;
            case h1v1 :
            	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
            	g.setColor(Color.BLACK);
                drawThree(g);
                drawSix(g);
               
                break;
            case h0h1:
            	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
            	g.setColor(Color.RED);
                drawThree(g);
                drawNine(g);
                break;
            case v0v1:
            	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
            	g.setColor(Color.RED);
                drawNoon(g);
                drawSix(g);
                break;
            case cross:
            	//g.fillRect(getWidth()/2-10, 0, 20, getWidth()/2+10);
                drawNoon(g);
                drawSix(g);
                drawThree(g);
                drawNine(g);
                break;

        }
    }
     
        public String toString() {
        return caseM.getX() + ", " + caseM.getY();

    }

}
