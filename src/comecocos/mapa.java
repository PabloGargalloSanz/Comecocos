package comecocos;

import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class Mapa {
	
	private Fantasmas fantasmasObj;
	private Personaje personajeObj;
	
	
	// Variable de la matriz
	private int alto = 15;
	private int ancho = 15;
	private char limite = '#';
	private char pared = '|';
	private char galletas = '.';
	private char chetadas = '*'; 
	private char vacio = ' ';
	
	//Variables niveles
	private char superior = 'U'; //Para subir a un nivel mas exigente (uppper)
	private char inferior = 'W'; //Para bajar a un nivel mas facil (lower)
	
	// Variable calculo centro
	private int centroFila;
	private int centroColumna;
	
	//Variable jugador
	private int filaJugador;
	private int columnaJugador;
	
	// Variables puntuacion 
	private int puntos = 0;
	
	
	// Variable seguir jugando
	public boolean seguirJugando = true;
	
	// Matriz del mapa bidemensional
	private char[][] mapa; 
	
	Random ran = new Random();
	
	//Constructor
	public Mapa(){
		this.mapa = new char[alto][ancho];
		this.personajeObj = personajeObj;
		this.fantasmasObj = fantasmasObj;
		recalcularVariables();
		generarMapa();
	}
	
	private void recalcularVariables() {
		centroFila = alto / 2;
        centroColumna = ancho / 2;
    }
	
	public char[][] getMapa() {
		fantasmasObj.getPosicionesFantasmas();
        return mapa;
    }
	
	public void setPersonajeObj(Personaje personaje) {
        this.personajeObj = personaje; 
    }

	public boolean getSeguirJugando(){
        return seguirJugando;
    }
	
    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public char getLimite() {
        return limite;
    }

    public char getPared() {
        return pared;
    }
    public char getGalletas() {
        return galletas;
    }

    public char getChetadas() {
        return chetadas;
    }

    public char getSuperior() {
        return superior;
    }

    public char getInferior() {
        return inferior;
    }

	
	// Metodo Relleno del mapa
	public void generarMapa() {
		
		do {
			generarBordes();
			generarParedes();
			generarChetadas();
			personajeObj.generarPersonaje();
			generarCaja();
			fantasmasObj.generarFantasmas();
			
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
		
		// No aumente el tamño mas de 25
		if (alto > 25) {
			alto = 25;
		}
		
		if (ancho > 25) {
			ancho = 25;
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
	        fantasmasObj.generarFantasmas();
	        
	    }while (!verificarGalletasAlcanzables());

	}
	
	
	//Puntuación
 	public void puntos() {
		if(mapa[filaJugador][columnaJugador] == '*') {
			puntos = puntos + 10;
			
			mapa[filaJugador][columnaJugador] = ' ';
		}
		
		System.out.println(puntos);
	}
	
	public void sumarPuntos() {
		puntos = puntos + 100;
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
			
			personajeObj.movimientoJugador();
			
			hayPuntos();
			
			niveles();
			
			fantasmasObj.movimientoFantasma();
			
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