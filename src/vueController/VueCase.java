package vueController;
import javax.swing.*;

import modele.CaseModele;
import modele.Jeu;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;


// TODO : redÃ©finir la fonction hashValue() et equals(Object) si vous souhaitez utiliser la hashmap de VueControleurGrille avec VueCase en clef

public class VueCase extends JPanel {
	private int x;
	private int y;
	private CaseModele caseM;
	
    public VueCase(int x, int y, Jeu j) {
    	caseM = j.getCase(x, y);
    	this.x = x;
    	this.y = y;
    }
    
    public CaseModele getCaseM() {
    	return caseM;
    }

    private void drawNoon(Graphics g) {
       g.drawLine(getWidth()/2, getHeight()/2, getWidth()/2, 0);
    }

    private void drawNine(Graphics g) {
    	g.drawLine(0, getHeight()/2, getWidth()/2, getHeight()/2);
    }

    private void drawSix(Graphics g) {
       g.drawLine(getWidth()/2, getHeight()/2, getWidth()/2, getHeight());
    }

    private void drawThree(Graphics g) {
    	g.drawLine(getWidth()/2, getHeight()/2, getWidth(), getHeight()/2);
    }



    @Override
    public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
	    g.drawRect(0, 0, getWidth(), getHeight());
		g.fillRect(0, 0, getWidth(), getHeight());
	
		g.setColor(Color.WHITE);
		g.drawLine(0, 0, getWidth(), 0);
		g.drawLine(0,0,0, getHeight());
		g.setColor(Color.BLUE);
		
	 	Rectangle2D deltaText =  g.getFont().getStringBounds("0", g.getFontMetrics().getFontRenderContext()); // "0" utilisé pour gabarit
	 	
	 	Graphics2D g1 = (Graphics2D) g;
		BasicStroke line = new BasicStroke(15.0f);
		g1.setStroke(line);
        switch(caseM.getType()) {
	        case S1 :
	            g.setColor(Color.YELLOW);
	            g.fillOval(getWidth()/3+5, getHeight()/3 +5,getWidth()/3, getHeight()/3);
	            break;
	        case S2 :
	            g.setColor(Color.RED);
	            g.fillOval(getWidth()/3 +5, getHeight()/3 +5,getWidth()/3, getHeight()/3);
	            break;
	        case S3 :
	            g.setColor(Color.GREEN);
	            g.fillOval(getWidth()/3+5, getHeight()/3 +5,getWidth()/3, getHeight()/3);
	            break;
	        case S4 :
	            g.setColor(Color.ORANGE);
	            g.fillOval(getWidth()/3+5, getHeight()/3 +5,getWidth()/3, getHeight()/3);
	            break;
	        case S5 :
	            g.setColor(Color.PINK);
	            g.fillOval(getWidth()/3+5, getHeight()/3 +5,getWidth()/3, getHeight()/3);
	            break;
	
	        case h0v0 :
	        	drawNine(g);
	            drawNoon(g);
	            break;
	        case h0v1 :
	            drawNine(g);
	            drawSix(g);
	            break;
	        case h1v0:
	            drawNoon(g);
	            drawThree(g);
	            break;
	        case h1v1 :
	            drawThree(g);
	            drawSix(g);
	           
	            break;
	        case h0h1:
	            drawThree(g);
	            drawNine(g);
	            break;
	        case v0v1:
	            drawNoon(g);
	            drawSix(g);
	            break;
	        case cross:
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
