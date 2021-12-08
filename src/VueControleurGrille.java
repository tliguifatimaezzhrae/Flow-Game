import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VueControleurGrille extends JFrame implements Observer{
    private static final int PIXEL_PER_SQUARE = 200;
    private Jeu jeu;
    private boolean etatSouris = false;	//pour savoir si la souris est pressée
    // tableau de cases : i, j -> case
    private VueCase[][] tabCV;
    private int size;
    private JComponent currentComponent;
    public VueControleurGrille(int size) {
    	this.size = size;
    	tabCV = new VueCase[size][size];
        jeu = new Jeu(size);
        
        jeu.addObserver(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        JPanel contentPane = new JPanel(new GridLayout(size, size));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPane.setBackground(Color.BLACK);
        JDialog popup = new JDialog(this);
        popup.setLayout(new BorderLayout());
       	popup.setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        JLabel jLabel = new JLabel("Bravo vous avez resolu le puzzle ", JLabel.CENTER);
        JButton quitter = new JButton("quitter");
        popup.add(jLabel);
        popup.add(quitter, BorderLayout.SOUTH);
        popup.setVisible(false);
        quitter.addActionListener(e -> {
            this.dispose();
         });
        CaseModele[][] puzzle = new CaseModele[size][size];
        puzzle = jeu.initPuzzle();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tabCV[i][j] = new VueCase(i, j, jeu);
                tabCV[i][j].getCaseM().setType(puzzle[i][j].getType());
                contentPane.add(tabCV[i][j]);

                tabCV[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
	                    	VueCase v = (VueCase) e.getSource();
	                        System.out.println("mousePressed : " + e.getSource());
	                        
	                        jeu.setDepart(v.getCaseM());
                    	if(jeu.verifDepartChemin()) {
	                    	etatSouris = true;
	                        jeu.addCaseCheminEnCours(v.getCaseM());
                    	}

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        currentComponent = (JComponent) e.getSource();
                        VueCase v = (VueCase) currentComponent;
                        System.out.println("mouseEntered : " + e.getSource());
                        
                        if(etatSouris) {
                            jeu.addCaseCheminEnCours(v.getCaseM());
                            jeu.dessineMotif(v.getCaseM());
                        }
                    }


                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                    	etatSouris = false;
                        VueCase v = (VueCase) currentComponent;
                        System.out.println("mouseReleased : " + currentComponent);
                        
                        jeu.setArrivee(v.getCaseM());
                        jeu.verifCheminValide();
                        if(jeu.verifPuzzle()) {
                            popup.setVisible(true);
                        }
                   }
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	VueCase v = (VueCase) currentComponent;
                    	System.out.println("mouseclicked : " + currentComponent);
                		jeu.detruitCheminValide(v.getCaseM());
                    
                    }
                });


            }
        }

        setContentPane(contentPane);

    }

	@Override
	public void update(Observable o, Object arg) {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				tabCV[i][j].repaint();
			}
		}
		
	}
	
    public static void main(String[] args) {

        VueControleurGrille vue = new VueControleurGrille(3);  //attention à la taille de VCGrille

        vue.setVisible(true);

    }



	


	

}
