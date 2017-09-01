import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Component;

//Clase que se encarga del puntaje, lineas, nivel, y mostrar la siguiente pieza.

public class Stats extends JPanel{
	private JLabel nextLbl;
	private JLabel nivelLbl;
	private JLabel lineasLbl;
	private JLabel linesLbl;
	private JLabel puntajeLbl;	
	private JLabel scoreLbl;
	private JLabel levelLbl;
	private JPanel nextExternoPnl;
	private JPanel nextInternoPnl;
	private JPanel siguientePnl;
	private JPanel puntajePnl;
	private JPanel scorePnl;
	private JPanel lineasPnl;
	private JPanel linesPnl;
	private JPanel nivelPnl;
	private JPanel levelPnl;
	private JPanel[][] matrizPnl;
	
	public Stats(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		nextLbl = new JLabel("Siguiente: ");
		nextLbl.setFont(new Font("System",Font.PLAIN,20));
		nextLbl.setForeground(Color.yellow);
		nextInternoPnl = new JPanel(new GridLayout(4,3));
		siguientePnl = new JPanel();
		nextExternoPnl = new JPanel();
		nextExternoPnl.setLayout(new BoxLayout(nextExternoPnl,BoxLayout.Y_AXIS));
		matrizPnl = new JPanel[4][4];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 3; j++){
				matrizPnl[i][j] = new JPanel();
				matrizPnl[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				matrizPnl[i][j].setBackground(Color.black);
				nextInternoPnl.add(matrizPnl[i][j]);
			}
		}
		
		nextInternoPnl.setBorder(BorderFactory.createMatteBorder(0,30,0,30,Color.black));
		nextInternoPnl.setBackground(Color.black);
		nextInternoPnl.setMaximumSize(new Dimension(140,100));
		nextInternoPnl.setMinimumSize(new Dimension(140,100));
		siguientePnl.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.gray));
		siguientePnl.setBackground(Color.blue);
		siguientePnl.add(nextLbl);
		siguientePnl.setMaximumSize(new Dimension(150,40));
		nextExternoPnl.add(siguientePnl);
		nextExternoPnl.add(nextInternoPnl);
		this.add(nextExternoPnl);

		this.add(Box.createRigidArea(new Dimension(0,30)));
		
		nivelLbl = new JLabel("Nivel");
		nivelLbl.setFont(new Font("System",Font.PLAIN,20));
		nivelLbl.setForeground(Color.yellow);
		nivelPnl = new JPanel();
		nivelPnl.setBorder(BorderFactory.createMatteBorder(5,5,0,5,Color.red));
		nivelPnl.setBackground(Color.blue);
		nivelPnl.add(nivelLbl);
		nivelPnl.setMaximumSize(new Dimension(150,40));
		this.add(nivelPnl);

		levelLbl = new JLabel("1");
		levelLbl.setFont(new Font("System",Font.PLAIN,20));
		levelLbl.setForeground(Color.yellow);
		levelPnl = new JPanel();
		levelPnl.setBorder(BorderFactory.createMatteBorder(0,5,5,5,Color.red));
		levelPnl.setBackground(Color.blue);
		levelPnl.add(levelLbl);
		levelPnl.setMaximumSize(new Dimension(150,40));
		this.add(levelPnl);
		
		this.add(Box.createRigidArea(new Dimension(0,30)));

		puntajeLbl = new JLabel("Puntaje: ");
		puntajeLbl.setFont(new Font("System",Font.PLAIN,20));
		puntajeLbl.setForeground(Color.yellow);
		puntajePnl = new JPanel();
		puntajePnl.setBorder(BorderFactory.createMatteBorder(5,5,0,5,Color.magenta));
		puntajePnl.setBackground(Color.blue);
		puntajePnl.add(puntajeLbl);
		puntajePnl.setMaximumSize(new Dimension(150,40));
		this.add(puntajePnl);

		scoreLbl = new JLabel("0");
		scoreLbl.setFont(new Font("System",Font.PLAIN,20));
		scoreLbl.setForeground(Color.yellow);
		scorePnl = new JPanel();
		scorePnl.setBorder(BorderFactory.createMatteBorder(0,5,5,5,Color.magenta));
		scorePnl.setBackground(Color.blue);
		scorePnl.add(scoreLbl);
		scorePnl.setMaximumSize(new Dimension(150,40));
		this.add(scorePnl);

		this.add(Box.createRigidArea(new Dimension(0,30)));
		
		lineasLbl = new JLabel("Lineas");
		lineasLbl.setFont(new Font("System",Font.PLAIN,20));
		lineasLbl.setForeground(Color.yellow);
		lineasPnl = new JPanel();
		lineasPnl.setBorder(BorderFactory.createMatteBorder(5,5,0,5,Color.orange));
		lineasPnl.setBackground(Color.blue);
		lineasPnl.add(lineasLbl);
		lineasPnl.setMaximumSize(new Dimension(150,40));
		this.add(lineasPnl);

		linesLbl = new JLabel("0");
		linesLbl.setFont(new Font("System",Font.PLAIN,20));
		linesLbl.setForeground(Color.yellow);
		linesPnl = new JPanel();
		linesPnl.setBorder(BorderFactory.createMatteBorder(0,5,5,5,Color.orange));
		linesPnl.setBackground(Color.blue);
		linesPnl.add(linesLbl);
		linesPnl.setMaximumSize(new Dimension(150,40));
		this.add(linesPnl);

	}

	public void next(int[][] n, Color c){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 3; j++){
				matrizPnl[i][j].setBackground(Color.black);
			}
		}
		for(int i = 0; i < 4; i++){
			matrizPnl[n[i][0]][n[i][1]-6].setBackground(c);
		}
	}

	public void setStats(int s, int l){
		scoreLbl.setText("" + s);
		linesLbl.setText("" + l);
	}

	public void setNivel(int l){
		levelLbl.setText("" + l);
	}

	
}