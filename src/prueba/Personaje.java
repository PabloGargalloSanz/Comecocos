package prueba;

import java.util.Random;
import java.util.Scanner;

public class Personaje {
	private final Mapa mapa;
	private Funcionalidades funcionalidades;
	
	
    int filaJugador;
    int columnaJugador;
    boolean bloqueado;
    
    
    private int fila;
    private int columna;
    private char personaje = 'C';
    
    
    private int puntos = 0;
    private char direccion;
    
    
    Random ran = new Random();
    Scanner sc = new Scanner(System.in);
 
    public Personaje(Mapa mapa) {
        this.mapa = mapa;
        generarPersonaje();
    }
 
    public void generarPersonaje() {
    	int alto = mapa.getAlto();
        int ancho = mapa.getAncho();
        int centroFila = alto / 2;
        int centroColumna = ancho / 2;
        bloqueado = (filaJugador >= (centroFila - 1) && filaJugador <= (centroFila + 1) 
    			&& columnaJugador >= (centroColumna - 2) && columnaJugador <= (centroColumna + 2));
    	
	    
		do {
		
		// Posicion jugador aleatorio
		filaJugador = (ran.nextInt(alto - 2)) + 1 ;  // Random entre el valor de las filas 
		columnaJugador = (ran.nextInt(ancho - 2)) + 1;
			
		// Para que el personaje no se genere donde ira la caja de los fantasmas y los fantasmas
		} while(bloqueado || mapa.getMapa()[filaJugador][columnaJugador] != ' ');
					
		mapa.getMapa()[filaJugador][columnaJugador] = personaje;	
	}
 
    public void movimientoPersonaje(char direccion) {
    	direccion = sc.next().charAt(0);
        int nuevaFila = fila;
        int nuevaColumna = columna;
 
        switch (direccion) {
            case 'w' -> nuevaFila--;
            case 's' -> nuevaFila++;
            case 'a' -> nuevaColumna--;
            case 'd' -> nuevaColumna++;
        }
 
        if (mapa.getMapa()[nuevaFila][nuevaColumna] != mapa.getLimite() &&
            mapa.getMapa()[nuevaFila][nuevaColumna] != mapa.getPared()) {
 
            mapa.getMapa()[fila][columna] = '.';
            fila = nuevaFila;
            columna = nuevaColumna;
            
            mapa.getMapa()[fila][columna] = personaje;
            puntos++;
            funcionalidades.puntuacion();
        }
    }
 
   
    public int getFilaJugador() {
        return filaJugador;
    }
    public int getColumnaJugador() {
        return columnaJugador;
    }
    public int getNuevaFilaJugador() {
        return filaJugador;
    }
    public int getNuevaColumnaJugador() {
        return columnaJugador;
    }
    public int getPersonaje() {
        return personaje;
    }
    
    
    
}