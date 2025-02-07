package comecocos;



public class juego {
	//Main
		public static void main(String[]args) {
			
			mapa nuevoMapa = new mapa();
			
	        
	        while (true) {
	        	
	            nuevoMapa.imprimirMapa();
	            
	            nuevoMapa.movimientoJugador();
	        }
	    }
}
