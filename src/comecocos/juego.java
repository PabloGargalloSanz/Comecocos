package comecocos;



public class Juego {
	//Main
		public static void main(String[]args) {
			
			Mapa nuevoMapa = new Mapa();
			Fantasmas fantasmas = new Fantasmas(nuevoMapa, null);
			Personaje personaje = new Personaje(nuevoMapa, fantasmas);
			
			
			nuevoMapa.generarMapa();
			
	        nuevoMapa.jugar();
	      
	    }
}
