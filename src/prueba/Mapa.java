package prueba;

import java.util.Random;

public class Mapa {
    private int alto = 15;
    private int ancho = 15;
    char[][] mapa;
    private final char limite = '#';
    private final char pared = '|';
    private final char galletas = '.';
    private int centroFila = alto / 2;
    int centroColumna = ancho / 2;  
 
    private Personaje personaje;
    private Fantasmas fantasmas;
    private Funcionalidades funcionalidades;
    private Generaciones generaciones;
    private Comprobaciones comprobaciones;
    
    Random ran = new Random();
 
    public Mapa(int alto, int ancho) {
        this.alto = alto;
        this.ancho = ancho;
        this.generaciones = new Generaciones(this);
        this.personaje = new Personaje(this);
        this.mapa = new char[alto][ancho];
        generarMapa();
        
    }
 
    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }
 
    public void setFantasmas(Fantasmas fantasmas) {
        this.fantasmas = fantasmas;
    }
    public void setGeneraciones(Generaciones generaciones) {
    	this.generaciones = new Generaciones(this);
    }
    
   
    private void generarMapa() {
    	do {
			generarBordes();
			generarParedes();
			generaciones.generarChetadas();
			personaje.generarPersonaje();
			generarCaja();
			fantasmas.generarFantasmas();
			
		} while (!funcionalidades.verificarGalletasAlcanzables());
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
 					
 	public void ajusteMapa() {
 		     do{
 		        mapa = new char[alto][ancho];
 		        
 		        comprobaciones.recalcularVariables();
 		        generarBordes();
 		        generarParedes();
 		        generaciones.generarChetadas();
 		        generarCaja();
 		        fantasmas.generarFantasmas();
 		        
 		    }while (!funcionalidades.verificarGalletasAlcanzables());

 		}
 
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
 		
    public boolean hayPuntos() {
        for (char[] fila : mapa) {
            for (char celda : fila) {
                if (celda == galletas) {
                    return true;
                }
            }
        }
        return false;
    }
 
    public void imprimirMapa() {
        for (char[] fila : mapa) {
            System.out.println(fila);
        }
    }

    
    
    
    
    public char[][] getMapa() {
        return mapa;
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
}
 