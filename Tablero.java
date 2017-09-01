import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JOptionPane;

public class Tablero extends JPanel{
	private JPanel[][] casillas;                            
	private int[][] mapa;				//matriz de enteros en donde se marca la posicion de la figura cayendo
	private int ancho = 15;
	private int alto = 30;
	private Piezas[] p;	
	private boolean llegarAbajo = true;
	private int[][] coordenadasPiezaCayendo;			
	private int piezaActual;
	private int piezaSiguiente;
	private int[][] piezasPuestas;				//matriz de enteros en donde se marcan las piezas ya colocadas
	private int velocidad = 800;
	private int velocidadActual = velocidad;
	private TetrisGUI owner;
	private boolean pausa = false;
	private Color fondoCasillas = Color.black;
	private final int puntosLlegarAbajo = 30;
	private final int puntosFilaCompleta = 100;
	private final int bonusFila = 15;
	private int puntaje = 0;
	private int lineas = 0;
	private int nivel = 1;
	
	public Tablero(TetrisGUI t){
		owner = t;
		casillas = new JPanel[alto][ancho];
		mapa = new int[alto][ancho];
		piezasPuestas = new int[alto][ancho];
		for(int i = 0; i < alto; i ++){
			for(int j = 0; j < ancho; j++){
				casillas[i][j] = new JPanel();
				casillas[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				casillas[i][j].setBackground(fondoCasillas);
				this.add(casillas[i][j]);
				mapa[i][j] = 0;
				piezasPuestas[i][j] = 0;
		
			}
		}
		coordenadasPiezaCayendo = new int[4][2];
		mapa = new int[alto][ancho];
		p = Piezas.values();
		Random rnd = new Random();
		piezaSiguiente = rnd.nextInt(7);				//en este arreglo se guardan los datos de las diferentes piezas
		this.setLayout(new GridLayout(alto,ancho));
		this.addKeyListener(new KeyAdapter() { 
					public void keyPressed(KeyEvent e){
						precionarTecla(e);
					}
					public void keyReleased(KeyEvent e){
						soltarTecla(e);
					};
				});
		this.setFocusable(true);
	}

	public void precionarTecla(KeyEvent e){				//Metodo encargado de la jugabilidad, moviendo la pieza de derecha a izquierda, o acelerando su caida
		if(e.getKeyCode() == KeyEvent.VK_LEFT){			//dependiendo del boton pulsado
			if(this.extremoIzq() > 0 && !colicionIzquierda()){
				Color aux = casillas[coordenadasPiezaCayendo[0][0]][coordenadasPiezaCayendo[0][1]].getBackground();
				for(int i = 0; i < 4; i ++){
					mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 0;
					casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(fondoCasillas);
					coordenadasPiezaCayendo[i][1]--;
				}
				for(int i = 0; i < 4; i ++){
					mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 1;
					casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(aux);
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(this.extremoDer() + 1 < ancho && !colicionDerecha()){
				Color aux = casillas[coordenadasPiezaCayendo[0][0]][coordenadasPiezaCayendo[0][1]].getBackground();
				for(int i = 0; i < 4; i ++){
					mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 0;
					casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(fondoCasillas);
					coordenadasPiezaCayendo[i][1]++;
				}
				for(int i = 0; i < 4; i ++){
					mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 1;
					casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(aux);
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			velocidad = 15;
		}
	}

	public boolean colicionIzquierda(){			//Metodo que comprueba si hay piezas a la izquierda de nuestra pieza actual
		boolean hayColicion = false;
		for(int i = 0; i < 4; i ++){
			if(piezasPuestas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1] -1] == 1)
				hayColicion = true;
			
		}
		return hayColicion;
	}
	
	public boolean colicionDerecha(){			//Metodo que comprueba si hay piezas a la derecha de nuestra pieza actual
		boolean hayColicion = false;
		for(int i = 0; i < 4; i ++){
			if(piezasPuestas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1] + 1] == 1)
				hayColicion = true;
			
		}
		return hayColicion;
	}

	public boolean colicionVertical(){			//Metodo que comprueba si hay piezas abajo de nuestra pieza actual
		boolean hayColicion = false;
		for(int i = 0; i < 4; i ++){
			if(piezasPuestas[coordenadasPiezaCayendo[i][0] + 1][coordenadasPiezaCayendo[i][1]] == 1)
				hayColicion = true;
			
		}
		return hayColicion;
	}
	
	public void soltarTecla(KeyEvent e){				//Metodo encargado de redibujar la pieza al rotarla
		if(e.getKeyCode() == KeyEvent.VK_UP){
			Color aux = casillas[coordenadasPiezaCayendo[0][0]][coordenadasPiezaCayendo[0][1]].getBackground();

			for(int i = 0; i < 4; i ++){
				mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 0;
				casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(fondoCasillas);
			}

			p[piezaActual].rotar(coordenadasPiezaCayendo,piezasPuestas);

			for(int i = 0; i < 4; i ++){
				mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 1;
				casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(aux);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			velocidad = velocidadActual;
		}
		if(e.getKeyCode() == KeyEvent.VK_P){
			pausa = !pausa;
			if(pausa)
				owner.setTitle(owner.getTitle() + " ****PAUSA****");
			else
				owner.setTitle(owner.getTitle().substring(0,26));
		}
	}

	public int extremoDer(){				//Retorna la componente X mayor de las coordenadas de la pieza actual
		int mayor = coordenadasPiezaCayendo[0][1];
		for(int i = 0; i < 4; i++){
			if(coordenadasPiezaCayendo[i][1] > mayor)
				mayor = coordenadasPiezaCayendo[i][1];
			
		}
		return mayor;
	}

	public int extremoIzq(){				//Retorna la componente X menor de las coordenadas de la pieza actual
		int menor = coordenadasPiezaCayendo[0][1];
		for(int i = 0; i < 4; i++){
			if(coordenadasPiezaCayendo[i][1] < menor)
				menor = coordenadasPiezaCayendo[i][1];
			
		}
		return menor;
	}

	public int extremoInf(){				//Retorna la componente Y mayor de las coordenadas de la pieza actual
		int mayor = coordenadasPiezaCayendo[0][0];
		for(int i = 0; i < 4; i++){
			if(coordenadasPiezaCayendo[i][0] > mayor)
				mayor = coordenadasPiezaCayendo[i][0];
			
		}
		return mayor;
	}

	public int extremoSup(){				//Retorna la componente Y menor de las coordenadas de la pieza actual
		int menor = coordenadasPiezaCayendo[0][0];
		for(int i = 0; i < 4; i++){
			if(coordenadasPiezaCayendo[i][0] < menor)
				menor = coordenadasPiezaCayendo[i][0];
			
		}
		return menor;
	}

	public int getVelocidad(){
		return velocidad;
	}

	public void gameOver(){
		Object[] opciones = {"Empezar un juego nuevo","Ragequit"};
		int n = JOptionPane.showOptionDialog(owner,"Se acabo el juego, PERDEDOR!","Game Over",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,null,opciones,opciones[1]);
		if(n == JOptionPane.YES_OPTION)
			reset();
		if(n == JOptionPane.NO_OPTION || n == JOptionPane.CLOSED_OPTION)
			System.exit(0);
	}

	public void reset(){
		for(int i = 0; i < alto; i ++){
			for(int j = 0; j < ancho; j++){
				casillas[i][j].setBackground(fondoCasillas);
				mapa[i][j] = 0;
				piezasPuestas[i][j] = 0;
		
			}
		}
		velocidad = 800;
		this.llegarAbajo = true;
		puntaje = 0;
		lineas = 0;
		nivel = 1;
		enviarStats();
		owner.ponerNivel(nivel);
		this.mover();
	}

	public void pausar(){
		pausa = !pausa;
	}

	public boolean enFila(int[] filas, int coo){			//Metodo para hacer que una coordenada repetida no sea contada como una fila completa
		for(int i = 0; i < 4; i++){
			if(filas[i] == coo){
				return true;
			}
		}
		return false;
	}

	public void enviarPiezaSiguiente(){
		owner.ponerPiezaSiguiente(p[piezaSiguiente].getPieza(),p[piezaSiguiente].getColor());
	}

	public void enviarStats(){
		owner.ponerStats(puntaje,lineas);
	}

	public void enviarNivel(){
		if(velocidad >= 0){
			velocidad -=40;
			velocidadActual = velocidad;
			nivel++;
			owner.ponerNivel(nivel);
		}
	}

	public void comprobarFilas(){				//Metodo para comprobar si se completo una fila
		int[] filas = new int[4];
		int contador = 0;
		int numFilas = 0;

		for(int i = 0; i < 4; i++){
			for(int j = 0; j < ancho; j++){
				if(piezasPuestas[coordenadasPiezaCayendo[i][0]][j] == 1){			//Si una fila de piezasPuestas tiene solo unos, significa que esta completa
					contador++;
				}
			}
			if(contador == ancho && !enFila(filas,coordenadasPiezaCayendo[i][0])){			//Se almacenan las coordenadas de las filas completas en el arreglo filas
				filas[numFilas] = coordenadasPiezaCayendo[i][0];
				System.out.println("Coordenada: " + coordenadasPiezaCayendo[i][0]);
				System.out.println("filas[" + numFilas + "]: " + filas[numFilas]);
				
				numFilas++;
			}
			contador = 0;
		}
	
		if(numFilas > 0){		//Si se completa una fila luego de poner una pieza...
			for(int i=0;i <= numFilas-1;i++){
					System.out.print("|" + filas[i] + "|");
			}
	        		 for(int i = 0; i < numFilas - 1; i++){					//Algoritmo Burbuja, para ordenar las coordenadas de las filas de mayor a menor
	              		for(int j = 0; j < numFilas - i - 1; j++){
	                   			if(filas[j + 1] < filas[j]){
	                      				int aux = filas[j + 1];
	                     				filas[j + 1] = filas[j];
	                     				filas[j] = aux;
	                   			}
				}
			}
			
			System.out.println("numFilas: " + numFilas);
			for(int i=0;i <= numFilas-1;i++){
				System.out.print("|" + filas[i] + "|");
			}

			System.out.println("numFilas: " + numFilas);
			
			puntaje += (puntosFilaCompleta*numFilas) + (bonusFila*(numFilas-1));
			lineas += numFilas;
		
			for(int i = filas[numFilas-1]; i > 0; i--){				//Esta seccion se encarga de borrar las filas completas, y de bajar todas las demas
				for(int j = 0; j < ancho; j++){	
					if(i - numFilas < 0){
						piezasPuestas[i][j] = 0;
						casillas[i][j].setBackground(fondoCasillas);
					}
					else{
						piezasPuestas[i][j] = piezasPuestas[i-numFilas][j];
						casillas[i][j].setBackground(casillas[i-numFilas][j].getBackground());
					}
				}
			}
			
			
		}
	}

	public void mover(){	
		if(!pausa){				//Movimiento uniforme hacia abajo del juego
			if(llegarAbajo){
				this.piezaNueva();
				llegarAbajo = false;
				enviarPiezaSiguiente();	
			}
			else{
				if(extremoInf() + 1 < alto && !colicionVertical()){
					Color aux = casillas[coordenadasPiezaCayendo[0][0]][coordenadasPiezaCayendo[0][1]].getBackground();
					for(int i = 0; i < 4; i ++){
						mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 0;
						casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(fondoCasillas);
						coordenadasPiezaCayendo[i][0]++;
					}
					for(int i = 0; i < 4; i ++){
						mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 1;
						casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(aux);
					}
				}
				else {
					llegarAbajo = true;
					for(int i = 0; i < 4; i ++){
						piezasPuestas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 1;
					}
					puntaje += puntosLlegarAbajo;
					
					comprobarFilas();
					enviarStats();

					for(int i = 0; i < alto; i ++){
						for(int j = 0; j < ancho; j++){
							System.out.print("|"+piezasPuestas[i][j]+"|");
						}
						System.out.println("");
					}
					this.mover();
				}
			}
		}
	}
	
	public void piezaNueva(){				//Genera una nueva pieza aleatoria
		Random rnd = new Random();
		piezaActual = piezaSiguiente;
		piezaSiguiente = rnd.nextInt(7);
		//piezaActual = 0;

		coordenadasPiezaCayendo = p[piezaActual].getPieza();
		
		for(int i = 0; i < 4; i++){
			if(piezasPuestas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] == 1){
				gameOver();
				break;
			}
		}
		for(int i = 0; i < 4; i++){
			mapa[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]] = 1;
			casillas[coordenadasPiezaCayendo[i][0]][coordenadasPiezaCayendo[i][1]].setBackground(p[piezaActual].getColor());
		}
	}

}