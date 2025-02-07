package comecocos;

import java.util.Random;
import java.util.Scanner;

public class mapa {
	
	// Variable de la matriz
	int alto = 25;
	int ancho = 25;
	char limite = '#';
	char pared = '|';
	char personaje = 'C';
	char personajeChetado = 'X' ;
	char fantasma = 'F';
	char puntos = '*';
	char comerFantasmas = '+';
	char vacio = ' ';
	
	// Variable calculo centro
	int centroFila = (alto) / 2;
	int centroColumna = (ancho) /2;
	
	// Variable posicion personajes
	int filaJugador;
	int columnaJugador;
	
	// Caja fantasmas para jugador
	boolean bloqueado = (filaJugador >= (centroFila - 1) && filaJugador <= (centroFila + 1) 
			&& columnaJugador >= (centroColumna - 2) && columnaJugador <= (centroColumna + 2));
	
	// Variables movimiento
	
	char direccion;
	
	
	/*
	char superior = 'U'; //Para subir a un nivel mas exigente (uppper)
	char inferior = 'L'; //Para bajar a un nivel mas falic (lower)
	*/

	Random ran = new Random();
	Scanner sc = new Scanner(System.in);
	
	
	// Matriz del mapa bidemensional
	char[][] mapa; 
	
	//Constructor
	public mapa(){
		mapa= new char[alto][ancho];
		generarMapa();
	}
	
	
	// Metodo Relleno del mapa
	
	public void generarMapa() {
	
		generarBordes();
		generarParedes();
		generarJugador();
		generarCaja();
		generarFantasmas();
	}
		
	// Generar Bordes y puntos
	public void generarBordes() {
		for(int i = 0; i < alto; i++) {
			for(int j = 0; j < ancho; j++) {
				
				if ( i == 0 || j == 0 || i == alto -1 || j == ancho -1 ) {
					mapa[i][j] = limite ;
				
				
				}else {
					mapa[i][j] = puntos;
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
		
		mapa[filaJugador][columnaJugador] = vacio ;
		
		switch (direccion) {
			case 'w' -> {
				
				if (filaJugador > 1 && mapa[filaJugador - 1 ][columnaJugador] != limite ) {
					filaJugador--;
				}
			}
			
			case 's' -> {
				
				if (filaJugador < alto - 2 && mapa[filaJugador + 1 ][columnaJugador] != limite ) {
					filaJugador++;
				}
			}
			
			case 'a' -> {
				
				if (columnaJugador > 1 && mapa[filaJugador][columnaJugador - 1 ] != limite ) {
					columnaJugador--;
				}
			}
			
			case 'd' -> {
				
				if (columnaJugador < ancho - 2 && mapa[filaJugador][columnaJugador + 1 ] != limite ) {
					columnaJugador++;
				}
			}
			
			
		}
		
		mapa[filaJugador][columnaJugador] = personaje;	
		
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
		for(int i = 0; i < alto; i++) {
			for(int j = 0; j < ancho; j++) {
					
				//mapa[(mapa[i].length -1) / 2][(mapa[j].length -1) / 2] = fantasma; // En el centro del mapa generado
				
				mapa[centroFila][centroColumna] = fantasma; // Fantasma del centro
				mapa[centroFila -1 ][centroColumna] = fantasma; // Fantasma del centro pero por 1 fila por encima
				mapa[centroFila][centroColumna - 1] = fantasma; // Fantasma del centro izquierda
				mapa[centroFila][centroColumna + 1] = fantasma; // Fantasma del centro derecha
				
			}
		}		
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
		
		
		
	}
	
}

// Horas= 9