package comecocos;

import java.util.Random;

public class Fantasmas {
	
	private Mapa mapaObj;
	private Personaje personajeObj;
	private char[][] mapa;
	
	private int alto;
	private int ancho;
		
	private char [] fantasma = { 'F', 'F', 'F', 'F'};
	private int[] filaFantasma = new int[4];
	private int[] columnaFantasma = new int[4];
	private int [][] posicionesFantasmas;
	
	
	
	Random ran = new Random();
	
	//Constructor
	public Fantasmas(Mapa mapaObj, Personaje personajeObj) {
		this.mapaObj = mapaObj;
		this.mapa = mapaObj.getMapa();
		this.personajeObj = personajeObj;
		this.alto = mapaObj.getAlto();
		this.ancho = mapaObj.getAncho();
		
		inicializarPosicionesFantasmas();
	}
		
	public int[][] getPosicionesFantasmas() {
        return posicionesFantasmas;
    }
	
	public char[] getFantasma() {
        return fantasma;
    }	
		
	private void inicializarPosicionesFantasmas() {
		int centroFila = alto / 2;
		int centroColumna = ancho / 2;
		this.posicionesFantasmas = new int[][] {
				{centroFila, centroColumna},
				{centroFila - 1, centroColumna},
				{centroFila, centroColumna - 1},
				{centroFila, centroFila + 1}
		};
	}
		
	
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
                	mapa[tempFila][tempColumna] == mapaObj.getLimite() || 
                	mapa[tempFila][tempColumna] == mapaObj.getPared() ||
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
            	
            	if (estadoAnterior == mapaObj.getGalletas()) {
            		mapa[filaFantasma[i]][columnaFantasma[i]] = '.';
            		
                } else if (estadoAnterior == mapaObj.getSuperior()) {
                	mapa[filaFantasma[i]][columnaFantasma[i]] = 'U';
                	
                } else if (estadoAnterior == mapaObj.getInferior()) {
                	mapa[filaFantasma[i]][columnaFantasma[i]] = 'W';
                	
                }else if (estadoAnterior == mapaObj.getChetadas()) {
                	mapa[filaFantasma[i]][columnaFantasma[i]] = '*';
                	
                }else {
                	mapa[filaFantasma[i]][columnaFantasma[i]] = ' ';
                }
                 
            	filaFantasma[i] = nuevaFila;
               	columnaFantasma[i] = nuevaColumna;
                mapa[filaFantasma[i]][columnaFantasma[i]] = 'F';
                
                
                if (filaFantasma[i] == personajeObj.getFilaJugador() &&
                	columnaFantasma[i] ==  personajeObj.getColumnaJugador()) {
                    
                   if (!personajeObj.getModoCazador()) {
                	   mapaObj.seguirJugando = false;
                	   return;
                	   
                   }else {
                	   eliminarFantasma(filaFantasma[i], columnaFantasma[i]);
                   }
                }
                
            }
		}
	}
	
	public void eliminarFantasma(int fila, int columna) {
		
		mapa[fila][columna] = personajeObj.getPersonajeChetado();
		mapaObj.sumarPuntos();
		
		
		for (int i = 0; i < fantasma.length; i++) {
			if (filaFantasma[i] == fila && columnaFantasma[i] == columna) {
				filaFantasma[i] = posicionesFantasmas[i][0];
				columnaFantasma[i] = posicionesFantasmas[i][1];
				
				mapa[filaFantasma[i]][columnaFantasma[i]] = fantasma[i];
				break;
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

}
