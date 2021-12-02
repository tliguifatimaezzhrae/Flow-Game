import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VueControleurGrille extends JFrame implements Observer{
    private static final int PIXEL_PER_SQUARE = 90;
    private Jeu jeu;
    private boolean etatSouris = false;	//pour savoir si la souris est pr�ss�e
    // tableau de cases : i, j -> case
    private VueCase[][] tabCV;
    private int size;
    // hashmap : case -> i, j
    private HashMap<VueCase, Point> hashmap; // voir (*)
    // currentComponent : par défaut, mouseReleased est exécutée pour le composant (JLabel) sur lequel elle a été enclenchée (mousePressed) même si celui-ci a changé
    // Afin d'accéder au composant sur lequel le bouton de souris est relaché, on le conserve avec la variable currentComponent à
    // chaque entrée dans un composant - voir (**)
    private JComponent currentComponent;
    
    public VueControleurGrille(int size) {
    	this.size = size;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        tabCV = new VueCase[size][size];
        hashmap = new HashMap<VueCase, Point>();

        jeu = new Jeu(size);
        jeu.addObserver(this);
        JPanel contentPane = new JPanel(new GridLayout(size, size));
        JDialog popup = new JDialog(this);
        popup.setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        JLabel jLabel = new JLabel("Bravo");
        popup.add(jLabel);
        popup.setVisible(false);
        CaseModele[][] puzzle = new CaseModele[size][size];
        puzzle = jeu.initPuzzle();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	
                tabCV[i][j] = new VueCase(i, j, jeu);
                tabCV[i][j].getCaseM().setType(puzzle[i][j].getType());
                contentPane.add(tabCV[i][j]);

                hashmap.put(tabCV[i][j], new Point(j, i));

                tabCV[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        //Point p = hashmap.get(e.getSource()); // (*) permet de récupérer les coordonnées d'une caseVue
                    	//jeu.videChemin();
	                    	VueCase v = (VueCase) e.getSource();
	                        jeu.setDepart(v.getCaseM());
                    	if(jeu.verifDepartChemin()) {
	                    	etatSouris = true;
	                    	
	                        System.out.println("mousePressed : " + e.getSource());
	                        jeu.addCase(v.getCaseM());
                    	}

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        currentComponent = (JComponent) e.getSource();
                        VueCase v = (VueCase) currentComponent;
                        
                        System.out.println("mouseEntered : " + e.getSource());
                        if(etatSouris) {
                            jeu.addCase(v.getCaseM());
                            jeu.dessineMotif(v.getCaseM());
                        }
                    }


                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                    	etatSouris = false;
                        VueCase v = (VueCase) currentComponent;
                        jeu.setArrive(v.getCaseM());
                        jeu.verifChemin();
                        System.out.println("mouseReleased : " + currentComponent);
                        if(jeu.verifPuzzle()) {
                            popup.setVisible(true);
                        }
                        }
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	VueCase v = (VueCase) currentComponent;
                    	System.out.println("mouseclicked : " + currentComponent);
                    	if(!etatSouris) {
                    		System.out.println("laboucle");
                    		jeu.effaceChemin(v.getCaseM());
                    	}
                    }
                });


            }
        }

        setContentPane(contentPane);

    }

	
	
    public static void main(String[] args) {

        VueControleurGrille vue = new VueControleurGrille(3);  //attention � la taille de VCGrille

        vue.setVisible(true);

    }



	@Override
	public void update(Observable o, Object arg) {
		// repaint les cases ici
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				tabCV[i][j].repaint();
			}
		}
		
	}


	

}
