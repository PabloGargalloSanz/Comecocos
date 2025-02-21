package prueba;

import java.util.Random;

public class Generaciones {
	private final Mapa mapa;
	
	private final char superior = 'U';
    private final char inferior = 'W';
    private final char chetadas = '*';
    
    Random ran = new Random();
    
    public Generaciones(Mapa mapa) {
        this.mapa = mapa;
        generarChetadas();
    }

	// Generar galletas chetadas
	 	public void generarChetadas() {
	 	
	 		int cantidadChetadas = 0;
	 		int maxChetadas = (mapa.getAlto() + mapa.getAncho()) / 3;
	 		int centroFila = mapa.getAlto() / 2;
	         int centroColumna = mapa.getAncho() / 2;
	 		
	 		for(int i = 1; i < mapa.getAlto() - 1; i++) { 
	 			for(int j = 1; j < mapa.getAncho() - 1; j++) {
	 				
	 				do {
	 					if (i >= (centroFila - 2) && i <= (centroFila + 2) 
	 						&& j >= (centroColumna - 3)	 && j <= (centroColumna + 3) ) {
	 							
	 							continue; // no colocar paredes al rededor de la caja de fantasmas
	 					} 
	 				
	 					if (ran.nextInt(100) < 5) {
	 						mapa.getMapa()[i][j] = chetadas;
	 						cantidadChetadas++;
	 					}
	 					
	 				} while(cantidadChetadas <= maxChetadas );
	 			}
	 		}
	 	}
 	
	// Generar galletas para subir o bajar de nivel
	public void niveles() {
			boolean existeSuperior = false;
			boolean existeInferior = false;
			
			for(int i = 0; i < mapa.getAlto(); i++) {
				for(int j = 0; j < mapa.getAncho(); j++) {
					
					if(mapa.getMapa()[i][j] == superior) {
						existeSuperior = true;
					}
						
					if(mapa.getMapa()[i][j] == inferior) {
						existeInferior = true;					
					}
				}
			}
		
			if (!existeSuperior && (ran.nextInt(100) < 5)) {
				int fila ;
				int columna;
				
				do {
					fila = ran.nextInt(mapa.getAlto() -3 ) + 1; //Para que al comersela el jugador no se genere en el limite
					columna = ran.nextInt(mapa.getAncho() - 3) + 1;
					
				}while (mapa.getMapa()[fila][columna] != mapa.getGalletas());
				
				mapa.getMapa()[fila][columna] = superior;
			}
			
			if (!existeInferior && (ran.nextInt(100) < 5)) {
				int fil;
				int col;
				
				do {
					fil = ran.nextInt(mapa.getAlto() -2 ) + 1;
					col = ran.nextInt(mapa.getAncho() - 2) + 1;
					
				}while (mapa.getMapa()[fil][col] != mapa.getGalletas());
				
				mapa.getMapa()[fil][col] = inferior;
			}
				
		}
		
		
}
