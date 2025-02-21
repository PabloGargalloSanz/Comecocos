package prueba;

public class Comprobaciones {
	private final Mapa mapa;
	private Personaje personaje;
	private Fantasmas fantasmas;
	private Juego juego;
   	
	int centroFila;
	int centroColumna;
	char personajeChetado = 'X';
	private boolean modoCazador = false;
	int tiempoCazador = 0;
	
	
	
	public Comprobaciones(Mapa mapa) {
        this.mapa = mapa;
        
    }
	
	public void eliminarFantasma(int fila, int columna) {
		
		mapa.getMapa()[fila][columna] = personajeChetado;
		//mapa.sumarPuntos();
		
		
		for (int i = 0; i < 3; i++) { // el 3 seria fantasmas.lenght pero no se como relacionarlo
			if (fantasmas.getFilaFantasma()[i] == fila && fantasmas.getColumnaFantasmas()[i] == columna) {
				fantasmas.getFilaFantasma()[i] = fantasmas.getPosicionesFantasmas()[i][0];
				fantasmas.getColumnaFantasmas()[i] = fantasmas.getPosicionesFantasmas()[i][1];
				
				mapa.getMapa()[fantasmas.getFilaFantasma()[i]][fantasmas.getColumnaFantasmas()[i]] = fantasmas.getFantasma()[i];
				break;
			}
		}
	}

	
	public void comprobarPosiciones(int fila, int columna) {
		for (int i = 0; i < 3; i++) {
			if (fantasmas.getFilaFantasma()[i] == personaje.getFilaJugador() &&
				fantasmas.getColumnaFantasmas()[i] ==  personaje.getColumnaJugador()) {
		            
		           if (!modoCazador) {
		        	  // juego.seguirJugando = false;
		        	   return;
		        	   
		           }else {
		        	   eliminarFantasma(fantasmas.getFilaFantasma()[i], fantasmas.getColumnaFantasmas()[i]);
		           }
			}
		}
	}

	public void recalcularVariables() {
		centroFila = mapa.getAlto() / 2;
        centroColumna = mapa.getAncho() / 2;
    }
	
	
	public void personajeChetado() {
		
		if (mapa.getMapa()[personaje.getNuevaFilaJugador()][personaje.getNuevaColumnaJugador()] == '*') {
			modoCazador = true;
			tiempoCazador = 10;
			//personaje.getPersonaje() = personajeChetado;
			
		}
	}
	
	public boolean igualdadPosicion() {
		
		for (char F : fantasmas.getFantasma()) {
			if (mapa.getMapa()[personaje.getNuevaFilaJugador()][personaje.getNuevaColumnaJugador()] == F) {
				
				if (modoCazador) {
					eliminarFantasma(personaje.getNuevaFilaJugador(),personaje.getNuevaColumnaJugador());
					
				} else {
					//mapa.seguirJugando = false;
					return false;
				}
				
			}
		}
		return true;
	}
}
