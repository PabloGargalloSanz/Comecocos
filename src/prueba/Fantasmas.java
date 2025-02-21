package prueba;

import java.util.Random;

public class Fantasmas {
    private final Mapa mapa;
    private char[] fantasma = {'F', 'F', 'F', 'F'};
    int [][] posicionesFantasmas;
    private int[] filaFantasma = new int[4];
	private int[] columnaFantasma = new int[4];
    private final Random ran = new Random();
 
    public Fantasmas(Mapa mapa) {
        this.mapa = mapa;
        inicializarPosiciones();
    }
 
    public void generarFantasmas() {
		for(int i = 0; i < fantasma.length; i++) {
				
			int fila = posicionesFantasmas[i][0];
			int columna = posicionesFantasmas[i][1];
			
			mapa.getMapa()[fila][columna] = fantasma[i];
			filaFantasma[i] = fila;
			columnaFantasma[i] = columna;
			
		}		
	}
    
    private void inicializarPosiciones() {
    	int centroFila = mapa.getAlto() / 2;
		int centroColumna = mapa.getAncho() / 2;
		this.posicionesFantasmas = new int[][] {
				{centroFila, centroColumna},
				{centroFila - 1, centroColumna},
				{centroFila, centroColumna - 1},
				{centroFila, centroFila + 1}
		};
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
                
                if (tempFila <= 0 || tempFila >= mapa.getAlto() - 1 || tempColumna <= 0 || tempColumna >= mapa.getAncho() - 1 || 
                	mapa.getMapa()[tempFila][tempColumna] == mapa.getLimite() || 
                	mapa.getMapa()[tempFila][tempColumna] == mapa.getPared() ||
                	esPosicionFantasma(tempFila, tempColumna)) {
                	
                    movimientoValido = false;
                }
                
                if (movimientoValido) {
                	estadoAnterior = mapa.getMapa()[tempFila][tempColumna];
                    nuevaFila = tempFila;
                    nuevaColumna = tempColumna;
                    break;
                }
                intentos++;
                
            } while (intentos < 10);
            
            filaFantasma[i] = nuevaFila;
           	columnaFantasma[i] = nuevaColumna;
            mapa.getMapa()[filaFantasma[i]][columnaFantasma[i]] = 'F';
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


    
    public int[] getFilaFantasma() {
    	return filaFantasma;
    }
    public int[] getColumnaFantasmas() {
    	return columnaFantasma;
    }
    public int[][] getPosicionesFantasmas() {
    	return posicionesFantasmas;
    }
    
    public char[] getFantasma() {
    	return fantasma;
    }
    
}
 
