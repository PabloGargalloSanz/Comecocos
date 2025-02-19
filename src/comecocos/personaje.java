package comecocos;

import java.util.Random;
import java.util.Scanner;

public class Personaje {
	private int filaJugador;
	private int columnaJugador;
	private char personaje = 'C';
	private char personajeChetado = 'X';
	private boolean modoCazador = false;
	private int tiempoCazador = 0;
	private int puntos = 0;
	
	private Mapa mapaObj;
	private Fantasmas fantasmasObj;
	private char[][] mapa;
	
	Random ran = new Random();
	Scanner sc = new Scanner(System.in);
	
	//Constructor
	public Personaje(Mapa mapaObj, Fantasmas fantasmasObj) {
		this.mapaObj = mapaObj;
		this.fantasmasObj = fantasmasObj;
		this.mapa = mapaObj.getMapa();
	}
	
	public void setFantasmasObj(Fantasmas fantasmasObj) {
        this.fantasmasObj = fantasmasObj;
    }
	
	public int getFilaJugador() {
        return filaJugador;
    }
	
	public int getColumnaJugador() {
        return columnaJugador;
    }
	
	public boolean getModoCazador() {
        return modoCazador;
    }
	
	public char getPersonajeChetado() {
        return personajeChetado;
    }
	
	public int getGenerarPersonaje() {
      return  mapa[filaJugador][columnaJugador] = personaje;		
    }
	
	public void generarPersonaje() {
		
		int alto = mapaObj.getAlto();
	    int ancho = mapaObj.getAncho();
	    int centroFila = alto / 2;
	    int centroColumna = ancho / 2;
	    boolean bloqueado;
	    
		do {
		
		// Posicion jugador aleatorio
		filaJugador = (ran.nextInt(alto - 2)) + 1 ;  // Random entre el valor de las filas 
		columnaJugador = (ran.nextInt(ancho - 2)) + 1;
		bloqueado = (filaJugador >= (centroFila - 1) && filaJugador <= (centroFila + 1) 
				&& columnaJugador >= (centroColumna - 2) && columnaJugador <= (centroColumna + 2));
			
		// Para que el personaje no se genere donde ira la caja de los fantasmas y los fantasmas
		} while(bloqueado || mapa[filaJugador][columnaJugador] != ' ');
					
		mapa[filaJugador][columnaJugador] = personaje;	
	}
	
	
	
	public void movimientoJugador() {
		
		char direccion = sc.next().charAt(0);
		int nuevaFila = filaJugador;
		int nuevaColumna = columnaJugador;
		
		
		mapa[filaJugador][columnaJugador] = ' ' ;
		
		switch (direccion) {
			
			case 'w' -> nuevaFila--;
			case 's' -> nuevaFila++;
			case 'a' -> nuevaColumna--;
			case 'd' -> nuevaColumna++;
		}
		
			if (mapa[nuevaFila][nuevaColumna] == mapaObj.getLimite() && 
				mapa[nuevaFila][nuevaColumna] == mapaObj.getPared()) {
				
				nuevaFila = filaJugador;
				nuevaColumna = columnaJugador;
			}
		
		if (mapa[nuevaFila][nuevaColumna] == mapaObj.getChetadas()) {
			modoCazador = true;
			tiempoCazador = 10;
			personaje = personajeChetado;
			
		}
		
		for (char F : fantasmasObj.getFantasma()) {
			if (mapa[nuevaFila][nuevaColumna] == F) {
				
				if (modoCazador) {
					fantasmasObj.eliminarFantasma(nuevaFila, nuevaColumna);
					
				} else {
					mapaObj.seguirJugando = false;
					return;
				}
			}
		}
	
	
		if(modoCazador) {
			tiempoCazador--;
			if (tiempoCazador == 1) { //Cuando queda un solo movimiento cambia
				personaje = 'x';
			}
			if (tiempoCazador <= 0) {
				modoCazador = false;
				personaje = 'C';
			}
		}
		
		//Si come la U
		if (mapa[nuevaFila][nuevaColumna] == mapaObj.getSuperior()) {
			mapaObj.aumentarDificultad();
		}
		//Si come la W
		if (mapa[nuevaFila][nuevaColumna] == mapaObj.getSuperior() ) {
			mapaObj.bajarDificultad();
		}
		
		filaJugador = nuevaFila;
		columnaJugador = nuevaColumna;
		mapa[filaJugador][columnaJugador] = personaje;	
		puntos++; //Cada vez que se mueva se suma 1 punto (simulando puntuacion por tiempo vivo)
		
		
		mapaObj.puntos();
	}
}
