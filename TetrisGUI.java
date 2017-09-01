import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Dimension;

public class TetrisGUI extends JFrame{
	private JPanel panel;
	private Tablero zonaJuego;
	private Stats zonaStats;
	private JMenuBar menuMnb;
	private JMenu juegoMnu;
	private JMenuItem juegoNuevoItm;
	private JMenuItem salirItm;
	private JMenu configMnu;
	private JMenuItem controlesItm;
	
	public TetrisGUI(){
		this.setTitle("Super Amazing Sugoi Tetris");
		this.setSize(480,700);
		this.setLocationRelativeTo(null);
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuMnb = new JMenuBar();
		juegoMnu = new JMenu("Juego");
		juegoNuevoItm = new JMenuItem("Juego nuevo");
		juegoNuevoItm.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							zonaJuego.reset();
						}
					});
		salirItm = new JMenuItem("Salir");
		salirItm.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							System.exit(0);
						}
					});
		juegoMnu.add(juegoNuevoItm);
		juegoMnu.add(salirItm);
		configMnu = new JMenu("Configuraciones");
		controlesItm = new JMenuItem("Controles");
		controlesItm.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							dialogoControles(e);
						}
					});
		configMnu.add(controlesItm);
		menuMnb.add(juegoMnu);
		menuMnb.add(configMnu);
		this.setJMenuBar(menuMnb); 

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		zonaJuego = new Tablero(this);
		zonaJuego.setMaximumSize(new Dimension(300,600));
		zonaJuego.setMinimumSize(new Dimension(300,600));
		zonaJuego.setBorder(BorderFactory.createEmptyBorder(10,10,10,0));
		panel.add(zonaJuego);
		zonaStats = new Stats();
		panel.add(zonaStats);
		this.setContentPane(panel);
	}

	public Tablero getTablero(){
		return zonaJuego;
	}

	public void ponerPiezaSiguiente(int[][] next, Color co){
		zonaStats.next(next, co);
	}

	public void ponerStats(int p, int l){
		zonaStats.setStats(p,l);
	}

	public void ponerNivel(int level){
		zonaStats.setNivel(level);
	}

	public void dialogoControles(ActionEvent e){
		zonaJuego.pausar();
		JDialog controles = new JDialog(this,"Controles",true);
		ImageIcon ic = new ImageIcon("Controles.jpg");
		JLabel etiqueta = new JLabel(ic);
		controles.add(etiqueta);
		controles.pack();
		controles.setLocationRelativeTo(this);
		controles.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		controles.setVisible(true);
		zonaJuego.pausar();
	}

	public static void main(String[] args){
		long time = System.currentTimeMillis();
		long  controlNivel = System.currentTimeMillis();
	
		TetrisGUI f = new TetrisGUI();
		f.setVisible(true);
		while(true){			//Game loop. 
			if(System.currentTimeMillis() - time > f.getTablero().getVelocidad()){
				f.getTablero().mover();
				time = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - controlNivel > 105000){
				f.getTablero().enviarNivel();
				controlNivel = System.currentTimeMillis();
			}
			
		}
	}
}