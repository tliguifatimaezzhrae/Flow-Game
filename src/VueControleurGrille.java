import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class VueControleurGrille extends JFrame implements Observer{
    private static final int PIXEL_PER_SQUARE = 60;
    private Jeu jeu;
    // tableau de cases : i, j -> case
    private VueCase[][] tabCV;
    // hashmap : case -> i, j
    private HashMap<VueCase, Point> hashmap; // voir (*)
    // currentComponent : par défaut, mouseReleased est exécutée pour le composant (JLabel) sur lequel elle a été enclenchée (mousePressed) même si celui-ci a changé
    // Afin d'accéder au composant sur lequel le bouton de souris est relaché, on le conserve avec la variable currentComponent à
    // chaque entrée dans un composant - voir (**)
    private JComponent currentComponent;

    public VueControleurGrille(int size) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE);
        tabCV = new VueCase[size][size];
        hashmap = new HashMap<VueCase, Point>();

        jeu = new Jeu(size);
        JPanel contentPane = new JPanel(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                tabCV[i][j] = new VueCase(i, j, jeu);
                contentPane.add(tabCV[i][j]);

                hashmap.put(tabCV[i][j], new Point(j, i));

                tabCV[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        //Point p = hashmap.get(e.getSource()); // (*) permet de récupérer les coordonnées d'une caseVue


                        ((VueCase) e.getSource()).caseM.rndType();
                        System.out.println("mousePressed : " + e.getSource());

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        currentComponent = (JComponent) e.getSource();
                        System.out.println("mouseEntered : " + e.getSource());
                    }


                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        System.out.println("mouseReleased : " + currentComponent);
                    }
                });


            }
        }

        setContentPane(contentPane);

    }

	
	
    public static void main(String[] args) {

        VueControleurGrille vue = new VueControleurGrille(6);

        vue.setVisible(true);

    }



	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


	

}
