package comecocos;

import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class mapa {
	
	// Variable de la matriz
	int alto = 25;
	int ancho = 25;
	char limite = '#';
	char pared = '|';
	char personaje = 'C';
	char personajeChetado = 'X' ;
	boolean modoCazador = false;
	int tiempoCazador = 0;
	
	char galletas = '.';
	char chetadas = '*'; 
	char vacio = ' ';
	
	// Variable calculo centro
	int centroFila = (alto) / 2;
	int centroColumna = (ancho) /2;
	
	// Variable posicion personajes
	int filaJugador;
	int columnaJugador;
	
	//Variable posicion fantasma
	char [] fantasma = { 'F', 'F', 'F', 'F'};
	int[] filaFantasma = new int[4];
	int[] columnaFantasma = new int[4];
	int [][] posicionesFantasmas = {
			{centroFila, centroColumna},
			{centroFila - 1, centroColumna},
			{centroFila, centroColumna - 1},
			{centroFila, centroFila + 1}
	};
	
	
	
	// Variables puntuacion
	int puntos = 0;
	int multiplicador = 1;
	
	//Variable nivel
	int nivel = 0;
	
	// Variable seguir jugando
	boolean seguirJugando = true;
	
	// Caja fantasmas para jugador
	boolean bloqueado = (filaJugador >= (centroFila - 1) && filaJugador <= (centroFila + 1) 
			&& columnaJugador >= (centroColumna - 2) && columnaJugador <= (centroColumna + 2));
	
	// Variables movimiento
	
	char direccion;
	
	
	// Variables niveles
	char superior = 'U'; //Para subir a un nivel mas exigente (uppper)
	char inferior = 'W'; //Para bajar a un nivel mas facil (lower)
	

	Random ran = new Random();
	Scanner sc = new Scanner(System.in);
	
	
	// Matriz del mapa bidemensional
	char[][] mapa; 
	
	//Constructor
	public mapa(){
		mapa = new char[alto][ancho];
	}
	
	
	// Metodo Relleno del mapa
	
	public void generarMapa() {
		
		do {
			
			generarBordes();
			generarParedes();
			generarChetadas();
			generarJugador();
			generarCaja();
			generarFantasmas();
			
		} while (!verificarGalletasAlcanzables());
	}
		
	// Generar Bordes y galletas
	public void generarBordes() {
		for(int i = 0; i < alto; i++) {
			for(int j = 0; j < ancho; j++) {
				
				if ( i == 0 || j == 0 || i == alto -1 || j == ancho -1 ) {
					mapa[i][j] = limite ;
				
				
				 }else {
					mapa[i][j] = galletas;
				}
			}
			
		}
	}
		
		// Generar paredes
	public void generarParedes() {
		for(int i = 2; i < alto - 2; i++) { // para tener espacio para pasar seguro al lado de los limites ponemos el valor 2 y -2
			for(int j = 2; j < ancho - 2; j++) {
				
				if (i >= (centroFila - 2) && i <= (centroFila + 2) 
					&& j >= (centroColumna - 3)	 && j <= (centroColumna + 3)) {
						
						continue; // no colocar paredes al rededor de la caja de fantasmas
				} 
				
				if (ran.nextInt(100) < 20) {
					
					mapa[i][j] = pared;
				}
			}
		}
	}
				
	// Generar galletas para subir o bajar de nivel
	public void niveles() {
		boolean existeSuperior = false;
		boolean existeInferior = false;
		
		for(int i = 0; i < alto; i++) {
			for(int j = 0; j < ancho; j++) {
				
				if(mapa[i][j] == superior) {
					existeSuperior = true;
				}
					
				if(mapa[i][j] == inferior) {
					existeInferior = true;					
				}
			}
		}
	
		if (!existeSuperior && (ran.nextInt(100) < 5)) {
			int fila ;
			int columna;
			
			do {
				fila = ran.nextInt(alto -3 ) + 1; //Para que al comersela el jugador no se genere en el limite
				columna = ran.nextInt(ancho - 3) + 1;
				
			}while (mapa[fila][columna] != galletas);
			
			mapa[fila][columna] = superior;
		}
		
		if (!existeInferior && (ran.nextInt(100) < 5)) {
			int fil;
			int col;
			
			do {
				fil = ran.nextInt(alto -2 ) + 1;
				col = ran.nextInt(ancho - 2) + 1;
				
			}while (mapa[fil][col] != galletas);
			
			mapa[fil][col] = inferior;
		}
			
	}
	
	// Generar galletas chetadas
	public void generarChetadas() {
	
		int cantidadChetadas = 0;
		int maxChetadas = (alto + ancho) / 3;
		
		for(int i = 1; i < alto - 1; i++) { 
			for(int j = 1; j < ancho - 1; j++) {
				
				do {
					if (i >= (centroFila - 2) && i <= (centroFila + 2) 
						&& j >= (centroColumna - 3)	 && j <= (centroColumna + 3) ) {
							
							continue; // no colocar paredes al rededor de la caja de fantasmas
					} 
				
					if (ran.nextInt(100) < 5) {
						mapa[i][j] = chetadas;
						cantidadChetadas++;
					}
					
				} while(cantidadChetadas <= maxChetadas );
			}
		}
	}
	
		// Generar jugador
	public void generarJugador() {
		
		do {
		
		// Posicion jugador aleatorio
		
		
			filaJugador = (ran.nextInt(alto - 2)) + 1 ;  // Random entre el valor de las filas 
			columnaJugador = (ran.nextInt(ancho - 2)) + 1;
		
		// Para que el personaje no se genere donde ira la caja de los fantasmas y los fantasmas
		} while(bloqueado);
					
		mapa[filaJugador][columnaJugador] = personaje;	
	}
	
	
	
	public void movimientoJugador() {
		
		direccion = sc.next().charAt(0);
		int nuevaFila = filaJugador;
		int nuevaColumna = columnaJugador;
		
		
		mapa[filaJugador][columnaJugador] = vacio ;
		
		switch (direccion) {
			
		case 'w' -> {
			
			if ((nuevaFila > 1 && mapa[nuevaFila - 1 ][nuevaColumna] != limite ) &&
				(nuevaFila > 1 && mapa[nuevaFila - 1 ][nuevaColumna] != pared )){
				nuevaFila--;
			}
		}
		case 's' -> {
			
			if ((nuevaFila < alto - 2 && mapa[nuevaFila + 1 ][nuevaColumna] != limite ) &&
				(nuevaFila < alto - 2 && mapa[nuevaFila + 1 ][nuevaColumna] != pared )) {
				nuevaFila++;
			}
		}
		case 'a' -> {
			
			if ((nuevaColumna > 1 && mapa[nuevaFila][nuevaColumna - 1 ] != limite ) &&
				(nuevaColumna > 1 && mapa[nuevaFila][nuevaColumna - 1 ] != pared )) {
				nuevaColumna--;
			}
		}
		case 'd' -> {
			
			if ((nuevaColumna < ancho - 2 && mapa[nuevaFila][nuevaColumna + 1 ] != limite ) &&
				(nuevaColumna < ancho - 2 && mapa[nuevaFila][nuevaColumna + 1 ] != pared )){
				nuevaColumna++;
			}
		}
		}
		if (mapa[nuevaFila][nuevaColumna] == chetadas) {
			modoCazador = true;
			tiempoCazador = 10;
			personaje = personajeChetado;
			
		}
		for (char F : fantasma) {
			if (mapa[nuevaFila][nuevaColumna] == F) {
				
				if (modoCazador) {
					eliminarFantasma(nuevaFila, nuevaColumna);
					
				} else {
					seguirJugando = false;
					return;
				}
			}
		}
	
	
		if(modoCazador) {
			tiempoCazador--;
			if (tiempoCazador == 1) {
				personaje = 'x';
			}
			if (tiempoCazador <= 0) {
				modoCazador = false;
				personaje = 'C';
			}
		}
		
		if (mapa[nuevaFila][nuevaColumna] == superior ) {
			aumentarDificultad();
		}
		
		if (mapa[nuevaFila][nuevaColumna] == inferior ) {
			bajarDificultad();
		}
		
		filaJugador = nuevaFila;
		columnaJugador = nuevaColumna;
		mapa[filaJugador][columnaJugador] = personaje;	
		puntos++; //Cada vez que se mueva se suma 1 punto (simulando puntuacion por tiempo vivo)
		
		
		puntos();
	}
	
	//Aumentar dificultad
	public void aumentarDificultad() {
		
		alto = alto - 1;
		ancho = ancho - 1;
		
		
		if (alto < 7) {
			alto = 7;
		}
		
		if (ancho < 7) {
			ancho = 7;
		}
		
		mapa[filaJugador][columnaJugador] = vacio ;
		
		
		ajusteMapa();		
		
	}
	
	//Bajar dificultad
	public void bajarDificultad() {
		
		
		alto = alto + 1;
		ancho = ancho + 1;
		
		if (alto > 35) {
			alto = 35;
		}
		
		if (ancho > 35) {
			ancho = 35;
		}
		
		
		mapa[filaJugador][columnaJugador] = vacio ;
		
		ajusteMapa();		
		
	}
		

	//Reajustar despues de cambiar de nivel
	public void ajusteMapa() {
	     do{
	        mapa = new char[alto][ancho];
	        
	        recalcularVariables();
	        generarBordes();
	        generarParedes();
	        generarChetadas();
	        generarCaja();
	        generarFantasmas();
	        
	    }while (!verificarGalletasAlcanzables());

	}
	
	public void recalcularVariables() {
		centroFila = (alto) / 2;
		centroColumna = (ancho) /2;
		
		
		posicionesFantasmas = new int [][] {
				{centroFila, centroColumna},
				{centroFila - 1, centroColumna},
				{centroFila, centroColumna - 1},
				{centroFila, centroFila + 1}
		};
	}
	
	//Puntuación
 	public void puntos() {
		if(mapa[filaJugador][columnaJugador] == '*') {
			puntos = puntos + 10;
			
			mapa[filaJugador][columnaJugador] = ' ';
		}
		
		System.out.println(puntos);
	}
	
	public void eliminarFantasma(int fila, int columna) {
		
		mapa[fila][columna] = personajeChetado;
		puntos += 100;
		puntos();
		
		for (int i = 0; i < fantasma.length; i++) {
			if (filaFantasma[i] == fila && columnaFantasma[i] == columna) {
				filaFantasma[i] = posicionesFantasmas[i][0];
				columnaFantasma[i] = posicionesFantasmas[i][1];
				
				mapa[filaFantasma[i]][columnaFantasma[i]] = fantasma[i];
				break;
			}
		}
	}
	
		// Generar caja fantasmas
	public void generarCaja() {
		for(int i = 0; i < alto; i++) {
			for(int j = 0; j < ancho; j++) {
				
				if (i >= (centroFila - 1) && i <= (centroFila + 1) 
					&& j >= (centroColumna - 2)	 && j <= (centroColumna + 2)){
					
					mapa[i][j] = limite;
				} 
			}
		}
	}

		// Generar fantasmas
	public void generarFantasmas() {
		for(int i = 0; i < fantasma.length; i++) {
				
			int fila = posicionesFantasmas[i][0];
			int columna = posicionesFantasmas[i][1];
			
			mapa[fila][columna] = fantasma[i];
			
			filaFantasma[i] = fila;
			columnaFantasma[i] = columna;
			
		}		
	}
	
	public void movimientoFantasma() {
        for (int i = 0; i < fantasma.length; i++) {
            int nuevaFila = filaFantasma[i];
            int nuevaColumna = columnaFantasma[i];
            int direccion;
            boolean movimientoValido;
            char estadoAnterior = ' ';

            int intentos = 0;
            do {
                direccion = ran.nextInt(4);
                movimientoValido = true;
                int tempFila = filaFantasma[i];
                int tempColumna = columnaFantasma[i];
                
                switch (direccion) {
                    case 0 -> tempFila--; // Arriba
                    case 1 -> tempFila++; // Abajo
                    case 2 -> tempColumna--; // Izquierda
                    case 3 -> tempColumna++; // Derecha
                }
                
                if (tempFila <= 0 || tempFila >= alto - 1 || tempColumna <= 0 || tempColumna >= ancho - 1 ||
                    mapa[tempFila][tempColumna] == limite || mapa[tempFila][tempColumna] == pared ||
                    esPosicionFantasma(tempFila, tempColumna)) {
                    movimientoValido = false;
                }
                
                if (movimientoValido) {
                	estadoAnterior = mapa[tempFila][tempColumna];
                    nuevaFila = tempFila;
                    nuevaColumna = tempColumna;
                    break;
                }
                intentos++;
            } while (intentos < 10);
            
            if (movimientoValido) {
            	
                 mapa[filaFantasma[i]][columnaFantasma[i]] = ' ';
            	
            	if (estadoAnterior == galletas) {
            		mapa[filaFantasma[i]][columnaFantasma[i]] = '.';
            		
                } else if (estadoAnterior == superior) {
                	mapa[filaFantasma[i]][columnaFantasma[i]] = 'U';
                	
                } else if (estadoAnterior == inferior) {
                	mapa[filaFantasma[i]][columnaFantasma[i]] = 'W';
                	
                }else if (estadoAnterior == chetadas) {
                	mapa[filaFantasma[i]][columnaFantasma[i]] = '*';
                	
                }else {
                	mapa[filaFantasma[i]][columnaFantasma[i]] = ' ';
                }
                 
            	filaFantasma[i] = nuevaFila;
               	columnaFantasma[i] = nuevaColumna;
                mapa[filaFantasma[i]][columnaFantasma[i]] = 'F';
                
                
                if (filaFantasma[i] == filaJugador && columnaFantasma[i] == columnaJugador) {
                    
                   if (!modoCazador) {
                	   seguirJugando = false;
                	   return;
                	   
                   }else {
                	   eliminarFantasma(filaFantasma[i], columnaFantasma[i]);
                   }
                }
                
                
            }
		}
	}

	private boolean esPosicionFantasma(int fila, int columna) {
		for (int i = 0; i < filaFantasma.length; i++) {
			if (filaFantasma[i] == fila && columnaFantasma[i] == columna) {
				return true;
			}
		}
		return false;
	}

	public boolean buscarCamino(int inicioFila, int inicioColumna, int objetivoFila, int objetivoColumna) {
		
        boolean[][] visitado = new boolean[alto][ancho];
        Queue<int[]> cola = new LinkedList<>();
        int[][] direccion = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Arriba, Abajo, Izquierda, Derecha

        // Añadir la posición inicial a la cola y marcarla como visitada
        cola.add(new int[]{inicioFila, inicioColumna});
        visitado[inicioFila][inicioColumna] = true;

        while (!cola.isEmpty()) {
        	
            int[] posicionActual = cola.poll();
            int filaActual = posicionActual[0];
            int columnaActual = posicionActual[1];

            // Si se ha llegado al objetivo, se encontró un camino
            if (filaActual == objetivoFila && columnaActual == objetivoColumna) {
                return true; // Se encontró un camino
            }

            // Explorar las direcciones posibles
            for (int[] dir : direccion) {
                int nuevaFila = filaActual + dir[0];
                int nuevaColumna = columnaActual + dir[1];

                // Verificar si la nueva posición es válida y no ha sido visitada
                if (nuevaFila > 0 && nuevaFila < alto - 1 && nuevaColumna > 0 && nuevaColumna < ancho - 1 &&
                    mapa[nuevaFila][nuevaColumna] != limite && mapa[nuevaFila][nuevaColumna] != pared &&
                    !visitado[nuevaFila][nuevaColumna]) {
                	
                    cola.add(new int[]{nuevaFila, nuevaColumna}); // Añadir nueva posición a la cola
                    
                    visitado[nuevaFila][nuevaColumna] = true; // Marcar como visitada
                }
            }
        }
        return false; // No se encontró un camino
    }
    
    // Método para llegar a todas las galletas
	public boolean verificarGalletasAlcanzables() {
        for (int fila = 0; fila < alto; fila++) {
            for (int columna = 0; columna < ancho; columna++) {
            	
                if (mapa[fila][columna] == '.') { // Si hay una galleta
                    boolean alcanzable = buscarCamino(filaJugador, columnaJugador, fila, columna);
                    
                    if (!alcanzable) {
                        return false; // Si alguna galleta no es alcanzable, devuelve false
                    }
                }
            }
        }
        return true; // Todas las galletas son alcanzables
    }
	
	// Finalizar partida por puntos
	private boolean hayPuntos() {
		
		for (char[] mapa1 : mapa) {
			for (int j = 0; j < mapa1.length; j++) {
				if (mapa1[j] == galletas) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	// Metodo imprimir mapa
	public void imprimirMapa() {
		for(int i = 0; i < alto; i++) {
			for(int j = 0; j < ancho; j++) {
				System.out.print(mapa[i][j]);
			}
			
			System.out.println("");
		}
		
	}

	
	public void jugar() {
		while (seguirJugando) {
			
			imprimirMapa();
			
			movimientoJugador();
			
			hayPuntos();
			
			niveles();
			
			movimientoFantasma();
			
			if (!hayPuntos()) {
				seguirJugando = false;
				
				for (int i = 0; i < (alto + 5); i++) {
					System.out.println(" ");
				}
				
				System.out.println("Has ganado!!");
				System.out.println("Puntuación obtenida " + puntos );
				break;
			}
			
			if (seguirJugando == false) {
				
				for (int i = 0; i < (alto + 5); i++) {
					System.out.println(" ");
				}
				
				System.out.println("¡Has sido atrapado por un fantasma!");
				System.out.println("GAME OVER");
				System.out.println("Puntuación obtenida " + puntos );
			}
		}
	}
}

// Horas= 40