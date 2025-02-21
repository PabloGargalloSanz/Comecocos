package prueba;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Funcionalidades {
	private final Mapa mapa;
	private Personaje personaje;
	
	
	
    
    Random ran = new Random();
	
    public Funcionalidades(Mapa mapa) {
        this.mapa = mapa;
        
        verificarGalletasAlcanzables();
    }
    
 
	//Aumentar dificultad
	public void aumentarDificultad() {
		int alto = mapa.getAlto();
		int ancho = mapa.getAncho();
		
		
		alto = alto - 1;
		ancho = ancho - 1;
		
		if (alto < 7) {
			alto = 7;
		}
		
		if (ancho < 7) {
			ancho = 7;
		}
		
		mapa.getMapa()[personaje.getFilaJugador()][personaje.getColumnaJugador()] = ' ' ;
		
		mapa.ajusteMapa();		
		
	}
	
	//Bajar dificultad
	public void bajarDificultad() {
		int alto = mapa.getAlto();
		int ancho = mapa.getAncho();	
			
		alto = alto + 1;
		ancho = ancho + 1;
		
		// No aumente el tamño mas de 25
		if (alto > 25) {
			alto = 25;
		}
		
		if (ancho > 25) {
			ancho = 25;
		}
		
		
		mapa.getMapa()[personaje.getFilaJugador()][personaje.getColumnaJugador()] = ' ';
		
		mapa.ajusteMapa();		
		
	}
			

	public boolean buscarCamino(int inicioFila, int inicioColumna, int objetivoFila, int objetivoColumna) {
		
        boolean[][] visitado = new boolean[mapa.getAlto()][mapa.getAncho()];
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
                if (nuevaFila > 0 && nuevaFila < mapa.getAlto() - 1 && nuevaColumna > 0 && nuevaColumna < mapa.getAncho() - 1 &&
                    mapa.getMapa()[nuevaFila][nuevaColumna] != mapa.getLimite() && mapa.getMapa()[nuevaFila][nuevaColumna] != mapa.getPared() &&
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
        for (int i = 0; i < mapa.getAlto(); i++) {
            for (int j = 0; j < mapa.getAncho(); j++) {
            	
                if (mapa.getMapa()[i][j] == '.') { // Si hay una galleta
                    boolean alcanzable = buscarCamino(personaje.getFilaJugador(), personaje.getColumnaJugador(), i, j);
                    
                    if (!alcanzable) {
                        return false; // Si alguna galleta no es alcanzable, devuelve false
                    }
                }
            }
        }
        return true; // Todas las galletas son alcanzables
    }
	
	public void puntuacion() {
		
	}
		

}
