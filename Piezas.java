import java.awt.Color;

public enum Piezas{BARRA(Color.red), ZETA(Color.green), ESE(Color.blue), ELE(Color.yellow), ELEINV(Color.gray), TE(Color.cyan), CUADRADO(Color.pink);
	Color color;
	int[] forma;
	int giro = 0;
	int[][] coordenadas = new int[][] {	//Todas las coordenadas que pueden usar las figuras
					{ 0, 0},    //0			
					{ 0, 1},    //1
					{-1, 0},	//2
					{ 1, 0},	//3
					{-1,-1},	//4
					{ 0,-1},	//5
					{ 1,-1},	//6
					{ 0,-2}};	//7
	
	Piezas(Color c){
		this.color = c;
		switch(this.ordinal()){		//Estos arreglos representan distintas caracteristicas de las figuras. Los 4 primeros elementos son sus coordenadas para dibujarlos en un plano
						//El 5to elemento es el numero de giros que puede dar. Los 6to y 7mo elementos son deltas X e Y para poder dibujarlos correctamentes en el Tablero
			case 0 :  forma = new int[] 	{0,1,5,7,1,2,7};
				break;
			case 1 :  forma = new int[]    {0,2,5,6,1,1,7};
				break;
			case 2:  forma = new int[]	{0,3,5,4,1,1,7};
				break;
			case 3 :  forma = new int[]	{0,1,5,6,3,1,7};
				break;
			case 4 :  forma = new int[]	{0,1,5,4,3,1,7};
				break;
			case 5 :  forma = new int[]	{0,1,2,3,3,0,7};
				break;
			case 6 :  forma = new int[]	{0,5,6,3,0,1,6};
				break;
		}
	}

	public Color getColor(){
		return color;
	}

	public int[][] getPieza(){			//Genera las coordenadas de las figuras. Las genera en base a las dimensiones del Tablero
		int[][] matrix = new int[4][2];
		for(int i = 0; i < 4; i++){
			matrix[i][1] = coordenadas[forma[i]][0] + forma[6];
			matrix[i][0] = coordenadas[forma[i]][1] + forma[5];
		}
		return matrix;
	}

	public void rotar(int[][] coordenadasPiezaActual, int[][] piezasPuestas){
		
		if(giro < forma[4] && forma[4] > 0){		//Este if es para gestionar la rotacion de las piezas, mas especificamente a las L, L invertida y la T, que pueden girar sin problemas
							//ademas de excluir al cuadrado por razones obvias
			int[][] arregloAux = new int[4][2];
			int[] eje = new int[] {coordenadasPiezaActual[0][0],coordenadasPiezaActual[0][1]};		//Establece el eje de rotacion
			boolean rotacionPermitida = true;
			
			for(int i = 0; i < 4; i++){
					arregloAux[i][0] = coordenadasPiezaActual[i][0];
					arregloAux[i][1] = coordenadasPiezaActual[i][1];
				}
			
			for(int i = 0; i < 4; i++){				//Toda la rotacion se hace en un arreglo auxiliar, para despues verificar si la pieza rotada no sale de los margenes
									//o choca con otras piezas
				arregloAux[i][0] -= eje[0];
				arregloAux[i][1] -= eje[1];
				int aux = arregloAux[i][0];
				arregloAux[i][0] = arregloAux[i][1];
				arregloAux[i][1] = aux * -1;
				arregloAux[i][0] += eje[0];
				arregloAux[i][1] += eje[1];
				if(arregloAux[i][0] >= 30 || arregloAux[i][0] < 0 || arregloAux[i][1] >= 15 || arregloAux[i][1] < 0 || piezasPuestas[arregloAux[i][0]][arregloAux[i][1]] == 1){
					rotacionPermitida = false;
					break;
				}
			}
	
			if(rotacionPermitida){		//Si se puede rotar sin problemas, modifica el arreglo enviado por referencia
				for(int i = 0; i < 4; i++){
					coordenadasPiezaActual[i][0] = arregloAux[i][0];
					coordenadasPiezaActual[i][1] = arregloAux[i][1];
				}
			}
			giro++;
			if(giro >= 3)
				giro = 0;
		}
		else if(forma[4] != 0){			//Aqui se trata a la I, S , Z
			int[][] arregloAux = new int[4][2];
			int[] eje = new int[] {coordenadasPiezaActual[0][0],coordenadasPiezaActual[0][1]};
			boolean rotacionPermitida = true;
			
			for(int i = 0; i < 4; i++){
					arregloAux[i][0] = coordenadasPiezaActual[i][0];
					arregloAux[i][1] = coordenadasPiezaActual[i][1];
				}
			
			for(int i = 0; i < 4; i++){
				arregloAux[i][0] -= eje[0];
				arregloAux[i][1] -= eje[1];
				int aux = arregloAux[i][0];
				arregloAux[i][0] = arregloAux[i][1]*-1;			//Aqui varia un poco respecto a lo de arriba, ya que en estas piezas no se puede estabecer un unico eje de rotacion
				arregloAux[i][1] = aux;				//la pieza se va girando y trasladando, en vez de solo girar en un eje
				arregloAux[i][0] += eje[0];
				arregloAux[i][1] += eje[1];
				if(arregloAux[i][0] >= 30 || arregloAux[i][0] < 0 || arregloAux[i][1] >= 15 || arregloAux[i][1] < 0 || piezasPuestas[arregloAux[i][0]][arregloAux[i][1]] == 1){
					rotacionPermitida = false;
					break;
				}
			}
	
			if(rotacionPermitida){
				for(int i = 0; i < 4; i++){
					coordenadasPiezaActual[i][0] = arregloAux[i][0];
					coordenadasPiezaActual[i][1] = arregloAux[i][1];
				}
			}
			giro = 0;
		}
	}


}